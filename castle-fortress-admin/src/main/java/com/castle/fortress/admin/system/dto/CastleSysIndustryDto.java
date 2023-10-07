package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 行业职位 实体类
 *
 * @author Mgg
 * @since 2021-09-02
 */
@Data
@ApiModel(value = "castleSysIndustry对象", description = "行业职位")
public class CastleSysIndustryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "上级ID，一级为0")
	@JsonProperty("parentId")
	private Long parentId;
	@ApiModelProperty(value = "名称")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "层级")
	@JsonProperty("treeLevel")
	private Integer treeLevel;
	@ApiModelProperty(value = "是否叶子节点  0：否   1：是")
	@JsonProperty("leaf")
	private Integer leaf;
	@ApiModelProperty(value = "短拼音")
	@JsonProperty("shortpinyin")
	private String shortpinyin;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "排序")
	@JsonProperty("sort")
	private Long sort;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "更新人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "更新时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
