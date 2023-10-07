package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.check.entity.ParseData;
import com.castle.fortress.admin.check.service.SimDegreeAlgorithm;
import com.castle.fortress.admin.check.utils.IKUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class CosSim implements SimDegreeAlgorithm {

    /**
     * 计算两个句子的相似度：余弦相似度算法
     *
     * @param a 句子1
     * @param b 句子2
     **/
    @Override
    public HashMap<String, Object> getSimDegree(String a, String b, int weight) {
        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(a) || StringUtils.isBlank(b)) {
            map.put("simDegree", 0.0);
            return map;
        }
        // 将句子进行分词
        List<String> aWords = IKUtil.divideText(a);
        List<String> bWords = IKUtil.divideText(b);
        // 计算句子中词的词频
        Map<String, Integer> aWordsFrequency = getWordsFrequency(aWords);
        Map<String, Integer> bWordsFrequency = getWordsFrequency(bWords);
        // 向量化，先并集，然后遍历在并集中对应词语，在自己的分词集合中对应词语出现次数，组成的数就是向量
        Set<String> union = new HashSet<>();
        union.addAll(aWords);
        union.addAll(bWords);
        // a、b 一维向量
        int[] aVector = new int[union.size()];
        int[] bVector = new int[union.size()];
        List<String> collect = new ArrayList<>(union);
        for (int i = 0; i < collect.size(); ++i) {
            aVector[i] = aWordsFrequency.getOrDefault(collect.get(i), 0);
            bVector[i] = bWordsFrequency.getOrDefault(collect.get(i), 0);
        }
        double similarity = similarity(aVector, bVector);
        map.put("simDegree", similarity);
        if (similarity * 100 > weight) {
            highlightMatchingWords(a, aWords, b, bWords, map);
        }
        // 分别计算三个参数，再结合公式计算
        return map;
    }

    private static void highlightMatchingWords(String original, List<String> words1, String target, List<String> words2, HashMap<String, Object> map) {
        String originalTextData = getHighlight(original.toLowerCase(), words1, words2);
        String targetTextData = getHighlight(target.toLowerCase(), words2, words1);
        map.put("highlightedSentence1", getRest(originalTextData, target.toLowerCase(), original.toLowerCase(), 1));
        map.put("highlightedSentence2", getRest(targetTextData, original.toLowerCase(), target.toLowerCase(), 2));
    }

    public static String getHighlight(String sentence, List<String> words1, List<String> words2) {
//        List<String> specialWord = Arrays.asList("f", "o", "n", "t", "fo", "on", "ont", "font", "c", "l", "co", "ol", "or", "olo", "lor", "colo", "olor", "color", "r", "e", "d", "ed", "red");
//        List<String> specialWord = Arrays.asList("f");
        List<String> afterProcessingWordList = words1.stream()
//                .filter(item -> !specialWord.contains(item))
                .sorted((o1, o2) -> {
                    return Integer.compare(o2.length(), o1.length());
                }).collect(Collectors.toList());
        StringBuilder highlightedSentence = new StringBuilder(sentence);
        CopyOnWriteArrayList<String> charStr = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<String> hisList = new CopyOnWriteArrayList<>();
        new HashSet<String>();
        try {
            for (String item : afterProcessingWordList) {
                if (isWordPresent(item, null, words2)) {
                    HashSet<String> addList = new HashSet<>();
                    for (String word : hisList) {
                        if (word.contains(item)) {
                            String[] split = word.split(item);
                            test(hisList, split, addList);
                            addListLength(charStr, item, addList);// 长度小于当前添加到list中
                        } else {
                            if (charStr.size() > 0) {
                                for (String s1 : charStr) {
                                    if (word.contains(s1)) {
                                        String[] split = word.split(s1);
                                        test(hisList, split, addList);
                                        addListLength(charStr, item, addList); // 长度小于当前添加到list中
                                    }
                                }
                            }
                        }
                    }
                    hisList.add(item);
                    hisList.addAll(deduplicatedWord(new ArrayList<>(addList)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(sentence);
        }

        // 处理中文分词 加高亮
        for (String word : new HashSet<>(hisList)) {
            if (StrUtil.isNotEmpty(word)) {
//                highlightedSentence = new StringBuilder(highlightedSentence.toString().replace(word, "<font color=red>" + word + "</font>"));
                highlightedSentence = new StringBuilder(highlightedSentence.toString().replace(word, "<-->" + word + "</-->"));
            }
        }

        return highlightedSentence.toString();
    }

    private static void addListLength(CopyOnWriteArrayList<String> charStr, String item, HashSet<String> addList) {
        for (String s : addList) {
            if (s.length() < item.length()) {
                charStr.add(s);
            }
        }
    }


    public static void test(CopyOnWriteArrayList<String> hisList, String[] split, HashSet<String> addSet) {
        boolean res = false;
        for (String item : split) {
            if (StrUtil.isNotEmpty(item)) {
                for (String his : hisList) {
                    if (StrUtil.isNotEmpty(his) && his.contains(item)) {
                        String[] split1 = new String[0];
                        try {
                            split1 = his.split(item);
                        } catch (Exception e) {
                            System.out.println("item :" + item);
                            // throw new RuntimeException(e);
                        }
                        hisList.remove(his);
                        addSet.addAll(Arrays.stream(split1).filter(StrUtil::isNotEmpty).collect(Collectors.toList()));
                        test(hisList, split1, addSet);
                        res = true;
                    }
                }
            }
        }
        if (res) {
            addSet.addAll(Arrays.stream(split).filter(StrUtil::isNotEmpty).collect(Collectors.toList()));
        }
    }


    private static ArrayList<String> deduplicatedWord(ArrayList<String> strings) {
        ArrayList<String> addList = new ArrayList<>();
        ArrayList<String> removeList = new ArrayList<>();

        for (int i = 0; i < strings.size(); i++) {
            for (int i1 = 0; i1 < strings.size(); i1++) {
                if (strings.get(i).contains(strings.get(i1)) && i != i1) {
                    addList.addAll(Arrays.stream(strings.get(i).split(strings.get(i1))).filter(StrUtil::isNotEmpty).collect(Collectors.toList()));
                    removeList.add(strings.get(i));
                }
            }
        }
        strings.addAll(addList);
        strings.removeAll(removeList);
        return strings;
    }

    static ArrayList<ParseData> getRest(String input, String text) {
        Pattern pattern = Pattern.compile("<font color=red>(.*?)</font>");
        String regex = "[,，。：~？【】、 %&*《》()+—＋；/\\[\\]（）:“”.;!?\\s]";
        // 创建 Pattern 对象
        Pattern res = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int index = 0;
        String tmpText = "";
        String beforeTest = "";
        String AfterTest = "";
        ArrayList<ParseData> parseDataArrayList = new ArrayList<>();

        while (matcher.find()) {
            String coloredText = matcher.group(1);
            if (index == 0) {
                index++;
                String substring = text.substring(0, text.indexOf(coloredText) + 1);
                if (substring.equals(coloredText)) {
                    // 说明最开始已经是高亮了
                    tmpText = substring;
                } else {
                    tmpText = coloredText;
                    if (substring.length() > 0) {
                        beforeTest = substring.substring(0, substring.length() - 1);
                    }
                }
            } else {
                if (text.indexOf(beforeTest + tmpText + coloredText) == 0) {
                    tmpText = tmpText + coloredText;
                } else {
                    String substring = text.substring((beforeTest + tmpText).length());
                    boolean biaoji = true;
                    a:
                    for (int i = 0; i < substring.length(); i++) {
                        Matcher ma = res.matcher(String.valueOf(substring.charAt(i)));
                        String tmpSymbol = "";
                        while (ma.find()) {
                            String group = ma.group();
                            if (group.equals("—")) {
                                tmpSymbol += "——";
                            } else {
                                tmpSymbol += group;
                            }
                            if (text.indexOf(beforeTest + tmpText + tmpSymbol + coloredText) == 0) {
                                tmpText = tmpText + tmpSymbol + coloredText;
                                biaoji = false;
                                break a;
                            }
                        }
                    }

                    if (biaoji) {
                        if (StrUtil.isNotEmpty(tmpText)) {
                            if (tmpText.length() > 10) {
                                ParseData parseData = new ParseData();
                                parseData.setTextSize(tmpText.length());
                                parseData.setText("<font color=red>" + tmpText + "</font>");
                                parseData.setBeforeText(beforeTest);
                                parseData.setAfterTest(AfterTest);
                                parseDataArrayList.add(parseData);
                                int lastNum = 0;
                                if (parseDataArrayList.size() > 1) {
                                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                                } else {
                                    lastNum = parseData.getTextSize();
                                }
                                parseDataArrayList.get(0).setTextSize(lastNum);
                            } else {

                                ParseData parseData = new ParseData();
                                parseData.setText(tmpText);
                                parseData.setTextSize(0);
                                parseData.setBeforeText(beforeTest);
                                parseData.setAfterTest(AfterTest);
                                parseDataArrayList.add(parseData);
                                int lastNum = 0;
                                if (parseDataArrayList.size() > 1) {
                                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                                } else {
                                    lastNum = parseData.getTextSize();
                                }
                                parseDataArrayList.get(0).setTextSize(lastNum);
                            }
                            tmpText = "";
                            beforeTest = "";
                            AfterTest = "";
                            text = substring;

                            for (int i = 0; i < text.length(); i++) {
                                if (text.charAt(i) == coloredText.charAt(0)) {
                                    tmpText = coloredText;
                                    break;
                                } else {
                                    beforeTest += text.charAt(i);
                                }
                            }
                        }

                    }
                }
            }
        }
        if (StrUtil.isNotEmpty(tmpText)) {

            if (tmpText.length() > 10) {

                ParseData parseData = new ParseData();
                parseData.setTextSize(tmpText.length());
                parseData.setText("<font color=red>" + tmpText + "</font>");
                parseData.setBeforeText(beforeTest);
                parseData.setAfterTest(text.substring((beforeTest + tmpText).length()));
                parseDataArrayList.add(parseData);
                int lastNum = 0;
                if (parseDataArrayList.size() > 1) {
                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                } else {
                    lastNum = parseData.getTextSize();
                }
                parseDataArrayList.get(0).setTextSize(lastNum);
            } else {

                ParseData parseData = new ParseData();
                parseData.setTextSize(0);
                parseData.setText(tmpText);
                parseData.setBeforeText(beforeTest);
                parseData.setAfterTest(text.substring((beforeTest + tmpText).length()));
                parseDataArrayList.add(parseData);
                int lastNum = 0;
                if (parseDataArrayList.size() > 1) {
                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                } else {
                    lastNum = parseData.getTextSize();
                }
                parseDataArrayList.get(0).setTextSize(lastNum);
            }

        }
        return parseDataArrayList;
    }

    static ArrayList<ParseData> getRest(String input, String outputText, String text, int mark) {
        String historyText = text;
        Pattern pattern = Pattern.compile("<-->(.*?)</-->");
        String regex = "[,，。：~？\\-＞<>＋:【】 、\\[\\] \"`=%&*《》()+—；/（）“”.;!?\\s]";
        // 创建 Pattern 对象
        Pattern res = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        int index = 0;
        String tmpText = "";
        String beforeTest = "";
        String AfterTest = "";
        ArrayList<ParseData> parseDataArrayList = new ArrayList<>();
        while (matcher.find()) {
            String coloredText = matcher.group(1);
            if (index == 0) {
                index++;
                String substring = text.substring(0, text.indexOf(coloredText) + 1);
                if (substring.equals(coloredText)) {
                    // 说明最开始已经是高亮了
                    tmpText = substring;
                } else {
                    tmpText = coloredText;
                    if (substring.length() > 0) {
                        beforeTest = substring.substring(0, substring.length() - 1);
                    }
                }
            } else {
                if (text.indexOf(beforeTest + tmpText + coloredText) == 0) {
                    tmpText = tmpText + coloredText;
                } else {
                    String substring = text.substring((beforeTest + tmpText).length());
                    boolean biaoji = true;
                    String tmpSymbol = "";
                    a:
                    for (int i = 0; i < substring.length(); i++) {
                        Matcher ma = res.matcher(String.valueOf(substring.charAt(i)));
                        while (ma.find()) {
                            String group = ma.group();
                            if (group.equals("—")) {
                                tmpSymbol += "——";
                            } else {
                                tmpSymbol += group;
                            }
                            if (text.indexOf(beforeTest + tmpText + tmpSymbol + coloredText) == 0) {
                                tmpText = tmpText + tmpSymbol + coloredText;
                                biaoji = false;
                                break a;
                            }
                        }
                    }
                    if (biaoji) {
                        if (StrUtil.isNotEmpty(tmpText)) {
                            double lengthPercentage = Double.parseDouble(String.valueOf(tmpText.length())) / Double.parseDouble(String.valueOf(outputText.length())) * 100;
                            if ((tmpText.length() > 2 && mark == 1) || (lengthPercentage > 30 && mark == 2)) {
                                // 处理前缀是符号
                                if (StrUtil.isNotEmpty(beforeTest)) {
                                    char charAt = beforeTest.charAt(0);
                                    Matcher matchered = res.matcher(String.valueOf(charAt));
                                    if (matchered.find()) {
                                        String group = matchered.group();
                                        if (outputText.contains(group + tmpText)) {
                                            tmpText = group + tmpText;
                                            beforeTest = beforeTest.replaceFirst(group, "");
                                        }
                                    }
                                }
//                                System.out.println("----------" + beforeTest + "    " + tmpText);
                                ParseData parseData = new ParseData();
                                parseData.setTextSize(tmpText.length());
                                parseData.setText("<font color=red>" + tmpText + "</font>");
                                parseData.setBeforeText(beforeTest);
                                parseData.setAfterTest(AfterTest);
                                parseData.setRawText(historyText);
                                parseDataArrayList.add(parseData);
                                int lastNum = 0;
                                if (parseDataArrayList.size() > 1) {
                                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                                } else {
                                    lastNum = parseData.getTextSize();
                                }
                                parseDataArrayList.get(0).setTextSize(lastNum);
                            } else {
                                ParseData parseData = new ParseData();
                                parseData.setText(tmpText);
                                parseData.setTextSize(0);
                                parseData.setBeforeText(beforeTest);
                                parseData.setAfterTest(AfterTest);
                                parseDataArrayList.add(parseData);
                                parseData.setRawText(historyText);
                                int lastNum = 0;
                                if (parseDataArrayList.size() > 1) {
                                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                                } else {
                                    lastNum = parseData.getTextSize();
                                }
                                parseDataArrayList.get(0).setTextSize(lastNum);
                            }
                            tmpText = "";
                            beforeTest = "";
                            AfterTest = "";
                            text = substring;

                            for (int i = 0; i < text.length(); i++) {
                                if (text.charAt(i) == coloredText.charAt(0)) {
                                    tmpText = coloredText;
                                    break;
                                } else {
                                    beforeTest += text.charAt(i);
                                }
                            }
                        }

                    }
                }
            }
        }
        if (StrUtil.isNotEmpty(tmpText)) {
            double lengthPercentage = Double.parseDouble(String.valueOf(tmpText.length())) / Double.parseDouble(String.valueOf(outputText.length())) * 100;
            if ((tmpText.length() > 2 && mark == 1) || (lengthPercentage > 60 && mark == 2)) {
                // 处理前缀是符号
                if (StrUtil.isNotEmpty(beforeTest)) {
                    char charAt = beforeTest.charAt(0);
                    Matcher matchered = res.matcher(String.valueOf(charAt));
                    if (matchered.find()) {
                        String group = matchered.group();
                        if (outputText.contains(group + tmpText)) {
                            tmpText = group + tmpText;
                            if (group.contains("[")) {
                                beforeTest = beforeTest.replaceFirst("\\[", "");
                            } else {
                                beforeTest = beforeTest.replaceFirst(group, "");
                            }
                        }
                    }
                }
                // 处理最后是符号问题
                String tmpStr = text.substring(tmpText.length());
                if (StrUtil.isNotEmpty(tmpStr)) {
                    char charAt = tmpStr.charAt(0);
                    Matcher matchered = res.matcher(String.valueOf(charAt));
                    if (matchered.find()) {
                        String group = matchered.group();
                        if (outputText.contains(tmpText + group)) {
                            tmpText = tmpText + group;
                        }
                    }
                }
                ParseData parseData = new ParseData();
                parseData.setTextSize(tmpText.length());
                parseData.setRawText(historyText);
                parseData.setText("<font color=red>" + tmpText + "</font>");
                parseData.setBeforeText(beforeTest);
                if (text.length() > (beforeTest + tmpText).length()) {
                    parseData.setAfterTest(text.substring((beforeTest + tmpText).length()));
                }
                parseDataArrayList.add(parseData);
                int lastNum = 0;
                if (parseDataArrayList.size() > 1) {
                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                } else {
                    lastNum = parseData.getTextSize();
                }
                parseDataArrayList.get(0).setTextSize(lastNum);
            } else {
                ParseData parseData = new ParseData();
                parseData.setTextSize(0);
                parseData.setText(tmpText);
                parseData.setRawText(historyText);
                parseData.setBeforeText(beforeTest);
                parseData.setAfterTest(text.substring((beforeTest + tmpText).length()));
                parseDataArrayList.add(parseData);
                int lastNum = 0;
                if (parseDataArrayList.size() > 1) {
                    lastNum = parseDataArrayList.get(0).getTextSize() + parseData.getTextSize();
                } else {
                    lastNum = parseData.getTextSize();
                }
                parseDataArrayList.get(0).setTextSize(lastNum);
            }

        }
        return parseDataArrayList;
    }

    private static boolean isMatcher(String text, String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        // 查找匹配的结果
        while (matcher.find()) {
            String match = matcher.group();
            if (StrUtil.isNotEmpty(match)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWordPresent(String word, String LastWord, List<String> words) {
        for (String w : words) {
            if (word.equalsIgnoreCase(w)) {
//                System.out.println(words + "  " + w);
                return true;
            }
        }
        return false;
    }

    public static Map<String, Integer> getWordsFrequency(List<String> words) {
        Map<String, Integer> wordFrequency = new HashMap<>(16);
        // 统计词的出现次数，即词频
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }

    /**
     * 分别计算三个参数
     *
     * @param aVec a 一维向量
     * @param bVec b 一维向量
     **/
    public static double similarity(int[] aVec, int[] bVec) {
        int n = aVec.length;
        double p1 = 0;
        double p2 = 0;
        double p3 = 0;
        for (int i = 0; i < n; i++) {
            p1 += (aVec[i] * bVec[i]);
            p2 += (aVec[i] * aVec[i]);
            p3 += (bVec[i] * bVec[i]);
        }
        p2 = Math.sqrt(p2);
        p3 = Math.sqrt(p3);
        // 结合公式计算
        return (p1) / (p2 * p3);
    }
}
