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
 * 会员银行卡表 实体类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Data
@ApiModel(value = "memberBankCard对象", description = "会员银行卡表")
public class MemberBankCardDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员ID")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "银行卡号")
	@JsonProperty("cardNum")
	private String cardNum;
	@ApiModelProperty(value = "真实姓名")
	@JsonProperty("realName")
	private String realName;
	@ApiModelProperty(value = "身份证号")
	@JsonProperty("idCardNum")
	private String idCardNum;
	@ApiModelProperty(value = "开户行名称(分行)")
	@JsonProperty("openAccount")
	private String openAccount;
	@ApiModelProperty(value = "所属银行")
	@JsonProperty("bankName")
	private String bankName;
	@ApiModelProperty(value = "银行预留手机号")
	@JsonProperty("phone")
	private String phone;
	@ApiModelProperty(value = "银行卡照片")
	@JsonProperty("cardUrl")
	private String cardUrl;
	@ApiModelProperty(value = "卡片有效期")
	@JsonProperty("validDate")
	private String validDate;
	@ApiModelProperty(value = "卡类型DC(借记卡),  CC(贷记卡),  SCC(准贷记卡), DCC(存贷合一卡), PC(预付卡)")
	@JsonProperty("cardType")
	private String cardType;
	@ApiModelProperty(value = "银行类型")
	@JsonProperty("bankType")
	private String bankType;
	@ApiModelProperty(value = "支行所在省市")
	@JsonProperty("city")
	private String city;
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
