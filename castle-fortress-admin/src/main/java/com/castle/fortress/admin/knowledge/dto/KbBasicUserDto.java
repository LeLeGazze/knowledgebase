package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 知识浏览收藏评论 实体类
 *
 * @author 
 * @since 2023-05-05
 */
@Data
@ApiModel(value = "kbBasicUser对象", description = "知识浏览收藏评论")
public class KbBasicUserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "当前用户Id")
	@JsonProperty("userId")
	private Long userId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "基础信息Id")
	@JsonProperty("bId")
	private Long bId;
	@ApiModelProperty(value = "收藏、浏览等类型")
	@JsonProperty("type")
	private Integer type;
	@ApiModelProperty(value = "附件类型后缀")
	@JsonProperty("attachment")
	private String attachment;
	@ApiModelProperty(value = "知识类型")
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

}
