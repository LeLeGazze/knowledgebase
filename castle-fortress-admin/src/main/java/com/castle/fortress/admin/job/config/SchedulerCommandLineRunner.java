package com.castle.fortress.admin.job.config;

import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.admin.job.service.ConfigTaskService;
import com.castle.fortress.admin.utils.JobUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时任务项目启动后初始化数据
 * @author castle
 */
@Component
@Order(value = 1)
public class SchedulerCommandLineRunner implements CommandLineRunner {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ConfigTaskService configTaskService;


    @Override
    public void run(String... args) throws Exception {
        //查询所有运行中的任务
        List<ConfigTaskDto> taskDtos = configTaskService.listRun();
        for(ConfigTaskDto taskDto : taskDtos){
            JobUtils.createJob(scheduler,taskDto);
        }
    }
}
