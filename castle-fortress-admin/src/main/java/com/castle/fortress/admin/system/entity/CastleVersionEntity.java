package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 版本管理 实体类
 *
 * @author 
 * @since 2022-02-14
 */
@Data
@TableName("castle_version")
@EqualsAndHashCode(callSuper = true)
public class CastleVersionEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 版本号
	*/
	private String version;
	/**
	 * 标题
	*/
	private String title;
	/**
	 * 内容
	*/
	private String content;
	/**
	 * app下载地址
	*/
	private String appUrl;

}
