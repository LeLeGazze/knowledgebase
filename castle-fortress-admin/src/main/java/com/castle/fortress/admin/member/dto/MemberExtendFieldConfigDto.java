package com.castle.fortress.admin.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户扩展字段配置表 实体类
 *
 * @author whc
 * @since 2022-11-23
 */
@Data
@ApiModel(value = "memberExtendFieldConfig对象", description = "用户扩展字段配置表")
public class MemberExtendFieldConfigDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    @JsonProperty("id")
    private Long id;
    @ApiModelProperty(value = "字段标题")
    @JsonProperty("colTitle")
    private String colTitle;
    @ApiModelProperty(value = "字段名")
    @JsonProperty("colName")
    private String colName;
    @ApiModelProperty(value = "属性名")
    @JsonProperty("propName")
    private String propName;
    @ApiModelProperty(value = "字段类型")
    @JsonProperty("colType")
    private String colType;

    @ApiModelProperty(value = "表单类型")
    @JsonProperty("formType")
    private Integer formType;
    @ApiModelProperty(value = "排序字段")
    @JsonProperty("sort")
    private Integer sort;

    @ApiModelProperty("集合数据类型")
    private Integer dataType;

    @ApiModelProperty("集合数据配置")
    private String dataConfig;


}
