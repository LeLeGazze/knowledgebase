package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.CastleVersionDto;
import com.castle.fortress.admin.system.entity.CastleVersionEntity;
import com.castle.fortress.admin.system.mapper.CastleVersionMapper;
import com.castle.fortress.admin.system.service.CastleVersionService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 版本管理 服务实现类
 *
 * @author 
 * @since 2022-02-14
 */
@Service
public class CastleVersionServiceImpl extends ServiceImpl<CastleVersionMapper, CastleVersionEntity> implements CastleVersionService {
    /**
    * 获取查询条件
    * @param castleVersionDto
    * @return
    */
    public QueryWrapper<CastleVersionEntity> getWrapper(CastleVersionDto castleVersionDto){
        QueryWrapper<CastleVersionEntity> wrapper= new QueryWrapper();
        if(castleVersionDto != null){
            CastleVersionEntity castleVersionEntity = ConvertUtil.transformObj(castleVersionDto,CastleVersionEntity.class);
            wrapper.like(StrUtil.isNotEmpty(castleVersionEntity.getVersion()),"version",castleVersionEntity.getVersion());
            wrapper.like(StrUtil.isNotEmpty(castleVersionEntity.getTitle()),"title",castleVersionEntity.getTitle());
            wrapper.like(StrUtil.isNotEmpty(castleVersionEntity.getContent()),"content",castleVersionEntity.getContent());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleVersionDto> pageCastleVersion(Page<CastleVersionDto> page, CastleVersionDto castleVersionDto) {
        QueryWrapper<CastleVersionEntity> wrapper = getWrapper(castleVersionDto);
        Page<CastleVersionEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleVersionEntity> castleVersionPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleVersionDto> pageDto = new Page(castleVersionPage.getCurrent(), castleVersionPage.getSize(),castleVersionPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleVersionPage.getRecords(),CastleVersionDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleVersionDto> pageCastleVersionExtends(Page<CastleVersionDto> page, CastleVersionDto castleVersionDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleVersionEntity castleVersionEntity = ConvertUtil.transformObj(castleVersionDto,CastleVersionEntity.class);
        List<CastleVersionEntity> castleVersionList=baseMapper.extendsList(pageMap,castleVersionEntity);
        Long total = baseMapper.extendsCount(castleVersionEntity);
        Page<CastleVersionDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleVersionList,CastleVersionDto.class));
        return pageDto;
    }
    @Override
    public CastleVersionDto getByIdExtends(Long id){
        CastleVersionEntity  castleVersionEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleVersionEntity,CastleVersionDto.class);
    }

    @Override
    public List<CastleVersionDto> listCastleVersion(CastleVersionDto castleVersionDto){
        QueryWrapper<CastleVersionEntity> wrapper = getWrapper(castleVersionDto);
        List<CastleVersionEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleVersionDto.class);
    }

    @Override
    public List<CastleVersionDto> getDataList() {
        Map<String , Object> map = new HashMap<>();
        map.put("status" , YesNoEnum.YES.getCode());
        List<CastleVersionDto> list = ConvertUtil.transformObjList(baseMapper.selectDataList(map) , CastleVersionDto.class);
        return list;
    }

    @Override
    public CastleVersionDto getNewVersion() {
        CastleVersionDto dto = ConvertUtil.transformObj(baseMapper.selectNewVersion() , CastleVersionDto.class);
        return dto;
    }
}

