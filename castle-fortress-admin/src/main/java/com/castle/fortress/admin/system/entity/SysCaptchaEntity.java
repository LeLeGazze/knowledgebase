package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 手机验证码 实体类
 *
 * @author castle
 * @since 2021-07-13
 */
@Data
@TableName("castle_sys_captcha")
public class SysCaptchaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	*/
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 手机号
	*/
	private String phone;
	/**
	 * 验证码
	*/
	private String captcha;
	/**
	 * 创建时间
	*/
	private Date createTime;

}
