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
 * 系统部门表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@ApiModel(value = "sysDept对象", description = "系统部门表")
public class SysDeptDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "部门名称")
	private String name;
	@ApiModelProperty(value = "部门描述")
	private String remark;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "上级部门")
	private Long parentId;
	@ApiModelProperty(value = "状态 YesNoEnum。yes生效；no失效")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	private Long createUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除 YesNoEnum。 yes删除；no未删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	private String createUserName;
	@ApiModelProperty(value = "子部门")
	private List<SysDeptDto> children;
	@ApiModelProperty(value = "所有上级")
	@JsonProperty("parents")
	private String parents;
}
