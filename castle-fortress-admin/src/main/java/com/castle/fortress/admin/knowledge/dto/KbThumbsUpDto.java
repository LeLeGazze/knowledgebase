package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识点赞表 实体类
 *
 * @author 
 * @since 2023-05-11
 */
@Data
@ApiModel(value = "kbThumbsUp对象", description = "知识点赞表")
public class KbThumbsUpDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "评价id")
	@JsonProperty("commentId")
	private Long commentId;
	@ApiModelProperty(value = "评价状态")
	@JsonProperty("status")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "知识id")
	@JsonProperty("basicId")
	private Long basicId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户id")
	@JsonProperty("userId")
	private Long userId;

}
