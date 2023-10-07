package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 用户与表签关联表 实体类
 *
 * @author 
 * @since 2023-05-17
 */
@Data
@ApiModel(value = "kbUserLabel对象", description = "用户与表签关联表")
public class KbUserLabelDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户Id")
	@JsonProperty("userId")
	private Long userId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "标签Id")
	@JsonProperty("labelId")
	private Long labelId;

}
