package com.castle.fortress.admin.member.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 会员商品收藏表 实体类
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Data
@TableName("member_goods_collect")
@EqualsAndHashCode(callSuper = true)
public class MemberGoodsCollectEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 商品id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long goodsId;
	/**
	 * 会员id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;

}
