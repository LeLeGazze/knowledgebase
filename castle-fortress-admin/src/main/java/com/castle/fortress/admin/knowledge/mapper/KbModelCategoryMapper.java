package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbModelCategoryEntity;
import org.apache.ibatis.annotations.Select;

import java.util.Map;
import java.util.List;
/**
 * 模型分类管理Mapper 接口
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
public interface KbModelCategoryMapper extends BaseMapper<KbModelCategoryEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbModelCategoryEntity
    * @return
    */
    List<KbModelCategoryEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbModelCategoryEntity") KbModelCategoryEntity kbModelCategoryEntity);

    /**
    * 扩展信息记录总数
    * @param kbModelCategoryEntity
    * @return
    */
    Long extendsCount(@Param("kbModelCategoryEntity") KbModelCategoryEntity kbModelCategoryEntity);

    /**
    * 模型分类管理扩展详情
    * @param id 模型分类管理id
    * @return
    */
    KbModelCategoryEntity getByIdExtends(@Param("id")Long id);


    @Select("select distinct model.code, design.prop_name,design.form_type, design.name,design.select_frame\n" +
            "from kb_model model\n" +
            "         join kb_subject_warehouse sw on sw.model_id = model.id\n" +
            "         join kb_property_design design on design.model_id = model.id\n" +
            "where model.is_deleted = 2\n" +
            "  and sw.is_deleted = 2\n" +
            "  and design.is_deleted = 2\n" +
            "  and sw.id = #{swId}\n" +
            "  and form_type in (1, 2,5)\n" +
            "  and is_filtrate = 1\n")
    List<Map<String, Object>> findBySwId(@Param("swId") String swId);

}
