package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
/**
 * 标签分组和标签关联表Mapper 接口
 *
 * @author 
 * @since 2023-06-14
 */
public interface KbCategoryLabelMapper extends BaseMapper<KbCategoryLabelEntity> {

	List<KbCategoryLabelEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbCategoryLabelEntity") KbCategoryLabelEntity kbCategoryLabelEntity);


    List<KbModelLabelDto> findLabelByCategory(@Param("ctId") Long ctId, @Param("map")Map<String, Long> pageMap,@Param("kbModelLabelDto") KbModelLabelDto kbModelLabelDto);

    Integer findLabelByCategoryCount(@Param("ctId") Long ctId,@Param("kbModelLabelDto") KbModelLabelDto kbModelLabelDto);

    List<KbModelLabelEntity> findAllLabelByCategory(Long ctId);

    KbLabelCategoryEntity findCategoryByLabel(@Param("labelId") Long id);

    List<KbLabelCategoryEntity> findCategoryByLabelIds(@Param("ids") List<Long> ids);

    List<KbCategoryLabelEntity> isExistLabel(@Param("ids") List<Long> longs);

    List<KbModelLabelDto> findAllLabelByCategorys(@Param("ids") List<Long> ids);

    List<KbLabelCategoryEntity> findByIds(@Param("ids") List<Long> ids);

    List<KbLabelCategoryEntity> isExiteName(@Param("names") List<String> list);

    Integer removeByLabelId(@Param("labelId") Long id);

    void deleteBylId(@Param("kbCategoryLabelEntities") ArrayList<KbCategoryLabelEntity> kbCategoryLabelEntities);
}
