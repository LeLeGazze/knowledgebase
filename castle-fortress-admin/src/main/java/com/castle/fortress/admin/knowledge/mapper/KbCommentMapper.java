package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识评论管理表Mapper 接口
 *
 * @author sunhr
 * @since 2023-05-09
 */
@Mapper
public interface KbCommentMapper extends BaseMapper<KbCommentEntity> {

	List<KbCommentEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbCommentEntity") KbCommentEntity kbCommentEntity);


	List<KbCommentDto> getComments(@Param("newsId") Long newsId);

	List<KbCommentDto> selectComment(@Param("basicId") Long basicId,@Param("pageIndex") Integer pageIndex,@Param("pageSize") Integer pageSize,@Param("userId") Long userId);

	List<KbCommentDto> selectDownComment(@Param("parentId") Long praentId, @Param("basicId") Long basicId);

	List<KbCommentDto>  selectSon(@Param("map") Map<String, Long> pageMap,@Param("oneId") Long id,@Param("userId") Long userId);

	SysUser findByUidToUserName(@Param("parentId") Long parentId );

	List<Long> selectSonId(@Param("oneId") Long id);

    int deleteByBid(@Param("bId") Long id);

    Integer findByBid(@Param("bId") Long id);

    Integer getCount(@Param("userId") Long userId);

	List<KbModelTransmitDto> recentComments(@Param("map") Map<String, Long> pageMap,@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	Integer recentCountComments(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	List<KbModelTransmitDto> recentCommentsVideo(@Param("map") Map<String, Long> pageMap,@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

	Integer recentCountCommentsVideo(@Param("kbBaseShowDto") KbBaseShowDto kbBaseShowDto);

    KbCommentDto getCommentOne(@Param("oneId") Long pId);

	Integer selectSonCount(@Param("oneId") Long id,@Param("userId") Long userId);
}
