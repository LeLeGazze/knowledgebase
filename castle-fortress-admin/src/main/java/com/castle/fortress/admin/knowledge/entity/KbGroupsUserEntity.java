package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识库用户组与用户关联关系管理 实体类
 *
 * @author 
 * @since 2023-04-22
 */
@Data
@TableName("kb_groups_user")
public class KbGroupsUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 用户群组ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long groupId;
	/**
	 * 用户ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

}
