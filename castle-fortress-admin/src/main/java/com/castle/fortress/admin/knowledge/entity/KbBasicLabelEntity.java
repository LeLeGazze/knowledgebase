package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 知识与标签的中间表 实体类
 *
 * @author 
 * @since 2023-04-28
 */
@Data
@TableName("kb_basic_label")
public class KbBasicLabelEntity{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 知识id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long bId;
	/**
	 * 标签id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long lId;

}
