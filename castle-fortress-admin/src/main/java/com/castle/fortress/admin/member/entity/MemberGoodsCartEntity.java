package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 会员商品购物车 实体类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Data
@TableName("member_goods_cart")
@EqualsAndHashCode(callSuper = true)
public class MemberGoodsCartEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 商品id
	*/
	private String goodsId;
	/**
	 * 规格项目详情id
	*/
	private String optionId;
	/**
	 * 数量
	*/
	private String total;
	/**
	 * 当前价格（加入购物车时的价格）
	*/
	private String price;

}
