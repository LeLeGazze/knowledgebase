package com.castle.fortress.admin.knowledge.entity;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
/**
 * 标签删除任务表 实体类
 *
 * @author 
 * @since 2023-06-07
 */
@Data
@TableName("kb_base_label_task")
public class KbBaseLabelTaskEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主鍵
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	@TableId(
			value = "id",
			type = IdType.ASSIGN_ID
	)
	private Long id;
	/**
	 * 删除标签id
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Long lId;
	/**
	 * 状态: 1：新增，2：正常处理中 3：处理失败
	*/
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer status;
	/**
	 * 失败日志
	*/
	private String message;
	/**
	 * 创建时间
	*/
	private Date createTime;
	/**
	 * 任务处理时间
	*/
	private Date taskTime;

}
