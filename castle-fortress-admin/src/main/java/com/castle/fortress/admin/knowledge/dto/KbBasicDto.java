package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 知识基本表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-17
 */
@Data
@ApiModel(value = "kbBasic对象", description = "知识基本表")
public class KbBasicDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主题仓库ID")
    @JsonProperty("whId")
    private Long whId;
    @ApiModelProperty(value = "标题")
    @JsonProperty("title")
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "作者")
    @JsonProperty("auth")
    private Long auth;
    @ApiModelProperty(value = "旧作者")
    @JsonProperty("oldAuth")
    private Long oldAuth;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "部门ID")
    @JsonProperty("deptId")
    private Long deptId;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "发布时间")
    @JsonProperty("pubTime")
    private Date pubTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "过期时间")
    @JsonProperty("expTime")
    private Date expTime;
    @ApiModelProperty(value = "附件")
    @JsonProperty("attachment")
    private String attachment;
    @ApiModelProperty(value = "标签")
    @JsonProperty("label")
    private String label;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "知识仓库分类ID")
    @JsonProperty("categoryId")
    private Long categoryId;
    @ApiModelProperty(value = "模型编码")
    @JsonProperty("modelCode")
    private String modelCode;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "模型id")
    @JsonProperty("modelId")
    private Long modelId;
    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;
    @ApiModelProperty(value = "排序号")
    @JsonProperty("sort")
    private Integer sort;
    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private Integer status;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private Date createTime;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "创建人")
    @JsonProperty("createUser")
    private Long createUser;
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;
    @ApiModelProperty(value = "标记删除")
    @JsonProperty("isDeleted")
    private Integer isDeleted;

    @ApiModelProperty(value = "所属分类")
    @JsonProperty("authId")
    private Integer authId;

    @ApiModelProperty(value = "作者")
    @JsonProperty("userName")
    private String userName;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "发布起始时间")
    @JsonProperty("fromTime")
    private Date fromTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "结束起始时间")
    @JsonProperty("endTime")
    private Date endTime;
    @ApiModelProperty(value = "知识分类list")
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "目录id")
    @JsonProperty("swId")
    private String  swId;
    /**
     * 词云
     */
    @ApiModelProperty(value = "词云")
    @JsonProperty("wordCloud")
    private String wordCloud;
}
