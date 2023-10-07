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
 * 标签删除任务表 实体类
 *
 * @author 
 * @since 2023-06-07
 */
@Data
@ApiModel(value = "kbBaseLabelTask对象", description = "标签删除任务表")
public class KbBaseLabelTaskDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主鍵")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "删除标签id")
	@JsonProperty("lId")
	private Long lId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "状态: 1：新增，2：正常处理中 3：处理失败")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "失败日志")
	@JsonProperty("message")
	private String message;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "任务处理时间")
	@JsonProperty("taskTime")
	private Date taskTime;

}
