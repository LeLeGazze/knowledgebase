package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 帮助中心文章 实体类
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Data
@TableName("castle_help_article")
@EqualsAndHashCode(callSuper = true)
public class CastleHelpArticleEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 文章标题
	*/
	private String title;
	/**
	 * 文章内容
	*/
	private String content;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 类型id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long typeId;


	/**
	 * 类型名称
	 */
	@TableField(exist = false)
	private String typeName;
}
