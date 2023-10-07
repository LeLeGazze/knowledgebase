package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 知识banner图表 实体类
 *
 * @author 
 * @since 2023-06-17
 */
@Data
@TableName("kb_banner")
public class KbBannerEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 背景图
	*/
	private String pcImage;
	/**
	 * app图片地址
	*/
	private String appImage;
	/**
	 * 图片标签，分号间隔
	*/
	private String tags;
	/**
	 * 状态 YesNoEnum
	*/
	private Integer status;
	/**
	 * 跳转连接
	*/
	private String url;
	/**
	 * 权重
	*/
	private Integer weight;
	/**
	 * 修改人
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long updateUser;
	/**
	 * 修改时间
	*/
	private Date updateTime;

}
