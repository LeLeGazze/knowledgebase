package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
/**
 * 知识浏览收藏评论Mapper 接口
 *
 * @author sunhr
 * @since 2023-05-05
 */
public interface KbBasicUserMapper extends BaseMapper<KbBasicUserEntity> {

	List<KbBasicUserEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbBasicUserEntity") KbBasicUserEntity kbBasicUserEntity);

	List<HashMap<String, Integer>> findByBid(@Param("bid") Long id);
	List<KbModelTransmitDto> recentViews(@Param("map") Map<String, Long> map,@Param("userId") Long userId);

	List<KbModelTransmitDto> recentlyUploaded(@Param("map") Map<String, Long> map,@Param("userId") Long userId);

	List<KbModelTransmitDto> recentlyDownloaded(@Param("map") Map<String, Long> map,@Param("userId") Long userId);

	List<Long> randomId();

    int deleteByBid(@Param("bId") Long id);

	Integer recentlyDownloadedCount(@Param("userId") Long userId);

	Integer recentlyUploadedCount(@Param("userId") Long userId);

	Integer recentViewsCount(@Param("userId") Long userId);

	KbModelTransmitDto selectDownNum(@Param("userId") Long userId);

	KbModelTransmitDto selectUpNum(@Param("userId") Long userId);

    void removeBasicUser(@Param("auth") Long auth,@Param("basicId") Long id);
}
