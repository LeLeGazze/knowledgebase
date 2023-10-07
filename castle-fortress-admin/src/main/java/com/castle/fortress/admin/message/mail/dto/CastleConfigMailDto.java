package com.castle.fortress.admin.message.mail.dto;

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
 * 邮件配置表 实体类
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Data
@ApiModel(value = "castleConfigMail对象", description = "邮件配置表")
public class CastleConfigMailDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "编码code")
	@JsonProperty("code")
	private String code;
	@ApiModelProperty(value = "邮箱SMTP地址")
	@JsonProperty("smtp")
	private String smtp;
	@ApiModelProperty(value = "端口号")
	@JsonProperty("port")
	private Integer port;
	@ApiModelProperty(value = "邮箱账号")
	@JsonProperty("mail")
	private String mail;
	@ApiModelProperty(value = "邮箱密码")
	@JsonProperty("password")
	private String password;
	@ApiModelProperty(value = "邮箱昵称")
	@JsonProperty("nickName")
	private String nickName;
	@ApiModelProperty(value = "状态 YesNo  yes启用，no禁用")
	@JsonProperty("status")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建部门")
	@JsonProperty("createDept")
	private Long createDept;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建职位")
	@JsonProperty("createPost")
	private Long createPost;
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
	@ApiModelProperty(value = "是否删除 YesNoEnum。 yes删除；no未删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;

}
