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
 * oss上传记录 实体类
 *
 * @author castle
 * @since 2022-03-01
 */
@Data
@ApiModel(value = "sysOssRecord对象", description = "oss上传记录")
public class SysOssRecordDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "id")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "文件名")
	@JsonProperty("resourceName")
	private String resourceName;
	@ApiModelProperty(value = "文件地址")
	@JsonProperty("resourceUrl")
	private String resourceUrl;
	@ApiModelProperty(value = "平台类型")
	@JsonProperty("ossPlatform")
	private Integer ossPlatform;
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
	@ApiModelProperty(value = "用户类型")
	@JsonProperty("userType")
	private Integer userType;
	//image / video
	@ApiModelProperty(value = "资源类型")
	@JsonProperty("resourceType")
	private String resourceType;

}
