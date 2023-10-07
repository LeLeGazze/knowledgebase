package com.castle.fortress.admin.utils;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.job.config.ScheduleTask;
import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.common.enums.JobStatusEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.quartz.*;

/**
 * JOB工具类
 */
public class JobUtils {
    /**
     * 任务调度对象
     */
    public static final String TASK_NAME = "TASK_NAME";
    public static final String TASK_PARAMS = "TASK_PARAMS";

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long taskId) {
        return TriggerKey.triggerKey(GlobalConstants.TRIGGER_NAME_PREFIX + taskId,GlobalConstants.TRIGGER_GROUP_DEFAULT);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(Long taskId) {
        return JobKey.jobKey(GlobalConstants.JOB_NAME_PREFIX + taskId,GlobalConstants.JOB_GROUP_DEFAULE);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long taskId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(taskId));
        } catch (SchedulerException e) {
            throw new BizException(BizErrorCode.JOB_ERROR);
        }
    }

    /**
     * 创建定时任务
     */
    public static void createJob(Scheduler scheduler, ConfigTaskDto taskDto) {
        try {
            //校验job是否存在
            if(scheduler.checkExists(getJobKey(taskDto.getId()))){
                //移除原有的定时任务
                scheduler.deleteJob(getJobKey(taskDto.getId()));
            }
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleTask.class).withIdentity(getJobKey(taskDto.getId())).build();
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskDto.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(taskDto.getId())).withSchedule(scheduleBuilder).build();
            //放入参数，运行时的方法可以获取
            JobDataMap dataMap = jobDetail.getJobDataMap();
            dataMap.put(TASK_NAME, taskDto.getTaskName());
            dataMap.put(TASK_PARAMS, taskDto.getParams());
            scheduler.scheduleJob(jobDetail, trigger);
            if(!JobStatusEnum.RUN.getCode().equals(taskDto.getStatus())){
                scheduler.pauseJob(getJobKey(taskDto.getId()));
            }
        } catch (SchedulerException e) {
//            throw new BizException(BizErrorCode.JOB_ERROR);
            //when error do nothing
        }
    }

    /**
     * 立即执行任务
     */
    public static void triggerJob(Scheduler scheduler, ConfigTaskDto taskDto) {
        try {
            //参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(TASK_NAME, taskDto.getTaskName());
            dataMap.put(TASK_PARAMS, taskDto.getParams());
            scheduler.triggerJob(getJobKey(taskDto.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new BizException(BizErrorCode.JOB_ERROR);
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BizException(BizErrorCode.JOB_ERROR);
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BizException(BizErrorCode.JOB_ERROR);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new BizException(BizErrorCode.JOB_ERROR);
        }
    }

}
