package com.castle.fortress.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.log.dto.LogOpenapiDto;
import com.castle.fortress.admin.log.entity.LogOpenapiEntity;

import java.util.List;

/**
 * 对外开放api调用日志 服务类
 *
 * @author castle
 * @since 2021-04-01
 */
public interface LogOpenapiService extends IService<LogOpenapiEntity> {

    /**
     * 分页展示对外开放api调用日志列表
     * @param page
     * @param logOpenapiDto
     * @return
     */
    IPage<LogOpenapiDto> pageLogOpenapi(Page<LogOpenapiDto> page, LogOpenapiDto logOpenapiDto);


    /**
     * 展示对外开放api调用日志列表
     * @param logOpenapiDto
     * @return
     */
    List<LogOpenapiDto> listLogOpenapi(LogOpenapiDto logOpenapiDto);

    /**
     * 异步保存日志
     * @param logEntity
     */
    void saveLog(LogOpenapiEntity logEntity);

}
