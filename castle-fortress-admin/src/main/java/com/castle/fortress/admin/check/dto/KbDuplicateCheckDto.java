package com.castle.fortress.admin.check.dto;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.check.utils.IdUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 知识库查重表 实体类
 *
 * @author
 * @since 2023-07-15
 */
@Data
@ApiModel(value = "kbDuplicateCheck对象", description = "知识库查重表")
public class KbDuplicateCheckDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    @JsonProperty("id")
    private String id;
    @ApiModelProperty(value = "标题")
    @JsonProperty("title")
    private String title;
    @ApiModelProperty(value = "作者")
    @JsonProperty("author")
    private String author;
    @ApiModelProperty(value = "文件后缀")
    @JsonProperty("fileSuffix")
    private String fileSuffix;
    @ApiModelProperty(value = "上传路径")
    @JsonProperty("uploadPath")
    private Object uploadPath;
    @ApiModelProperty(value = "状态：1=未开始，2=处理中，3=转换PDF中，4=成功，5=失败")
    @JsonProperty("status")
    private Integer status;
    @ApiModelProperty(value = "检测类型")
    @JsonProperty("type")
    private String type;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "创建时间")
    @JsonProperty("createTime")
    private Date createTime;
    @ApiModelProperty(value = "PDF转换成功后地址")
    @JsonProperty("pdfPath")
    private String pdfPath;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "检测时间")
    @JsonProperty("detectionTime")
    private Date detectionTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "结束时间")
    @JsonProperty("deadlineTime")
    private Date deadlineTime;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "开始时间")
    @JsonProperty("startTime")
    private Date startTime;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "结束时间")
    @JsonProperty("endTime")
    private Date endTime;
    @ApiModelProperty(value = "html原文地址")
    @JsonProperty("htmlPath")
    private String htmlPath;    //html原文地址
    @ApiModelProperty(value = "权重")
    @JsonProperty("weight")
    private int weight; //权重
    @ApiModelProperty(value = "上下文长度")
    @JsonProperty("contextLength")
    private int contextLength;    //上下文长度
    @ApiModelProperty(value = "最大连续数")
    @JsonProperty("maxNumberContiguous")
    private int maxNumberContiguous;    //最大连续数
    @ApiModelProperty(value = "读取数据长度")
    @JsonProperty("readDataLength")
    private int readDataLength;    //读取数据长度
    @ApiModelProperty(value = "预计结束时间")
    @JsonProperty("estimatedEndTime")
    private String estimatedEndTime; //预计结束时间
    @ApiModelProperty(value = "用户")
    @JsonProperty("createUser")
    private Long createUser;
    @ApiModelProperty(value = "上传类型")
    @JsonProperty("uploadType")
    private int uploadType;
    @ApiModelProperty(value = "对比路径")
    @JsonProperty("contrastPath")
    private Object contrastPath;

    @ApiModelProperty("处理类名字")
    @JsonProperty("beanName")
    private  String  beanName;
    public String getId() {
        return StrUtil.isEmpty(id) ? IdUtil.getId() : id;
    }

    public void setContrastPath(Object contrastPath) {
        if (contrastPath instanceof String) {
            this.contrastPath = JSONObject.parseObject(String.valueOf(contrastPath));
        } else {
            this.contrastPath = contrastPath;

        }
    }

    public void setUploadPath(Object uploadPath) {
        if (uploadPath instanceof String) {
            this.uploadPath = JSONObject.parseObject(String.valueOf(uploadPath));
        } else {
            this.uploadPath = uploadPath;

        }
    }

}
