package com.castle.fortress.develop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.develop.entity.DevViewListConfig;
import org.apache.ibatis.annotations.Param;

/**
 * @author castle
 */
public interface DevViewListConfigMapper extends BaseMapper<DevViewListConfig> {
    /**
     * 删除指定指定表名的记录
     * @param tbName
     */
    int delByTbName(@Param("tbName") String tbName);

    /**
     * 通过表名
     * @param tbName
     */
    void init(@Param("tbName")String tbName);
}
