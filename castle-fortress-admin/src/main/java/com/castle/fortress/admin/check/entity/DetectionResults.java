package com.castle.fortress.admin.check.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetectionResults {
    private String title;
    private String length;
    private BigDecimal replicationRatio;
    private String code;
    private String count;
}
