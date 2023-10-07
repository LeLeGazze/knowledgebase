package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * banner图 实体类
 *
 * @author majunjie
 * @since 2022-02-14
 */
@Data
@TableName("castle_banner")
@EqualsAndHashCode(callSuper = true)
public class CastleBannerEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 图片
	*/
	private String bannerUrl;
	/**
	 * 跳转链接
	*/
	private String link;
	/**
	 * 排序
	*/
	private Integer sort;
	/**
	 * 备注
	*/
	private String remark;

}
