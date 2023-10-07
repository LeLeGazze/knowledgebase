package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.entity.*;
import com.castle.fortress.admin.check.enums.CheckEnum;
import com.castle.fortress.admin.check.enums.FileEnum;
import com.castle.fortress.admin.check.es.EsCheckLine;
import com.castle.fortress.admin.check.service.CheckService;
import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
import com.castle.fortress.admin.check.utils.*;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static com.castle.fortress.admin.check.service.impl.FileComparisonCheckServiceImpl.TaskCalculationDocumentGeneration;

@Slf4j
@Service("duplicateCheck")
public class DuplicateCheckServiceImpl implements CheckService {
    @Autowired
    private ConfigOssService ossService;

    @Autowired
    private FileReadFactory fileReadFactory;

    @Autowired
    private EsSearchService esSearchService;

    @Value("${pdf.html.toll}")
    private String TOPDFTOOL;
    @Autowired
    private KbDuplicateCheckService kbDuplicateCheckService;

    @Value("${pdf.url}")
    private String PDFURL;
    @Override
    @Async
    public void taskProcessing(KbDuplicateCheckDto kbDuplicateCheckDto, Long userId) {
        KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto, KbDuplicateCheckEntity.class);
        kbDuplicateCheckEntity.setUploadPath(JSONObject.toJSONString(kbDuplicateCheckDto.getUploadPath()));
        kbDuplicateCheckEntity.setCreateUser(userId);
        kbDuplicateCheckEntity.setEstimatedEndTime("1-10分钟");
        if (kbDuplicateCheckService.save(kbDuplicateCheckEntity)) {
            try {
                // 修改文件状态为处理中
                kbDuplicateCheckEntity.setStatus(2);
                kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
                // 获取文件路径前缀
                String filePathPrefix = ossService.getFilePathPrefix();
                // 文件内容读取
                JSONObject fileJson = JSONObject.parseObject(kbDuplicateCheckEntity.getUploadPath());
                String fileName = fileJson.getString("name");
                FileEnum fileEnum = FileEnum.typeFromFileName(fileName);
                String instanceName = fileEnum.getInstanceName();
                FileReadService fileReadService = fileReadFactory.get(instanceName);
                HashMap<MapKey, Object> path = fileReadService.fileDataHandle(filePathPrefix + fileJson.getString("path"), kbDuplicateCheckEntity.getReadDataLength());
                RestData restData = new RestData();
                restData.setId(kbDuplicateCheckEntity.getId());
                restData.setFamous(kbDuplicateCheckEntity.getTitle());
                restData.setAuthorName(kbDuplicateCheckEntity.getAuthor());
                restData.setDetectionType(kbDuplicateCheckEntity.getType());
                //创建一个线程池
                ExecutorService executorService = Executors.newFixedThreadPool(path.size());
                //使用的计数器
                CountDownLatch countDownLatch = new CountDownLatch(path.size());
                for (MapKey s : path.keySet()) {
                    executorService.execute(() -> {
                        try {
                            if (path.get(s) instanceof List) {
                                List<Sentence> sentenceList = (List<Sentence>) path.get(s);
                                RestDataList restDataList = new RestDataList();
                                // 拿着内容去es搜索
                                int contentLength = sentenceList.stream().mapToInt(sentence -> sentence.getText().length()).sum();
                                ExecutorService ex = Executors.newFixedThreadPool(20);
                                CountDownLatch cd = new CountDownLatch(sentenceList.size());
                                AtomicInteger countSum = new AtomicInteger();
                                restDataList.setSort(s.getSort());
                                for (Sentence sentence : sentenceList) {
                                    sentence.setParagraphName(StrUtil.isEmpty(s.getKey()) ? "測試" : s.getKey());
                                    ex.execute(() -> {
                                        try {
                                            SearchHits<EsCheckLine> searchHits = esSearchService.queryByList(sentence.getText());
                                            ArrayList<Sentence> targetList = new ArrayList<>();
                                            for (SearchHit<EsCheckLine> searchHit : searchHits) {
                                                Sentence sentence1 = new Sentence(searchHit.getContent().getFileLine());
                                                sentence1.setDate(DateUtil.formatDate(searchHit.getContent().getFileDate()));
                                                sentence1.setTitle(searchHit.getContent().getTitle());
                                                sentence1.setLength(searchHit.getContent().getLength());
                                                sentence1.setFileName(searchHit.getContent().getFileName());
                                                targetList.add(sentence1);
                                            }
                                            HashMap<String, Object> simSentenceCnt2 = AnalysisUtil.getSimSentenceCnt2(sentence, targetList, new CosSim(), kbDuplicateCheckEntity.getWeight());
                                            if (simSentenceCnt2.size() == 3) {
                                                restDataList.getMetaData().addAll((ArrayList<HistoryMetaData>) simSentenceCnt2.get("metadata"));
                                                restDataList.getOriginal().add((TmpParseData) simSentenceCnt2.get("tmpParseData"));
                                                int countSumInt = (int) simSentenceCnt2.get("countSum");
                                                countSum.addAndGet(countSumInt);
                                            }
                                        } finally {
                                            cd.countDown();
                                        }
                                    });
                                }
                                try {
                                    cd.await();
                                    ex.shutdown();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                // 设置长度
                                sentenceList.get(0).setLength(contentLength);
                                if (restDataList.getOriginal().size() > 0 && restDataList.getMetaData().size() > 0) {
                                    AnalysisUtil.extracted(sentenceList, restDataList, countSum);
                                    restData.getRestDataLists().add(restDataList);
                                }
                            }
                        } finally {
                            countDownLatch.countDown();
                        }
                    });
                }

                try {
                    countDownLatch.await();
                    executorService.shutdown();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                TaskCalculationDocumentGeneration(kbDuplicateCheckEntity, filePathPrefix, fileJson, fileName, path, restData,kbDuplicateCheckService,TOPDFTOOL,PDFURL);


            } catch (Exception e) {
                e.printStackTrace();
                kbDuplicateCheckEntity.setStatus(CheckEnum.ERROR.getCode());
                kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
            }
        }
        log.debug("处理完成了");
    }

    public static BigDecimal getPercentage(String number, String sum) {
        double tmpV = Double.parseDouble(number);
        double tmpN = Double.parseDouble(sum);
        return new BigDecimal((tmpV / tmpN) * 100).setScale(1, BigDecimal.ROUND_HALF_UP);
    }
}
