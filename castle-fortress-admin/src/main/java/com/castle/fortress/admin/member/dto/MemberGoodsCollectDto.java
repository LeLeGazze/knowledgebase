package com.castle.fortress.admin.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 会员商品收藏表 实体类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Data
@ApiModel(value = "memberGoodsCollect对象", description = "会员商品收藏表")
public class MemberGoodsCollectDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "商品id")
	@JsonProperty("goodsId")
	private Long goodsId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	@JsonProperty("memberId")
	private Long memberId;
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
	@ApiModelProperty(value = "删除状态 YesNoEnum。 yes删除；no未删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
