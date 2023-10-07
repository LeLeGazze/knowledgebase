package com.castle.fortress.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.log.dto.LogLoginDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;

import java.util.List;

/**
 * 登录操作日志 服务类
 *
 * @author castle
 * @since 2021-04-01
 */
public interface LogLoginService extends IService<LogLoginEntity> {

    /**
     * 分页展示登录操作日志列表
     * @param page
     * @param logLoginDto
     * @return
     */
    IPage<LogLoginDto> pageLogLogin(Page<LogLoginDto> page, LogLoginDto logLoginDto);


    /**
     * 展示登录操作日志列表
     * @param logLoginDto
     * @return
     */
    List<LogLoginDto> listLogLogin(LogLoginDto logLoginDto);

    /**
     * 异步保存日志
     * @param logEntity
     */
    void saveLog(LogLoginEntity logEntity);
}
