package com.castle.fortress.develop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.entity.DevTbConfig;

import java.util.List;

/**
 * 开发 表字段 配置服务类
 * @author castle
 */
public interface DevColConfigService extends IService<DevColConfig> {


    IPage<DevColConfig> pageColConfig(Page<DevColConfig> page, DevColConfig colConfig);

    /**
     * 依据数据源和表结构初始化表字段
     * @param dbConfig
     * @param tbConfig1
     * @return
     */
    boolean initColumns(DevDbConfig dbConfig, DevTbConfig tbConfig1);

    /**
     * 删除表对应的字段信息
     * @param tbId
     * @return
     */
    boolean delByTbId(Long  tbId);

    /**
     * 查询表对应的字段列表
     * @param tbId
     * @return
     */
    List<DevColConfig> listColConfig(Long tbId);

    /**
     * 更新表字段信息
     * @param oldCols
     * @param newCols
     * @return
     */
    boolean synColumns(List<DevColConfig> oldCols, List<DevColConfig> newCols);
}
