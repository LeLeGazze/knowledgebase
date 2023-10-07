package com.castle.fortress.develop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.develop.entity.DevViewListConfig;

/**
 * 开发 表字段 配置服务类
 * @author castle
 */
public interface DevViewListConfigService extends IService<DevViewListConfig> {


    /**
     * 删除表对应的字段信息
     * @param tbName
     * @return
     */
    boolean delByTbName(String tbName);

    void init(String tbName);

}
