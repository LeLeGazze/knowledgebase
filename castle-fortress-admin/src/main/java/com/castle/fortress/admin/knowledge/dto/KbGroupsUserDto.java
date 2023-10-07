package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识库用户组与用户关联关系管理 实体类
 *
 * @author 
 * @since 2023-04-22
 */
@Data
@ApiModel(value = "kbGroupsUser对象", description = "知识库用户组与用户关联关系管理")
public class KbGroupsUserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户群组ID")
	@JsonProperty("groupId")
	private Long groupId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户ID")
	@JsonProperty("userId")
	private Long userId;

}
