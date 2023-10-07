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
 * 对外开放api调用日志 实体类
 *
 * @author castle
 * @since 2021-04-01
 */
@Data
@ApiModel(value = "logOpenapi对象", description = "对外开放api调用日志")
public class LogOpenapiDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "ip地址")
	@JsonProperty("remoteAddr")
	private String remoteAddr;
	@ApiModelProperty(value = "调用路径")
	@JsonProperty("invokeUrl")
	private String invokeUrl;
	@ApiModelProperty(value = "调用参数")
	@JsonProperty("invokeParams")
	private String invokeParams;
	@ApiModelProperty(value = "执行类")
	@JsonProperty("className")
	private String className;
	@ApiModelProperty(value = "执行方法")
	@JsonProperty("methodName")
	private String methodName;
	@ApiModelProperty(value = "调用状态")
	@JsonProperty("invokeStatus")
	private String invokeStatus;
	@ApiModelProperty(value = "秘钥")
	@JsonProperty("secretId")
	private String secretId;
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
}
