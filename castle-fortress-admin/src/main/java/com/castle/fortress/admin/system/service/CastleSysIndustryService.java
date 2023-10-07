package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.CastleSysIndustryDto;
import com.castle.fortress.admin.system.entity.CastleSysIndustryEntity;

import java.util.List;

/**
 * 行业职位 服务类
 *
 * @author Mgg
 * @since 2021-09-02
 */
public interface CastleSysIndustryService extends IService<CastleSysIndustryEntity> {

    /**
     * 分页展示行业职位列表
     * @param page
     * @param castleSysIndustryDto
     * @return
     */
    IPage<CastleSysIndustryDto> pageCastleSysIndustry(Page<CastleSysIndustryDto> page, CastleSysIndustryDto castleSysIndustryDto);

    /**
    * 分页展示行业职位扩展列表
    * @param page
    * @param castleSysIndustryDto
    * @return
    */
    IPage<CastleSysIndustryDto> pageCastleSysIndustryExtends(Page<CastleSysIndustryDto> page, CastleSysIndustryDto castleSysIndustryDto);
    /**
    * 行业职位扩展详情
    * @param id 行业职位id
    * @return
    */
    CastleSysIndustryDto getByIdExtends(Long id);

    /**
     * 展示行业职位列表
     * @param castleSysIndustryDto
     * @return
     */
    List<CastleSysIndustryDto> listCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto);

}
