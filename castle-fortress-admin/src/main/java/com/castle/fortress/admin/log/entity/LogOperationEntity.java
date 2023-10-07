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
 * 用户操作记录日志表 实体类
 *
 * @author castle
 * @since 2021-03-31
 */
@Data
@TableName("castle_log_operation")
public class LogOperationEntity implements Serializable {
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
	 * 调用路径
	*/
	private String invokeUrl;
	/**
	 * 调用参数
	*/
	private String invokeParams;
	/**
	 * 执行类
	*/
	private String className;
	/**
	 * 执行方法
	*/
	private String methodName;
	/**
	 * 调用状态 00 成功 01 失败
	*/
	private String invokeStatus;
	/**
	 * 调用人ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long invokeUserId;
	/**
	 * 调用人
	 */
	private String invokeUserName;
	/**
	 * 调用时间
	*/
	private Date invokeTime;
	/**
	 * 响应结果
	*/
	private String resultData;
	/**
	 * 耗时(毫秒)
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long elapsedTime;
	/**
	 * 创建时间
	*/
	private Date createTime;


	/**
	 * 操作位置
	 */
	private String operLocation;

	/**
	 * 操作类型
	 */
	private Integer operType;

	public LogOperationEntity() {
	}

	public LogOperationEntity(String invokeUrl, String invokeParams, String className, String methodName, String invokeStatus, Long invokeUserId, String invokeUserName, Date invokeTime, String resultData, Long elapsedTime) {
		this.invokeUrl = invokeUrl;
		this.invokeParams = invokeParams;
		this.className = className;
		this.methodName = methodName;
		this.invokeStatus = invokeStatus;
		this.invokeUserId = invokeUserId;
		this.invokeUserName = invokeUserName;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
	}

	public LogOperationEntity(Long id, String invokeUrl, String invokeParams, String className, String methodName, String invokeStatus, Long invokeUserId, String invokeUserName, Date invokeTime, String resultData, Long elapsedTime, Date createTime) {
		this.id = id;
		this.invokeUrl = invokeUrl;
		this.invokeParams = invokeParams;
		this.className = className;
		this.methodName = methodName;
		this.invokeStatus = invokeStatus;
		this.invokeUserId = invokeUserId;
		this.invokeUserName = invokeUserName;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
		this.createTime = createTime;
	}
}
