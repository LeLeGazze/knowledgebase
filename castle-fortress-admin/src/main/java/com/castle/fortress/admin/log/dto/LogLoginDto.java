package com.castle.fortress.admin.log.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 登录操作日志 实体类
 *
 * @author castle
 * @since 2021-04-01
 */
@Data
@ApiModel(value = "logLogin对象", description = "登录操作日志")
public class LogLoginDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "登录路径")
	@JsonProperty("invokeUrl")
	private String invokeUrl;
	@ApiModelProperty(value = "IP地址")
	@JsonProperty("remoteAddr")
	private String remoteAddr;
	@ApiModelProperty(value = "登录地址")
	@JsonProperty("address")
	private String address;
	@ApiModelProperty(value = "用户浏览器")
	@JsonProperty("cusBrowser")
	private String cusBrowser;
	@ApiModelProperty(value = "用户操作系统")
	@JsonProperty("cusOs")
	private String cusOs;
	@ApiModelProperty(value = "调用参数")
	@JsonProperty("invokeParams")
	private String invokeParams;
	@ApiModelProperty(value = "调用状态")
	@JsonProperty("invokeStatus")
	private String invokeStatus;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "登录时间")
	@JsonProperty("invokeTime")
	private Date invokeTime;
	@ApiModelProperty(value = "登录结果")
	@JsonProperty("resultData")
	private String resultData;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "耗时(毫秒)")
	@JsonProperty("elapsedTime")
	private Long elapsedTime;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@DateTimeFormat(
					pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
					pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("invokeTimeStart")
	private Date invokeTimeStart;
	@DateTimeFormat(
					pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
					pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("invokeTimeEnd")
	private Date invokeTimeEnd;
	/**
	 * 登录方式  枚举 LoginMethodEnum
	 */
	@ApiModelProperty(value = "登录方式")
	@JsonProperty("loginMethod")
	private Integer loginMethod;
	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID")
	@JsonProperty("userId")
	private Long userId;

	@ApiModelProperty(value = "用户名称")
	@JsonProperty("userName")
	private String userName;
}
