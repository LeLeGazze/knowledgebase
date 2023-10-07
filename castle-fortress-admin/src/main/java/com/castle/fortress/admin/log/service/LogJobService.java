package com.castle.fortress.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.log.dto.LogJobDto;
import com.castle.fortress.admin.log.entity.LogJobEntity;

import java.util.List;

/**
 * 定时任务调用日志 服务类
 *
 * @author castle
 * @since 2021-04-02
 */
public interface LogJobService extends IService<LogJobEntity> {

    /**
     * 分页展示定时任务调用日志列表
     * @param page
     * @param logJobDto
     * @return
     */
    IPage<LogJobDto> pageLogJob(Page<LogJobDto> page, LogJobDto logJobDto);


    /**
     * 展示定时任务调用日志列表
     * @param logJobDto
     * @return
     */
    List<LogJobDto> listLogJob(LogJobDto logJobDto);

    /**
     * 异步保存日志
     * @param logEntity
     */
    void saveLog(LogJobEntity logEntity);

}
