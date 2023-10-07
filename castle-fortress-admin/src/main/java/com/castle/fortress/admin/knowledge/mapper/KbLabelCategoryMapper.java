package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import java.util.Map;
import java.util.List;
/**
 * 标签分类表Mapper 接口
 *
 * @author 
 * @since 2023-06-14
 */
public interface KbLabelCategoryMapper extends BaseMapper<KbLabelCategoryEntity> {

	List<KbLabelCategoryEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbLabelCategoryEntity") KbLabelCategoryEntity kbLabelCategoryEntity);


    List<KbLabelCategoryDto> selectByNameOrLabelName(@Param("map") Map<String, Long> pageMap, @Param("kbLabelCategoryDto") KbLabelCategoryDto kbLabelCategoryDto);

    Integer countByNameOrLabelName(@Param("map") Map<String, Long> pageMap, @Param("kbLabelCategoryDto") KbLabelCategoryDto kbLabelCategoryDto);
}
