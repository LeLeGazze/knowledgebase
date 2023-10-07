package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.ProtocolDto;
import com.castle.fortress.admin.system.entity.ProtocolEntity;
import com.castle.fortress.admin.system.mapper.ProtocolMapper;
import com.castle.fortress.admin.system.service.ProtocolService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 协议管理 服务实现类
 *
 * @author majunjie
 * @since 2022-01-28
 */
@Service
public class ProtocolServiceImpl extends ServiceImpl<ProtocolMapper, ProtocolEntity> implements ProtocolService {
    /**
    * 获取查询条件
    * @param protocolDto
    * @return
    */
    public QueryWrapper<ProtocolEntity> getWrapper(ProtocolDto protocolDto){
        QueryWrapper<ProtocolEntity> wrapper= new QueryWrapper();
        if(protocolDto != null){
            ProtocolEntity protocolEntity = ConvertUtil.transformObj(protocolDto,ProtocolEntity.class);
            wrapper.like(StrUtil.isNotEmpty(protocolEntity.getCode()),"code",protocolEntity.getCode());
            wrapper.like(StrUtil.isNotEmpty(protocolEntity.getTitle()),"title",protocolEntity.getTitle());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<ProtocolDto> pageProtocol(Page<ProtocolDto> page, ProtocolDto protocolDto) {
        QueryWrapper<ProtocolEntity> wrapper = getWrapper(protocolDto);
        Page<ProtocolEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<ProtocolEntity> protocolPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<ProtocolDto> pageDto = new Page(protocolPage.getCurrent(), protocolPage.getSize(),protocolPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(protocolPage.getRecords(),ProtocolDto.class));
        return pageDto;
    }

    @Override
    public IPage<ProtocolDto> pageProtocolExtends(Page<ProtocolDto> page, ProtocolDto protocolDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        ProtocolEntity protocolEntity = ConvertUtil.transformObj(protocolDto,ProtocolEntity.class);
        List<ProtocolEntity> protocolList=baseMapper.extendsList(pageMap,protocolEntity);
        Long total = baseMapper.extendsCount(protocolEntity);
        Page<ProtocolDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(protocolList,ProtocolDto.class));
        return pageDto;
    }
    @Override
    public ProtocolDto getByIdExtends(Long id){
        ProtocolEntity  protocolEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(protocolEntity,ProtocolDto.class);
    }

    @Override
    public List<ProtocolDto> listProtocol(ProtocolDto protocolDto){
        QueryWrapper<ProtocolEntity> wrapper = getWrapper(protocolDto);
        List<ProtocolEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,ProtocolDto.class);
    }

    @Override
    public ProtocolDto getByCode(String code) {
        Map<String , Object> params = new HashMap<>();
        params.put("code" , code);
        params.put("status" , YesNoEnum.YES.getCode());
        List<ProtocolEntity> list = baseMapper.selectDataList(params);
        return list.size() > 0 ? ConvertUtil.transformObj(list.get(0) , ProtocolDto.class) : null;
    }


}

