package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import com.castle.fortress.admin.system.mapper.SysPostMapper;
import com.castle.fortress.admin.system.service.SysPostService;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统职位表 服务实现类
 *
 * @author castle
 * @since 2021-01-04
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPostEntity> implements SysPostService {

    @Override
    public IPage<SysPostDto> pageSysPost(Page<SysPostDto> page, SysPostDto sysPostDto) {
        QueryWrapper<SysPostEntity> wrapper= new QueryWrapper();
        Page<SysPostEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysPostEntity> sysPostPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysPostDto> pageDto = new Page(sysPostPage.getCurrent(), sysPostPage.getSize(),sysPostPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysPostPage.getRecords(),SysPostDto.class));
        return pageDto;
    }

    @Override
    public List<SysPostDto> listPost(SysPostDto sysPostDto) {
        QueryWrapper<SysPostEntity> wrapper= new QueryWrapper();
        wrapper.eq("dept_id",sysPostDto.getDeptId());
        wrapper.eq("is_deleted", YesNoEnum.NO.getCode());
        List<SysPostEntity> postList =baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(postList,SysPostDto.class);
    }

    @Override
    public List<SysPostDto> children(Long id) {
        QueryWrapper<SysPostEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        queryWrapper.eq("is_deleted", YesNoEnum.NO.getCode());
        return ConvertUtil.transformObjList(baseMapper.selectList(queryWrapper),SysPostDto.class);
    }

    @Override
    public boolean checkColumnRepeat(SysPostEntity sysPostEntity) {
        boolean existFlag = false;
        //校验名称是否重复
        QueryWrapper<SysPostEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",sysPostEntity.getName());
        if(sysPostEntity.getParentId() == null){
            queryWrapper.isNull("parent_id");
        }else{
            queryWrapper.eq(sysPostEntity.getParentId()!=null,"parent_id",sysPostEntity.getParentId());
        }
        queryWrapper.eq("dept_id",sysPostEntity.getDeptId());
        List<SysPostEntity> list = baseMapper.selectList(queryWrapper);
        if(list == null || list.isEmpty()){
            return existFlag;
        }
        //新增
        if(sysPostEntity.getId() == null){
            existFlag = true;
            //修改
        }else{
            for(SysPostEntity entity:list){
                if(!entity.getId().equals(sysPostEntity.getId())){
                    existFlag = true;
                    break;
                }
            }
        }
        return existFlag;
    }

    @Override
    public List<SysPostDto> leadersPost(SysDeptEntity deptEntity) {
        if(deptEntity==null){
            return new ArrayList<>();
        }
        return ConvertUtil.transformObjList(baseMapper.leadersPost(deptEntity.getId(),deptEntity.getParents()),SysPostDto.class);
    }
}

