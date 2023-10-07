package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.TmpDemoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * 组件示例表Mapper 接口
 *
 * @author castle
 * @since 2021-06-02
 */
public interface TmpDemoMapper extends BaseMapper<TmpDemoEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param tmpDemoEntity
    * @return
    */
    List<TmpDemoEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("tmpDemoEntity") TmpDemoEntity tmpDemoEntity);

    /**
    * 扩展信息记录总数
    * @param tmpDemoEntity
    * @return
    */
    Long extendsCount(@Param("tmpDemoEntity") TmpDemoEntity tmpDemoEntity);

    /**
    * 组件示例表扩展详情
    * @param id 组件示例表id
    * @return
    */
    TmpDemoEntity getByIdExtends(@Param("id")Long id);


    List<Map> infoTmpDemo(String tb1, String tb2, String col1, String col2, String col3);
}

