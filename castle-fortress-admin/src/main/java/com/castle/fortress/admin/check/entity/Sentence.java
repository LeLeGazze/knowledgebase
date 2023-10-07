package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Sentence {
    /**
     * 文本
     */
    private String text = "";

    /**
     * 相似度
     */
    private Double similar = 0.0;

    /**
     * 是否重复，0否，1是，默认0，重复标准就是，当相似度大于60%时，就认为该句子是重复的
     */
    private Integer duplicatesState = 0;

    /**
     * 与该句子最相似的句子
     */

    private String title;
    private String fileName;
    private String date;
    private int length;
    private String similarToTxt = "";
    private String paragraphName;
    /**
     * 重复句子下标，可能存在多个重复句子，所以使用集合记录
     */
    private List<Integer> duplicatesIndex = new ArrayList<>();

    private List<Sentence> duplicatesList = new ArrayList<>();

    public Sentence(String text) {
        this.text = text;
    }

    public Sentence() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sentence sentence = (Sentence) o;
        return length == sentence.length && Objects.equals(text, sentence.text) && Objects.equals(title, sentence.title) && Objects.equals(fileName, sentence.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, title, fileName, length);
    }
}
