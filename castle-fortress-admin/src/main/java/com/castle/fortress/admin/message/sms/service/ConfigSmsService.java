package com.castle.fortress.admin.message.sms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.message.sms.dto.ConfigSmsDto;
import com.castle.fortress.admin.message.sms.entity.ConfigSmsEntity;
import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * 短信配置表 服务类
 *
 * @author castle
 * @since 2021-04-12
 */
public interface ConfigSmsService extends IService<ConfigSmsEntity> {

    /**
     * 分页展示短信配置表列表
     * @param page
     * @param configSmsDto
     * @return
     */
    IPage<ConfigSmsDto> pageConfigSms(Page<ConfigSmsDto> page, ConfigSmsDto configSmsDto);

    /**
    * 分页展示短信配置表扩展列表
    * @param page
    * @param configSmsDto
    * @return
    */
    IPage<ConfigSmsDto> pageConfigSmsExtends(Page<ConfigSmsDto> page, ConfigSmsDto configSmsDto);
    /**
    * 短信配置表扩展详情
    * @param id 短信配置表id
    * @return
    */
    ConfigSmsDto getByIdExtends(Long id);

    /**
     * 展示短信配置表列表
     * @param configSmsDto
     * @return
     */
    List<ConfigSmsDto> listConfigSms(ConfigSmsDto configSmsDto);

    /**
     * 检查字段是否重复
     * @param configSmsDto
     * @return
     */
    RespBody checkColumnRepeat(ConfigSmsDto configSmsDto);

}
