package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBasicHistoryEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识基本表历史表Mapper 接口
 *
 * @author 
 * @since 2023-07-03
 */
public interface KbBasicHistoryMapper extends BaseMapper<KbBasicHistoryEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param kbBasicHistoryEntity
    * @return
    */
    List<KbBasicHistoryEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("kbBasicHistoryEntity") KbBasicHistoryEntity kbBasicHistoryEntity);

    /**
    * 扩展信息记录总数
    * @param kbBasicHistoryEntity
    * @return
    */
    Long extendsCount(@Param("kbBasicHistoryEntity") KbBasicHistoryEntity kbBasicHistoryEntity);

    /**
    * 知识基本表历史表扩展详情
    * @param id 知识基本表历史表id
    * @return
    */
    KbBasicHistoryEntity getByIdExtends(@Param("id")Long id);


    KbModelTransmitDto findAllByBasic(@Param("id") Long id);

    int deleteByBId(@Param("bid") Long id);
}
