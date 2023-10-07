package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 系统参数表 实体类
 *
 * @author castle
 * @since 2022-05-07
 */
@Data
@TableName("castle_config_params")
@EqualsAndHashCode(callSuper = true)
public class ConfigParamsEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 参数编码
	*/
	private String paramCode;
	/**
	 * 参数值
	*/
	private String paramValue;
	/**
	 * 类型
	*/
	private Integer paramType;
	/**
	 * 参数的描述
	*/
	private String paramRemark;
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

}
