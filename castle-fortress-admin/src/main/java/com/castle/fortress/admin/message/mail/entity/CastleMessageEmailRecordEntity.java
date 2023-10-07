package com.castle.fortress.admin.message.mail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 邮件发送记录表 实体类
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Data
@TableName("castle_message_email_record")
@EqualsAndHashCode(callSuper = true)
public class CastleMessageEmailRecordEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 发送人
	*/
	private String sender;
	/**
	 * 发送时间
	*/
	private Date sendTime;
	/**
	 * 邮件标题
	*/
	private String emailTitle;
	/**
	 * 邮件内容
	*/
	private String emailBody;
	/**
	 * 创建部门
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createDept;
	/**
	 * 接收人
	*/
	private String toUser;
	/**
	 * 抄送人
	*/
	private String toCuser;

}
