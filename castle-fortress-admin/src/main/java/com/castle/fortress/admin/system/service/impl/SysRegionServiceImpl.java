package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.core.constants.RedisKeyConstants;
import com.castle.fortress.admin.system.dto.SysRegionDto;
import com.castle.fortress.admin.system.entity.SysRegionEntity;
import com.castle.fortress.admin.system.mapper.SysRegionMapper;
import com.castle.fortress.admin.system.service.SysRegionService;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 行政区域 服务实现类
 *
 * @author castle
 * @since 2021-04-28
 */
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegionEntity> implements SysRegionService {
    @Autowired
    private RedisUtils redisUtils;
    /**
    * 获取查询条件
    * @param sysRegionDto
    * @return
    */
    public QueryWrapper<SysRegionEntity> getWrapper(SysRegionDto sysRegionDto){
        QueryWrapper<SysRegionEntity> wrapper= new QueryWrapper();
        if(sysRegionDto != null){
            SysRegionEntity sysRegionEntity = ConvertUtil.transformObj(sysRegionDto,SysRegionEntity.class);
            wrapper.eq(sysRegionEntity.getId() != null,"id",sysRegionEntity.getId());
            wrapper.eq(sysRegionEntity.getParentId() != null,"parent_id",sysRegionEntity.getParentId());
            wrapper.eq(StrUtil.isNotEmpty(sysRegionEntity.getName()),"name",sysRegionEntity.getName());
            wrapper.eq(sysRegionEntity.getTreeLevel() != null,"tree_level",sysRegionEntity.getTreeLevel());
            wrapper.eq(sysRegionEntity.getLeaf() != null,"leaf",sysRegionEntity.getLeaf());
            wrapper.eq(sysRegionEntity.getSort() != null,"sort",sysRegionEntity.getSort());
            wrapper.eq(sysRegionEntity.getCreateUser() != null,"create_user",sysRegionEntity.getCreateUser());
            wrapper.eq(sysRegionEntity.getUpdateUser() != null,"update_user",sysRegionEntity.getUpdateUser());
            wrapper.eq(sysRegionEntity.getUpdateTime() != null,"update_time",sysRegionEntity.getUpdateTime());
        }
        wrapper.orderByAsc("sort");
        return wrapper;
    }


    @Override
    public IPage<SysRegionDto> pageSysRegion(Page<SysRegionDto> page, SysRegionDto sysRegionDto) {
        QueryWrapper<SysRegionEntity> wrapper = getWrapper(sysRegionDto);
        Page<SysRegionEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysRegionEntity> sysRegionPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysRegionDto> pageDto = new Page(sysRegionPage.getCurrent(), sysRegionPage.getSize(),sysRegionPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysRegionPage.getRecords(),SysRegionDto.class));
        return pageDto;
    }


    @Override
    public List<SysRegionDto> listSysRegion(SysRegionDto sysRegionDto){
        QueryWrapper<SysRegionEntity> wrapper = getWrapper(sysRegionDto);
        List<SysRegionEntity> list = baseMapper.selectList(wrapper);
        List<SysRegionDto> dtos = ConvertUtil.transformObjList(list,SysRegionDto.class);
        for(SysRegionDto d:dtos){
            d.setHasChildren(d.getLeaf().equals(0));
        }
        return dtos;
    }

    @Override
    public SysRegionEntity getByIdExtends(Long id) {
        return baseMapper.getByIdExtends(id);
    }
    @Async
    @Override
    public void initRegionTree() {
        List<SysRegionEntity> list = this.list();
        List<SysRegionDto> dtos=ConvertUtil.transformObjList(list,SysRegionDto.class);
        List<SysRegionDto> tree = ConvertUtil.listToTree(dtos);
        redisUtils.set(RedisKeyConstants.REGION_TREE,tree);
    }
}

