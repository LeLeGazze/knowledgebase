package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
/**
 * 会员积分表 实体类
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Data
@TableName("member_points")
public class MemberPointsEntity implements Serializable {
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
	 * 剩余积分
	*/
	private String balance;
	/**
	 * 冻结积分
	*/
	private String frozen;
	/**
	 * 状态
	*/
	private Integer status;

}
