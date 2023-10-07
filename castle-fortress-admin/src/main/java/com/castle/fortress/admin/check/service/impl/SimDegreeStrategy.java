package com.castle.fortress.admin.check.service.impl;

import com.castle.fortress.admin.check.service.SimDegreeAlgorithm;

import java.util.Map;

/**
 * @author god23bin
 * @description 相似度算法的策略
 */
public class SimDegreeStrategy {

    private SimDegreeAlgorithm simDegreeAlgorithm;

    public SimDegreeStrategy(SimDegreeAlgorithm simDegreeAlgorithm) {
        this.simDegreeAlgorithm = simDegreeAlgorithm;
    }

    public Map<String, Object> getSimDegree(String a, String b,int weight) {
        return simDegreeAlgorithm.getSimDegree(a, b, weight);
    }
}
