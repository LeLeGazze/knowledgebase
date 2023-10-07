package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.ConfigParamsDto;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;

import java.util.List;

/**
 * 系统参数表 服务类
 *
 * @author castle
 * @since 2022-05-07
 */
public interface ConfigParamsService extends IService<ConfigParamsEntity> {

    /**
     * 分页展示系统参数表列表
     * @param page
     * @param configParamsDto
     * @return
     */
    IPage<ConfigParamsDto> pageConfigParams(Page<ConfigParamsDto> page, ConfigParamsDto configParamsDto);

    /**
    * 分页展示系统参数表扩展列表
    * @param page
    * @param configParamsDto
    * @return
    */
    IPage<ConfigParamsDto> pageConfigParamsExtends(Page<ConfigParamsDto> page, ConfigParamsDto configParamsDto);
    /**
    * 系统参数表扩展详情
    * @param id 系统参数表id
    * @return
    */
    ConfigParamsDto getByIdExtends(Long id);

    /**
     * 展示系统参数表列表
     * @param configParamsDto
     * @return
     */
    List<ConfigParamsDto> listConfigParams(ConfigParamsDto configParamsDto);

}
