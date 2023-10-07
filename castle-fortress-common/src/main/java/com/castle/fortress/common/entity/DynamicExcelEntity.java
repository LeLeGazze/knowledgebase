package com.castle.fortress.common.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 动态表头excel实体类
 * @author castle
 */
@Data
public class DynamicExcelEntity<T> {
    private T dto;
    private List<Map<String,String>> headerList;
    private String fileName="导出文件";
}
