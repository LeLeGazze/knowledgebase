package com.castle.fortress.admin.knowledge.dto;

import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识分类表 实体类
 *
 * @author 
 * @since 2023-04-24
 */
@Data
@ApiModel(value = "kbCategory对象", description = "知识分类表")
public class KbCategoryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "分类名称")
	@JsonProperty("name")
	private String name;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "知识仓库")
	@JsonProperty("swId")
	private Long swId;
	@ApiModelProperty(value = "排序号")
	@JsonProperty("sort")
	private Integer sort;
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;
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
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除 YesNoEnum。 yes 删除；no 未删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;
	@ApiModelProperty(value = "摘要")
	@JsonProperty("remark")
	private String remark;
//	@ApiModelProperty(value = "知识库下的知识LIST")
//	@JsonProperty("deptList")
//	private List<KbWarehouseAuthDto> deptList;
	@ApiModelProperty(value = "知识下的分类权限")
	@JsonProperty("authsList")
	private List<KbWarehouseAuthDto> authsList;

}
