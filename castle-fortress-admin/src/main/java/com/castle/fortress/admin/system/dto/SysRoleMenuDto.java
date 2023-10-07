package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色菜单关联对象
 * @author castle
 */
@Data
@ApiModel("系统角色菜单关联表")
public class SysRoleMenuDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("角色编码")
    private Long roleId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("菜单编码")
    private Long menuId;
}
