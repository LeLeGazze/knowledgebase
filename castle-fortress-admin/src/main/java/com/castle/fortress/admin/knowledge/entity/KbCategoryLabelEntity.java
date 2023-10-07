package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 标签分组和标签关联表 实体类
 *
 * @author 
 * @since 2023-06-14
 */
@Data
@TableName("kb_category_label")
public class KbCategoryLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 标签分类Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long ctId;
	/**
	 * 标签Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long lId;

}
