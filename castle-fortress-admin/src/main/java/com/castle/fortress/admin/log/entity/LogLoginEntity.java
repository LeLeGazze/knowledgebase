package com.castle.fortress.admin.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 登录操作日志 实体类
 *
 * @author castle
 * @since 2021-04-01
 */
@Data
@TableName("castle_log_login")
public class LogLoginEntity implements Serializable {
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
	 * 登录路径
	*/
	private String invokeUrl;
	/**
	 * IP地址
	*/
	private String remoteAddr;
	/**
	 * 登录地址
	 */
	private String address;
	/**
	 * 用户浏览器
	 */
	private String cusBrowser;
	/**
	 * 用户操作系统
	 */
	private String cusOs;
	/**
	 * 调用参数
	*/
	private String invokeParams;
	/**
	 * 调用状态
	*/
	private String invokeStatus;
	/**
	 * 登录时间
	*/
	private Date invokeTime;
	/**
	 * 登录结果
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
	 * 登录方式  枚举 LoginMethodEnum
	 */
	private Integer loginMethod;
	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 用户名称
	 */
	@TableField(exist = false)
	private String userName;


	/**
	 * 时间段 搜索用
	 */
	@TableField(exist = false)
	private Date invokeTimeStart;
	@TableField(exist = false)
	private Date invokeTimeEnd;

	public LogLoginEntity(String invokeUrl, String remoteAddr, String invokeParams, String invokeStatus, Date invokeTime,String resultData,Long userId,Integer loginMethod) {
		this.invokeUrl = invokeUrl;
		this.remoteAddr = remoteAddr;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.userId = userId;
		this.loginMethod = loginMethod;
	}

	public LogLoginEntity(String invokeUrl, String remoteAddr, String invokeParams, String invokeStatus, Date invokeTime, String resultData, Long elapsedTime) {
		this.invokeUrl = invokeUrl;
		this.remoteAddr = remoteAddr;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
	}

	public LogLoginEntity(Long id, String invokeUrl, String remoteAddr, String invokeParams, String invokeStatus, Date invokeTime, String resultData, Long elapsedTime, Date createTime) {
		this.id = id;
		this.invokeUrl = invokeUrl;
		this.remoteAddr = remoteAddr;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.elapsedTime = elapsedTime;
		this.createTime = createTime;
	}

	public LogLoginEntity(){ }
}
