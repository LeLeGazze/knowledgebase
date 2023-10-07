package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "kbVideo对象", description = "视频表")
public class KbVideoShowDto {


    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @JsonProperty("id")
    private String id;

    @ApiModelProperty(value = "目录id")
    @JsonProperty("swId")
    private String swId;
    @ApiModelProperty(value = "查询知识分类list")
    @JsonProperty("swIds")
    private List<Long> swIds;
    @ApiModelProperty(value = "标题")
    @JsonProperty("title")
    private String title;
    @ApiModelProperty(value = "查询知识分类list")
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;
    @ApiModelProperty(value = "分类名称")
    @JsonProperty("categoryName")
    private String categoryName;

    @ApiModelProperty(value = "作者")
    @JsonProperty("userName")
    private String userName;
    @ApiModelProperty(value = "作者Id")
    @JsonProperty("createUser")
    private Long createUser;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private Date createTime;

    @ApiModelProperty(value = "发布起始时间")
    @JsonProperty("fromTime")
    private String fromTime;

    @ApiModelProperty(value = "结束起始时间")
    @JsonProperty("endTime")
    private String endTime;

    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private Integer status;


    @ApiModelProperty(value = "浏览量")
    @JsonProperty("readCount")
    private Integer readCount;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "部门ID")
    @JsonProperty("deptId")
    private Long deptId;

    @ApiModelProperty(value = "所属分类")
    @JsonProperty("authId")
    private Integer authId;

    @ApiModelProperty(value = "排序类型")
    @JsonProperty("sortType")
    private String sortType;

    @ApiModelProperty(value = "排序字段")
    @JsonProperty("sortField")
    private String sortField;

    @ApiModelProperty(value = "摘要")
    @JsonProperty("remark")
    private String remark;
    @ApiModelProperty(value = "封面")
    @JsonProperty("cover")
    private String cover;
}
