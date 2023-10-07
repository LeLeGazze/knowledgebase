package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 手机验证码 实体类
 *
 * @author castle
 * @since 2021-07-13
 */
@Data
@ApiModel(value = "sysCaptcha对象", description = "手机验证码")
public class SysCaptchaDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "手机号")
	@JsonProperty("phone")
	private String phone;
	@ApiModelProperty(value = "验证码")
	@JsonProperty("captcha")
	private String captcha;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;

}
