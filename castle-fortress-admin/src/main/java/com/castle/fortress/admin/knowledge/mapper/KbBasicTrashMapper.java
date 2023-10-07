package com.castle.fortress.admin.knowledge.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbBasicTrashDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBasicTrashEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识回收表Mapper 接口
 *
 * @author 
 * @since 2023-06-01
 */
public interface KbBasicTrashMapper extends BaseMapper<KbBasicTrashEntity> {

	List<KbBasicTrashEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbBasicTrashEntity") KbBasicTrashEntity kbBasicTrashEntity);


    int deleteByid(@Param("id") Long id);

    List<KbModelTransmitDto> selectTrashAdmin(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    Integer selectTrashCountAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    List<KbModelTransmitDto> selectTrashVideoAdmin(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    Integer selectTrashVideoAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    List<KbModelTransmitDto> selectTrash(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

    Integer selectTrashCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

    List<KbModelTransmitDto> selectTrashVideo(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

    Integer selectTrashVideoCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);
}
