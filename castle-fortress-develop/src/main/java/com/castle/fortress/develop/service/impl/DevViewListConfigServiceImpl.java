package com.castle.fortress.develop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.develop.entity.DevViewListConfig;
import com.castle.fortress.develop.mapper.DevViewListConfigMapper;
import com.castle.fortress.develop.service.DevViewListConfigService;
import org.springframework.stereotype.Service;

/**
 * 开发 数据源 配置服务实现类
 * @author castle
 */
@Service
public class DevViewListConfigServiceImpl extends ServiceImpl<DevViewListConfigMapper, DevViewListConfig> implements DevViewListConfigService {

    @Override
    public boolean delByTbName(String tbName) {
        if(StrUtil.isEmpty(tbName)){
            return false;
        }
        return baseMapper.delByTbName(tbName)>0;
    }

    @Override
    public void init(String tbName) {
        baseMapper.init(tbName);
    }
}
