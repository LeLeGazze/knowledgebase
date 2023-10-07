package com.castle.fortress.admin.check.utils;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class IKUtil {

    /**
     * 以List的形式返回经过IK分词器处理的文本分词的结果
     * @param text 需要分词的文本
     * @return
     */
    public static List<String> divideText(String text) {
        if (null == text || "".equals(text.trim())) {
            return new ArrayList<>();
        }
        // 分词结果集
        List<String> resultList = new ArrayList<>();
        // 文本串 Reader
        StringReader re = new StringReader(text);
        // 智能分词： 合并数词和量词，对分词结果进行歧义判断
        IKSegmenter ik = new IKSegmenter(re, true);
        // Lexeme 词元对象
        Lexeme lex = null;
        try {
            // 分词，获取下一个词元
            while ((lex = ik.next()) != null) {
                // 获取词元的文本内容，存入结果集中
                resultList.add(lex.getLexemeText());
            }
        } catch (IOException e) {
            System.out.println("分词IO异常：" + e.getMessage());
        }
        return resultList;
    }
}
