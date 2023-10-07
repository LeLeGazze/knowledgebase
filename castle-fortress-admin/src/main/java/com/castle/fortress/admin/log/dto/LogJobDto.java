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
 * 定时任务调用日志 实体类
 *
 * @author castle
 * @since 2021-04-02
 */
@Data
public class LogJobDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("id")
	private Long id;
	@JsonProperty("taskName")
	private String taskName;
	@JsonProperty("taskId")
	private String taskId;
	@JsonProperty("invokeParams")
	private String invokeParams;
	@JsonProperty("invokeStatus")
	private String invokeStatus;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("invokeTime")
	private Date invokeTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonProperty("elapsedTime")
	private Long elapsedTime;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
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
