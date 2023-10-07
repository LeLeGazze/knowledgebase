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
 * PDF/word 等转换成PDF 实体类
 *
 * @author
 * @since 2023-05-08
 */
@Data
@ApiModel(value = "kbVideVersion对象", description = "PDF/word 等转换成PDF")
public class KbVideVersionDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    private Long id;
    @ApiModelProperty(value = "文件存储路径")
    @JsonProperty("fileUrl")
    private String fileUrl;
    @ApiModelProperty(value = "文件名称")
    @JsonProperty("fileName")
    private String fileName;
    @ApiModelProperty(value = "自动生成的URL图片路径")
    @JsonProperty("url")
    private List<String> url;
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
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "修改人")
    @JsonProperty("updateUser")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "修改时间")
    @JsonProperty("updateTime")
    private Date updateTime;
    @ApiModelProperty(value = "是否删除 YesNoEnum。 yes 删除；no 未删除")
    @JsonProperty("isDeleted")
    private Integer isDeleted;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "文章id")
    @JsonProperty("bId")
    private Long bId;
    @ApiModelProperty(value = "文章MD5值")
    @JsonProperty("fId")
    private String fId;
    @ApiModelProperty(value = "文件大小")
    @JsonProperty("fileSize")
    private String fileSize;
    @ApiModelProperty(value = "类型（扩展、基础）")
    @JsonProperty("type")
    private String type;
    @ApiModelProperty(value = "状态：1提取成功、2正在提取中、3：提取失败 ")
    @JsonProperty("status")
    private Integer status;
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;
    @ApiModelProperty(value = "前端访问路径")
    @JsonProperty("AccessPath")
    private String AccessPath;
    @ApiModelProperty(value = "文件下载路径")
    @JsonProperty("downloadUrl")
    private String downloadUrl;
    @ApiModelProperty(value = "前端文件下载")
    @JsonProperty("vides")
    private List<Long> vides;
    @ApiModelProperty(value = "版本")
    @JsonProperty("version")
    private String version;
    @ApiModelProperty(value = "baId")
    @JsonProperty("baId")
    private Long baId;
}
