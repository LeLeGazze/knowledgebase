package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleBannerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * banner图Mapper 接口
 *
 * @author majunjie
 * @since 2022-02-14
 */
public interface CastleBannerMapper extends BaseMapper<CastleBannerEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleBannerEntity
    * @return
    */
    List<CastleBannerEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleBannerEntity") CastleBannerEntity castleBannerEntity);

    /**
    * 扩展信息记录总数
    * @param castleBannerEntity
    * @return
    */
    Long extendsCount(@Param("castleBannerEntity") CastleBannerEntity castleBannerEntity);

    /**
    * banner图扩展详情
    * @param id banner图id
    * @return
    */
    CastleBannerEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @param params
     * @return
     */
    List<CastleBannerEntity> selectDataList(Map<String, Object> params);
}

