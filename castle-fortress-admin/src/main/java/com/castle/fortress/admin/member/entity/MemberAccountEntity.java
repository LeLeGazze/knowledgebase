package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
/**
 * 会员账户表 实体类
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Data
@TableName("member_account")
public class MemberAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 唯一主键
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 余额
	*/
	private String balance;
	/**
	 * 冻结金额
	*/
	private String frozen;
	/**
	 * 状态(冻结？正常)
	*/
	private Integer status;

}
