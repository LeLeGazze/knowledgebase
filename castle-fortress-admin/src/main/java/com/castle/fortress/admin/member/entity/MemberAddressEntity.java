package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 会员收货地址表 实体类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Data
@TableName("member_address")
@EqualsAndHashCode(callSuper = true)
public class MemberAddressEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 收货人
	*/
	private String consignee;
	/**
	 * 手机号
	*/
	private String phone;
	/**
	 * 省
	*/
	private String province;
	/**
	 * 省地区编号形式
	 */
	private String provinceCode;
	/**
	 * 市
	*/
	private String city;
	/**
	 * 市地区编号形式
	*/
	private String cityCode;
	/**
	 * 区
	*/
	private String area;
	/**
	 * 区地区编号形式
	*/
	private String areaCode;
	/**
	 * 详细地址
	*/
	private String address;
	/**
	 * 是否为默认地址1.是2.否
	*/
	private Integer isDefault;



}
