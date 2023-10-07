package com.castle.fortress.admin.log.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户操作记录日志表 实体类
 *
 * @author castle
 * @since 2021-03-31
 */
@Data
public class LogOperationDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;
	private String invokeUrl;
	private String invokeParams;
	private String className;
	private String methodName;
	private String invokeStatus;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long invokeUserId;
	private String invokeUserName;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	private Date invokeTime;
	private String resultData;
	@JsonSerialize(using = ToStringSerializer.class)
	private Long elapsedTime;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
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
	 * 操作位置
	 */
	private String operLocation;

	/**
	 * 操作类型
	 */
	private Integer operType;
}
