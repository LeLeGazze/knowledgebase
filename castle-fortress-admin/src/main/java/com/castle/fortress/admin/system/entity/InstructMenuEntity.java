package com.castle.fortress.admin.system.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 菜单指令配置表 实体类
 *
 * @author castle
 * @since 2022-08-24
 */
@Data
@TableName("castle_instruct_menu")
public class InstructMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 菜单ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 指令前缀
	*/
	private String instructPre;
	/**
	 * 指令关键字
	*/
	private String instructKeys;
	/**
	 * 菜单
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
	/**
	 * 展示名称
	*/
	private String title;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 状态
	*/
	private Integer status;

}
