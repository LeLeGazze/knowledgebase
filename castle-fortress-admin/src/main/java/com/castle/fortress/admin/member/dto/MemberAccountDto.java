package com.castle.fortress.admin.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * 会员账户表 实体类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Data
@ApiModel(value = "memberAccount对象", description = "会员账户表")
public class MemberAccountDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "余额")
	@JsonProperty("balance")
	private String balance;
	@ApiModelProperty(value = "冻结金额")
	@JsonProperty("frozen")
	private String frozen;
	@ApiModelProperty(value = "状态(冻结？正常)")
	@JsonProperty("status")
	private Integer status;

}
