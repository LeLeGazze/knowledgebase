package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统角色表
 *
 * @author castle
 */
@Data
@ApiModel(value = "sysRoleDto对象", description = "系统角色表")
public class SysRoleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("状态")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建者id")
    private Long createUser;
    @ApiModelProperty("创建者姓名")
    private String createUserName;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("创建时间")
    private Date createTime;

    @JsonSerialize(
            using = ToStringSerializer.class
    )
    @ApiModelProperty("更新人id")
    private Long updateUser;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("是否删除")
    private Integer isDeleted;
    @ApiModelProperty("角色名称")
    private String name;
    @ApiModelProperty("角色描述")
    private String remark;
    @ApiModelProperty("是否超级管理员")
    private Integer isAdmin;

    private List<Long> roleMenuDtoList;

    Map<String, List> mapMenu;

}
