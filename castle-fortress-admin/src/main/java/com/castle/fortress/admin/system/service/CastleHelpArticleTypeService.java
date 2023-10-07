package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.CastleHelpArticleTypeDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心文章类型 服务类
 *
 * @author majunjie
 * @since 2022-02-09
 */
public interface CastleHelpArticleTypeService extends IService<CastleHelpArticleTypeEntity> {

    /**
     * 分页展示帮助中心文章类型列表
     * @param page
     * @param castleHelpArticleTypeDto
     * @return
     */
    IPage<CastleHelpArticleTypeDto> pageCastleHelpArticleType(Page<CastleHelpArticleTypeDto> page, CastleHelpArticleTypeDto castleHelpArticleTypeDto);

    /**
    * 分页展示帮助中心文章类型扩展列表
    * @param page
    * @param castleHelpArticleTypeDto
    * @return
    */
    IPage<CastleHelpArticleTypeDto> pageCastleHelpArticleTypeExtends(Page<CastleHelpArticleTypeDto> page, CastleHelpArticleTypeDto castleHelpArticleTypeDto);
    /**
    * 帮助中心文章类型扩展详情
    * @param id 帮助中心文章类型id
    * @return
    */
    CastleHelpArticleTypeDto getByIdExtends(Long id);

    /**
     * 展示帮助中心文章类型列表
     * @param castleHelpArticleTypeDto
     * @return
     */
    List<CastleHelpArticleTypeDto> listCastleHelpArticleType(CastleHelpArticleTypeDto castleHelpArticleTypeDto);

    /**
     * 获取所有帮助文章类型
     * @param params
     * @return
     */
    List<CastleHelpArticleTypeDto> getDataList(Map<String , Object> params);

    /**
     * 根据文章标题模糊查询文章的分类
     * @param params
     * @return
     */
    List<CastleHelpArticleTypeDto> listByArticleTitle(Map<String, Object> params);
}
