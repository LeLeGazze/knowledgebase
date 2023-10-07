package com.castle.fortress.admin.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.job.entity.ConfigTaskEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 系统任务调度表Mapper 接口
 *
 * @author 
 * @since 2021-03-24
 */
public interface ConfigTaskMapper extends BaseMapper<ConfigTaskEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param configTaskEntity
    * @return
    */
    List<ConfigTaskEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("configTaskEntity") ConfigTaskEntity configTaskEntity);

    /**
    * 扩展信息记录总数
    * @param configTaskEntity
    * @return
    */
    Long extendsCount(@Param("configTaskEntity") ConfigTaskEntity configTaskEntity);

    /**
    * 系统任务调度表扩展详情
    * @param id 系统任务调度表id
    * @return
    */
    ConfigTaskEntity getByIdExtends(@Param("id")Long id);



}

