package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色关联对象
 * @author castle
 */
@Data
@TableName(value = "castle_sys_user_role")
@ApiModel("系统用户角色关联表")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户编码")
    private Long userId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("角色编码")
    private Long roleId;
}
