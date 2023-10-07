package com.castle.fortress.admin.system.dto;

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
 * 意见反馈 实体类
 *
 * @author castle
 * @since 2022-11-01
 */
@Data
@ApiModel(value = "feedback对象", description = "意见反馈")
public class FeedbackDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "反馈内容")
	@JsonProperty("content")
	private String content;
	@ApiModelProperty(value = "反馈图片")
	@JsonProperty("img")
	private String img;
	@ApiModelProperty(value = "反馈类型")
	@JsonProperty("typeId")
	private Integer typeId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "反馈人")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "联系方式")
	@JsonProperty("contactWay")
	private String contactWay;
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
	@ApiModelProperty(value = "修改时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "是否删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "状态 YesNoEnum")
	@JsonProperty("status")
	private Integer status;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	@JsonProperty("createUser")
	private Long createUser;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "修改人")
	@JsonProperty("updateUser")
	private Long updateUser;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
