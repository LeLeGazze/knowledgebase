package com.castle.fortress.admin.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;


/**
 * 仅供查询字段名=>类型 结构 接收数据用
 */
@Data
@ApiModel(value = "字段名=>字段类型")
public class FieldTypeMapDto implements Serializable {
    private String name;
    private String type;
}
