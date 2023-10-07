package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBasicLogEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识移动日志表Mapper 接口
 *
 * @author 
 * @since 2023-06-02
 */
@Mapper
public interface KbBasicLogMapper extends BaseMapper<KbBasicLogEntity> {

	List<KbBasicLogEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbBasicLogEntity") KbBasicLogEntity kbBasicLogEntity);


	List<KbBasicLogEntity> selectByBasic(@Param("basicId") Long id);

	boolean deleteByid(@Param("basicId") Long id);

	List<KbModelTransmitDto> selectLog(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

	Integer selectLogCount( @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

	List<KbModelTransmitDto> selectLogVideo(@Param("map")Map<String, Long> pageMap, @Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

	Integer selectLogVideoCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("userId") Long userId);

	boolean deleteByIds(@Param("ids") List<Long> ids);
}
