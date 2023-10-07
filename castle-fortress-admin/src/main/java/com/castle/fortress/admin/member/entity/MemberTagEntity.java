package com.castle.fortress.admin.member.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 会员标签表 实体类
 *
 * @author whc
 * @since 2022-12-08
 */
@Data
@TableName("member_tag")
@EqualsAndHashCode(callSuper = true)
public class MemberTagEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 标签名
	*/
	private String name;
	/**
	 * 排序字段
	*/
	private Integer sort;

}
