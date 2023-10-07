package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.math.BigDecimal;

import static com.castle.fortress.admin.check.service.impl.KbDuplicateCheckServiceImpl.getPercentage;

@Data
public class HistoryMetaData {
    private String paragraphName;
    private String date;
    private String title;
    private int length; // 总长度
    private BigDecimal repeatRatio; //重复比率
    private int repeatCount; // 重复字数
    private String fileName;

    public HistoryMetaData() {
    }

    public HistoryMetaData(String date, String title, int length, int repeatCount, String fileName, String paragraphName) {
        this.date = date;
        this.title = title;
        this.length = length;
        this.repeatRatio = getPercentage(String.valueOf(repeatCount), String.valueOf(length));
        this.repeatCount = repeatCount;
        this.fileName = fileName;
        this.paragraphName = paragraphName;
    }
}
