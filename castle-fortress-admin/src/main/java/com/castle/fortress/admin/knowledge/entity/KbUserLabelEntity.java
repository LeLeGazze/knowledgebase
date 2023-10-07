package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 用户与表签关联表 实体类
 *
 * @author 
 * @since 2023-05-17
 */
@Data
@TableName("kb_user_label")
public class KbUserLabelEntity implements Serializable {
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
	 * 用户Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 标签Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long labelId;

}
