package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 系统部门表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@TableName("castle_sys_dept")
@EqualsAndHashCode(callSuper = true)
public class SysDeptEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 部门名称
	*/
	private String name;
	/**
	 * 部门描述
	*/
	private String remark;
	/**
	 * 上级部门
	*/
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 所有的上级
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String parents;

}
