package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.CastleBannerDto;
import com.castle.fortress.admin.system.entity.CastleBannerEntity;
import com.castle.fortress.admin.system.mapper.CastleBannerMapper;
import com.castle.fortress.admin.system.service.CastleBannerService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * banner图 服务实现类
 *
 * @author majunjie
 * @since 2022-02-14
 */
@Service
public class CastleBannerServiceImpl extends ServiceImpl<CastleBannerMapper, CastleBannerEntity> implements CastleBannerService {
    /**
    * 获取查询条件
    * @param castleBannerDto
    * @return
    */
    public QueryWrapper<CastleBannerEntity> getWrapper(CastleBannerDto castleBannerDto){
        QueryWrapper<CastleBannerEntity> wrapper= new QueryWrapper();
        if(castleBannerDto != null){
            CastleBannerEntity castleBannerEntity = ConvertUtil.transformObj(castleBannerDto,CastleBannerEntity.class);
            wrapper.like(castleBannerEntity.getStatus() != null,"status",castleBannerEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleBannerDto> pageCastleBanner(Page<CastleBannerDto> page, CastleBannerDto castleBannerDto) {
        QueryWrapper<CastleBannerEntity> wrapper = getWrapper(castleBannerDto);
        Page<CastleBannerEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleBannerEntity> castleBannerPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleBannerDto> pageDto = new Page(castleBannerPage.getCurrent(), castleBannerPage.getSize(),castleBannerPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleBannerPage.getRecords(),CastleBannerDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleBannerDto> pageCastleBannerExtends(Page<CastleBannerDto> page, CastleBannerDto castleBannerDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleBannerEntity castleBannerEntity = ConvertUtil.transformObj(castleBannerDto,CastleBannerEntity.class);
        List<CastleBannerEntity> castleBannerList=baseMapper.extendsList(pageMap,castleBannerEntity);
        Long total = baseMapper.extendsCount(castleBannerEntity);
        Page<CastleBannerDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleBannerList,CastleBannerDto.class));
        return pageDto;
    }
    @Override
    public CastleBannerDto getByIdExtends(Long id){
        CastleBannerEntity  castleBannerEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleBannerEntity,CastleBannerDto.class);
    }

    @Override
    public List<CastleBannerDto> listCastleBanner(CastleBannerDto castleBannerDto){
        QueryWrapper<CastleBannerEntity> wrapper = getWrapper(castleBannerDto);
        List<CastleBannerEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleBannerDto.class);
    }

    @Override
    public List<CastleBannerDto> getDataList() {
        Map<String , Object> params = new HashMap<>();
        params.put("status" , YesNoEnum.YES.getCode());
        List<CastleBannerDto> list = ConvertUtil.transformObjList(baseMapper.selectDataList(params), CastleBannerDto.class);
        return list;
    }
}

