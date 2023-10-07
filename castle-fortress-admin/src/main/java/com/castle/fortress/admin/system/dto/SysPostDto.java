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
import java.util.List;
/**
 * 系统职位表 实体类
 *
 * @author castle
 * @since 2021-01-04
 */
@Data
@ApiModel(value = "sysPost对象", description = "系统职位表")
public class SysPostDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "职位名称")
	private String name;
	@ApiModelProperty(value = "职位描述")
	private String remark;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "上级职位")
	private Long parentId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "所属部门")
	private Long deptId;
	@ApiModelProperty(value = "数据权限类型参照枚举DataPermissionPostEnum")
	private Integer dataAuthType;
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
	@ApiModelProperty(value = "创建者姓名")
	private String createUserName;
	@ApiModelProperty(value = "下级职位")
	private List<SysPostDto> children;
}
