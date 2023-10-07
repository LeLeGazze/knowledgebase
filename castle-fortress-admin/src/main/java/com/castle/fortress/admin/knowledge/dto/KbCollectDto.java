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
 * 知识收藏表 实体类
 *
 * @author 
 * @since 2023-05-12
 */
@Data
@ApiModel(value = "kbCollect对象", description = "知识收藏表")
public class KbCollectDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "知识id")
	@JsonProperty("basicId")
	private Long basicId;
	@ApiModelProperty(value = "收藏和取消收藏")
	@JsonProperty("collectStatus")
	private Integer collectStatus;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户id")
	@JsonProperty("userId")
	private Long userId;
	@ApiModelProperty(value = "收藏类型")
	@JsonProperty("type")
	private Integer type;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建日期")
	@JsonProperty("createTime")
	private Date createTime;

}
