package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 数据同步 实体类
 *
 * @author 
 * @since 2023-06-29
 */
@Data
@TableName("kb_data_synchronization_task")
public class KbDataSynchronizationTaskEntity implements Serializable {
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
	 * 同步类型
	*/
	private String type;
	/**
	 * 任务运行SQL
	*/
	@TableField(value = "`sql`")
	private String sql;
	/**
	 * 作者
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long auth;
	/**
	 * 部门
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long dept;
	/**
	 * 分类
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long categoryId;
	/**
	 * 模型id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modeId;
	/**
	 * 是否启用
	*/
	private Integer status;

}
