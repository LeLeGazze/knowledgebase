package com.castle.fortress.admin.knowledge.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import java.util.Map;
import java.util.List;
/**
 * 数据同步Mapper 接口
 *
 * @author 
 * @since 2023-06-29
 */
public interface KbDataSynchronizationTaskMapper extends BaseMapper<KbDataSynchronizationTaskEntity> {

	List<KbDataSynchronizationTaskEntity> queryList(@Param("map")Map<String, Long> pageMap, @Param("kbDataSynchronizationTaskEntity") KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity);


    List<Map<String, Object>> runSQL(@Param("taskEntitySql") String taskEntitySql);
}
