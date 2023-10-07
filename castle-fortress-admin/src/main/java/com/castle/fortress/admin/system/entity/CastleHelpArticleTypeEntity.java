package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 帮助中心文章类型 实体类
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Data
@TableName("castle_help_article_type")
@EqualsAndHashCode(callSuper = true)
public class CastleHelpArticleTypeEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 类型名称
	*/
	private String name;
	/**
	 * 备注
	*/
	private String remark;

}
