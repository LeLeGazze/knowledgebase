package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
/**
 * 知识回收表 实体类
 *
 * @author 
 * @since 2023-06-01
 */
@Data
@TableName("kb_basic_trash")
public class KbBasicTrashEntity implements Serializable {
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
	 * 知识Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long basicId;
	/**
	 * 用户Id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;
	/**
	 * 收藏类型
	 */
	private Integer type;
	/**
	 * 创建时间
	*/
	@DateTimeFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@JsonFormat(
			pattern = "yyyy-MM-dd HH:mm:ss"
	)
	@ApiModelProperty(value = "创建时间")
	@JsonProperty("createTime")
	private Date createTime;

}
