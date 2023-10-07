package com.castle.fortress.admin.system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 银行信息 实体类
 *
 * @author castle
 * @since 2022-11-02
 */
@Data
@ApiModel(value = "bank对象", description = "银行信息")
public class BankDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键")
	@JsonProperty("id")
	private Long id;
	@ApiModelProperty(value = "银行名称")
	@JsonProperty("bankName")
	private String bankName;
	@ApiModelProperty(value = "银行简码")
	@JsonProperty("bankCode")
	private String bankCode;
	@ApiModelProperty(value = "银行logo")
	@JsonProperty("bankLogo")
	private String bankLogo;


	@ApiModelProperty(value = "银行卡类型 BankCardTypeEnum")
	@JsonProperty("cardType")
	private Integer cardType;

}
