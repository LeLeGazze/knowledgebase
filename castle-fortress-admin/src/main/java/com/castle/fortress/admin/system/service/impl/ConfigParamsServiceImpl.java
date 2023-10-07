package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.ConfigParamsDto;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;
import com.castle.fortress.admin.system.mapper.ConfigParamsMapper;
import com.castle.fortress.admin.system.service.ConfigParamsService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统参数表 服务实现类
 *
 * @author castle
 * @since 2022-05-07
 */
@Service
public class ConfigParamsServiceImpl extends ServiceImpl<ConfigParamsMapper, ConfigParamsEntity> implements ConfigParamsService {
    /**
    * 获取查询条件
    * @param configParamsDto
    * @return
    */
    public QueryWrapper<ConfigParamsEntity> getWrapper(ConfigParamsDto configParamsDto){
        QueryWrapper<ConfigParamsEntity> wrapper= new QueryWrapper();
        if(configParamsDto != null){
            ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
            wrapper.like(StrUtil.isNotEmpty(configParamsEntity.getParamCode()),"param_code",configParamsEntity.getParamCode());
            wrapper.like(configParamsEntity.getParamType() != null,"param_type",configParamsEntity.getParamType());
            wrapper.like(configParamsEntity.getStatus() != null,"status",configParamsEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<ConfigParamsDto> pageConfigParams(Page<ConfigParamsDto> page, ConfigParamsDto configParamsDto) {
        QueryWrapper<ConfigParamsEntity> wrapper = getWrapper(configParamsDto);
        Page<ConfigParamsEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ConfigParamsEntity> configParamsPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ConfigParamsDto> pageDto = new Page(configParamsPage.getCurrent(), configParamsPage.getSize(),configParamsPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(configParamsPage.getRecords(),ConfigParamsDto.class));
        return pageDto;
    }

    @Override
    public IPage<ConfigParamsDto> pageConfigParamsExtends(Page<ConfigParamsDto> page, ConfigParamsDto configParamsDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
        List<ConfigParamsEntity> configParamsList=baseMapper.extendsList(pageMap,configParamsEntity);
        Long total = baseMapper.extendsCount(configParamsEntity);
        Page<ConfigParamsDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(configParamsList,ConfigParamsDto.class));
        return pageDto;
    }
    @Override
    public ConfigParamsDto getByIdExtends(Long id){
        ConfigParamsEntity  configParamsEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(configParamsEntity,ConfigParamsDto.class);
    }

    @Override
    public List<ConfigParamsDto> listConfigParams(ConfigParamsDto configParamsDto){
        QueryWrapper<ConfigParamsEntity> wrapper = getWrapper(configParamsDto);
        List<ConfigParamsEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,ConfigParamsDto.class);
    }
}

