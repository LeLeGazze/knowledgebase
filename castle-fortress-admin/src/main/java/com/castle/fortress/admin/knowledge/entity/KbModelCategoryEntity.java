package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 模型分类管理 实体类
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
@Data
@TableName("kb_model_category")
@EqualsAndHashCode(callSuper = true)
public class KbModelCategoryEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 名字
	*/
	private String name;
	/**
	 * 排序
	*/
	private Integer sort;

}
