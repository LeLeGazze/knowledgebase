package com.castle.fortress.admin.knowledge.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
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
 * 视频库 实体类
 *
 * @author
 * @since 2023-05-13
 */
@Data
@ApiModel(value = "kbVideo对象", description = "视频库")
public class KbVideoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "目录id")
    @JsonProperty("swId")
    private Long swId;
    @ApiModelProperty(value = "标签")
    @JsonProperty("title")
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "作者")
    @JsonProperty("auth")
    private Long auth;
    @ApiModelProperty(value = "作者名字")
    @JsonProperty("authName")
    private String authName;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "部门ID")
    @JsonProperty("deptId")
    private Long deptId;
    @ApiModelProperty(value = "部门名称")
    @JsonProperty("deptName")
    private String deptName;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "发布时间")
    @JsonProperty("pubTime")
    private Date pubTime;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "知识仓库分类ID")
    @JsonProperty("categoryId")
    private Long categoryId;
    @ApiModelProperty(value = "知识仓库分类名字")
    @JsonProperty("categoryName")
    private String categoryName;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "过期时间")
    @JsonProperty("expTime")
    private Date expTime;
    @ApiModelProperty(value = "封面")
    @JsonProperty("cover")
    private String cover;
    @ApiModelProperty(value = "视频播放URL")
    @JsonProperty("videoUrl")
    private String videoUrl;
    @ApiModelProperty(value = "视频存储路径")
    @JsonProperty("videoSrc")
    private List<Object> videoSrc;
    @ApiModelProperty(value = "标签")
    @JsonProperty("label")
    private List<String> label;
    @ApiModelProperty(value = "标签")
    @JsonProperty("labels")
    private  List<KbModelLabelEntity> labels;
    @ApiModelProperty(value = "备注")
    @JsonProperty("remark")
    private String remark;
    @ApiModelProperty(value = "排序号")
    @JsonProperty("sort")
    private Integer sort;
    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private Integer status;
    @ApiModelProperty(value = "收藏状态")
    @JsonProperty("collectStatus")
    private Integer collectStatus;
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
    @ApiModelProperty(value = "标记删除")
    @JsonProperty("isDeleted")
    private Integer isDeleted;
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;
    @ApiModelProperty(value = "知识分类list")
    @JsonProperty("categoryIds")
    private List<Long> categoryIds;

    @ApiModelProperty(value = "知识目录名字")
    @JsonProperty("subjectWarehouseName")
    private String subjectWarehouseName;

    private Integer readCount; // 浏览次数
    private Integer commentsCount; //评论次数
    private boolean isUpdateAuthority; // 是否有编辑
    private boolean isDeleteCommentsAuthority; // 是否有删除评论权限
    public Integer getCommentsCount() {
        return commentsCount == null ? 0 : commentsCount;
    }
    public Integer getReadCount() {
        return readCount == null ? 0 : readCount;
    }

}
