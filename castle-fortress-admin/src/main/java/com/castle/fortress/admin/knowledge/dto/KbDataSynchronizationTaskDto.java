package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 数据同步 实体类
 *
 * @author 
 * @since 2023-06-29
 */
@Data
@ApiModel(value = "kbDataSynchronizationTask对象", description = "数据同步")
public class KbDataSynchronizationTaskDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "同步类型")
	@JsonProperty("type")
	private String type;
	@ApiModelProperty(value = "任务运行SQL")
	@JsonProperty("sql")
	private String sql;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "作者")
	@JsonProperty("auth")
	private Long auth;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "部门")
	@JsonProperty("dept")
	private Long dept;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "分类")
	@JsonProperty("categoryId")
	private Long categoryId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "模型id")
	@JsonProperty("modeId")
	private Long modeId;
	@ApiModelProperty(value = "是否启用")
	@JsonProperty("status")
	private Integer status;

}
