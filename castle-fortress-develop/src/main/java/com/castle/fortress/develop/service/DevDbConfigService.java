package com.castle.fortress.develop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.develop.entity.DevDbConfig;

import java.util.List;

/**
 * 开发 数据源 配置服务类
 * @author castle
 */
public interface DevDbConfigService extends IService<DevDbConfig> {
    /**
     * 查询生效的数据源配置
     * @return
     */
    public List<DevDbConfig> queryEffectiveDbConfigs();

    IPage<DevDbConfig> pageDbConfig(Page<DevDbConfig> page, DevDbConfig dbConfig);
}
