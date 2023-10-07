package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * cms模型表数据对象
 * @author castle
 */
@Data
@ApiModel(value = "表数据对象", description = "表数据对象")
	public class KbFormDataDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "模型ID")
	@JsonProperty("modelId")
	private String modelId;
	@ApiModelProperty(value = "表单数据")
	@JsonProperty("formData")
	private Map<String,Object> formData;


}
