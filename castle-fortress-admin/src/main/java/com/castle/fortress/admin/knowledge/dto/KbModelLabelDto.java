package com.castle.fortress.admin.knowledge.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 标签管理表 实体类
 *
 * @author 
 * @since 2023-04-26
 */
@Data
@ApiModel(value = "kbModelLabel对象", description = "标签管理表")
public class KbModelLabelDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "标签名")
	@JsonProperty("name")
	private String name;
	@ApiModelProperty(value = "标签名")
	@JsonProperty("ctName")
	private String ctName;
	@ApiModelProperty(value = "标签状态")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "排序号")
	@JsonProperty("sort")
	private Integer sort;
	@ApiModelProperty(value = "是否热词")
	@JsonProperty("hotWord")
	private Integer hotWord;
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
	@ApiModelProperty(value = "是否已删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;

	@ApiModelProperty(value = "使用次数")
	@JsonProperty("count")
	private int count;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KbModelLabelDto that = (KbModelLabelDto) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
