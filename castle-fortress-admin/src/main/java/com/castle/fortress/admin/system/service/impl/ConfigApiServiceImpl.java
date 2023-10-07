package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.ConfigApiEntity;
import com.castle.fortress.admin.system.mapper.ConfigApiMapper;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 框架绑定api配置管理 服务实现类
 *
 * @author castle
 * @since 2022-04-12
 */
@Service
public class ConfigApiServiceImpl extends ServiceImpl<ConfigApiMapper, ConfigApiEntity> implements ConfigApiService {
    /**
    * 获取查询条件
    * @param configApiDto
    * @return
    */
    public QueryWrapper<ConfigApiEntity> getWrapper(ConfigApiDto configApiDto){
        QueryWrapper<ConfigApiEntity> wrapper= new QueryWrapper();
        if(configApiDto != null){
            ConfigApiEntity configApiEntity = ConvertUtil.transformObj(configApiDto,ConfigApiEntity.class);
            wrapper.eq(configApiEntity.getId() != null,"id",configApiEntity.getId());
            wrapper.eq(StrUtil.isNotEmpty(configApiEntity.getGroupCode()),"group_code",configApiEntity.getGroupCode());
            wrapper.eq(StrUtil.isNotEmpty(configApiEntity.getBindCode()),"bind_code",configApiEntity.getBindCode());
            wrapper.like(StrUtil.isNotEmpty(configApiEntity.getBindDetail()),"bind_detail",configApiEntity.getBindDetail());
        }
        return wrapper;
    }


    @Override
    public IPage<ConfigApiDto> pageConfigApi(Page<ConfigApiDto> page, ConfigApiDto configApiDto) {
        QueryWrapper<ConfigApiEntity> wrapper = getWrapper(configApiDto);
        Page<ConfigApiEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ConfigApiEntity> configApiPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ConfigApiDto> pageDto = new Page(configApiPage.getCurrent(), configApiPage.getSize(),configApiPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(configApiPage.getRecords(),ConfigApiDto.class));
        return pageDto;
    }


    @Override
    public List<ConfigApiDto> listConfigApi(ConfigApiDto configApiDto){
        QueryWrapper<ConfigApiEntity> wrapper = getWrapper(configApiDto);
        List<ConfigApiEntity> list = baseMapper.selectList(wrapper);
        List<ConfigApiDto> dtoList = ConvertUtil.transformObjList(list,ConfigApiDto.class);
        for(ConfigApiDto dto:dtoList){
            if(StrUtil.isNotEmpty(dto.getBindDetail())){
                    //注列表是jsonarray
                try {
                    dto.setParamMap(JSONUtil.toBean(dto.getBindDetail(),Map.class));
                } catch (Exception e) {
                    //配置错误不处理
                }
            }
        }
        return dtoList;
    }

    @Override
    public ConfigApiDto getByBindCode(String bindCode) {
        if (StrUtil.isEmpty(bindCode)){
            return null;
        }
        QueryWrapper<ConfigApiEntity> wrapper= new QueryWrapper();
        wrapper.eq("bind_code",bindCode);
        List<ConfigApiEntity> list = baseMapper.selectList(wrapper);
        return list.size() > 0 ? ConvertUtil.transformObj(list.get(0) , ConfigApiDto.class) : null;
    }
}

