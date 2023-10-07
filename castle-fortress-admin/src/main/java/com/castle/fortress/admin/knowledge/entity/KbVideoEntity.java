package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 视频库 实体类
 *
 * @author 
 * @since 2023-05-13
 */
@Data
@TableName("kb_video")
@EqualsAndHashCode(callSuper = true)
public class KbVideoEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
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
	 * 封面
	*/
	private String cover;
	/**
	 * 视频播放URL
	*/
	private String videoUrl;
	/**
	 * 视频存储路径
	*/
	private String videoSrc;
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

}
