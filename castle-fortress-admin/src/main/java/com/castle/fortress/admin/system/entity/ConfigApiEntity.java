package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
/**
 * 框架绑定api配置管理 实体类
 *
 * @author castle
 * @since 2022-04-12
 */
@Data
@TableName("castle_config_api")
public class ConfigApiEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 配置分组 00 平台类型 01 基本配置
	*/
	private String groupCode;
	/**
	 * 编码 BindApiCodeEnum 
	*/
	private String bindCode;
	/**
	 * 配置详情json格式
	*/
	private String bindDetail;

}
