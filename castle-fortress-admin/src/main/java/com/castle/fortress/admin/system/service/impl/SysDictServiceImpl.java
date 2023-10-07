package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysDictDto;
import com.castle.fortress.admin.system.entity.SysDictEntity;
import com.castle.fortress.admin.system.mapper.SysDictMapper;
import com.castle.fortress.admin.system.service.SysDictService;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统字典服务实现类
 * @author castle
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDictEntity> implements SysDictService {


    @Override
    public IPage<SysDictDto> pageSysDict(Page<SysDictDto> page, SysDictDto sysDictDto) {
        QueryWrapper<SysDictEntity> wrapper= new QueryWrapper();
        wrapper.eq("parent_id",0);
        wrapper.eq(StrUtil.isNotEmpty(sysDictDto.getCode()),"code",sysDictDto.getCode());
        wrapper.like(StrUtil.isNotEmpty(sysDictDto.getDictValue()),"dict_value",sysDictDto.getDictValue());
        wrapper.orderByDesc("create_time");
        Page<SysDictEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysDictEntity> sysDictPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysDictDto> pageDto = new Page(sysDictPage.getCurrent(), sysDictPage.getSize(),sysDictPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysDictPage.getRecords(),SysDictDto.class));
        return pageDto;
    }

    @Override
    public boolean deleteDict(Long id) {
        return baseMapper.deleteDict(id) >= 1;
    }

    @Override
    public List<SysDictDto> listByCode(String code) {
        QueryWrapper<SysDictEntity> wrapper= new QueryWrapper();
        wrapper.eq("code",code);
        wrapper.eq("status", YesNoEnum.YES.getCode());
        wrapper.ne("parent_id",0);
        wrapper.orderByAsc("sort");
        List<SysDictEntity> dictEntities = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(dictEntities,SysDictDto.class);
    }

    @Override
    public IPage<SysDictDto> subPageSysDict(Page<SysDictDto> page, SysDictDto sysDictDto) {
        if(sysDictDto == null || sysDictDto.getParentId()==null || sysDictDto.getParentId() == 0){
            return null;
        }
        QueryWrapper<SysDictEntity> wrapper= new QueryWrapper();
        wrapper.eq("parent_id",sysDictDto.getParentId());
        wrapper.eq(StrUtil.isNotEmpty(sysDictDto.getCode()),"code",sysDictDto.getCode());
        wrapper.like(StrUtil.isNotEmpty(sysDictDto.getDictValue()),"dict_value",sysDictDto.getDictValue());
        wrapper.orderByAsc("sort");
        Page<SysDictEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysDictEntity> sysDictPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysDictDto> pageDto = new Page(sysDictPage.getCurrent(), sysDictPage.getSize(),sysDictPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysDictPage.getRecords(),SysDictDto.class));
        return pageDto;
    }

    @Override
    public List<SysDictEntity> selectBySelective(SysDictEntity sysDict) {
        QueryWrapper<SysDictEntity> wrapper= new QueryWrapper();
        if(sysDict != null){
            wrapper.eq(StrUtil.isNotEmpty(sysDict.getCode()),"code",sysDict.getCode());
            wrapper.eq(sysDict.getParentId() !=null,"parent_id", sysDict.getParentId());
            wrapper.eq(StrUtil.isNotEmpty(sysDict.getDictKey()),"dict_key",sysDict.getDictKey());
            wrapper.eq(sysDict.getStatus() != null && sysDict.getStatus()!= 0 ,"status",sysDict.getStatus());
        }
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void updateSubCode(SysDictEntity sysDictParent) {
        baseMapper.updateSubCode(sysDictParent);
    }
}
