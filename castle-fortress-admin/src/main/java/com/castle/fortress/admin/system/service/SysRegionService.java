package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysRegionDto;
import com.castle.fortress.admin.system.entity.SysRegionEntity;

import java.util.List;

/**
 * 行政区域 服务类
 *
 * @author castle
 * @since 2021-04-28
 */
public interface SysRegionService extends IService<SysRegionEntity> {

    /**
     * 分页展示行政区域列表
     * @param page
     * @param sysRegionDto
     * @return
     */
    IPage<SysRegionDto> pageSysRegion(Page<SysRegionDto> page, SysRegionDto sysRegionDto);


    /**
     * 展示行政区域列表
     * @param sysRegionDto
     * @return
     */
    List<SysRegionDto> listSysRegion(SysRegionDto sysRegionDto);

    SysRegionEntity getByIdExtends(Long id);

    /**
     * 异步初始化redis的地区树
     */
    void initRegionTree();
}
