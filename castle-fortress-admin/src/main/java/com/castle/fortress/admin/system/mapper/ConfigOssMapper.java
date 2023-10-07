package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.ConfigOssEntity;

import java.util.List;

/**
 * 文件传输配置
 * @author castle
 */
public interface ConfigOssMapper extends BaseMapper<ConfigOssEntity> {
    /**
     * 将所有的配置状态置为失效
     */
    void updateStatusNo();

    /**
     * 查询生效的记录
     * @return
     */
    List<ConfigOssEntity> selectStatusYes();
}
