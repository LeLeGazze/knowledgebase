package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.CastleVersionDto;
import com.castle.fortress.admin.system.entity.CastleVersionEntity;

import java.util.List;

/**
 * 版本管理 服务类
 *
 * @author 
 * @since 2022-02-14
 */
public interface CastleVersionService extends IService<CastleVersionEntity> {

    /**
     * 分页展示版本管理列表
     * @param page
     * @param castleVersionDto
     * @return
     */
    IPage<CastleVersionDto> pageCastleVersion(Page<CastleVersionDto> page, CastleVersionDto castleVersionDto);

    /**
    * 分页展示版本管理扩展列表
    * @param page
    * @param castleVersionDto
    * @return
    */
    IPage<CastleVersionDto> pageCastleVersionExtends(Page<CastleVersionDto> page, CastleVersionDto castleVersionDto);
    /**
    * 版本管理扩展详情
    * @param id 版本管理id
    * @return
    */
    CastleVersionDto getByIdExtends(Long id);

    /**
     * 展示版本管理列表
     * @param castleVersionDto
     * @return
     */
    List<CastleVersionDto> listCastleVersion(CastleVersionDto castleVersionDto);

    /**
     * 查询多条
     * @return
     */
    List<CastleVersionDto> getDataList();

    /**
     * 查询最新版本信息
     * @return
     */
    CastleVersionDto getNewVersion();
}
