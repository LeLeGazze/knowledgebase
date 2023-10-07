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
 * 会员收货地址表 实体类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Data
@ApiModel(value = "memberAddress对象", description = "会员收货地址表")
public class MemberAddressDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员ID")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "收货人")
	@JsonProperty("consignee")
	private String consignee;
	@ApiModelProperty(value = "手机号")
	@JsonProperty("phone")
	private String phone;
	@ApiModelProperty(value = "省")
	@JsonProperty("province")
	private String province;
	@ApiModelProperty(value = "省地区编号形式")
	@JsonProperty("provinceCode")
	private String provinceCode;
	@ApiModelProperty(value = "市")
	@JsonProperty("city")
	private String city;

	@ApiModelProperty(value = "市地区编号形式")
	@JsonProperty("cityCode")
	private String cityCode;

	@ApiModelProperty(value = "区")
	@JsonProperty("area")
	private String area;

	@ApiModelProperty(value = "区地区编号形式")
	@JsonProperty("areaCode")
	private String areaCode;

	@ApiModelProperty(value = "详细地址")
	@JsonProperty("address")
	private String address;
	@ApiModelProperty(value = "是否为默认地址1.是2.否")
	@JsonProperty("isDefault")
	private Integer isDefault;

	@ApiModelProperty(value = "是否为默认地址bool")
	@JsonProperty("isDefaultBool")
	private Boolean isDefaultBool;

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
