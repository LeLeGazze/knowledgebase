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
import java.util.List;
/**
 * 行政区域 实体类
 *
 * @author castle
 * @since 2021-04-28
 */
@Data
@ApiModel(value = "sysRegion对象", description = "行政区域")
public class SysRegionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "区域标识")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "上级ID，一级为0")
	@JsonProperty("parentId")
	private Long parentId;
	@ApiModelProperty(value = "上级名称")
	@JsonProperty("parentName")
	private String parentName;
	@ApiModelProperty(value = "区域名称")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "区域类型")
	@JsonProperty("treeLevel")
	private Integer treeLevel;
	@ApiModelProperty(value = "是否叶子节点  0：否   1：是")
	@JsonProperty("leaf")
	private Integer leaf;
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
	@ApiModelProperty("下级行政区域")
	private List<SysRegionDto> children;
	@ApiModelProperty("是否有子节点")
	private Boolean hasChildren;

}
