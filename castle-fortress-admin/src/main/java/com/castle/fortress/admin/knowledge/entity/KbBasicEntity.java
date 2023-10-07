package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

import io.github.classgraph.json.Id;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 知识基本表 实体类
 *
 * @author Pan Chen
 * @since 2023-04-17
 */
@Data
@TableName("kb_basic")
@EqualsAndHashCode(callSuper = false)
public class KbBasicEntity  {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 主题仓库ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long whId;
	/**
	 * 标题
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
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("pubTime")
	private Date pubTime;
	/**
	 * 附件
	 */
	private String attachment;
	/**
	 * 标签
	 */
	private String label;

	/**
	 * 过期时间
	*/
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("expTime")
	private Date expTime;
	/**
	 * 知识仓库分类ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long categoryId;
	/**
	 * 模型编码
	*/
	private String modelCode;
	/**
	 * 模型id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long modelId;
	/**
	 * 备注
	*/
	private String remark;
	/**
	 * 排序号
	*/
	private Integer sort;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 标记删除
	 */
	private Integer isDeleted;
	/**
	 * 词云
	 */
	private String wordCloud;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("createTime")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("创建者id")
	private Long createUser;

	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonProperty("updateTime")
	private Date updateTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("修改用户id")
	private Long updateUser;
}
