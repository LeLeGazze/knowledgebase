package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户钉钉信息表 实体类
 *
 * @author Mgg
 * @since 2022-12-13
 */
@Data
@ApiModel(value = "castleSysUserDing对象", description = "用户钉钉信息表")
public class CastleSysUserDingDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户id")
	@JsonProperty("userId")
	private Long userId;
	@ApiModelProperty(value = "钉钉unionid")
	@JsonProperty("dingUnionid")
	private String dingUnionid;
	@ApiModelProperty(value = "钉钉userid")
	@JsonProperty("dingUserid")
	private String dingUserid;
	@ApiModelProperty(value = "姓名")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "成员所属部门id列表")
	@JsonProperty("deptIdList")
	private String deptIdList;
	@ApiModelProperty(value = "角色列表(角色ID,名称,角色组名称)")
	@JsonProperty("roleList")
	private String roleList;
	@ApiModelProperty(value = "手机号")
	@JsonProperty("mobile")
	private String mobile;
	@ApiModelProperty(value = "邮箱")
	@JsonProperty("email")
	private String email;
	@ApiModelProperty(value = "企业邮箱。")
	@JsonProperty("orgEmail")
	private String orgEmail;
	@ApiModelProperty(value = "头像")
	@JsonProperty("avatar")
	private String avatar;

}
