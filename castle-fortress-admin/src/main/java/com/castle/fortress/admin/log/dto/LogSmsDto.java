package com.castle.fortress.admin.log.dto;

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
 * 短信发送记录 实体类
 *
 * @author Mgg
 * @since 2021-12-06
 */
@Data
@ApiModel(value = "logSms对象", description = "短信发送记录")
public class LogSmsDto implements Serializable {
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
	@ApiModelProperty(value = "手机号")
	@JsonProperty("mobile")
	private String mobile;
	@ApiModelProperty(value = "参数1")
	@JsonProperty("params1")
	private String params1;
	@ApiModelProperty(value = "参数2")
	@JsonProperty("params2")
	private String params2;
	@ApiModelProperty(value = "参数3")
	@JsonProperty("params3")
	private String params3;
	@ApiModelProperty(value = "参数4")
	@JsonProperty("params4")
	private String params4;
	@ApiModelProperty(value = "状态 0生效 1失效")
	@JsonProperty("status")
	private Integer status;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createDate")
	private Date createDate;
	@ApiModelProperty(value = "短信类型 0：注册验证码  1：登录验证码")
	@JsonProperty("type")
	private Integer type;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
