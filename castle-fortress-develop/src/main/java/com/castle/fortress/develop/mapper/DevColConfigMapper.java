package com.castle.fortress.develop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.develop.entity.DevColConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author castle
 */
public interface DevColConfigMapper extends BaseMapper<DevColConfig> {
    /**
     * 删除指定数据源的
     * @param dbId
     * @param tbName
     */
    void delByTbName(@Param("dbId") Long dbId, @Param("tbName") String tbName);


    /**
     * 批量插入数据
     * @param colConfigList
     * @return
     */
    Integer insertBatch(@Param("list")List<DevColConfig> colConfigList);

    Integer delById(@Param("tbId")Long tbId);

    /**
     * 批量修改字段类型
     * @param updateList
     */
	  void updateCols(@Param("list")List<DevColConfig> updateList);
}
