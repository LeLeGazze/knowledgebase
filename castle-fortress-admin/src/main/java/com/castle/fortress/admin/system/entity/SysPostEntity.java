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
 * 系统职位表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@TableName("castle_sys_post")
@EqualsAndHashCode(callSuper = true)
public class SysPostEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 职位名称
	*/
	private String name;
	/**
	 * 职位描述
	*/
	private String remark;
	/**
	 * 上级职位
	*/
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 所属部门
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deptId;
	/**
	 * 数据权限类型参照枚举DataPermissionPostEnum
	*/
	private Integer dataAuthType;

}
