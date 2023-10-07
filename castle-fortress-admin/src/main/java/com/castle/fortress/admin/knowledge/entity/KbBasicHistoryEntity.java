package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 知识基本表历史表 实体类
 *
 * @author 
 * @since 2023-07-03
 */
@Data
@TableName("kb_basic_history")
@EqualsAndHashCode(callSuper = true)
public class KbBasicHistoryEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 父类id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
    	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Long parentId;
	/**
	 * 知识id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long bId;
	/**
	 * 主题仓库分类ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long whId;
	/**
	 * 标签
	*/
	private String title;
	/**
	 * 作者
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long auth;
	/**
	 * 部门ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deptId;
	/**
	 * 发布时间
	*/
	private Date pubTime;
	/**
	 * 知识仓库分类ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long categoryId;
	/**
	 * 模型id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modelId;
	/**
	 * 过期时间
	*/
	private Date expTime;
	/**
	 * 附件
	*/
	private String attachment;
	/**
	 * 标签
	*/
	private String label;
	/**
	 * 模型编码
	*/
	private String modelCode;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 排序号
	*/
	private Integer sort;
	/**
	 * 词云
	*/
	private String wordCloud;

	private String version;

	private Integer readCount; // 浏览次数
	private Integer commentsCount;//评论次数
	private Integer downloadCount;//下载次数

}
