package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleHelpArticleTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 帮助中心文章类型Mapper 接口
 *
 * @author majunjie
 * @since 2022-02-09
 */
public interface CastleHelpArticleTypeMapper extends BaseMapper<CastleHelpArticleTypeEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleHelpArticleTypeEntity
    * @return
    */
    List<CastleHelpArticleTypeEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleHelpArticleTypeEntity") CastleHelpArticleTypeEntity castleHelpArticleTypeEntity);

    /**
    * 扩展信息记录总数
    * @param castleHelpArticleTypeEntity
    * @return
    */
    Long extendsCount(@Param("castleHelpArticleTypeEntity") CastleHelpArticleTypeEntity castleHelpArticleTypeEntity);

    /**
    * 帮助中心文章类型扩展详情
    * @param id 帮助中心文章类型id
    * @return
    */
    CastleHelpArticleTypeEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<CastleHelpArticleTypeEntity> selectDataList(Map<String, Object> params);

    /**
     * 根据文章标题模糊查询文章的分类
     */
    List<CastleHelpArticleTypeEntity> selectListByArticleTitle(Map<String, Object> params);
}

