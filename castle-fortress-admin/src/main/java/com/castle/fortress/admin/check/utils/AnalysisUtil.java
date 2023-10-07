package com.castle.fortress.admin.check.utils;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.check.entity.*;
import com.castle.fortress.admin.check.service.SimDegreeAlgorithm;
import com.castle.fortress.admin.check.service.impl.SimDegreeStrategy;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.castle.fortress.admin.check.service.impl.KbDuplicateCheckServiceImpl.getPercentage;

@Slf4j
public class AnalysisUtil {

    /**
     * 计算出与项目库内容重复的句子在当前内容下所占的比例
     *
     * @param sentencesA 待查重的句子集合
     * @param sentencesB 项目库中的项目内容句子集合
     * @param algorithm  相似度算法
     * @return java.math.BigDecimal
     **/
    public static BigDecimal getAnalysisResult(List<Sentence> sentencesA, List<Sentence> sentencesB, SimDegreeAlgorithm algorithm) {
//        int simSentenceCnt = getSimSentenceCnt(sentencesA, sentencesB, algorithm);
//        BigDecimal analysisResult = null;
//        if (CollectionUtil.isNotEmpty(sentencesA)) {
//            analysisResult = BigDecimal.valueOf((double) simSentenceCnt / sentencesA.size()).setScale(4, BigDecimal.ROUND_HALF_UP);
//        } else {
//            analysisResult = new BigDecimal(0);
//        }
//        return analysisResult;
        return null;
    }

    /**
     * 根据相似度算法，分析句子集合，返回 A 在 B 中的相似句子数量，同时记录相似句子的相似度及其所在位置（在进行处理的过程中，通过对 A 中数据进行相关操作实现）。
     *
     * @param sentencesA 原始文本集合，即断好的句子集合
     * @param sentencesB 模式文本集合，即断好的句子集合
     * @param algorithm  相似度算法
     * @return int
     **/
    public static RestDataList getSimSentenceCnt(List<Sentence> sentencesA, List<Sentence> sentencesB, SimDegreeAlgorithm algorithm) {
        RestDataList restDataList = new RestDataList();
// 当前句子相似度
        final double[] simDegree = {0};
        AtomicInteger countSum = new AtomicInteger();
        // 计算相似度的策略
        SimDegreeStrategy simDegreeStrategy = new SimDegreeStrategy(algorithm);

        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //使用的计数器
        CountDownLatch countDownLatch = new CountDownLatch(sentencesA.size());
        for (Sentence sentence1 : sentencesA) {
            executorService.execute(() -> {
                try {
                    // 当前句子匹配到的最大的相似度
                    double maxSimDegree = 0;
                    // 记录 B 里的，与 A 中最大相似度的那个句子
                    List<ParseData> parseData = new ArrayList<>();
                    List<List<ParseData>> parseDataArrayList = new ArrayList<>();
                    ArrayList<HistoryMetaData> metadata = new ArrayList<>();
                    for (Sentence sentence2 : sentencesB) {
                        // 计算相似度
                        Map<String, Object> simDegreeMap = simDegreeStrategy.getSimDegree(sentence1.getText(), sentence2.getText(), 60);
                        simDegree[0] = (double) simDegreeMap.get("simDegree");
                        // 相似度大于60，认为文本重复
                        if (simDegree[0] * 100 > 60) {
                            sentence1.setDuplicatesState(1);
                            // 记录该句子在 B 中的位置
                            sentence1.getDuplicatesIndex().add(sentencesB.indexOf(sentence2));
                            List<ParseData> highlightedSentence2 = (List<ParseData>) simDegreeMap.get("highlightedSentence2");
                            boolean mark = true;
                            for (ParseData data : highlightedSentence2) {
                                if (StrUtil.isNotEmpty(data.getText())) {
                                    if (data.getText().contains("</font>")) {
                                        mark = false;
                                    }
                                }
                            }
                            if (!mark) {
                                List<ParseData> highlightedSentence1 = (List<ParseData>) simDegreeMap.get("highlightedSentence1");
                                if (parseData.size() == 0) {
                                    parseData = highlightedSentence1;
                                } else if (parseData.get(0).getTextSize() < highlightedSentence1.get(0).getTextSize()) {
                                    parseData = highlightedSentence1;
                                }
                                parseDataArrayList.add(highlightedSentence2);
                                int sentence2TextSize = highlightedSentence2.get(0).getTextSize();
                                if (sentence2TextSize > highlightedSentence1.get(0).getTextSize()) {
                                    sentence2TextSize = highlightedSentence1.get(0).getTextSize();
                                }
                                metadata.add(new HistoryMetaData(sentence2.getDate(), sentence2.getTitle(), sentence2.getLength(), sentence2TextSize, sentence2.getFileName(), sentence2.getParagraphName()));
                            }
                        }
                        // 记录最大的相似度
                        if (simDegree[0] * 100 > maxSimDegree) {
                            maxSimDegree = simDegree[0] * 100;
                        }
                    }
                    // 如果当前句子匹配到的最大相似度是大于60的，那么说明该句子在 B 中至少有一个句子是相似的，即该句子是重复的

                    if (maxSimDegree > 60) {
                        if (parseData.size() > 0) {
                            parseData.stream().forEach(item -> {
                                item.setSimilarList(metadata);
                                item.setList(parseDataArrayList);
                                item.setSize(parseDataArrayList.size() * 2);
                            });
                            countSum.addAndGet(parseData.get(0).getTextSize());
                            TmpParseData tmpParseData = new TmpParseData();
                            tmpParseData.setParseData(parseData);
                            restDataList.getMetaData().addAll(metadata);
                            restDataList.getOriginal().add(tmpParseData);
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
        extracted(sentencesA, restDataList, countSum);
//        addNum(restData);
        return restDataList;
    }

    public static void extracted(List<Sentence> sentencesA, RestDataList restDataList, AtomicInteger countSum) {
        HashMap<String, HistoryMetaData> dataHashMap = new HashMap<>();

        for (HistoryMetaData metadatum : restDataList.getMetaData()) {
            if (dataHashMap.containsKey(metadatum.getParagraphName() + metadatum.getFileName())) {
                HistoryMetaData historyMetaData = dataHashMap.get(metadatum.getParagraphName() + metadatum.getFileName());
                historyMetaData.setRepeatCount(historyMetaData.getRepeatCount() + metadatum.getRepeatCount());
                historyMetaData.setRepeatRatio(getPercentage(String.valueOf(historyMetaData.getRepeatCount()), String.valueOf(historyMetaData.getLength())));
                dataHashMap.put(metadatum.getParagraphName() + metadatum.getFileName(), historyMetaData);
            } else {
                dataHashMap.put(metadatum.getParagraphName() + metadatum.getFileName(), metadatum);
            }
        }

        List<HistoryMetaData> resMetaData = dataHashMap.values().stream().sorted(new Comparator<HistoryMetaData>() {
            @Override
            public int compare(HistoryMetaData o1, HistoryMetaData o2) {
                return Integer.compare(o2.getRepeatCount(), o1.getRepeatCount());
            }
        }).collect(Collectors.toList());
        restDataList.setMetaData(resMetaData);

        // 计算复制比
        BigDecimal percentage = getPercentage(String.valueOf(countSum.get()), String.valueOf(sentencesA.get(0).getLength()));
        restDataList.setTextCopyRatio(percentage);
        restDataList.setCount(countSum.get());
        restDataList.setLength(sentencesA.get(0).getLength());
        // 新增code
        restDataList.setCode(createCode(5));
    }

    public static HashMap<String, Object> getSimSentenceCnt2(Sentence sentencesA, List<Sentence> sentencesB, SimDegreeAlgorithm algorithm, int weight) {
        HashMap<String, Object> resMap = new HashMap<>();
        // 当前句子相似度
        final double[] simDegree = {0};
        // 计算相似度的策略
        SimDegreeStrategy simDegreeStrategy = new SimDegreeStrategy(algorithm);
        // 当前句子匹配到的最大的相似度
        double maxSimDegree = 0;
        // 记录 B 里的，与 A 中最大相似度的那个句子
        List<ParseData> parseData = new ArrayList<>();
        List<List<ParseData>> parseDataArrayList = new ArrayList<>();
        ArrayList<HistoryMetaData> metadata = new ArrayList<>();

        //创建一个线程池
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //使用的计数器
//        CountDownLatch countDownLatch = new CountDownLatch(sentencesB.size());


        for (Sentence sentence2 : sentencesB) {
            // 计算相似度
            Map<String, Object> simDegreeMap = simDegreeStrategy.getSimDegree(sentencesA.getText(), sentence2.getText(), weight);
            simDegree[0] = (double) simDegreeMap.get("simDegree");
            // 相似度大于60，认为文本重复
            if (simDegree[0] * 100 > weight) {
                sentencesA.setDuplicatesState(1);
                // 记录该句子在 B 中的位置
                sentencesA.getDuplicatesIndex().add(sentencesB.indexOf(sentence2));
                List<ParseData> highlightedSentence2 = (List<ParseData>) simDegreeMap.get("highlightedSentence2");
                boolean mark = true;
                for (ParseData data : highlightedSentence2) {
                    if (StrUtil.isNotEmpty(data.getText())) {
                        if (data.getText().contains("</font>")) {
                            mark = false;
                        }
                    }
                }
                if (!mark) {
                    // 定义标记
                    boolean h1Mark = false;
                    List<ParseData> highlightedSentence1 = (List<ParseData>) simDegreeMap.get("highlightedSentence1");
                    for (ParseData data : highlightedSentence1) {
                        if (data.getText().contains("</font>")) {
                            h1Mark = true;
                            break;
                        }
                    }
                    if (!h1Mark) continue;
                    if (parseData.size() == 0) {
                        parseData = highlightedSentence1;
                    } else if (parseData.get(0).getTextSize() < highlightedSentence1.get(0).getTextSize()) {
                        parseData = highlightedSentence1;
                    }
                    parseDataArrayList.add(highlightedSentence2);
                    int sentence2TextSize = highlightedSentence2.get(0).getTextSize();
                    if (sentence2TextSize > highlightedSentence1.get(0).getTextSize()) {
                        sentence2TextSize = highlightedSentence1.get(0).getTextSize();
                    }
                    metadata.add(new HistoryMetaData(sentence2.getDate(), sentence2.getTitle(), sentence2.getLength(), sentence2TextSize, sentence2.getFileName(), sentencesA.getParagraphName()));
                }
            }
            // 记录最大的相似度
            if (simDegree[0] * 100 > maxSimDegree) {
                maxSimDegree = simDegree[0] * 100;
            }
        }
        // 如果当前句子匹配到的最大相似度是大于60的，那么说明该句子在 B 中至少有一个句子是相似的，即该句子是重复的
        if (maxSimDegree > weight) {
            if (parseData.size() > 0) {
                parseData.stream().forEach(item -> {
                    item.setSimilarList(metadata);
                    item.setList(parseDataArrayList);
                    item.setSize(parseDataArrayList.size() * 2);
                });
                TmpParseData tmpParseData = new TmpParseData();
                tmpParseData.setParseData(parseData);
                resMap.put("metadata", metadata);
                resMap.put("tmpParseData", tmpParseData);
                resMap.put("countSum", parseData.get(0).getTextSize());
            }
        }
        return resMap;
    }


    public static BigDecimal getPercentage(String number, String sum) {
        double tmpV = Double.parseDouble(number);
        double tmpN = Double.parseDouble(sum);
        return new BigDecimal((tmpV / tmpN) * 100).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static String createCode(int n) {
        Random r = new Random();
        String code = "";
        for (int i = 0; i < n; i++) {
            int type = r.nextInt(3);  // 0  1  2
            switch (type) {
                case 0:
                    // 大写英文 A 65  Z 90
                    char ch = (char) (r.nextInt(25) + 65);
                    code += ch;
                    break;
                case 1:
                    // 小写英文 A 97 Z 122
                    char ch1 = (char) (r.nextInt(25) + 97);
                    code += ch1;
                    break;
                case 2:
                    // 数字
                    int ch2 = r.nextInt(10);
                    code += ch2;
                    break;
            }
        }
        return code;
    }
}
