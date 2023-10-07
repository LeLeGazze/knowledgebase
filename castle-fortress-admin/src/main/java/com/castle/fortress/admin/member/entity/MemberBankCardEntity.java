package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
/**
 * 会员银行卡表 实体类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Data
@TableName("member_bank_card")
@EqualsAndHashCode(callSuper = true)
public class MemberBankCardEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员ID
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 银行卡号
	*/
	private String cardNum;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String idCardNum;
	/**
	 * 开户行名称(分行)
	*/
	private String openAccount;
	/**
	 * 所属银行
	*/
	private String bankName;
	/**
	 * 银行预留手机号
	*/
	private String phone;
	/**
	 * 银行卡照片
	*/
	private String cardUrl;
	/**
	 * 卡片有效期
	*/
	private String validDate;
	/**
	 * 卡类型DC(借记卡),  CC(贷记卡),  SCC(准贷记卡), DCC(存贷合一卡), PC(预付卡)
	*/
	private String cardType;
	/**
	 * 银行类型
	*/
	private String bankType;
	/**
	 * 支行所在省市
	*/
	private String city;

}
