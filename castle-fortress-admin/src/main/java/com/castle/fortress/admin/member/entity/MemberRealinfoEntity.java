package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 会员真实信息表 实体类
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Data
@TableName("member_realinfo")
@EqualsAndHashCode(callSuper = true)
public class MemberRealinfoEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 真实姓名
	*/
	private String realName;
	/**
	 * 身份证号
	*/
	private String cardNum;
	/**
	 * 证件正面照片
	*/
	private String imgFront;
	/**
	 * 证件背面照片
	*/
	private String imgBack;
	/**
	 * 出生日期
	*/
	private String birthday;
	/**
	 * 住址
	*/
	private String address;
	/**
	 * 证件有效期
	*/
	private String validityDate;
	/**
	 * 发证机关
	*/
	private String issuingAuthority;
	/**
	 * 民族
	*/
	private String nation;
	/**
	 * 性别
	*/
	private String gender;
	/**
	 * 营业执照类型
	*/
	private String cardType;
	/**
	 * 法定代表人
	*/
	private String person;
	/**
	 * 注册资本
	*/
	private String capital;
	/**
	 * 经营范围
	*/
	private String business;
	/**
	 * 公司名称
	*/
	private String enterpriseName;
	/**
	 * 统一社会信用代码
	*/
	private String creditCode;
	/**
	 * 成立日期
	*/
	private Date regDate;
	/**
	 * 营业期限
	*/
	private String businessTerm;
	/**
	 * 住所
	*/
	private String residence;
	/**
	 * 登记机关
	*/
	private String regAuthority;
	/**
	 * 发证日期
	*/
	private Date awardDate;

}
