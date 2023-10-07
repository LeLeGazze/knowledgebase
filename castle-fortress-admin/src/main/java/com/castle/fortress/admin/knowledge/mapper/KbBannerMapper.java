package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbBannerEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识banner图表Mapper 接口
 *
 * @author 
 * @since 2023-06-17
 */
public interface KbBannerMapper extends BaseMapper<KbBannerEntity> {

	List<KbBannerEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbBannerEntity") KbBannerEntity kbBannerEntity);



}
