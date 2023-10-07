package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 角色数据权限表-细化到部门 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@ApiModel(value = "sysRoleDataAuth对象", description = "角色数据权限表-细化到部门")
public class SysRoleDataAuthDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "角色编码")
	private Long roleId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "菜单编码")
	private Long deptId;

}
