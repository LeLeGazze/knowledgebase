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
 * 知识banner图表 实体类
 *
 * @author 
 * @since 2023-06-17
 */
@Data
@ApiModel(value = "kbBanner对象", description = "知识banner图表")
public class KbBannerDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键Id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "背景图")
	@JsonProperty("pcImage")
	private String pcImage;
	@ApiModelProperty(value = "app图片地址")
	@JsonProperty("appImage")
	private String appImage;
	@ApiModelProperty(value = "图片标签，分号间隔")
	@JsonProperty("tags")
	private String tags;
	@ApiModelProperty(value = "状态 YesNoEnum")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "跳转连接")
	@JsonProperty("url")
	private String url;
	@ApiModelProperty(value = "权重")
	@JsonProperty("weight")
	private Integer weight;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "修改时间")
	@JsonProperty("updateTime")
	private Date updateTime;

}
