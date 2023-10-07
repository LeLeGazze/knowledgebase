package com.castle.fortress.admin.member.dto;

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
 * 微信会员表 实体类
 *
 * @author whc
 * @since 2022-11-28
 */
@Data
@ApiModel(value = "memberWx对象", description = "微信会员表")
public class MemberWxDto implements Serializable {
	private static final long serialVersionUID = 1L;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "唯一主键")
	@JsonProperty("id")
	private Long id;
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "关联主表ID")
	@JsonProperty("memberId")
	private Long memberId;
	@ApiModelProperty(value = "用户昵称")
	@JsonProperty("nickName")
	private String nickName;
	@ApiModelProperty(value = "性别 1为男性，2为女性")
	@JsonProperty("sex")
	private Integer sex;
	@ApiModelProperty(value = "省")
	@JsonProperty("province")
	private String province;
	@ApiModelProperty(value = "市")
	@JsonProperty("city")
	private String city;
	@ApiModelProperty(value = "国家")
	@JsonProperty("country")
	private String country;
	@ApiModelProperty(value = "头像")
	@JsonProperty("headImgUrl")
	private String headImgUrl;
	@ApiModelProperty(value = "会员状态")
	@JsonProperty("status")
	private Boolean status;
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
	@ApiModelProperty(value = "是否删除")
	@JsonProperty("isDeleted")
	private Integer isDeleted;
	@ApiModelProperty(value = "微信openid")
	@JsonProperty("openId")
	private String openId;
	@ApiModelProperty(value = "微信unionid")
	@JsonProperty("unionId")
	private String unionId;
	@ApiModelProperty(value = "创建者姓名")
	@JsonProperty("createUserName")
	private String createUserName;

}
