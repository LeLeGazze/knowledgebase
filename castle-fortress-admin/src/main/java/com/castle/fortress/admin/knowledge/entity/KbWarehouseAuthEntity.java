package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

/**
 * 主题知识仓库权限表 实体类
 *
 * @author
 * @since 2023-04-24
 */
@Data
@TableName("kb_warehouse_auth")
public class KbWarehouseAuthEntity implements Serializable {
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
     * 知识仓库
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long whId;
    /**
     * 分类
     */
    private String category;
    /**
     * 人员ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 知识库权限枚举
     */
    private Integer whAuth;
    /**
     * 知识库下的知识枚举
     */
    private Integer kbAuth;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 状态  1 为用户 2 为群组  3  为部门
     */
    private Integer status;

    /**
     * 状态 排序
     */
    private Integer sort;
}
