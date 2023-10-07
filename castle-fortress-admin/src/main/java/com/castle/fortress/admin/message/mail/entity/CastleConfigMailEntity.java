package com.castle.fortress.admin.message.mail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 邮件配置表 实体类
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Data
@TableName("castle_config_mail")
@EqualsAndHashCode(callSuper = true)
public class CastleConfigMailEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 编码code
	 */
	private String code;
	/**
	 * 邮箱SMTP地址
	*/
	private String smtp;
	/**
	 * 端口号
	*/
	private Integer port;
	/**
	 * 邮箱账号
	*/
	private String mail;
	/**
	 * 邮箱密码
	*/
	private String password;
	/**
	 * 邮箱昵称
	*/
	private String nickName;
	/**
	 * 创建部门
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createDept;
	/**
	 * 创建职位
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createPost;
	/**
	 * remark
	 */
	private String remark;


}
