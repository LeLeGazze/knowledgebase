package com.castle.fortress.admin.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 短信发送记录 实体类
 *
 * @author Mgg
 * @since 2021-12-06
 */
@Data
@TableName("castle_log_sms")
public class LogSmsEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 短信编码
	*/
	private String smsCode;
	/**
	 * 平台类型
	*/
	private Integer platform;
	/**
	 * 手机号
	*/
	private String mobile;
	/**
	 * 参数1
	*/
	private String params1;
	/**
	 * 参数2
	*/
	private String params2;
	/**
	 * 参数3
	*/
	private String params3;
	/**
	 * 参数4
	*/
	private String params4;
	/**
	 * 创建时间
	*/
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createDate;
	/**
	 * 短信类型 0：注册验证码  1：登录验证码
	*/
	private Integer type;


	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("主键id")
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;

	@ApiModelProperty("状态")
	private Integer status;

}
