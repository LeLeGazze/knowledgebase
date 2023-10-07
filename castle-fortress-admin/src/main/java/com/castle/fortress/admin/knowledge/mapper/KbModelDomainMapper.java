package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbModelDomainEntity;

import javax.annotation.ManagedBean;
import java.util.Map;
import java.util.List;
/**
 * 值域字典表Mapper 接口
 *
 * @author 
 * @since 2023-04-20
 */
@Mapper
public interface KbModelDomainMapper extends BaseMapper<KbModelDomainEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbModelDomainEntity
    * @return
    */
    List<KbModelDomainEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbModelDomainEntity") KbModelDomainEntity kbModelDomainEntity);

    /**
    * 扩展信息记录总数
    * @param kbModelDomainEntity
    * @return
    */
    Long extendsCount(@Param("kbModelDomainEntity") KbModelDomainEntity kbModelDomainEntity);

    /**
    * 值域字典表扩展详情
    * @param id 值域字典表id
    * @return
    */
    KbModelDomainEntity getByIdExtends(@Param("id")Long id);


    List<KbModelDomainEntity> findAll();
}
