package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.SysUserRole;
import com.castle.fortress.admin.system.mapper.SysUserRoleMapper;
import com.castle.fortress.admin.system.service.SysUserRoleService;
import com.castle.fortress.common.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户角色服务实现类
 * @author castle
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<SysUserRole> queryListByUser(Long userId) {
        if(CommonUtil.verifyParamNull(userId)){
            return null;
        }
        QueryWrapper<SysUserRole> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> list=baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<SysUserRole> queryByRole(Long id) {
        if(CommonUtil.verifyParamNull(id)){
            return null;
        }
        QueryWrapper<SysUserRole> wrapper=new QueryWrapper<>();
        wrapper.eq("role_id",id);
        List<SysUserRole> list=baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public void delByUserId(Long userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        baseMapper.deleteByMap(map);
    }

    @Override
    public List<SysUserRole> queryByRoleList(List<Long> roleIds) {
        List<SysUserRole> ur=new ArrayList<>();
        if(roleIds==null||roleIds.isEmpty()){
            return ur;
        }
        QueryWrapper<SysUserRole> query= new QueryWrapper<>();
        query.in("role_id",roleIds);
        return baseMapper.selectList(query);
    }
}
