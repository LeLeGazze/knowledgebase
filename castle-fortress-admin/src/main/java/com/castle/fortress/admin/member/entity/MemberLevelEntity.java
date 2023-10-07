package com.castle.fortress.admin.member.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 会员等级表 实体类
 *
 * @author whc
 * @since 2022-12-29
 */
@Data
@TableName("member_level")
@EqualsAndHashCode(callSuper = true)
public class MemberLevelEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 级别
	*/
	private Integer level;
	/**
	 * 名称
	*/
	private String name;
	/**
	 * 升级条件（三种）
	*/
	private Integer upConditions;
	/**
	 * 订单总额
	*/
	private String orderTotal;
	/**
	 * 订单次数
	*/
	private String orderCount;
	/**
	 * 指定商品id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long goodsId;
	/**
	 * 折扣
	*/
	private String discount;
	/**
	 * 关联商品名称
	 */
	@TableField(exist = false)
	private String goodsName;

}
