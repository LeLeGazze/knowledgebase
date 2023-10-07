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
 * 对外开放接口秘钥 实体类
 *
 * @author 
 * @since 2021-03-19
 */
@Data
@ApiModel(value = "apiSecret对象", description = "对外开放接口秘钥")
public class ApiSecretDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "客户名称")
	private String custName;
	@ApiModelProperty(value = "秘钥id")
	private String secretId;
	@ApiModelProperty(value = "秘钥key")
	private String secretKey;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd"
	)
	@ApiModelProperty(value = "过期日期")
	private Date expiredDate;
	@ApiModelProperty(value = "启用状态")
	private Integer status;

}
