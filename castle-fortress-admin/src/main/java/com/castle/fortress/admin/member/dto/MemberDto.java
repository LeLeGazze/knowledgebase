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
 * 会员表 实体类
 *
 * @author Mgg
 * @since 2021-11-25
 */
@Data
@ApiModel(value = "member对象", description = "会员表")
public class MemberDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "用户名")
	@JsonProperty("userName")
	private String userName;
	@ApiModelProperty(value = "密码")
	@JsonProperty("password")
	private String password;
	@ApiModelProperty(value = "手机号")
	@JsonProperty("phone")
	private String phone;
	@ApiModelProperty(value = "昵称")
	@JsonProperty("nickName")
	private String nickName;

	@ApiModelProperty(value = "微信openid")
	@JsonProperty("openid")
	private String openid;
	@ApiModelProperty(value = "微信unionid")
	@JsonProperty("unionid")
	private String unionid;
	@ApiModelProperty(value = "头像")
	@JsonProperty("avatar")
	private String avatar;
	@ApiModelProperty(value = "性别")
	@JsonProperty("gender")
	private Integer gender;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "生日")
	@JsonProperty("birthday")
	private Date birthday;

	@ApiModelProperty(value = "会员状态(正常、禁用、黑名单)")
	@JsonProperty("status")
	private Integer status;

	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
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
	@ApiModelProperty(value = "是否删除YesNoEnum")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

	@ApiModelProperty(value = "短信验证码/登录用")
	private String smsCode;


	@ApiModelProperty(value = "标签ids")
	private String tags;


	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "开始时间(筛选用)")
	private Date startTime;

	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "结束时间(筛选用)")
	private Date endTime;

	@ApiModelProperty(value = "会员等级id")
	@JsonProperty("levelId")
	private Long levelId;


	@ApiModelProperty(value = "省名称")
	@JsonProperty("provinceName")
	private String provinceName;

	@ApiModelProperty(value = "省代号")
	@JsonProperty("provinceCode")
	private String provinceCode;


	@ApiModelProperty(value = "市名称")
	@JsonProperty("cityName")
	private String cityName;

	@ApiModelProperty(value = "市代号")
	@JsonProperty("cityCode")
	private String cityCode;

	@ApiModelProperty(value = "区名称")
	@JsonProperty("areaName")
	private String areaName;

	@ApiModelProperty(value = "区代号")
	@JsonProperty("areaCode")
	private String areaCode;


}
