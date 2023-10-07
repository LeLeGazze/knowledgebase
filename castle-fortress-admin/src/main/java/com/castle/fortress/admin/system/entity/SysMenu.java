package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统菜单表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_menu")
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统菜单表")
public class SysMenu extends BaseEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    /**
     * 父级id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
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
}
