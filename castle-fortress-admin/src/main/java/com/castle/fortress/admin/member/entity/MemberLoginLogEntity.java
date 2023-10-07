package com.castle.fortress.admin.member.entity;

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
 * 会员登录日志表 实体类
 *
 * @author Mgg
 * @since 2021-11-26
 */
@Data
@TableName("member_login_log")
public class MemberLoginLogEntity implements Serializable {
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
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 调用路径
	*/
	private String invokeUrl;
	/**
	 * 调用ip
	*/
	private String remoteAddr;
	/**
	 * 登录地址
	*/
	private String address;
	/**
	 * 浏览器
	*/
	private String cusBrowser;
	/**
	 * 操作系统
	*/
	private String cusOs;
	/**
	 * 调用参数
	*/
	private String invokeParams;
	/**
	 * 调用状态 00 成功 01 失败
	*/
	private String invokeStatus;
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
	 * 登录方式  枚举 LoginMethodEnum
	*/
	private Integer loginMethod;

	/**
	 * 时间段 搜索用
	 */
	@TableField(exist = false)
	private Date invokeTimeStart;
	@TableField(exist = false)
	private Date invokeTimeEnd;
	/**
	 * 会员昵称
	 */
	@TableField(exist = false)
	private String nickName;

	public MemberLoginLogEntity(String invokeUrl, String remoteAddr, String invokeParams, String invokeStatus, Date invokeTime,String resultData,Long memberId,Integer loginMethod) {
		this.invokeUrl = invokeUrl;
		this.remoteAddr = remoteAddr;
		this.invokeParams = invokeParams;
		this.invokeStatus = invokeStatus;
		this.invokeTime = invokeTime;
		this.resultData = resultData;
		this.memberId = memberId;
		this.loginMethod = loginMethod;
	}

	public MemberLoginLogEntity(){}
}
