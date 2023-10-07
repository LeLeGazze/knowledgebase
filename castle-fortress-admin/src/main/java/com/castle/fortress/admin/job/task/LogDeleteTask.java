package com.castle.fortress.admin.job.task;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.log.service.LogOperationService;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;
import com.castle.fortress.admin.system.service.ConfigParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 删除过期操作日志的定时任务
 * 每天凌晨0点执行
 **/
@Component("logDeleteTask")
public class LogDeleteTask implements ITask{
	@Autowired
	private LogOperationService logOperationService;

	@Autowired
	private ConfigParamsService configParamsService;

	@Override
	public void runTask(String params){
		//默认30天
		int days = 30;
		//
		QueryWrapper<ConfigParamsEntity> wrapper= new QueryWrapper();
		wrapper.eq("param_code","logDelete");
		wrapper.last("limit 1");
		ConfigParamsEntity entity = configParamsService.getOne(wrapper);
		if (entity !=null && StrUtil.isNotEmpty(entity.getParamValue())){
			int paramValue = Integer.parseInt(entity.getParamValue());
			days = paramValue>0?paramValue:days;
		}
		//获取删除x天的日志
		logOperationService.deleteByTime(days);
	}
}