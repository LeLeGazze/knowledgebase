package com.castle.fortress.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.log.dto.LogOperationDto;
import com.castle.fortress.admin.log.entity.LogOperationEntity;

import java.util.List;

/**
 * 用户操作记录日志表 服务类
 *
 * @author castle
 * @since 2021-03-31
 */
public interface LogOperationService extends IService<LogOperationEntity> {

    /**
     * 分页展示用户操作记录日志表列表
     * @param page
     * @param logOperationDto
     * @return
     */
    IPage<LogOperationDto> pageLogOperation(Page<LogOperationDto> page, LogOperationDto logOperationDto);


    /**
     * 展示用户操作记录日志表列表
     * @param logOperationDto
     * @return
     */
    List<LogOperationDto> listLogOperation(LogOperationDto logOperationDto);

    /**
     * 异步保存操作日志
     * @param logOperationEntity
     */
    void saveLog(LogOperationEntity logOperationEntity);

    /**
     * 删除x天之外的数据
     * @param i
     */
    void deleteByTime(int days);
}
