package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.entity.*;
import com.castle.fortress.admin.check.service.CheckService;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
import com.castle.fortress.admin.check.utils.IKUtil;
import com.castle.fortress.admin.es.EsFileDto;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.system.service.ConfigOssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
//@Service("duplicateCheck")
public class DuplicateCheckServiceImpl2 implements CheckService {
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

    @Override
    @Async
    public void taskProcessing(KbDuplicateCheckDto kbDuplicateCheckDto, Long userId) {
//        KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto, KbDuplicateCheckEntity.class);
//        kbDuplicateCheckEntity.setUploadPath(JSONObject.toJSONString(kbDuplicateCheckDto.getUploadPath()));
//        kbDuplicateCheckEntity.setCreateUser(userId);
//        if (kbDuplicateCheckService.save(kbDuplicateCheckEntity)) {
//            try {
//                // 修改文件状态为处理中
//                kbDuplicateCheckEntity.setStatus(2);
//                kbDuplicateCheckEntity.setEstimatedEndTime("1-10分钟");
//                kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
//                // 获取文件路径前缀
//                String filePathPrefix = ossService.getFilePathPrefix();
//                // 文件内容读取
//                JSONObject fileJson = JSONObject.parseObject(kbDuplicateCheckEntity.getUploadPath());
//                String fileName = fileJson.getString("name");
//                FileEnum fileEnum = FileEnum.typeFromFileName(fileName);
//                String instanceName = fileEnum.getInstanceName();
//                FileReadService fileReadService = fileReadFactory.get(instanceName);
//                HashMap<String, Object> path = fileReadService.fileDataHandle(filePathPrefix + fileJson.getString("path"), kbDuplicateCheckEntity.getReadDataLength());
//                RestData restData = new RestData();
//                restData.setId(kbDuplicateCheckEntity.getId());
//                restData.setFamous(kbDuplicateCheckEntity.getTitle());
//                restData.setAuthorName(kbDuplicateCheckEntity.getAuthor());
//                restData.setDetectionType(kbDuplicateCheckEntity.getType());
//                //创建一个线程池
//                ExecutorService executorService = Executors.newFixedThreadPool(path.size());
//                //使用的计数器
//                CountDownLatch countDownLatch = new CountDownLatch(path.size());
//                HashMap<String, Map<String, Set<Sentence>>> map = new HashMap<>();
//                for (String s : path.keySet()) {
//                    executorService.execute(() -> {
//                        try {
//                            if (path.get(s) instanceof List) {
//                                List<Sentence> sentenceList = (List<Sentence>) path.get(s);
//                                RestDataList restDataList = new RestDataList();
//                                // 拿着内容去es搜索
//                                int contentLength = sentenceList.stream().mapToInt(sentence -> sentence.getText().length()).sum();
//                                ExecutorService ex = Executors.newFixedThreadPool(20);
//                                CountDownLatch cd = new CountDownLatch(sentenceList.size());
//                                AtomicInteger countSum = new AtomicInteger();
//                                for (Sentence sentence : sentenceList) {
//                                    sentence.setParagraphName(s);
//                                    ex.execute(() -> {
//                                        try {
//                                            SearchHits<EsFileDto> searchHits = esSearchService.queryByList(sentence.getText());
//                                            HashSet<Sentence> targetList = new HashSet<>();
//                                            // 读取数据
//                                            ArrayList<String> md5List = new ArrayList<>();
//                                            for (SearchHit<EsFileDto> searchHit : searchHits) {
//                                                String filePath = filePathPrefix + searchHit.getContent().getFilePath();
//                                                md5List.add(SecureUtil.md5(new File(filePath)));
//                                                fileReadFactory.get(FileEnum.typeFromFileName(searchHit.getContent().getFilePath()).getInstanceName()).fileContrastDataHandle(filePath, map, searchHit.getContent(), s);
//                                            }
//                                            List<String> sentenceData = IKUtil.divideText(sentence.getText());
//                                            HashSet<String> sentenceDataList = new HashSet<>(sentenceData);
//                                            for (String word : sentenceDataList) {
//                                                for (String md5 : md5List) {
//                                                    Map<String, Set<Sentence>> setMap = map.get(md5);
//                                                    Set<Sentence> sentences = setMap.get(word);
//                                                    if (sentences != null) {
//                                                        targetList.addAll(sentences);
//                                                    }
//                                                }
////                                                for (Map<String, Set<Sentence>> value : map.values()) {
////                                                    Set<Sentence> sentences = value.get(word);
////                                                    if (sentences != null) {
////                                                        targetList.addAll(sentences);
////                                                    }
////                                                }
//                                            }
//                                            log.debug(targetList.size() +"" +sentence.getText());
//                                            HashMap<String, Object> simSentenceCnt2 = AnalysisUtil.getSimSentenceCnt2(sentence, new ArrayList<>(targetList), new CosSim(), kbDuplicateCheckEntity.getWeight());
//                                            if (simSentenceCnt2.size() == 3) {
//                                                restDataList.getMetaData().addAll((ArrayList<HistoryMetaData>) simSentenceCnt2.get("metadata"));
//                                                restDataList.getOriginal().add((TmpParseData) simSentenceCnt2.get("tmpParseData"));
//                                                int countSumInt = (int) simSentenceCnt2.get("countSum");
//                                                countSum.addAndGet(countSumInt);
//                                            }
//                                        } catch (Exception e) {
//                                            throw new RuntimeException(e);
//                                        } finally {
//                                            cd.countDown();
//                                        }
//                                    });
//                                }
//                                try {
//                                    cd.await();
//                                    ex.shutdown();
//                                } catch (InterruptedException e) {
//                                    throw new RuntimeException(e);
//                                }
//                                // 设置长度
//                                sentenceList.get(0).setLength(contentLength);
//                                if (restDataList.getOriginal().size() > 0 && restDataList.getMetaData().size() > 0) {
//                                    AnalysisUtil.extracted(sentenceList, restDataList, countSum);
//                                    restData.getRestDataLists().add(restDataList);
//                                }
//                            }
//                        } finally {
//                            countDownLatch.countDown();
//                        }
//                    });
//                }
//
//                try {
//                    countDownLatch.await();
//                    executorService.shutdown();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                List<RestDataList> restDataLists = restData.getRestDataLists();
//                int maxNum = 0;
//                for (RestDataList restDataList : restDataLists) {
//                    DetectionResults detectionResults = new DetectionResults();
//                    detectionResults.setCode(restDataList.getCode());
//                    detectionResults.setLength(String.valueOf(restDataList.getLength()));
//                    detectionResults.setReplicationRatio(restDataList.getTextCopyRatio());
//                    detectionResults.setTitle(restDataList.getMetaData().get(0).getParagraphName());
//                    detectionResults.setCount(String.valueOf(restDataList.getCount()));
//                    int tmpMaxNum = 0;
//                    for (TmpParseData tmpParseData : restDataList.getOriginal()) {
//                        tmpMaxNum += tmpParseData.getParseData().get(0).getTextSize();
//                        restData.setNumberOfRepeated(restData.getNumberOfRepeated() + tmpParseData.getParseData().get(0).getTextSize());
//                    }
//                    if (tmpMaxNum > maxNum) {
//                        maxNum = tmpMaxNum;
//                    }
//                    restData.getResultsList().add(detectionResults);
//                }
//                //  获取对应的信息
//                restData.setMaxNumber(maxNum);
//                restData.setTotalNumber((int) path.get("length"));
//                restData.setReplicationRatio(getPercentage(String.valueOf(restData.getNumberOfRepeated()), String.valueOf(restData.getTotalNumber())));
//                restData.setMaxReplicationRatio(getPercentage(String.valueOf(restData.getMaxNumber()), String.valueOf(restData.getTotalNumber())));
//                restData.setDeadline(DateUtil.format(new Date(), "yyyy-MM-dd"));
//                restData.setDetectionTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                try {
//                    // 生成 html 路径
//                    String md5 = MD5Util.computeMD5(new FileInputStream(filePathPrefix + fileJson.getString("path")));
//                    String htmlPath = ossService.getFilePathPrefix() + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + md5 + ".html";
//                    String pdfPath = ossService.getFilePathPrefix() + "upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + md5 + ".pdf";
//                    FileUtil.outPutFileHtml(restData, htmlPath);
//                    kbDuplicateCheckEntity.setHtmlPath("upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + md5 + ".html");
//                    kbDuplicateCheckEntity.setStatus(CheckEnum.CONVERTPDF.getCode());
//                    kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
//                    //html 转换成pdf
//                    HtmlToPDFUtil.convert(htmlPath, pdfPath, TOPDFTOOL);
//                    kbDuplicateCheckEntity.setPdfPath("upload/" + DateUtil.format(new Date(), "yyyyMMdd") + "/" + md5 + ".pdf");
//                    kbDuplicateCheckEntity.setStatus(CheckEnum.SUCCEED.getCode());
//                    kbDuplicateCheckEntity.setEstimatedEndTime(CheckEnum.SUCCEED.getDesc());
//                    kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                kbDuplicateCheckEntity.setEstimatedEndTime(CheckEnum.ERROR.getDesc());
//                kbDuplicateCheckEntity.setStatus(CheckEnum.ERROR.getCode());
//                kbDuplicateCheckService.updateById(kbDuplicateCheckEntity);
//            }
//        }
//        log.debug("处理完成了");
    }

    public static BigDecimal getPercentage(String number, String sum) {
        double tmpV = Double.parseDouble(number);
        double tmpN = Double.parseDouble(sum);
        return new BigDecimal((tmpV / tmpN) * 100).setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    public static void getFileData(String path, HashMap<String, Map<String, Set<Sentence>>> map, EsFileDto content, String s) throws IOException {

        File file = new File(path);
        if (!file.exists() || map.containsKey(SecureUtil.md5(file))) {
            return;
        }
        FileInputStream fis = new FileInputStream(path);
        XWPFDocument document = new XWPFDocument(fis);
        HashMap<String, Set<Sentence>> result = new HashMap<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StrUtil.isEmpty(paragraph.getText()) || paragraph.getText().length() < 3) {
                continue;
            }
            if (StrUtil.isEmpty(paragraph.getText())) {
                continue;
            }
            List<String> strings = IKUtil.divideText(paragraph.getText());
            for (String string : strings) {
                Sentence sentence = new Sentence(paragraph.getText());
                sentence.setDate(DateUtil.formatDate(content.getFileDate()));
                sentence.setTitle(content.getTitle());
                sentence.setLength(content.getLength());
                sentence.setFileName(content.getFileName());

                result.computeIfAbsent(string, k -> new HashSet<>()).add(sentence);
            }
        }
        map.put(SecureUtil.md5(file), result);
    }

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("C:\\Users\\lyz\\Downloads\\计算机应用技术.docx");
        XWPFDocument document = new XWPFDocument(fis);
        HashMap<String, Set<Sentence>> result = new HashMap<>();
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            if (StrUtil.isEmpty(paragraph.getText()) || paragraph.getText().length() < 3) {
                continue;
            }
            System.out.println(paragraph.getText());
        }
    }
}
