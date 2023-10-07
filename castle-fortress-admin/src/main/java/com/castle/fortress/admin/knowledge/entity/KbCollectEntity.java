package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识收藏表 实体类
 *
 * @author 
 * @since 2023-05-12
 */
@Data
@TableName("kb_collect")
public class KbCollectEntity implements Serializable {
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
	 * 知识id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long basicId;
	/**
	 * 收藏和取消收藏
	*/
	private Integer collectStatus;
	/**
	 * 用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 创建日期
	*/
	private Date createTime;
	/**
	 * 收藏类型
	 */
	private Integer type;

}
