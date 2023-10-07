package com.castle.fortress.admin.knowledge.dto;

import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 主题知识仓库权限表 实体类
 *
 * @author
 * @since 2023-04-24
 */
@Data
@ApiModel(value = "kbWarehouseAuth对象", description = "主题知识仓库权限表")
public class KbWarehouseAuthDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    @JsonProperty("id")
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "知识仓库")
    @JsonProperty("whId")
    private Long whId;
    @ApiModelProperty(value = "分类")
    @JsonProperty("category")
    private String category;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "人员ID")
    @JsonProperty("userId")
    private Long userId;
    @ApiModelProperty(value = "人员名称")
    @JsonProperty("userName")
    private String userName;
    @ApiModelProperty(value = "知识库权限枚举")
    @JsonProperty("whAuth")
    private Integer whAuth;
    @ApiModelProperty(value = "知识库下的知识枚举")
    @JsonProperty("kbAuth")
    private Integer kbAuth;

    @ApiModelProperty(value = "知识库下的知识LIST")
    @JsonProperty("knowledgeAuthList")
    private List<KbWarehouseAuthDto> knowledgeAuthList;

    @ApiModelProperty(value = "知识库下分类权限全局LIST")
    @JsonProperty("classAuthsList")
    private List<KbWarehouseAuthDto> classAuthsList;

    @ApiModelProperty(value = "单独分类权限List")
    @JsonProperty("authsList")
    private List<KbWarehouseAuthDto> authsList;

    @ApiModelProperty(value = "状态 1为用户 2为群组  3为部门")
    @JsonProperty("status")
    private Integer status;

    @ApiModelProperty(value = "排序")
    @JsonProperty("sort")
    private Integer sort;

    public KbWarehouseAuthDto(Long whId, List<KbWarehouseAuthDto> authsList) {
        this.whId = whId;
        this.authsList = authsList;
    }


    public KbWarehouseAuthDto(Long whId, List<KbWarehouseAuthDto> knowledgeAuthList, List<KbWarehouseAuthDto> classAuthsList) {
        this.whId = whId;
        this.knowledgeAuthList = knowledgeAuthList;
        this.classAuthsList = classAuthsList;
    }


    public KbWarehouseAuthDto() {
    }
}
