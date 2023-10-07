package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识与标签的中间表 实体类
 *
 * @author 
 * @since 2023-04-28
 */
@Data
@ApiModel(value = "kbBasicLabel对象", description = "知识与标签的中间表")
public class KbBasicLabelDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "知识id")
	@JsonProperty("bId")
	private Long bId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "标签id")
	@JsonProperty("lId")
	private Long lId;
	@ApiModelProperty(value = "是否删除 YES OR NO")
	@JsonProperty("isDeleted")
	private Integer isDeleted;

}
