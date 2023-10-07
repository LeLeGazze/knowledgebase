package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 知识分类表 实体类
 *
 * @author 
 * @since 2023-04-24
 */
@Data
@TableName("kb_category")
@EqualsAndHashCode(callSuper = true)
public class KbCategoryEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 分类名称
	*/
	private String name;
	/**
	 * 知识仓库
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long swId;

	private String remark; // 摘要
	/**
	 * 排序号
	*/
	private Integer sort;

}
