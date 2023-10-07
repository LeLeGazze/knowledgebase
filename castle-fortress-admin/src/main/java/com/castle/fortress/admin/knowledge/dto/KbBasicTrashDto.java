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
 * 知识回收表 实体类
 *
 * @author 
 * @since 2023-06-01
 */
@Data
@ApiModel(value = "kbBasicTrash对象", description = "知识回收表")
public class KbBasicTrashDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键Id")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "知识Id")
	@JsonProperty("basicId")
	private Long basicId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "用户Id")
	@JsonProperty("userId")
	private Long userId;
	@ApiModelProperty(value = "回收类型")
	@JsonProperty("type")
	private Integer type;
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
