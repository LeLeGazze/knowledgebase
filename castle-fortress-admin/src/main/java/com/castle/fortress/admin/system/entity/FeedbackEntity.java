package com.castle.fortress.admin.system.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import lombok.EqualsAndHashCode;
import com.castle.fortress.admin.core.entity.BaseEntity;
/**
 * 意见反馈 实体类
 *
 * @author castle
 * @since 2022-11-01
 */
@Data
@TableName("castle_feedback")
@EqualsAndHashCode(callSuper = true)
public class FeedbackEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 反馈内容
	*/
	private String content;
	/**
	 * 反馈图片
	*/
	private String img;
	/**
	 * 反馈类型
	*/
	private Integer typeId;
	/**
	 * 反馈人
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long memberId;
	/**
	 * 联系方式
	*/
	private String contactWay;

}
