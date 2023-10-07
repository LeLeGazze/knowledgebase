package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.CastleHelpArticleDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleEntity;
import com.castle.fortress.admin.system.mapper.CastleHelpArticleMapper;
import com.castle.fortress.admin.system.service.CastleHelpArticleService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助中心文章 服务实现类
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Service
public class CastleHelpArticleServiceImpl extends ServiceImpl<CastleHelpArticleMapper, CastleHelpArticleEntity> implements CastleHelpArticleService {
    /**
    * 获取查询条件
    * @param castleHelpArticleDto
    * @return
    */
    public QueryWrapper<CastleHelpArticleEntity> getWrapper(CastleHelpArticleDto castleHelpArticleDto){
        QueryWrapper<CastleHelpArticleEntity> wrapper= new QueryWrapper();
        if(castleHelpArticleDto != null){
            CastleHelpArticleEntity castleHelpArticleEntity = ConvertUtil.transformObj(castleHelpArticleDto,CastleHelpArticleEntity.class);
            wrapper.like(StrUtil.isNotEmpty(castleHelpArticleEntity.getTitle()),"title",castleHelpArticleEntity.getTitle());
            wrapper.eq(castleHelpArticleEntity.getTypeId() != null,"type_id",castleHelpArticleEntity.getTypeId());
            wrapper.like(castleHelpArticleEntity.getStatus() != null,"status",castleHelpArticleEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<CastleHelpArticleDto> pageCastleHelpArticle(Page<CastleHelpArticleDto> page, CastleHelpArticleDto castleHelpArticleDto) {
        QueryWrapper<CastleHelpArticleEntity> wrapper = getWrapper(castleHelpArticleDto);
        Page<CastleHelpArticleEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<CastleHelpArticleEntity> castleHelpArticlePage=baseMapper.selectPage(pageEntity,wrapper);
        Page<CastleHelpArticleDto> pageDto = new Page(castleHelpArticlePage.getCurrent(), castleHelpArticlePage.getSize(),castleHelpArticlePage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(castleHelpArticlePage.getRecords(),CastleHelpArticleDto.class));
        return pageDto;
    }

    @Override
    public IPage<CastleHelpArticleDto> pageCastleHelpArticleExtends(Page<CastleHelpArticleDto> page, CastleHelpArticleDto castleHelpArticleDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        CastleHelpArticleEntity castleHelpArticleEntity = ConvertUtil.transformObj(castleHelpArticleDto,CastleHelpArticleEntity.class);
        List<CastleHelpArticleEntity> castleHelpArticleList=baseMapper.extendsList(pageMap,castleHelpArticleEntity);
        Long total = baseMapper.extendsCount(castleHelpArticleEntity);
        Page<CastleHelpArticleDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(castleHelpArticleList,CastleHelpArticleDto.class));
        return pageDto;
    }
    @Override
    public CastleHelpArticleDto getByIdExtends(Long id){
        CastleHelpArticleEntity  castleHelpArticleEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(castleHelpArticleEntity,CastleHelpArticleDto.class);
    }

    @Override
    public List<CastleHelpArticleDto> listCastleHelpArticle(CastleHelpArticleDto castleHelpArticleDto){
        QueryWrapper<CastleHelpArticleEntity> wrapper = getWrapper(castleHelpArticleDto);
        List<CastleHelpArticleEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,CastleHelpArticleDto.class);
    }

    @Override
    public List<CastleHelpArticleDto> listByTypeId(Long typeId) {
        Map<String , Object> params = new HashMap<>();
        params.put("status" , YesNoEnum.YES.getCode());
        params.put("typeId" , typeId);
        List<CastleHelpArticleDto> list = ConvertUtil.transformObjList(baseMapper.selectDataList(params) ,CastleHelpArticleDto.class );
        return list;
    }
}

