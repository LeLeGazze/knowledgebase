package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.mapper.KbCategoryLabelMapper;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import com.castle.fortress.admin.knowledge.mapper.KbLabelCategoryMapper;
import com.castle.fortress.admin.knowledge.service.KbLabelCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 标签分类表 服务实现类
 *
 * @author
 * @since 2023-06-14
 */
@Service
public class KbLabelCategoryServiceImpl extends ServiceImpl<KbLabelCategoryMapper, KbLabelCategoryEntity> implements KbLabelCategoryService {
    @Autowired
    private KbCategoryLabelMapper kbCategoryLabelMapper;

    /**
     * 获取查询条件
     *
     * @param kbLabelCategoryDto
     * @return
     */
    public QueryWrapper<KbLabelCategoryEntity> getWrapper(KbLabelCategoryDto kbLabelCategoryDto) {
        QueryWrapper<KbLabelCategoryEntity> wrapper = new QueryWrapper();
        if (kbLabelCategoryDto != null) {
            KbLabelCategoryEntity kbLabelCategoryEntity = ConvertUtil.transformObj(kbLabelCategoryDto, KbLabelCategoryEntity.class);
            wrapper.like(kbLabelCategoryEntity.getId() != null, "id", kbLabelCategoryEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbLabelCategoryEntity.getName()), "name", kbLabelCategoryEntity.getName());
            wrapper.like(kbLabelCategoryEntity.getCreateTime() != null, "create_time", kbLabelCategoryEntity.getCreateTime());
            wrapper.like(kbLabelCategoryEntity.getIsDeleted() != null, "is_deleted", kbLabelCategoryEntity.getIsDeleted());
            wrapper.like(kbLabelCategoryEntity.getStatus() != null, "status", kbLabelCategoryEntity.getStatus());
            wrapper.like(kbLabelCategoryEntity.getCreateUser() != null, "create_user", kbLabelCategoryEntity.getCreateUser());
            wrapper.like(kbLabelCategoryEntity.getUpdateUser() != null, "update_user", kbLabelCategoryEntity.getUpdateUser());
            wrapper.like(kbLabelCategoryEntity.getUpdateTime() != null, "update_time", kbLabelCategoryEntity.getUpdateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<KbLabelCategoryDto> pageKbLabelCategory(Page<KbLabelCategoryDto> page, KbLabelCategoryDto kbLabelCategoryDto) {
        if (StrUtil.isNotEmpty(kbLabelCategoryDto.getLabelName())) {
            Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
            List<KbLabelCategoryDto> list = baseMapper.selectByNameOrLabelName(pageMap, kbLabelCategoryDto);
            Integer total = baseMapper.countByNameOrLabelName(pageMap, kbLabelCategoryDto);
            Page<KbLabelCategoryDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
            pageDto.setRecords(list);
            return pageDto;
        }
        QueryWrapper<KbLabelCategoryEntity> wrapper = getWrapper(kbLabelCategoryDto);
        Page<KbLabelCategoryEntity> pageEntity = new Page<>(page.getCurrent(), page.getSize());
        Page<KbLabelCategoryEntity> kbLabelCategoryPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbLabelCategoryDto> pageDto = new Page(kbLabelCategoryPage.getCurrent(), kbLabelCategoryPage.getSize(), kbLabelCategoryPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbLabelCategoryPage.getRecords(), KbLabelCategoryDto.class));
        return pageDto;
    }


    @Override
    public List<KbLabelCategoryDto> listKbLabelCategory(KbLabelCategoryDto kbLabelCategoryDto) {
        QueryWrapper<KbLabelCategoryEntity> wrapper = getWrapper(kbLabelCategoryDto);
        List<KbLabelCategoryEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbLabelCategoryDto.class);
    }

    @Override
    public List<KbModelLabelEntity> findLabelByCtId(Long id) {
        return kbCategoryLabelMapper.findAllLabelByCategory(id);
    }

    @Override
    public List<KbModelLabelDto> findLabelByCtIds(List<Long> ids) {
        return kbCategoryLabelMapper.findAllLabelByCategorys(ids);
    }

    @Override
    public List<KbLabelCategoryEntity> findByIds(List<Long> ids) {
        return kbCategoryLabelMapper.findByIds(ids);
    }

    @Override
    public List<KbLabelCategoryEntity> isExiteName(List<String> list) {
        return kbCategoryLabelMapper.isExiteName(list);
    }
}

