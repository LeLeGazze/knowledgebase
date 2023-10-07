package com.castle.fortress.admin.job.config;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.log.entity.LogJobEntity;
import com.castle.fortress.admin.log.service.LogJobService;
import com.castle.fortress.admin.utils.JobUtils;
import com.castle.fortress.admin.utils.SpringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 调度task
 * @DisallowConcurrentExecution 不允许并发执行同一个job
 * @author castle
 */
@DisallowConcurrentExecution
public class ScheduleTask implements Job, Serializable {

	private static final long serialVersionUID = 1L;
	@Value("${castle.logs.job}")
	private boolean jobFlag;


	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		Long start = System.currentTimeMillis();
		Date invokeTime = new Date();
		Long elapsedTime = 0L;
		String invokeStatus = "00";
		JobDetail jobDetail = jobExecutionContext.getJobDetail();
		String taskId = jobDetail.getKey().getName();
		JobDataMap dataMap = jobDetail.getJobDataMap();
		/**
		 * 获取任务中保存的方法名字，动态调用方法
		 */
		String taskName = dataMap.getString(JobUtils.TASK_NAME);
		String taskParams = dataMap.getString(JobUtils.TASK_PARAMS);
		try {
			if(StrUtil.isNotEmpty(taskName)){
				Object target = SpringUtils.getBean(taskName);
				Method method = target.getClass().getDeclaredMethod("runTask", String.class);
				method.invoke(target, taskParams);
				elapsedTime = System.currentTimeMillis()-start;
			}
		} catch (Exception e) {
			e.printStackTrace();
			invokeStatus ="01";
		}finally {
			if(jobFlag){
				LogJobEntity jobEntity = new LogJobEntity(taskName,taskId,taskParams,invokeStatus,invokeTime,elapsedTime);
				LogJobService logJobService = SpringUtils.getBean(LogJobService.class);
				logJobService.saveLog(jobEntity);
			}
		}
	}
}