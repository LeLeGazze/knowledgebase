package com.castle.fortress.admin.system.dto;

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
 * 文件传输配置表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@ApiModel(value = "文件传输配置对象", description = "文件传输配置对象")
public class ConfigOssDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "文件存储编码")
	private String ftCode;
	@ApiModelProperty(value = "存储平台类型")
	private Integer platform;
	@ApiModelProperty(value = "平台配置")
	private String ptConfig;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "状态 YesNoEnum。yes生效；no失效")
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
	@ApiModelProperty(value = "存储平台配置对象")
	private OssPlatFormDto ossPlatFormDto;

}
