package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 版本管理 实体类
 *
 * @author 
 * @since 2022-02-14
 */
@Data
@ApiModel(value = "castleVersion对象", description = "版本管理")
public class CastleVersionDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "版本号")
	@JsonProperty("version")
	private String version;
	@ApiModelProperty(value = "标题")
	@JsonProperty("title")
	private String title;
	@ApiModelProperty(value = "内容")
	@JsonProperty("content")
	private String content;
	@ApiModelProperty(value = "app下载地址")
	@JsonProperty("appUrl")
	private String appUrl;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建者")
	@JsonProperty("createUser")
	private Long createUser;
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
	@ApiModelProperty(value = "更新者")
	@JsonProperty("updateUser")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "更新时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除YesNoEnum")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
