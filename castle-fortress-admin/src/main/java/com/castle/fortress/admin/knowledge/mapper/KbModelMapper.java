package com.castle.fortress.admin.knowledge.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * cms模型配置表Mapper 接口
 *
 * @author castle
 * @since 2022-03-02
 */
public interface KbModelMapper extends BaseMapper<KbModelEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbModelEntity
    * @return
    */
    List<KbModelEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbModelEntity") KbModelEntity kbModelEntity);

    /**
    * 扩展信息记录总数
    * @param kbModelEntity
    * @return
    */
    Long extendsCount(@Param("kbModelEntity") KbModelEntity kbModelEntity);

    /**
    * cms模型配置表扩展详情
    * @param id cms模型配置表id
    * @return
    */
    KbModelEntity getByIdExtends(@Param("id")Long id);

    /**
     * 获取文章对应的模型
     * @param id
     * @return
     */
    KbModelEntity getByArticleId(@Param("id")Long id);

    /**
     * 获取编码对应的模型
     * @param code
     * @return
     */
    KbModelEntity getByCode(@Param("code")String code);

    List<Long> findswId(@Param("modelId") Long id);
}

