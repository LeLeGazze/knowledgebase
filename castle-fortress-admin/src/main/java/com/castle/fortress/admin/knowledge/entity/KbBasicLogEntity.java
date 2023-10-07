package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识移动日志表 实体类
 *
 * @author 
 * @since 2023-06-02
 */
@Data
@TableName("kb_basic_log")
public class KbBasicLogEntity implements Serializable {
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
	 * 知识Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long basicId;
	/**
	 * 旧分类Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long oldCategory;
	/**
	 * 新分类Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long newCategory;
	/**
	 * 创建人Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createUser;
	/**
	 * 日志知识类型
	 */
	private Integer type;
	/**
	 * 创建时间
	*/
	private Date createTime;

}
