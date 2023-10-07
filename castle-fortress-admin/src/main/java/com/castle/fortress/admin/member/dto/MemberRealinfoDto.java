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
 * 会员真实信息表 实体类
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Data
@ApiModel(value = "memberRealinfo对象", description = "会员真实信息表")
public class MemberRealinfoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "真实姓名")
	@JsonProperty("realName")
	private String realName;
	@ApiModelProperty(value = "身份证号")
	@JsonProperty("cardNum")
	private String cardNum;
	@ApiModelProperty(value = "证件正面照片")
	@JsonProperty("imgFront")
	private String imgFront;
	@ApiModelProperty(value = "证件背面照片")
	@JsonProperty("imgBack")
	private String imgBack;
	@ApiModelProperty(value = "出生日期")
	@JsonProperty("birthday")
	private String birthday;
	@ApiModelProperty(value = "住址")
	@JsonProperty("address")
	private String address;
	@ApiModelProperty(value = "证件有效期")
	@JsonProperty("validityDate")
	private String validityDate;
	@ApiModelProperty(value = "发证机关")
	@JsonProperty("issuingAuthority")
	private String issuingAuthority;
	@ApiModelProperty(value = "民族")
	@JsonProperty("nation")
	private String nation;
	@ApiModelProperty(value = "性别")
	@JsonProperty("gender")
	private String gender;
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
	@ApiModelProperty(value = "营业执照类型")
	@JsonProperty("cardType")
	private String cardType;
	@ApiModelProperty(value = "法定代表人")
	@JsonProperty("person")
	private String person;
	@ApiModelProperty(value = "注册资本")
	@JsonProperty("capital")
	private String capital;
	@ApiModelProperty(value = "经营范围")
	@JsonProperty("business")
	private String business;
	@ApiModelProperty(value = "公司名称")
	@JsonProperty("enterpriseName")
	private String enterpriseName;
	@ApiModelProperty(value = "统一社会信用代码")
	@JsonProperty("creditCode")
	private String creditCode;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "成立日期")
	@JsonProperty("regDate")
	private Date regDate;
	@ApiModelProperty(value = "营业期限")
	@JsonProperty("businessTerm")
	private String businessTerm;
	@ApiModelProperty(value = "住所")
	@JsonProperty("residence")
	private String residence;
	@ApiModelProperty(value = "登记机关")
	@JsonProperty("regAuthority")
	private String regAuthority;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "发证日期")
	@JsonProperty("awardDate")
	private Date awardDate;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
