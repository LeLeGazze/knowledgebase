package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 知识库用户组管理 实体类
 *
 * @author 
 * @since 2023-04-22
 */
@Data
@TableName("kb_groups")
@EqualsAndHashCode(callSuper = true)
public class KbGroupsEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 群组名称
	*/
	private String name;
	/**
	 * 父类d
	 */
	private Long pId;
	/**
	 * 排序号
	*/
	private Integer sort;

}
