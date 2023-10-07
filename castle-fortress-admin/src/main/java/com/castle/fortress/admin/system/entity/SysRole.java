package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 系统角色表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统角色表")
public class SysRole extends BaseEntity {
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String remark;
    @ApiModelProperty("是否超级管理员")
    private Integer isAdmin;
}
