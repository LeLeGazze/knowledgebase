package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识点赞表 实体类
 *
 * @author 
 * @since 2023-05-11
 */
@Data
@TableName("kb_thumbs_up")
public class KbThumbsUpEntity implements Serializable {
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
	 * 评价id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long commentId;
	/**
	 * 评价状态
	*/
	private Integer status;
	/**
	 * 知识id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long basicId;
	/**
	 * 用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

}
