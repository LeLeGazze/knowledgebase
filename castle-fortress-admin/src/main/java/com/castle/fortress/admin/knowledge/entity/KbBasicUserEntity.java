package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识浏览收藏评论 实体类
 *
 * @author 
 * @since 2023-05-05
 */
@Data
@TableName("kb_basic_user")
public class KbBasicUserEntity implements Serializable {
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
	 * 当前用户Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 基础信息Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long bId;
	/**
	 * 收藏、浏览等类型
	*/
	private Integer type;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 附件类型后缀
	 */
	private String attachment;
	/**
	 * 知识类型
	 */
	private Integer status;

}
