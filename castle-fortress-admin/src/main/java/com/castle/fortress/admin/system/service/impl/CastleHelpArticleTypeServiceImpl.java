package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.CastleHelpArticleTypeDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleTypeEntity;
import com.castle.fortress.admin.system.mapper.CastleHelpArticleTypeMapper;
import com.castle.fortress.admin.system.service.CastleHelpArticleTypeService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心文章类型 服务实现类
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Service
public class CastleHelpArticleTypeServiceImpl extends ServiceImpl<CastleHelpArticleTypeMapper, CastleHelpArticleTypeEntity> implements CastleHelpArticleTypeService {
    /**
    * 获取查询条件
    * @param castleHelpArticleTypeDto
    * @return
    */
    public QueryWrapper<CastleHelpArticleTypeEntity> getWrapper(CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        QueryWrapper<CastleHelpArticleTypeEntity> wrapper= new QueryWrapper();
        if(castleHelpArticleTypeDto != null){
            CastleHelpArticleTypeEntity castleHelpArticleTypeEntity = ConvertUtil.transformObj(castleHelpArticleTypeDto,CastleHelpArticleTypeEntity.class);
            wrapper.like(StrUtil.isNotEmpty(castleHelpArticleTypeEntity.getName()),"name",castleHelpArticleTypeEntity.getName());
            wrapper.like(castleHelpArticleTypeEntity.getStatus() != null,"status",castleHelpArticleTypeEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleHelpArticleTypeDto> pageCastleHelpArticleType(Page<CastleHelpArticleTypeDto> page, CastleHelpArticleTypeDto castleHelpArticleTypeDto) {
        QueryWrapper<CastleHelpArticleTypeEntity> wrapper = getWrapper(castleHelpArticleTypeDto);
        Page<CastleHelpArticleTypeEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleHelpArticleTypeEntity> castleHelpArticleTypePage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleHelpArticleTypeDto> pageDto = new Page(castleHelpArticleTypePage.getCurrent(), castleHelpArticleTypePage.getSize(),castleHelpArticleTypePage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleHelpArticleTypePage.getRecords(),CastleHelpArticleTypeDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleHelpArticleTypeDto> pageCastleHelpArticleTypeExtends(Page<CastleHelpArticleTypeDto> page, CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleHelpArticleTypeEntity castleHelpArticleTypeEntity = ConvertUtil.transformObj(castleHelpArticleTypeDto,CastleHelpArticleTypeEntity.class);
        List<CastleHelpArticleTypeEntity> castleHelpArticleTypeList=baseMapper.extendsList(pageMap,castleHelpArticleTypeEntity);
        Long total = baseMapper.extendsCount(castleHelpArticleTypeEntity);
        Page<CastleHelpArticleTypeDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleHelpArticleTypeList,CastleHelpArticleTypeDto.class));
        return pageDto;
    }
    @Override
    public CastleHelpArticleTypeDto getByIdExtends(Long id){
        CastleHelpArticleTypeEntity  castleHelpArticleTypeEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleHelpArticleTypeEntity,CastleHelpArticleTypeDto.class);
    }

    @Override
    public List<CastleHelpArticleTypeDto> listCastleHelpArticleType(CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        QueryWrapper<CastleHelpArticleTypeEntity> wrapper = getWrapper(castleHelpArticleTypeDto);
        List<CastleHelpArticleTypeEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleHelpArticleTypeDto.class);
    }

    @Override
    public List<CastleHelpArticleTypeDto> getDataList(Map<String , Object> params) {
        params.put("status" , YesNoEnum.YES.getCode());
        List<CastleHelpArticleTypeDto> list = ConvertUtil.transformObjList(baseMapper.selectDataList(params) , CastleHelpArticleTypeDto.class);
        return list;
    }

    @Override
    public List<CastleHelpArticleTypeDto> listByArticleTitle(Map<String, Object> params) {
        List<CastleHelpArticleTypeDto> list = ConvertUtil.transformObjList(baseMapper.selectListByArticleTitle(params) , CastleHelpArticleTypeDto.class);
        return list;
    }
}

