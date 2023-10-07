package com.castle.fortress.admin.check.service;
import java.util.HashMap;

public interface SimDegreeAlgorithm {

    /**
     * 计算两个句子的相似度
     * @param a
     * @param b
     * @return double
     **/
    HashMap<String,Object> getSimDegree(String a, String b,int weight);
}
