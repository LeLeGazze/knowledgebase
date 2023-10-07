package com.castle.fortress.develop.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.mapper.DevColConfigMapper;
import com.castle.fortress.develop.mapper.DevTbConfigMapper;
import com.castle.fortress.develop.service.DevTbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 开发 表结构 配置服务实现类
 * @author castle
 */
@Service
public class DevTbConfigServiceImpl extends ServiceImpl<DevTbConfigMapper, DevTbConfig> implements DevTbConfigService {
    @Autowired
    DevColConfigMapper colConfigMapper;

    @Override
    public IPage<DevTbConfig> pageTbConfig(Page<DevTbConfig> page, DevTbConfig tbConfig) {
        QueryWrapper<DevTbConfig> wrapper= new QueryWrapper<>();
        wrapper.like(tbConfig!=null&& StrUtil.isNotEmpty(tbConfig.getTbName()),"tb_name",tbConfig.getTbName());
        wrapper.like(tbConfig!=null&& StrUtil.isNotEmpty(tbConfig.getTbDesc()),"tb_desc",tbConfig.getTbDesc());
        wrapper.orderByDesc("create_time");
        Page<DevTbConfig> tbConfigPage=baseMapper.selectPage(page,wrapper);
        return tbConfigPage;
    }

    @Override
    public void delByTbName(Long dbId, String tbName) {
        colConfigMapper.delByTbName(dbId,tbName);
        QueryWrapper<DevTbConfig> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("db_id",dbId);
        queryWrapper.eq("tb_name",tbName);
        baseMapper.delete(queryWrapper);
    }
}
