package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbGroupsEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.castle.fortress.admin.knowledge.mapper.KbGroupsMapper;
import com.castle.fortress.admin.knowledge.service.KbGroupsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 知识库用户组管理 服务实现类
 *
 * @author
 * @since 2023-04-22
 */
@Service
public class KbGroupsServiceImpl extends ServiceImpl<KbGroupsMapper, KbGroupsEntity> implements KbGroupsService {
    /**
     * 获取查询条件
     *
     * @param kbGroupsDto
     * @return
     */
    public QueryWrapper<KbGroupsEntity> getWrapper(KbGroupsDto kbGroupsDto) {
        QueryWrapper<KbGroupsEntity> wrapper = new QueryWrapper();
        if (kbGroupsDto != null) {
            KbGroupsEntity kbGroupsEntity = ConvertUtil.transformObj(kbGroupsDto, KbGroupsEntity.class);
            wrapper.eq(kbGroupsEntity.getId() != null, "id", kbGroupsEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbGroupsEntity.getName()), "name", kbGroupsEntity.getName());
            wrapper.eq(kbGroupsEntity.getPId() != null, "p_id", kbGroupsEntity.getPId());
            wrapper.eq(kbGroupsEntity.getSort() != null, "sort", kbGroupsEntity.getSort());
            wrapper.eq(kbGroupsEntity.getStatus() != null, "status", kbGroupsEntity.getStatus());
            wrapper.eq(kbGroupsEntity.getCreateTime() != null, "create_time", kbGroupsEntity.getCreateTime());
            wrapper.eq(kbGroupsEntity.getCreateUser() != null, "create_user", kbGroupsEntity.getCreateUser());
            wrapper.eq(kbGroupsEntity.getUpdateUser() != null, "update_user", kbGroupsEntity.getUpdateUser());
            wrapper.eq(kbGroupsEntity.getUpdateTime() != null, "update_time", kbGroupsEntity.getUpdateTime());
            wrapper.eq(kbGroupsEntity.getIsDeleted() != null, "is_deleted", kbGroupsEntity.getIsDeleted());
        }
        return wrapper;
    }


    @Override
    public IPage<KbGroupsDto> pageKbGroups(Page<KbGroupsDto> page, KbGroupsDto kbGroupsDto) {
        QueryWrapper<KbGroupsEntity> wrapper = getWrapper(kbGroupsDto);
        Page<KbGroupsEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbGroupsEntity> kbGroupsPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbGroupsDto> pageDto = new Page(kbGroupsPage.getCurrent(), kbGroupsPage.getSize(), kbGroupsPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbGroupsPage.getRecords(), KbGroupsDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbGroupsDto> pageKbGroupsExtends(Page<KbGroupsDto> page, KbGroupsDto kbGroupsDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbGroupsEntity kbGroupsEntity = ConvertUtil.transformObj(kbGroupsDto, KbGroupsEntity.class);
        List<KbGroupsEntity> kbGroupsList = baseMapper.extendsList(pageMap, kbGroupsEntity);
        Long total = baseMapper.extendsCount(kbGroupsEntity);
        Page<KbGroupsDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbGroupsList, KbGroupsDto.class));
        return pageDto;
    }

    @Override
    public KbGroupsDto getByIdExtends(Long id) {
        KbGroupsEntity kbGroupsEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbGroupsEntity, KbGroupsDto.class);
    }

    @Override
    public List<KbGroupsDto> listKbGroups(KbGroupsDto kbGroupsDto) {
        QueryWrapper<KbGroupsEntity> wrapper = getWrapper(kbGroupsDto);
        if (kbGroupsDto.getPId() == null) {
            wrapper.isNull("p_id");
        }
        wrapper.orderByDesc("sort");
        List<KbGroupsEntity> list = baseMapper.selectList(wrapper);
        List<KbGroupsDto> kbGroupsDtos = ConvertUtil.transformObjList(list, KbGroupsDto.class);
        for (KbGroupsDto kbGroups : kbGroupsDtos) {
            QueryWrapper<KbGroupsEntity> lqw = new QueryWrapper<>();
            lqw.eq(kbGroups.getId() != null, "p_id", kbGroups.getId());
            lqw.eq(kbGroups.getIsDeleted() != null, "is_deleted", 2);
            lqw.orderByDesc("sort");
            List<KbGroupsEntity> kbGroupsEntities = baseMapper.selectList(lqw);
            kbGroups.setChildren(kbGroupsEntities);
        }
        return kbGroupsDtos;
    }

    @Override
    public List<KbGroupsDto> findpId(Long id) {
        LambdaQueryWrapper<KbGroupsEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KbGroupsEntity::getPId, id);
        lqw.orderByDesc(KbGroupsEntity::getSort);
        List<KbGroupsEntity> kbGroupsEntities = baseMapper.selectList(lqw);
        return ConvertUtil.transformObjList(kbGroupsEntities, KbGroupsDto.class);
    }

    @Override
    public KbGroupsEntity findById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public IPage<KbGroupsDto> findpIdPage(Long pId, Page<KbGroupsDto> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbGroupsDto> groupsDtoList = baseMapper.findGroupBypIdPage(pId, pageMap);
        Integer total = baseMapper.findGroupBypIdPageCount(pId);
        Page<KbGroupsDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(groupsDtoList);
        return pageDto;
    }
}

