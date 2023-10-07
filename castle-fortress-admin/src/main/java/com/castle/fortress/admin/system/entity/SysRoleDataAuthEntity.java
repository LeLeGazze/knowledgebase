package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
/**
 * 角色数据权限表-细化到部门 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@TableName("castle_sys_role_data_auth")
public class SysRoleDataAuthEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 角色编码
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long roleId;
	/**
	 * 菜单编码
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deptId;

}
