package com.castle.fortress.admin.job.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.castle.fortress.admin.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 系统任务调度表 实体类
 *
 * @author 
 * @since 2021-03-24
 */
@Data
@TableName("castle_config_task")
@EqualsAndHashCode(callSuper = true)
public class ConfigTaskEntity extends BaseEntity  {
	private static final long serialVersionUID = 1L;
	/**
	 * 任务名称
	*/
	private String taskName;
	/**
	 * 参数
	*/
	private String params;
	/**
	 * cron表达式
	*/
	private String cronExpression;
	/**
	 * 备注
	 */
	private String remark;

}
