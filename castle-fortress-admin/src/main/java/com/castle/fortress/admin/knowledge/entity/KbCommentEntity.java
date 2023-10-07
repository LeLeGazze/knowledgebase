package com.castle.fortress.admin.knowledge.entity;

import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * 知识评论管理表 实体类
 *
 * @author sunhr
 * @since 2023-05-09
 */
@Data
@TableName("kb_comment")
public class KbCommentEntity implements Serializable {
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
	 * 评价内容
	*/
	private String content;
	/**
	 * 对应的知识id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long basicId;
	/**
	 * 父id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 用户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 最上级id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long oneId;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 1：删除  2：非删除
	*/
	private Integer isDeleted;
	/**
	 * 是否有回复
	 */
	private Integer isReply;
	/**
	 * 评论知识类型
	 */
	private Integer status;
}
