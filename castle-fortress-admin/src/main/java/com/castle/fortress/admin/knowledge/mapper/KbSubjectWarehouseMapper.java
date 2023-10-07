package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;

import java.io.Serializable;
import java.util.Map;
import java.util.List;
/**
 * 主题知识仓库Mapper 接口
 *
 * @author lyz
 * @since 2023-04-24
 */
public interface KbSubjectWarehouseMapper extends BaseMapper<KbSubjectWarehouseEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbSubjectWarehouseEntity
    * @return
    */
    List<KbSubjectWarehouseEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbSubjectWarehouseEntity") KbSubjectWarehouseEntity kbSubjectWarehouseEntity);

    /**
    * 扩展信息记录总数
    * @param kbSubjectWarehouseEntity
    * @return
    */
    Long extendsCount(@Param("kbSubjectWarehouseEntity") KbSubjectWarehouseEntity kbSubjectWarehouseEntity);

    /**
    * 主题知识仓库扩展详情
    * @param id 主题知识仓库id
    * @return
    */
    KbSubjectWarehouseEntity getByIdExtends(@Param("id")Long id);

    @Override
    int deleteById(@Param("id") Serializable id);

    List<KbSubjectWarehouseDto> selectBasicByHouse(@Param("modelId") Long id);
}
