package com.castle.fortress.admin.knowledge.dto;

import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 主题知识仓库 实体类
 *
 * @author lyz
 * @since 2023-04-24
 */
@Data
@ApiModel(value = "kbSubjectWarehouse对象", description = "主题知识仓库")
public class KbSubjectWarehouseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键ID")
    @JsonProperty("id")
    private Long id;
    @ApiModelProperty(value = "仓库名称")
    @JsonProperty("name")
    private String name;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "模型")
    @JsonProperty("modelId")
    private Long modelId;
    @ApiModelProperty(value = "是否首页显示")
    @JsonProperty("isShow")
    private Integer isShow;
    @ApiModelProperty(value = "封面")
    @JsonProperty("cover")
    private String cover;
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
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;

    @ApiModelProperty(value = "知识库下的知识LIST")
    @JsonProperty("knowledgeAuthList")
    private List<KbWarehouseAuthDto> knowledgeAuthList;
    @ApiModelProperty(value = "知识库下的知识LIST")
    @JsonProperty("classAuthsList")
    private List<KbWarehouseAuthDto> classAuthsList;
    @ApiModelProperty(value = "目录下的分类")
    @JsonProperty("children")
    private List<KbCategoryEntity> children ;


}
