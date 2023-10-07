package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 标签分类表 实体类
 *
 * @author 
 * @since 2023-06-14
 */
@Data
@TableName("kb_label_category")
public class KbLabelCategoryEntity implements Serializable {
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
	 * 标签分类名称
	*/
	private String name;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 是否删除
	*/
	private Integer isDeleted;
	/**
	 * 是否启用
	*/
	private Integer status;
	/**
	 * 创建人
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createUser;
	/**
	 * 更新用户
	*/
	private Long updateUser;
	/**
	 * 修改时间
	*/
	private Date updateTime;

}
