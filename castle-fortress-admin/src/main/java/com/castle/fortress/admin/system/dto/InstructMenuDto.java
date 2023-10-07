package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 菜单指令配置表 实体类
 *
 * @author castle
 * @since 2022-08-24
 */
@Data
@ApiModel(value = "instructMenu对象", description = "菜单指令配置表")
public class InstructMenuDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "菜单ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "指令前缀")
	@JsonProperty("instructPre")
	private String instructPre;
	@ApiModelProperty(value = "指令关键字")
	@JsonProperty("instructKeys")
	private String instructKeys;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "菜单")
	@JsonProperty("menuId")
	private Long menuId;
	@ApiModelProperty(value = "展示名称")
	@JsonProperty("title")
	private String title;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;

}
