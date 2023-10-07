package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleHelpArticleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 帮助中心文章Mapper 接口
 *
 * @author majunjie
 * @since 2022-02-09
 */
public interface CastleHelpArticleMapper extends BaseMapper<CastleHelpArticleEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleHelpArticleEntity
    * @return
    */
    List<CastleHelpArticleEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleHelpArticleEntity") CastleHelpArticleEntity castleHelpArticleEntity);

    /**
    * 扩展信息记录总数
    * @param castleHelpArticleEntity
    * @return
    */
    Long extendsCount(@Param("castleHelpArticleEntity") CastleHelpArticleEntity castleHelpArticleEntity);

    /**
    * 帮助中心文章扩展详情
    * @param id 帮助中心文章id
    * @return
    */
    CastleHelpArticleEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<CastleHelpArticleEntity> selectDataList(Map<String, Object> params);
}

