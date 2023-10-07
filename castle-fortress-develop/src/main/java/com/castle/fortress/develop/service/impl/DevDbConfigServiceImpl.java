package com.castle.fortress.develop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.mapper.DevDbConfigMapper;
import com.castle.fortress.develop.service.DevDbConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 开发 数据源 配置服务实现类
 * @author castle
 */
@Service
public class DevDbConfigServiceImpl extends ServiceImpl<DevDbConfigMapper, DevDbConfig> implements DevDbConfigService {
    @Override
    public List<DevDbConfig> queryEffectiveDbConfigs() {
        QueryWrapper<DevDbConfig> wrapper=new QueryWrapper<>();
        wrapper.eq("status", YesNoEnum.YES.getCode());
        wrapper.orderByDesc("create_time");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<DevDbConfig> pageDbConfig(Page<DevDbConfig> page, DevDbConfig dbConfig) {
        QueryWrapper<DevDbConfig> wrapper= new QueryWrapper<>();
        Page<DevDbConfig> dbConfigPage=baseMapper.selectPage(page,wrapper);
        return dbConfigPage;
    }
}
