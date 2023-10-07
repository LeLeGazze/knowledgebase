package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestDataList {
    private List<TmpParseData> original = new ArrayList<>(); // 原文内容
    private List<HistoryMetaData> metaData = new ArrayList<>();
    private BigDecimal textCopyRatio;// 文字复制比
    private int count;
    private int length;
    private String code;
    private int sort;
}
