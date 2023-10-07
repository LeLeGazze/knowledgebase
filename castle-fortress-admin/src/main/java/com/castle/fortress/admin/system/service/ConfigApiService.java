package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.ConfigApiEntity;

import java.util.List;

/**
 * 框架绑定api配置管理 服务类
 *
 * @author castle
 * @since 2022-04-12
 */
public interface ConfigApiService extends IService<ConfigApiEntity> {

    /**
     * 分页展示框架绑定api配置管理列表
     * @param page
     * @param configApiDto
     * @return
     */
    IPage<ConfigApiDto> pageConfigApi(Page<ConfigApiDto> page, ConfigApiDto configApiDto);


    /**
     * 展示框架绑定api配置管理列表
     * @param configApiDto
     * @return
     */
    List<ConfigApiDto> listConfigApi(ConfigApiDto configApiDto);

    /**
     * 根据编码获取配置
     * @param bindCode
     * @return
     */
    ConfigApiDto getByBindCode(String bindCode);

}
