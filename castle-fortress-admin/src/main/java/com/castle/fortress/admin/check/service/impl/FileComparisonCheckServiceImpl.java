package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.entity.*;
import com.castle.fortress.admin.check.enums.CheckEnum;
import com.castle.fortress.admin.check.enums.FileEnum;
import com.castle.fortress.admin.check.es.EsTmpCheck;
import com.castle.fortress.admin.check.service.CheckService;
import com.castle.fortress.admin.check.service.FileReadService;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
import com.castle.fortress.admin.check.utils.*;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.admin.utils.FortressParseUtil;
import com.castle.fortress.admin.utils.HttpUtil;
import com.castle.fortress.admin.utils.Md5;
import com.castle.fortress.common.utils.ConvertUtil;
import com.qcloud.cos.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service("fileComparisonCheck")
public class FileComparisonCheckServiceImpl implements CheckService {
    @Autowired
    private KbDuplicateCheckService kbDuplicateCheckService;

    @Value("${pdf.html.toll}")
    private String TOPDFTOOL;
    @Autowired
    private ElasticsearchRestTemplate esTemplate;
    @Autowired
    private ConfigOssService ossService;
    @Autowired
    private FileReadFactory fileReadFactory;

    @Value("${pdf.url}")
    private String PDFURL;

    @Override
    @Async
    public void taskProcessing(KbDuplicateCheckDto kbDuplicateCheckDto, Long userId) {

        KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto, KbDuplicateCheckEntity.class);
        kbDuplicateCheckEntity.setUploadPath(JSONObject.toJSONString(kbDuplicateCheckDto.getUploadPath()));
        kbDuplicateCheckEntity.setContrastPath(JSONObject.toJSONString(kbDuplicateCheckDto.getContrastPath()));
        kbDuplicateCheckEntity.setEstimatedEndTime("1-10分钟");
        kbDuplicateCheckEntity.setCreateUser(userId);
        if (kbDuplicateCheckService.save(kbDuplicateCheckEntity)) {
            // 获取文件前缀
            String filePathPrefix = ossService.getFilePathPrefix();
            // 1. 将Contrast 数据写入到es 中
            JSONObject contrastJson = JSONObject.parseObject(kbDuplicateCheckEntity.getContrastPath());
            boolean check = saveEsCheck(filePathPrefix + contrastJson.getString("path"), kbDuplicateCheckEntity.getId(), contrastJson.getString("name"));
            if (!check) {
                log.debug("写入临时es出现了问题，{}", kbDuplicateCheckEntity.getId());
                return;
            }
            try {
                String checkMD5 = Md5.bytesToHex(Md5Utils.computeMD5Hash(Files.newInputStream(Paths.get(filePathPrefix + contrastJson.getString("path")))));
                // 修改文件状态为处理中
                kbDuplicateCheckEntity.setStatus(2);
                kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
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
                                            SearchHits<EsTmpCheck> searchHits = queryByList(sentence.getText(), checkMD5);
                                            ArrayList<Sentence> targetList = new ArrayList<>();
                                            for (SearchHit<EsTmpCheck> searchHit : searchHits) {
                                                Sentence sentence1 = new Sentence(searchHit.getContent().getFileContent());
                                                sentence1.setDate(DateUtil.formatDate(searchHit.getContent().getFileDate()));
                                                sentence1.setTitle(searchHit.getContent().getTitle());
                                                sentence1.setLength(searchHit.getContent().getLength());
                                                sentence1.setFileName(searchHit.getContent().getFileName());
                                                sentence1.setParagraphName(StrUtil.isEmpty(s.getKey()) ? "測試" : s.getKey());
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
            } finally {
                //执行es 删除
                deleteEsCheck(kbDuplicateCheckEntity.getId());
            }
        }
        log.debug("运行了.....");
    }

    public static void TaskCalculationDocumentGeneration(KbDuplicateCheckEntity kbDuplicateCheckEntity, String filePathPrefix, JSONObject fileJson, String fileName, HashMap<MapKey, Object> path, RestData restData, KbDuplicateCheckService kbDuplicateCheckService, String TOPDFTOOL, String PDFURL) {
        List<RestDataList> restDataLists = restData.getRestDataLists();
        restDataLists.sort(Comparator.comparing(RestDataList::getSort));
        int maxNum = 0;
        for (RestDataList restDataList : restDataLists) {
            DetectionResults detectionResults = new DetectionResults();
            detectionResults.setCode(restDataList.getCode());
            detectionResults.setLength(String.valueOf(restDataList.getLength()));
            detectionResults.setReplicationRatio(restDataList.getTextCopyRatio());
            detectionResults.setTitle(restDataList.getMetaData().get(0).getParagraphName());
            detectionResults.setCount(String.valueOf(restDataList.getCount()));
            int tmpMaxNum = 0;
            for (TmpParseData tmpParseData : restDataList.getOriginal()) {
                tmpMaxNum += tmpParseData.getParseData().get(0).getTextSize();
                restData.setNumberOfRepeated(restData.getNumberOfRepeated() + tmpParseData.getParseData().get(0).getTextSize());
            }
            if (tmpMaxNum > maxNum) {
                maxNum = tmpMaxNum;
            }
            restData.getResultsList().add(detectionResults);
        }
        //  获取对应的信息
        restData.setMaxNumber(maxNum);
        restData.setTotalNumber((int) path.get(new MapKey("length",0)));
        restData.setReplicationRatio(getPercentage(String.valueOf(restData.getNumberOfRepeated()), String.valueOf(restData.getTotalNumber())));
        restData.setMaxReplicationRatio(getPercentage(String.valueOf(restData.getMaxNumber()), String.valueOf(restData.getTotalNumber())));
        restData.setDeadline(DateUtil.format(new Date(), "yyyy-MM-dd"));
        restData.setDetectionTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try {
            // 生成 html 路径
            String md5 = Md5.bytesToHex(Md5Utils.computeMD5Hash(new FileInputStream(filePathPrefix + fileJson.getString("path"))));
            long file = System.currentTimeMillis();
            String htmlPath = "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + file + ".html";
            String pdfPath = filePathPrefix + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + file + ".pdf";
            String wordPath = filePathPrefix + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + file + "highlighting.docx";
            String wordPathDir = filePathPrefix + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/";
            String wordPathPDF = filePathPrefix + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + file + "highlighting.pdf";
            String mergePDFPath = "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + file + ".pdf";


            FileUtil.outPutFileHtml(restData, filePathPrefix + htmlPath);
            kbDuplicateCheckEntity.setHtmlPath(htmlPath);
            kbDuplicateCheckEntity.setStatus(CheckEnum.CONVERTPDF.getCode());
            kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
            //html 转换成pdf
            HtmlToPDFUtil.convert(filePathPrefix + htmlPath, pdfPath, TOPDFTOOL);
            //给原始word 添加样式
            if (fileJson.getString("path").endsWith(".docx")) {
                mergePDFPath = "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + md5 + ".pdf";
                WordUtil.wordHighlighting(restData.getRestDataLists(), filePathPrefix + fileJson.getString("path"), wordPath);
                // word 转pdf
                HashMap<String, String> headers = new HashMap<>();
                HashMap<String, String> queryParams = new HashMap<>();
                HashMap<String, Object> bodyMap = new HashMap<>();
                bodyMap.put("fileName", fileName);
                bodyMap.put("inputPath", wordPath);
                bodyMap.put("outPutDir", wordPathDir);
                HttpUtil.doRequest("post", PDFURL + "/knowledge/kbVideVersion/toWordPdf", headers, queryParams, bodyMap);
                String[] files = {pdfPath, wordPathPDF};
                PDFUtil.mergePdfFiles(files, filePathPrefix + mergePDFPath);
            }
            kbDuplicateCheckEntity.setPdfPath(mergePDFPath);
            kbDuplicateCheckEntity.setStatus(CheckEnum.SUCCEED.getCode());
            kbDuplicateCheckEntity.setEstimatedEndTime("成功");
            kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEsCheck(String id) {
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.prefixQuery("id", id));
        query.withQuery(boolQuery);
        esTemplate.delete(query.build(), EsTmpCheck.class);
    }

    private SearchHits<EsTmpCheck> queryByList(String content, String checkMD5) {
        HighlightBuilder highlightBuilder = new HighlightBuilder().fragmentSize(50)//高亮字段内容长度 默认100
                .numOfFragments(10);//高亮关键字数量 默认是5
        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("fileMd5", checkMD5));
        queryBuilder.must(QueryBuilders.matchQuery("fileContent", content));
        query.withQuery(queryBuilder).withSort(SortBuilders.fieldSort("_score").order(SortOrder.DESC)).withHighlightBuilder(highlightBuilder).withPageable(PageRequest.of(0, 20));
        SearchHits<EsTmpCheck> searchHits = esTemplate.search(query.build(), EsTmpCheck.class);
        return searchHits;
    }

    /**
     * 将目标文件写入到临时的es库中
     *
     * @param contrasPath 文件路径
     * @param id          目标id
     * @param fileName
     * @return
     */
    public boolean saveEsCheck(String contrasPath, String id, String fileName) {
        try {
            FortressParseUtil fortressParseUtil = new FortressParseUtil();
            List<String> list = fortressParseUtil.parserFileList(contrasPath);
            String joinStr = StringUtils.join(fortressParseUtil.parserFileList(contrasPath));
            ArrayList<EsTmpCheck> newArray = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String resStr = list.get(i);
                if (StrUtil.isEmpty(resStr) || resStr.length() < 3) continue;
                EsTmpCheck esTmpCheck = new EsTmpCheck();
                esTmpCheck
                        .setFileContent(resStr.trim())
                        .setFileName(fileName)
                        .setLength(joinStr.length())
                        .setFileMd5(Md5.bytesToHex(Md5Utils.computeMD5Hash(Files.newInputStream(Paths.get(contrasPath)))))
                        .setId(id + "&" + i)
                        .setTitle(fileName)
                        .setFileDate(new Date());
                newArray.add(esTmpCheck);
                if (i % 100 == 0 && i != 0) {
                    esTemplate.save(newArray);
                    newArray = new ArrayList<>();
                }
            }
            if (newArray.size() > 0) {
                esTemplate.save(newArray);
            }
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static BigDecimal getPercentage(String number, String sum) {
        double tmpV = Double.parseDouble(number);
        double tmpN = Double.parseDouble(sum);
        return new BigDecimal((tmpV / tmpN) * 100).setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    public static void main(String[] args) throws TikaException, IOException, SAXException {
        FortressParseUtil fortressParseUtil = new FortressParseUtil();
        List<String> list = fortressParseUtil.parserFileList("C:\\Users\\hcses\\Desktop\\11f508ba-24a7-40fa-a987-aca720ad44d2.docx");
        for (String s : list) {
            System.out.println(s);
        }
    }
}
