package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 会员账户流水 实体类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Data
@TableName("member_account_serial")
@EqualsAndHashCode(callSuper = true)
public class MemberAccountSerialEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 会员余额账户id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberAccountId;
	/**
	 * 金额
	*/
	private String money;
	/**
	 * 流水类型（加或减）
	*/
	private Integer type;
	/**
	 * 类别（为什么加或减）
	*/
	private Integer category;

}
