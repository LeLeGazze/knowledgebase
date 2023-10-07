package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbModelDomainEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelDomainDto;
import com.castle.fortress.admin.knowledge.mapper.KbModelDomainMapper;
import com.castle.fortress.admin.knowledge.service.KbModelDomainService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 值域字典表 服务实现类
 *
 * @author  sunhr
 * @since 2023-04-20
 */
@Service
public class KbModelDomainServiceImpl extends ServiceImpl<KbModelDomainMapper, KbModelDomainEntity> implements KbModelDomainService {
    @Autowired
    private KbModelDomainMapper kbModelDomainMapper;
    /**
    * 获取查询条件
    * @param kbModelDomainDto
    * @return
    */
    public QueryWrapper<KbModelDomainEntity> getWrapper(KbModelDomainDto kbModelDomainDto){
        QueryWrapper<KbModelDomainEntity> wrapper= new QueryWrapper();
        if(kbModelDomainDto != null){
            KbModelDomainEntity kbModelDomainEntity = ConvertUtil.transformObj(kbModelDomainDto,KbModelDomainEntity.class);
            wrapper.like(kbModelDomainEntity.getId() != null,"id",kbModelDomainEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbModelDomainEntity.getName()),"name",kbModelDomainEntity.getName());
            wrapper.like(StrUtil.isNotEmpty(kbModelDomainEntity.getUrl()),"url",kbModelDomainEntity.getUrl());
            wrapper.like(kbModelDomainEntity.getShowType() != null,"key",kbModelDomainEntity.getShowType());
            wrapper.like(kbModelDomainEntity.getUpdateTime() != null,"update_time",kbModelDomainEntity.getUpdateTime());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelDomainDto> pageKbModelDomain(Page<KbModelDomainDto> page, KbModelDomainDto kbModelDomainDto) {
        QueryWrapper<KbModelDomainEntity> wrapper = getWrapper(kbModelDomainDto);
        Page<KbModelDomainEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbModelDomainEntity> kbModelDomainPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbModelDomainDto> pageDto = new Page(kbModelDomainPage.getCurrent(), kbModelDomainPage.getSize(),kbModelDomainPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbModelDomainPage.getRecords(),KbModelDomainDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbModelDomainDto> pageKbModelDomainExtends(Page<KbModelDomainDto> page, KbModelDomainDto kbModelDomainDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        KbModelDomainEntity kbModelDomainEntity = ConvertUtil.transformObj(kbModelDomainDto,KbModelDomainEntity.class);
        List<KbModelDomainEntity> kbModelDomainList=baseMapper.extendsList(pageMap,kbModelDomainEntity);
        Long total = baseMapper.extendsCount(kbModelDomainEntity);
        Page<KbModelDomainDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbModelDomainList,KbModelDomainDto.class));
        return pageDto;
    }
    @Override
    public KbModelDomainDto getByIdExtends(Long id){
        KbModelDomainEntity  kbModelDomainEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbModelDomainEntity,KbModelDomainDto.class);
    }

    @Override
    public List<KbModelDomainDto> listKbModelDomain(KbModelDomainDto kbModelDomainDto){
        QueryWrapper<KbModelDomainEntity> wrapper = getWrapper(kbModelDomainDto);
        List<KbModelDomainEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbModelDomainDto.class);
    }

    @Override
    public List<KbModelDomainEntity> selectAll() {
        return kbModelDomainMapper.findAll();
    }
}

