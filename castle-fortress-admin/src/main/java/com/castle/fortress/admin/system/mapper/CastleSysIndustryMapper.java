package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.CastleSysIndustryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 行业职位Mapper 接口
 *
 * @author Mgg
 * @since 2021-09-02
 */
public interface CastleSysIndustryMapper extends BaseMapper<CastleSysIndustryEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param castleSysIndustryEntity
    * @return
    */
    List<CastleSysIndustryEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("castleSysIndustryEntity") CastleSysIndustryEntity castleSysIndustryEntity);

    /**
    * 扩展信息记录总数
    * @param castleSysIndustryEntity
    * @return
    */
    Long extendsCount(@Param("castleSysIndustryEntity") CastleSysIndustryEntity castleSysIndustryEntity);

    /**
    * 行业职位扩展详情
    * @param id 行业职位id
    * @return
    */
    CastleSysIndustryEntity getByIdExtends(@Param("id")Long id);



}

