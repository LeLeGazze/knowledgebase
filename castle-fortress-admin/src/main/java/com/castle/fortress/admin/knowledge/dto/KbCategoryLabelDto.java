package com.castle.fortress.admin.knowledge.dto;

import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 标签分组和标签关联表 实体类
 *
 * @author 
 * @since 2023-06-14
 */
@Data
@ApiModel(value = "kbCategoryLabel对象", description = "标签分组和标签关联表")
public class KbCategoryLabelDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键Id")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "标签分类Id")
	@JsonProperty("ctId")
	private Long ctId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "标签Id")
	@JsonProperty("lId")
	private Long lId;
	@ApiModelProperty(value = "标签")
	@JsonProperty("labels")
	private Set<KbModelLabelDto> labels;

}
