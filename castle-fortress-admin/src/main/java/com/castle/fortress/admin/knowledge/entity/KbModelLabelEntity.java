package com.castle.fortress.admin.knowledge.entity;

import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Objects;

/**
 * 标签管理表 实体类
 *
 * @author 
 * @since 2023-04-26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("kb_model_label")
public class KbModelLabelEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 标签名
	*/
	private String name;
	/**
	 * 标签状态
	*/
	private Integer status;
	/**
	 * 排序号
	*/
	private Integer sort;
	/**
	 * 是否热词
	 */
	private Integer hotWord;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		KbModelLabelEntity that = (KbModelLabelEntity) o;
		return Objects.equals(name, that.name) && Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, status);
	}
}
