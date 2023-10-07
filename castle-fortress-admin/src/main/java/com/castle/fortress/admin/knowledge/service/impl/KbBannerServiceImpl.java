package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBannerEntity;
import com.castle.fortress.admin.knowledge.dto.KbBannerDto;
import com.castle.fortress.admin.knowledge.mapper.KbBannerMapper;
import com.castle.fortress.admin.knowledge.service.KbBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 知识banner图表 服务实现类
 *
 * @author
 * @since 2023-06-17
 */
@Service
public class KbBannerServiceImpl extends ServiceImpl<KbBannerMapper, KbBannerEntity> implements KbBannerService {
    /**
     * 获取查询条件
     *
     * @param kbBannerDto
     * @return
     */
    public QueryWrapper<KbBannerEntity> getWrapper(KbBannerDto kbBannerDto) {
        QueryWrapper<KbBannerEntity> wrapper = new QueryWrapper();
        if (kbBannerDto != null) {
            KbBannerEntity kbBannerEntity = ConvertUtil.transformObj(kbBannerDto, KbBannerEntity.class);
            wrapper.like(kbBannerEntity.getId() != null, "id", kbBannerEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbBannerEntity.getPcImage()), "pc_image", kbBannerEntity.getPcImage());
            wrapper.like(StrUtil.isNotEmpty(kbBannerEntity.getAppImage()), "app_image", kbBannerEntity.getAppImage());
            wrapper.like(StrUtil.isNotEmpty(kbBannerEntity.getTags()), "tags", kbBannerEntity.getTags());
            wrapper.like(kbBannerEntity.getStatus() != null, "status", kbBannerEntity.getStatus());
            wrapper.like(StrUtil.isNotEmpty(kbBannerEntity.getUrl()), "url", kbBannerEntity.getUrl());
            wrapper.like(kbBannerEntity.getWeight() != null, "weight", kbBannerEntity.getWeight());
            wrapper.like(kbBannerEntity.getUpdateUser() != null, "update_user", kbBannerEntity.getUpdateUser());
            wrapper.like(kbBannerEntity.getUpdateTime() != null, "update_time", kbBannerEntity.getUpdateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<KbBannerDto> pageKbBanner(Page<KbBannerDto> page, KbBannerDto kbBannerDto) {
        QueryWrapper<KbBannerEntity> wrapper = getWrapper(kbBannerDto);
        wrapper.orderByDesc("weight");
        Page<KbBannerEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbBannerEntity> kbBannerPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbBannerDto> pageDto = new Page(kbBannerPage.getCurrent(), kbBannerPage.getSize(), kbBannerPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbBannerPage.getRecords(), KbBannerDto.class));
        return pageDto;
    }


    @Override
    public List<KbBannerDto> listKbBanner(KbBannerDto kbBannerDto) {
        QueryWrapper<KbBannerEntity> wrapper = getWrapper(kbBannerDto);
        wrapper.orderByDesc("weight");
        List<KbBannerEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbBannerDto.class);
    }

    @Override
    public List<KbBannerEntity> findByStatus(Integer status) {
        QueryWrapper<KbBannerEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        return baseMapper.selectList(wrapper);
    }
}

