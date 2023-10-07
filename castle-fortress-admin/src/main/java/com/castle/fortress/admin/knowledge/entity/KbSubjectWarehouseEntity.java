package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.experimental.Accessors;

/**
 * 主题知识仓库 实体类
 *
 * @author lyz
 * @since 2023-04-24
 */
@Data
@TableName("kb_subject_warehouse")
@EqualsAndHashCode(callSuper = true)
public class KbSubjectWarehouseEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 仓库名称
	*/
	private String name;
	/**
	 * 模型
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modelId;
	/**
	 * 是否首页显示
	*/
	private Integer isShow;
	/**
	 * 封面
	*/
	private String cover;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 排序号
	*/
	private Integer sort;



}
