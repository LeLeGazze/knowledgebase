package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
/**
 * 框架绑定api配置管理 实体类
 *
 * @author castle
 * @since 2022-04-12
 */
@Data
@ApiModel(value = "configApi对象", description = "框架绑定api配置管理")
public class ConfigApiDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "配置分组 00 平台类型 01 基本配置")
	@JsonProperty("groupCode")
	private String groupCode;
	@ApiModelProperty(value = "编码 BindApiCodeEnum ")
	@JsonProperty("bindCode")
	private String bindCode;
	@ApiModelProperty(value = "配置详情json格式")
	@JsonProperty("bindDetail")
	private String bindDetail;
	@ApiModelProperty(value = "配置详情map格式")
	@JsonProperty("paramMap")
	private Map<String,Object> paramMap;

}
