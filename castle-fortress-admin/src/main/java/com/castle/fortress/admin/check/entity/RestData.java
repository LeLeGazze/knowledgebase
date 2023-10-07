package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestData {
    private String id;
    private String famous; //篇名
    private String authorName; //作者
    private String detectionType;//检测类型

    private String detectionTime;//检测时间
    private String deadline; //截止日期
    private int totalNumber; // 总字数
    private int NumberOfRepeated;// 重复字符数
    private int maxNumber;// 最大的重复
    private BigDecimal replicationRatio;// 复制比
    private BigDecimal maxReplicationRatio;// 最大复制比
    private List<RestDataList> restDataLists = new ArrayList<>();
    private List<DetectionResults> resultsList =new ArrayList<>(); //检测结果
}
