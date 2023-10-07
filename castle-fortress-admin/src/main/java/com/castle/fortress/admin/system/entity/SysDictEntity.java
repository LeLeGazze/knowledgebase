package com.castle.fortress.admin.system.entity;

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
 * 系统字典表
 *
 * @author castle
 */
@Data
@TableName(value = "castle_sys_dict")
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统字典表")
public class SysDictEntity extends BaseEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 字典编码
     */
    private String code;
    /**
     * 字典key
     */
    private String dictKey;
    /**
     * 字典value
     */
    private String dictValue;
    /**
     * 字典排序
     */
    private Integer sort;
    /**
     * 字典备注
     */
    private String remark;
    /**
     * 字典key-value集合
     */
    @TableField(exist = false)
    private List<SysDictEntity> children;
}
