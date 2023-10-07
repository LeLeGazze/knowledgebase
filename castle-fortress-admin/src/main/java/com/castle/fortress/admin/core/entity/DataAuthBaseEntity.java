package com.castle.fortress.admin.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 有数据权限的父类
 * @author castle
 */
@Data
@ApiModel("DataAuthBaseEntity对象")
public class DataAuthBaseEntity extends BaseEntity{

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建部门")
    private Long createDept;

    @TableField(exist = false)
    @ApiModelProperty("创建部门名称")
    private String createDeptName;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("创建职位")
    private Long createPost;

    @TableField(exist = false)
    @ApiModelProperty("创建职位名称")
    private String createPostName;

    @TableField(exist = false)
    @ApiModelProperty("数据权限校验标识 true 校验数据权限 false 不校验数据权限")
    private Boolean dataAuthFlag=false;
}
