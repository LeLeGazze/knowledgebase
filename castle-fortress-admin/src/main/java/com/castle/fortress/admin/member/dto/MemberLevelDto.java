package com.castle.fortress.admin.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员等级表 实体类
 *
 * @author whc
 * @since 2022-12-29
 */
@Data
@ApiModel(value = "memberLevel对象", description = "会员等级表")
public class MemberLevelDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "唯一主键")
    @JsonProperty("id")
    private Long id;
    @ApiModelProperty(value = "级别")
    @JsonProperty("level")
    private Integer level;
    @ApiModelProperty(value = "名称")
    @JsonProperty("name")
    private String name;
    @ApiModelProperty(value = "升级条件（三种）")
    @JsonProperty("upConditions")
    private Integer upConditions;
    @ApiModelProperty(value = "订单总额")
    @JsonProperty("orderTotal")
    private String orderTotal;
    @ApiModelProperty(value = "订单次数")
    @JsonProperty("orderCount")
    private String orderCount;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "指定商品id")
    @JsonProperty("goodsId")
    private Long goodsId;
    @ApiModelProperty(value = "折扣")
    @JsonProperty("discount")
    private String discount;
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "创建者")
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
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "更新者")
    @JsonProperty("updateUser")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty(value = "更新时间")
    @JsonProperty("updateTime")
    private Date updateTime;
    @ApiModelProperty(value = "状态")
    @JsonProperty("status")
    private Integer status;
    @ApiModelProperty(value = "删除状态")
    @JsonProperty("isDeleted")
    private Integer isDeleted;
    @ApiModelProperty(value = "创建者姓名")
    @JsonProperty("createUserName")
    private String createUserName;

    @ApiModelProperty(value = "关联商品名称")
    @JsonProperty("goodsName")
    private String goodsName;

}
