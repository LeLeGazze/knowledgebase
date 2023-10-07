package com.castle.fortress.develop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.develop.entity.DevTbConfig;


/**
 * 开发 表结构 配置服务类
 * @author castle
 */
public interface DevTbConfigService extends IService<DevTbConfig> {


    IPage<DevTbConfig> pageTbConfig(Page<DevTbConfig> page, DevTbConfig tbConfig);

    /**
     * 删除指定数据源下的指定表的所有信息 表结构 表字段
     * @param dbId
     * @param tbName
     */
    void delByTbName(Long dbId, String tbName);
}
