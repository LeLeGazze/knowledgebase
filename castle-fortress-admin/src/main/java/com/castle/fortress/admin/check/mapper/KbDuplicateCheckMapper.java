package com.castle.fortress.admin.check.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.check.entity.KbDuplicateCheckEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识库查重表Mapper 接口
 *
 * @author 
 * @since 2023-07-15
 */
public interface KbDuplicateCheckMapper extends BaseMapper<KbDuplicateCheckEntity> {

	List<KbDuplicateCheckEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbDuplicateCheckEntity") KbDuplicateCheckEntity kbDuplicateCheckEntity);



}
