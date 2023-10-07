package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.DevViewListConfig;
import com.castle.fortress.admin.system.mapper.DevViewListConfigMapper;
import com.castle.fortress.admin.system.service.DevViewListConfigService;
import org.springframework.stereotype.Service;

/**
 * 开发 数据源 配置服务实现类
 * @author castle
 */
@Service
public class DevViewListConfigServiceImpl extends ServiceImpl<DevViewListConfigMapper, DevViewListConfig> implements DevViewListConfigService {
}
