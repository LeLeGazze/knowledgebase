package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysRoleDataAuthDto;
import com.castle.fortress.admin.system.entity.SysRoleDataAuthEntity;
import com.castle.fortress.admin.system.mapper.SysRoleDataAuthMapper;
import com.castle.fortress.admin.system.service.SysRoleDataAuthService;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;

/**
 * 角色数据权限表-细化到部门 服务实现类
 *
 * @author castle
 * @since 2021-01-04
 */
@Service
public class SysRoleDataAuthServiceImpl extends ServiceImpl<SysRoleDataAuthMapper, SysRoleDataAuthEntity> implements SysRoleDataAuthService {

    @Override
    public IPage<SysRoleDataAuthDto> pageSysRoleDataAuth(Page<SysRoleDataAuthDto> page, SysRoleDataAuthDto sysRoleDataAuthDto) {
        QueryWrapper<SysRoleDataAuthEntity> wrapper= new QueryWrapper();
        Page<SysRoleDataAuthEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysRoleDataAuthEntity> sysRoleDataAuthPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysRoleDataAuthDto> pageDto = new Page(sysRoleDataAuthPage.getCurrent(), sysRoleDataAuthPage.getSize(),sysRoleDataAuthPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysRoleDataAuthPage.getRecords(),SysRoleDataAuthDto.class));
        return pageDto;
    }

    @Override
    public void delByRoleId(Long roleId) {
        baseMapper.delByRoleId(roleId);
    }
}

