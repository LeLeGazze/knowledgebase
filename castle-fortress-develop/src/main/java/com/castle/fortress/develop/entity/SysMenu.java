package com.castle.fortress.develop.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 系统菜单表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_menu")
@ApiModel("系统菜单表")
public class SysMenu {
    private static final long serialVersionUID = 1L;


    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    @TableId(
            value = "id",
            type = IdType.ASSIGN_ID
    )
    private Long id;

    @ApiModelProperty("状态")
    private Integer status;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("创建者id")
    private Long createUser;
    @TableField(exist = false)
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

    @TableLogic(value= "2",delval="1")
    @ApiModelProperty("是否删除")
    private Integer isDeleted;
    @JsonSerialize(using = ToStringSerializer.class)
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 授权编码,多个用分号隔开
     */
    private String permissions;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单路径
     */
    private String url;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单类型
     */
    private Integer type;
    /**
     * 菜单排序
     */
    private Integer sort;
    /**
     * 页面路径
     */
    private String viewPath;
    /**
     * 菜单备注
     */
    private String remark;
    /**
     * 子数据
     */
    @TableField(exist = false)
    private List<SysMenu> children;

    public SysMenu() {
    }

    public SysMenu(Long id, Integer status, Integer isDeleted, Long parentId, String permissions, String name, String icon, Integer type, Integer sort, String viewPath, String remark) {
        this.id = id;
        this.status = status;
        this.isDeleted = isDeleted;
        this.parentId = parentId;
        this.permissions = permissions;
        this.name = name;
        this.icon = icon;
        this.type = type;
        this.sort = sort;
        this.viewPath = viewPath;
        this.remark = remark;
    }
}
