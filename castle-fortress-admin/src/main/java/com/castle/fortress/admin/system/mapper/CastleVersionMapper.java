package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleVersionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 版本管理Mapper 接口
 *
 * @author 
 * @since 2022-02-14
 */
public interface CastleVersionMapper extends BaseMapper<CastleVersionEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleVersionEntity
    * @return
    */
    List<CastleVersionEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleVersionEntity") CastleVersionEntity castleVersionEntity);

    /**
    * 扩展信息记录总数
    * @param castleVersionEntity
    * @return
    */
    Long extendsCount(@Param("castleVersionEntity") CastleVersionEntity castleVersionEntity);

    /**
    * 版本管理扩展详情
    * @param id 版本管理id
    * @return
    */
    CastleVersionEntity getByIdExtends(@Param("id")Long id);

    /**
     * 查询多条
     * @return
     */
    List<CastleVersionEntity> selectDataList(Map<String , Object> params);

    /**
     * 获取最新版本号
     * @return
     */
    CastleVersionEntity selectNewVersion();
}

