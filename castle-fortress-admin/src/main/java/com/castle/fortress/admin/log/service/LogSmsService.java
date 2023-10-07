package com.castle.fortress.admin.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogSmsEntity;

import java.util.List;
import java.util.Map;

/**
 * 短信发送记录 服务类
 *
 * @author Mgg
 * @since 2021-12-06
 */
public interface LogSmsService extends IService<LogSmsEntity> {

    /**
     * 分页展示短信发送记录列表
     * @param page
     * @param logSmsDto
     * @return
     */
    IPage<LogSmsDto> pageLogSms(Page<LogSmsDto> page, LogSmsDto logSmsDto);

    /**
    * 分页展示短信发送记录扩展列表
    * @param page
    * @param logSmsDto
    * @return
    */
    IPage<LogSmsDto> pageLogSmsExtends(Page<LogSmsDto> page, LogSmsDto logSmsDto);
    /**
    * 短信发送记录扩展详情
    * @param id 短信发送记录id
    * @return
    */
    LogSmsDto getByIdExtends(Long id);

    /**
     * 展示短信发送记录列表
     * @param logSmsDto
     * @return
     */
    List<LogSmsDto> listLogSms(LogSmsDto logSmsDto);

    /**
     * 查询短信列表
     */
    List<LogSmsDto> getDataList(Map<String , Object> params );

    /**
     * 检测短信状态是否失效
     */
    void checkCodeSms();
}
