package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 系统参数表Mapper 接口
 *
 * @author castle
 * @since 2022-05-07
 */
public interface ConfigParamsMapper extends BaseMapper<ConfigParamsEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param configParamsEntity
    * @return
    */
    List<ConfigParamsEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("configParamsEntity") ConfigParamsEntity configParamsEntity);

    /**
    * 扩展信息记录总数
    * @param configParamsEntity
    * @return
    */
    Long extendsCount(@Param("configParamsEntity") ConfigParamsEntity configParamsEntity);

    /**
    * 系统参数表扩展详情
    * @param id 系统参数表id
    * @return
    */
    ConfigParamsEntity getByIdExtends(@Param("id")Long id);



}

