package com.castle.fortress.admin.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 定时任务调用日志 实体类
 *
 * @author castle
 * @since 2021-04-02
 */
@Data
@TableName("castle_log_job")
public class LogJobEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 任务名
	*/
	private String taskName;
	/**
	 * 任务ID
	*/
	private String taskId;
	/**
	 * 调用参数
	*/
	private String invokeParams;
	/**
	 * 调用状态
	*/
	private String invokeStatus;
	/**
	 * 调用时间
	*/
	private Date invokeTime;
	/**
	 * 耗时(毫秒)
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long elapsedTime;
	/**
	 * 创建时间
	*/
	private Date createTime;

	public LogJobEntity() {
	}

	public LogJobEntity(String taskName, String taskId, String invokeParams, String invokeStatus, Date invokeTime, Long elapsedTime) {
		this.taskName = taskName;
		this.taskId = taskId;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.elapsedTime = elapsedTime;
	}

	public LogJobEntity(Long id, String taskName, String taskId, String invokeParams, String invokeStatus, Date invokeTime, Long elapsedTime, Date createTime) {
		this.id = id;
		this.taskName = taskName;
		this.taskId = taskId;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.elapsedTime = elapsedTime;
		this.createTime = createTime;
	}
}
