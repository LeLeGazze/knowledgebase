package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbCollectEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识收藏表Mapper 接口
 *
 * @author 
 * @since 2023-05-12
 */
@Mapper
public interface KbCollectMapper extends BaseMapper<KbCollectEntity> {

	List<KbCollectEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbCollectEntity") KbCollectEntity kbCollectEntity);


	KbCollectEntity checkExites(@Param("basicId") Long basicId, @Param("userId") Long userId);

	List<KbModelTransmitDto> findBasicByLike(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

	Integer findBasicByLikeCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	KbCollectEntity findByid(@Param("userId") Long userId, @Param("basicId") Long basicId);

    List<KbModelTransmitDto> findBasicByVideo(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

	Integer findBasicByVideoCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	boolean removeCollect(@Param("userId") Long userId, @Param("basicId") Long basicId);

	int deleteByBid(@Param("basicId") Long id);

	List<KbModelTransmitDto> findBasicByLikeAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

	Integer findBasicByLikeAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	List<KbModelTransmitDto> findBasicByVideoAdmin(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto, @Param("map") Map<String, Long> pageMap);

	Integer findBasicByVideoAdminCount(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);
}
