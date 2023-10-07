package com.castle.fortress.admin.member.dto;

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
 * 会员登录日志表 实体类
 *
 * @author Mgg
 * @since 2021-11-26
 */
@Data
@ApiModel(value = "memberLoginLog对象", description = "会员登录日志表")
public class MemberLoginLogDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键id")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "调用路径")
	@JsonProperty("invokeUrl")
	private String invokeUrl;
	@ApiModelProperty(value = "调用ip")
	@JsonProperty("remoteAddr")
	private String remoteAddr;
	@ApiModelProperty(value = "登录地址")
	@JsonProperty("address")
	private String address;
	@ApiModelProperty(value = "浏览器")
	@JsonProperty("cusBrowser")
	private String cusBrowser;
	@ApiModelProperty(value = "操作系统")
	@JsonProperty("cusOs")
	private String cusOs;
	@ApiModelProperty(value = "调用参数")
	@JsonProperty("invokeParams")
	private String invokeParams;
	@ApiModelProperty(value = "调用状态 00 成功 01 失败")
	@JsonProperty("invokeStatus")
	private String invokeStatus;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "调用时间")
	@JsonProperty("invokeTime")
	private Date invokeTime;
	@ApiModelProperty(value = "响应结果")
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

	@ApiModelProperty(value = "登录方式 枚举 LoginMethodEnum")
	@JsonProperty("loginMethod")
	private Integer loginMethod;

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
	 * 会员昵称
	 */
	@ApiModelProperty(value = "会员昵称")
	@JsonProperty("nickName")
	private String nickName;
}
