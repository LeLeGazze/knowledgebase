package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.CastleHelpArticleDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleEntity;

import java.util.List;

/**
 * 帮助中心文章 服务类
 *
 * @author majunjie
 * @since 2022-02-09
 */
public interface CastleHelpArticleService extends IService<CastleHelpArticleEntity> {

    /**
     * 分页展示帮助中心文章列表
     * @param page
     * @param castleHelpArticleDto
     * @return
     */
    IPage<CastleHelpArticleDto> pageCastleHelpArticle(Page<CastleHelpArticleDto> page, CastleHelpArticleDto castleHelpArticleDto);

    /**
    * 分页展示帮助中心文章扩展列表
    * @param page
    * @param castleHelpArticleDto
    * @return
    */
    IPage<CastleHelpArticleDto> pageCastleHelpArticleExtends(Page<CastleHelpArticleDto> page, CastleHelpArticleDto castleHelpArticleDto);
    /**
    * 帮助中心文章扩展详情
    * @param id 帮助中心文章id
    * @return
    */
    CastleHelpArticleDto getByIdExtends(Long id);

    /**
     * 展示帮助中心文章列表
     * @param castleHelpArticleDto
     * @return
     */
    List<CastleHelpArticleDto> listCastleHelpArticle(CastleHelpArticleDto castleHelpArticleDto);

    /**
     * 根据类型获取文章
     * @param typeId
     * @return
     */
    List<CastleHelpArticleDto> listByTypeId(Long typeId);
}
