package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.dto.SysRoleDataAuthDto;
import com.castle.fortress.admin.system.entity.SysRoleDataAuthEntity;

/**
 * 角色数据权限表-细化到部门 服务类
 *
 * @author castle
 * @since 2021-01-04
 */
public interface SysRoleDataAuthService extends IService<SysRoleDataAuthEntity> {

    /**
     * 分页展示角色数据权限表-细化到部门列表
     * @param page
     * @param sysRoleDataAuthDto
     * @return
     */
    IPage<SysRoleDataAuthDto> pageSysRoleDataAuth(Page<SysRoleDataAuthDto> page, SysRoleDataAuthDto sysRoleDataAuthDto);

    void delByRoleId(Long roleId);
}
