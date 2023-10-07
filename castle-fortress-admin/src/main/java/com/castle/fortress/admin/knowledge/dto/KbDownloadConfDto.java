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
 * 文件下载配置表 实体类
 *
 * @author 
 * @since 2023-06-25
 */
@Data
@ApiModel(value = "kbDownloadConf对象", description = "文件下载配置表")
public class KbDownloadConfDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键Id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "类型名称")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "状态 1：支持  2：不支持")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "更新时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "更新人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@ApiModelProperty(value = "1：删除 2：正常")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "状态 1: 水印 2: 下载")
	@JsonProperty("type")
	private Integer type;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
