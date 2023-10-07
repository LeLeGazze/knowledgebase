package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联表
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    void delByRoleId(@Param("roleId") Long roleId);
    void menuAuthSave(@Param("list") List<SysRoleMenu> list);
    void delByMenuId(@Param("menuId")Long menuId);
}
