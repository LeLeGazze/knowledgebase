package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.admin.system.entity.SysRoleMenu;
import com.castle.fortress.admin.system.entity.SysUser;

import java.util.List;

/**
 * 系统角色菜单关联服务类
 * @author castle
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {
    void delByRoleId(Long roleId);
    void delByMenuId(Long menuId);
    boolean menuAuthSave(List<SysRoleMenu> sysRoleMenus);
    void saveByCurrentUserAsync(SysUser user, SysMenu sysMenu);
}
