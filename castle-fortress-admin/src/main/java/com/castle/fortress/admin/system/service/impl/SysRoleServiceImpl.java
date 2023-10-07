package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysRoleDto;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.mapper.SysRoleMapper;
import com.castle.fortress.admin.system.service.SysRoleService;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户服务实现类
 * @author castle
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 查询用户对应的角色
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> queryListByUser(Long userId) {
        return baseMapper.queryListByUser(userId);
    }

    @Override
    public IPage<SysRoleDto> pageSysRole(Page<SysRoleDto> page, SysRoleDto sysRoleDto) {
        QueryWrapper<SysRole> wrapper= new QueryWrapper();
        Page<SysRole> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysRole> sysRolePage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysRoleDto> pageDto = new Page(sysRolePage.getCurrent(), sysRolePage.getSize(),sysRolePage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysRolePage.getRecords(),SysRoleDto.class));
        return pageDto;
    }

    @Override
    public boolean checkColumnRepeat(SysRole sysRole) {
        boolean existFlag = false;
        //校验名称是否重复
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",sysRole.getName());
        List<SysRole> list = baseMapper.selectList(queryWrapper);
        if(list == null || list.isEmpty()){
            return existFlag;
        }
        //新增
        if(sysRole.getId() == null){
            existFlag = true;
            //修改
        }else{
            for(SysRole role:list){
                if(!role.getId().equals(sysRole.getId())){
                    existFlag = true;
                    break;
                }
            }
        }
        return existFlag;
    }

    @Override
    public List<SysRole> listAdmin() {
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper();
        queryWrapper.eq("is_admin", YesNoEnum.YES.getCode());
        List<SysRole> list = baseMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public SysRole findById(Long id) {
        return baseMapper.findById(id);
    }
}
