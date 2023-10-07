package com.castle.fortress.admin.job.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * 定时的相关配置
 */
@Configuration
public class SchedulerConfig {
    @Autowired
    private SpringJobFactory springJobFactory;

    @Bean
    public Properties quartzProperties(){
        Properties properties = new Properties();
        //配置调度器配置
        //在同一个程序中，使用该名称来区分scheduler,如果是集群环境，必须使用同一个名称，表示逻辑相同的Scheduler
        properties.put("org.quartz.scheduler.instanceName", "CastleScheduler");
        //在集群环境下，instanceId必须唯一，即使是逻辑相同的Scheduler，一般设为AUTO
        properties.put("org.quartz.scheduler.instanceId", "AUTO");

        //线程池配置
        //必须配置，线程池实现的类名，Quartz附带的线程池是“org.quartz.simpl.SimpleThreadPool”，并且应该能够满足几乎每个用户的需求。它有非常简单的行为，并经过很好的测试。它提供了一个固定大小的线程池，
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        //必须配置，线程数
        properties.put("org.quartz.threadPool.threadCount", "20");
        //线程优先级，默认是5
        properties.put("org.quartz.threadPool.threadPriority", "5");

        //JobStore配置
        //RAMJobStore(基于内存)和JDBCJobStore用于在关系数据库中存储调度信息
//        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //springboot 2.7.0
        properties.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");
        //必须配置，代表了解不同数据库系统的特定“方言”。StdJDBCDelegate（用于完全符合JDBC的驱动程序）
        properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        //数据库表名的前缀，支持自定义
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        //在被认为“失火”之前，调度程序将“容忍”一个Triggers将其下一个启动时间通过的毫秒数
        properties.put("org.quartz.jobStore.misfireThreshold", "12000");
        //在给定的通行证中，工作区将处理的最大错误次数触发
        properties.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "7");
        //设置为“true”以打开群集功能
        properties.put("org.quartz.jobStore.isClustered", "false");
        //检测群集中的其他实例的频率（以毫秒为单位）
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        //在LOCKS表中选择一行，然后再这一行放一把锁
        properties.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS UPDLOCK WHERE LOCK_NAME = ?");
        return properties;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        //调度名称
        schedulerFactoryBean.setSchedulerName("CastleScheduler");
        // 延时启动，应用启动7秒后
        schedulerFactoryBean.setStartupDelay(7);
        schedulerFactoryBean.setJobFactory(springJobFactory);
        schedulerFactoryBean.setDataSource(dataSource);
        return schedulerFactoryBean;
    }


}
