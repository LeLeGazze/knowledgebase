package com.castle.fortress.admin.member.dto;

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
 * 会员积分流水 实体类
 *
 * @author Mgg
 * @since 2021-12-01
 */
@Data
@ApiModel(value = "memberPointsSerial对象", description = "会员积分流水")
public class MemberPointsSerialDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员id")
	@JsonProperty("memberId")
	private Long memberId;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "会员积分账户id")
	@JsonProperty("memberPointsId")
	private Long memberPointsId;
	@ApiModelProperty(value = "积分")
	@JsonProperty("points")
	private String points;
	@ApiModelProperty(value = "流水类型（加或减）")
	@JsonProperty("type")
	private Integer type;
	@ApiModelProperty(value = "类别（为什么加或减）")
	@JsonProperty("category")
	private Integer category;
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
	@ApiModelProperty(value = "状态")
	@JsonProperty("status")
	private Integer status;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

	@ApiModelProperty(value = "备注")
	@JsonProperty("memo")
	private String memo;

	@ApiModelProperty(value = "订单id")
	@JsonProperty("orderId")
	private String orderId;


	@ApiModelProperty(value = "变动后账户积分 实时积分")
	@JsonProperty("accountPoints")
	private String accountPoints;




	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "查询开始时间")
	@JsonProperty("startTime")
	private Date startTime;
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "查询结束时间")
	@JsonProperty("endTime")
	private Date endTime;

}
