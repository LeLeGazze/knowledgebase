package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbUserLabelEntity;
import java.util.Map;
import java.util.List;
/**
 * 用户与表签关联表Mapper 接口
 *
 * @author 
 * @since 2023-05-17
 */
@Mapper
public interface KbUserLabelMapper extends BaseMapper<KbUserLabelEntity> {

	List<KbUserLabelEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbUserLabelEntity") KbUserLabelEntity kbUserLabelEntity);


	List<KbModelTransmitDto> selectBasicByLabel(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap,@Param("ids") List<Long> ids);

	Integer selectBasicByLabelCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("ids") List<Long> ids);

	List<KbModelLabelEntity> findLabelByUser(@Param("userId") Long userId);

    List<Long> findIds(@Param("userId") Long userId);

	List<KbModelTransmitDto> selectBasicByLabelAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap,@Param("ids") List<Long> ids);

	Integer selectBasicByLabelaAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto,@Param("ids") List<Long> ids);

	Integer findLabelByUserToBasicCountAdmin(@Param("userId") Long id);

	Integer findLabelByUserToBasicCount(@Param("categoryIds")  List<Long> category_id,@Param("userId")  Long id);
}
