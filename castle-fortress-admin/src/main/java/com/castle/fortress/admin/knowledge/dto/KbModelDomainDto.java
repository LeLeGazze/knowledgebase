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
 * 值域字典表 实体类
 *
 * @author 
 * @since 2023-04-20
 */
@Data
@ApiModel(value = "kbModelDomain对象", description = "值域字典表")
public class KbModelDomainDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "库名称")
	@JsonProperty("name")
	private String name;
	private Integer pid;
	@ApiModelProperty(value = "值域地址")
	@JsonProperty("url")
	private String url;
	@ApiModelProperty(value = "展示类型")
	@JsonProperty("showType")
	private Integer key;
	@ApiModelProperty(value = "备注")
	@JsonProperty("remark")
	private String remark;
	@DateTimeFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
		pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "更新时间")
	@JsonProperty("updateTime")
	private Date updateTime;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
