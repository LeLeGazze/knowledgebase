package com.castle.fortress.admin.member.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 微信会员表 实体类
 *
 * @author whc
 * @since 2022-11-28
 */
@Data
@TableName("member_wx")
@EqualsAndHashCode(callSuper = true)
public class MemberWxEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 关联主表ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 用户昵称
	*/
	private String nickName;
	/**
	 * 性别 1为男性，2为女性
	*/
	private Integer sex;
	/**
	 * 省
	*/
	private String province;
	/**
	 * 市
	*/
	private String city;
	/**
	 * 国家
	*/
	private String country;
	/**
	 * 头像
	*/
	private String headImgUrl;
	/**
	 * 微信openid
	*/
	private String openId;
	/**
	 * 微信unionid
	*/
	private String unionId;

}
