package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 协议管理 实体类
 *
 * @author majunjie
 * @since 2022-01-28
 */
@Data
@TableName("castle_protocol")
@EqualsAndHashCode(callSuper = true)
public class ProtocolEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 协议编号
	*/
	private String code;
	/**
	 * 协议标题
	*/
	private String title;
	/**
	 * 协议内容
	*/
	private String content;

}
