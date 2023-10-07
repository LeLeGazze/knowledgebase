package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 行政区域 实体类
 *
 * @author castle
 * @since 2021-04-28
 */
@Data
@TableName("castle_sys_region")
public class SysRegionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 区域标识
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 上级ID，一级为0
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 上级名称
	 */
	@TableField(exist = false)
	private String parentName;
	/**
	 * 区域名称
	*/
	private String name;
	/**
	 * 区域类型
	*/
	private Integer treeLevel;
	/**
	 * 是否叶子节点  0：否   1：是
	*/
	private Integer leaf;
	/**
	 * 排序
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long sort;
	/**
	 * 创建人
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long createUser;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 更新人
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long updateUser;
	/**
	 * 更新时间
	*/
	private Date updateTime;

}
