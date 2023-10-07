package com.castle.fortress.admin.message.sms.dto;

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
 * 短信配置表 实体类
 *
 * @author castle
 * @since 2021-04-12
 */
@Data
@ApiModel(value = "configSms对象", description = "短信配置表")
public class ConfigSmsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "短信编码")
	@JsonProperty("smsCode")
	private String smsCode;
	@ApiModelProperty(value = "平台类型")
	@JsonProperty("platform")
	private Integer platform;
	@ApiModelProperty(value = "平台名称")
	@JsonProperty("platformName")
	private String platformName;
	@ApiModelProperty(value = "短信配置")
	@JsonProperty("smsConfig")
	private String smsConfig;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@ApiModelProperty(value = "状态")
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
	@ApiModelProperty(value = "创建部门名称")
	@JsonProperty("createDeptName")
	private String createDeptName;
	@ApiModelProperty(value = "创建职位名称")
	@JsonProperty("createPostName")
	private String createPostName;
	@ApiModelProperty(value = "数据权限校验标识")
	@JsonProperty("dataAuthFlag")
	private Boolean dataAuthFlag;
	@ApiModelProperty(value = "sms平台配置对象")
	private SmsPlatFormDto smsPlatFormDto;

}
