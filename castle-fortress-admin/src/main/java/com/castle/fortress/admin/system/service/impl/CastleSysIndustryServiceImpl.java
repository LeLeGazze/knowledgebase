package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.CastleSysIndustryDto;
import com.castle.fortress.admin.system.entity.CastleSysIndustryEntity;
import com.castle.fortress.admin.system.mapper.CastleSysIndustryMapper;
import com.castle.fortress.admin.system.service.CastleSysIndustryService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 行业职位 服务实现类
 *
 * @author Mgg
 * @since 2021-09-02
 */
@Service
public class CastleSysIndustryServiceImpl extends ServiceImpl<CastleSysIndustryMapper, CastleSysIndustryEntity> implements CastleSysIndustryService {
    /**
    * 获取查询条件
    * @param castleSysIndustryDto
    * @return
    */
    public QueryWrapper<CastleSysIndustryEntity> getWrapper(CastleSysIndustryDto castleSysIndustryDto){
        QueryWrapper<CastleSysIndustryEntity> wrapper= new QueryWrapper();
        if(castleSysIndustryDto != null){

            CastleSysIndustryEntity castleSysIndustryEntity = ConvertUtil.transformObj(castleSysIndustryDto,CastleSysIndustryEntity.class);
            wrapper.like(castleSysIndustryEntity.getParentId() != null,"parent_id",castleSysIndustryEntity.getParentId());
            wrapper.like(StrUtil.isNotEmpty(castleSysIndustryEntity.getName()),"name",castleSysIndustryEntity.getName());
            wrapper.like(castleSysIndustryEntity.getTreeLevel() != null,"tree_level",castleSysIndustryEntity.getTreeLevel());
            wrapper.like(castleSysIndustryEntity.getLeaf() != null,"leaf",castleSysIndustryEntity.getLeaf());
            wrapper.like(StrUtil.isNotEmpty(castleSysIndustryEntity.getShortpinyin()),"shortpinyin",castleSysIndustryEntity.getShortpinyin());
            wrapper.like(castleSysIndustryEntity.getSort() != null,"sort",castleSysIndustryEntity.getSort());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleSysIndustryDto> pageCastleSysIndustry(Page<CastleSysIndustryDto> page, CastleSysIndustryDto castleSysIndustryDto) {
        QueryWrapper<CastleSysIndustryEntity> wrapper = getWrapper(castleSysIndustryDto);
        Page<CastleSysIndustryEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleSysIndustryEntity> castleSysIndustryPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleSysIndustryDto> pageDto = new Page(castleSysIndustryPage.getCurrent(), castleSysIndustryPage.getSize(),castleSysIndustryPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleSysIndustryPage.getRecords(),CastleSysIndustryDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleSysIndustryDto> pageCastleSysIndustryExtends(Page<CastleSysIndustryDto> page, CastleSysIndustryDto castleSysIndustryDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleSysIndustryEntity castleSysIndustryEntity = ConvertUtil.transformObj(castleSysIndustryDto,CastleSysIndustryEntity.class);
        List<CastleSysIndustryEntity> castleSysIndustryList=baseMapper.extendsList(pageMap,castleSysIndustryEntity);
        Long total = baseMapper.extendsCount(castleSysIndustryEntity);
        Page<CastleSysIndustryDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleSysIndustryList,CastleSysIndustryDto.class));
        return pageDto;
    }
    @Override
    public CastleSysIndustryDto getByIdExtends(Long id){
        CastleSysIndustryEntity  castleSysIndustryEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleSysIndustryEntity,CastleSysIndustryDto.class);
    }

    @Override
    public List<CastleSysIndustryDto> listCastleSysIndustry(CastleSysIndustryDto castleSysIndustryDto){
        QueryWrapper<CastleSysIndustryEntity> wrapper = getWrapper(castleSysIndustryDto);
        List<CastleSysIndustryEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleSysIndustryDto.class);
    }
}

