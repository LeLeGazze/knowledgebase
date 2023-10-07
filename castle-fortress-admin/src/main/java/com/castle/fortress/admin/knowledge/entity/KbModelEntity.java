package com.castle.fortress.admin.knowledge.entity;

import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
/**
 * 模型表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-11
 */
@Data
@TableName("kb_model")
@EqualsAndHashCode(callSuper = true)
public class KbModelEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 模型名字
	*/
	private String name;
	/**
	 * 属性编码（用于代码中引用）
	*/
	private String code;
	/**
	 * 序号
	*/
	private Integer sort;
}
