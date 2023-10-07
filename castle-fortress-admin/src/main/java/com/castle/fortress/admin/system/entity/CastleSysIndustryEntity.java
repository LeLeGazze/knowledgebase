package com.castle.fortress.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 行业职位 实体类
 *
 * @author Mgg
 * @since 2021-09-02
 */
@Data
@TableName("castle_sys_industry")
public class CastleSysIndustryEntity {
	private static final long serialVersionUID = 1L;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("主键id")
	private Long id;

	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty("创建者id")
	private Long createUser;
	@TableField(exist = false)
	@ApiModelProperty("创建者姓名")
	private String createUserName;

	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("创建时间")
	private Date createTime;

	@JsonSerialize(
			using = ToStringSerializer.class
	)
	@ApiModelProperty("更新人id")
	private Long updateUser;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty("更新时间")
	private Date updateTime;

	/**
	 * 上级ID，一级为0
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/**
	 * 名称
	*/
	private String name;
	/**
	 * 层级
	*/
	private Integer treeLevel;
	/**
	 * 是否叶子节点  0：否   1：是
	*/
	private Integer leaf;
	/**
	 * 短拼音
	*/
	private String shortpinyin;
	/**
	 * 排序
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long sort;


	/**
	 * 子数据
	 */
	@TableField(exist = false)
	private List<CastleSysIndustryEntity> children;

}
