package com.castle.fortress.admin.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.demo.entity.TmpDemoGenerateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 代码生成示例表Mapper 接口
 *
 * @author castle
 * @since 2021-11-08
 */
public interface TmpDemoGenerateMapper extends BaseMapper<TmpDemoGenerateEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param tmpDemoGenerateEntity
    * @return
    */
    List<TmpDemoGenerateEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("tmpDemoGenerateEntity") TmpDemoGenerateEntity tmpDemoGenerateEntity);

    /**
    * 扩展信息记录总数
    * @param tmpDemoGenerateEntity
    * @return
    */
    Long extendsCount(@Param("tmpDemoGenerateEntity") TmpDemoGenerateEntity tmpDemoGenerateEntity);

    /**
    * 代码生成示例表扩展详情
    * @param id 代码生成示例表id
    * @return
    */
    TmpDemoGenerateEntity getByIdExtends(@Param("id")Long id);



}

