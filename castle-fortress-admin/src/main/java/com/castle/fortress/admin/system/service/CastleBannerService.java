package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.CastleBannerDto;
import com.castle.fortress.admin.system.entity.CastleBannerEntity;

import java.util.List;

/**
 * banner图 服务类
 *
 * @author majunjie
 * @since 2022-02-14
 */
public interface CastleBannerService extends IService<CastleBannerEntity> {

    /**
     * 分页展示banner图列表
     * @param page
     * @param castleBannerDto
     * @return
     */
    IPage<CastleBannerDto> pageCastleBanner(Page<CastleBannerDto> page, CastleBannerDto castleBannerDto);

    /**
    * 分页展示banner图扩展列表
    * @param page
    * @param castleBannerDto
    * @return
    */
    IPage<CastleBannerDto> pageCastleBannerExtends(Page<CastleBannerDto> page, CastleBannerDto castleBannerDto);
    /**
    * banner图扩展详情
    * @param id banner图id
    * @return
    */
    CastleBannerDto getByIdExtends(Long id);

    /**
     * 展示banner图列表
     * @param castleBannerDto
     * @return
     */
    List<CastleBannerDto> listCastleBanner(CastleBannerDto castleBannerDto);

    /**
     * 获取所有启用的banner图
     * @return
     */
    List<CastleBannerDto> getDataList();
}
