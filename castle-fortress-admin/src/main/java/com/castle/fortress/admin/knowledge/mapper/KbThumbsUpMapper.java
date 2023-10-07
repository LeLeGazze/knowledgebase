package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识点赞表Mapper 接口
 *
 * @author sunhr
 * @since 2023-05-11
 */
@Mapper
public interface KbThumbsUpMapper extends BaseMapper<KbThumbsUpEntity> {

	List<KbThumbsUpEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbThumbsUpEntity") KbThumbsUpEntity kbThumbsUpEntity);


	KbThumbsUpEntity checkExites(@Param("basicId") Long basicId, @Param("userId") Long userId,@Param("commentId") Long commentId);

    boolean removeUp(@Param("basicId") Long basicId, @Param("userId") Long userId,@Param("commentId") Long commentId);
}
