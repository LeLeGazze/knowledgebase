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
 * 帮助中心文章类型 实体类
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Data
@ApiModel(value = "castleHelpArticleType对象", description = "帮助中心文章类型")
public class CastleHelpArticleTypeDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键ID")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "类型名称")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;
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
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;
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
	@ApiModelProperty(value = "是否删除YesNoEnum")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
