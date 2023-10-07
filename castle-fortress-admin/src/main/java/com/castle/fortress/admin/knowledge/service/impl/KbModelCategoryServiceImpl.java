package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbModelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelCategoryDto;
import com.castle.fortress.admin.knowledge.mapper.KbModelCategoryMapper;
import com.castle.fortress.admin.knowledge.service.KbModelCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 模型分类管理 服务实现类
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
@Service
public class KbModelCategoryServiceImpl extends ServiceImpl<KbModelCategoryMapper, KbModelCategoryEntity> implements KbModelCategoryService {
    /**
    * 获取查询条件
    * @param kbModelCategoryDto
    * @return
    */
    public QueryWrapper<KbModelCategoryEntity> getWrapper(KbModelCategoryDto kbModelCategoryDto){
        QueryWrapper<KbModelCategoryEntity> wrapper= new QueryWrapper();
        if(kbModelCategoryDto != null){
            KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto,KbModelCategoryEntity.class);
            wrapper.like(StrUtil.isNotEmpty(kbModelCategoryEntity.getName()),"name",kbModelCategoryEntity.getName());
            wrapper.like(kbModelCategoryEntity.getSort() != null,"sort",kbModelCategoryEntity.getSort());
            wrapper.like(kbModelCategoryEntity.getStatus() != null,"status",kbModelCategoryEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbModelCategoryDto> pageKbModelCategory(Page<KbModelCategoryDto> page, KbModelCategoryDto kbModelCategoryDto) {
        QueryWrapper<KbModelCategoryEntity> wrapper = getWrapper(kbModelCategoryDto);
        Page<KbModelCategoryEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbModelCategoryEntity> kbModelCategoryPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbModelCategoryDto> pageDto = new Page(kbModelCategoryPage.getCurrent(), kbModelCategoryPage.getSize(),kbModelCategoryPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbModelCategoryPage.getRecords(),KbModelCategoryDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbModelCategoryDto> pageKbModelCategoryExtends(Page<KbModelCategoryDto> page, KbModelCategoryDto kbModelCategoryDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto,KbModelCategoryEntity.class);
        List<KbModelCategoryEntity> kbModelCategoryList=baseMapper.extendsList(pageMap,kbModelCategoryEntity);
        Long total = baseMapper.extendsCount(kbModelCategoryEntity);
        Page<KbModelCategoryDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbModelCategoryList,KbModelCategoryDto.class));
        return pageDto;
    }
    @Override
    public KbModelCategoryDto getByIdExtends(Long id){
        KbModelCategoryEntity  kbModelCategoryEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbModelCategoryEntity,KbModelCategoryDto.class);
    }

    @Override
    public List<KbModelCategoryDto> listKbModelCategory(KbModelCategoryDto kbModelCategoryDto){
        QueryWrapper<KbModelCategoryEntity> wrapper = getWrapper(kbModelCategoryDto);
        List<KbModelCategoryEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbModelCategoryDto.class);
    }

    @Override
    public List<Map<String, Object>> findBySwId(String swId) {
        return baseMapper.findBySwId(swId);
    }
}

