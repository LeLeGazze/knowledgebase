package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * 标签删除任务表Mapper 接口
 *
 * @author
 * @since 2023-06-07
 */
public interface KbBaseLabelTaskMapper extends BaseMapper<KbBaseLabelTaskEntity> {

    List<KbBaseLabelTaskEntity> queryList(@Param("map") Map<String, Long> pageMap, @Param("kbBaseLabelTaskEntity") KbBaseLabelTaskEntity kbBaseLabelTaskEntity);


    @Select("select *  from kb_base_label_task where status !=#{status}")
    List<KbBaseLabelTaskEntity> findByStatus(@Param("status") int status);

    void updateByIds(@Param("kbBaseLabelTaskEntities") List<KbBaseLabelTaskEntity> kbBaseLabelTaskEntities);

    void deleteByBid(@Param("arrayList") List<Long> arrayList);

    List<Map<String, Object>> findByLid(@Param("labelTaskEntities") List<KbBaseLabelTaskEntity> labelTaskEntities);
}
