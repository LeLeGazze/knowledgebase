package com.castle.fortress.admin.job.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 系统任务调度表 实体类
 *
 * @author 
 * @since 2021-03-24
 */
@Data
@ApiModel(value = "configTask对象", description = "系统任务调度表")
public class ConfigTaskDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "任务名称")
	private String taskName;
	@ApiModelProperty(value = "参数")
	private String params;
	@ApiModelProperty(value = "cron表达式")
	private String cronExpression;
	@ApiModelProperty(value = "状态")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	private Long createUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除 YesNoEnum。 yes删除；no未删除")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	private String createUserName;
	@ApiModelProperty(value = "备注")
	private String remark;
}
