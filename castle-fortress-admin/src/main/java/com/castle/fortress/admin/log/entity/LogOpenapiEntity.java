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
 * 对外开放api调用日志 实体类
 *
 * @author castle
 * @since 2021-04-01
 */
@Data
@TableName("castle_log_openapi")
public class LogOpenapiEntity implements Serializable {
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
	 * ip地址
	*/
	private String remoteAddr;
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
	 * 调用状态
	*/
	private String invokeStatus;
	/**
	 * 秘钥
	*/
	private String secretId;
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

	public LogOpenapiEntity() {
	}

	public LogOpenapiEntity(String remoteAddr, String invokeUrl, String invokeParams, String className, String methodName, String invokeStatus, String secretId, Date invokeTime, String resultData, Long elapsedTime) {
		this.remoteAddr = remoteAddr;
		this.invokeUrl = invokeUrl;
		this.invokeParams = invokeParams;
		this.className = className;
		this.methodName = methodName;
		this.invokeStatus = invokeStatus;
		this.secretId = secretId;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
	}

	public LogOpenapiEntity(Long id, String remoteAddr, String invokeUrl, String invokeParams, String className, String methodName, String invokeStatus, String secretId, Date invokeTime, String resultData, Long elapsedTime, Date createTime) {
		this.id = id;
		this.remoteAddr = remoteAddr;
		this.invokeUrl = invokeUrl;
		this.invokeParams = invokeParams;
		this.className = className;
		this.methodName = methodName;
		this.invokeStatus = invokeStatus;
		this.secretId = secretId;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
		this.createTime = createTime;
	}
}
