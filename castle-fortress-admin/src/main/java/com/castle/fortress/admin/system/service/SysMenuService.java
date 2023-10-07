package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.dto.SysMenuDto;
import com.castle.fortress.admin.system.entity.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务类
 * @author castle
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取角色列表分配的菜单权限
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityMenu(List<Long> roleIds);


    /**
     * 获取角色列表分配的菜单及按钮权限
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityAllData(List<Long> roleIds);

    /**
     * 获取角色列表分配的内页及按钮权限
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityButton(List<Long> roleIds);

    /**
     * 分页展示菜单列表
     * @param page
     * @param sysMenu
     * @return
     */
    IPage<SysMenu> pageMenu(Page<SysMenu> page, SysMenu sysMenu);

    /**
     * 获取子菜单
     * @param id
     * @return
     */
    List<SysMenu> children(Long id);

    /**
     * 角色选中的菜单数据，用于前端显示
     * @param roleId 角色id
     * @param menuTree 当前登录人员权限范围内的树形菜单
     * @return
     */
    List<SysMenu> showCheckedData(Long roleId,List<SysMenuDto> menuTree);

    /**
     * 获取角色列表分配的路由信息
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityRouters(List<Long> roleIds);


    /**
     * 获取用户的权限编码
     * @return
     */
    Set<String> getPermissions(CastleUserDetail userDetail);

    /**
     * 异步保存表单菜单
     * @param tbId
     * @param tbName
     */
    void saveFormMenuAsync(String tbId,String tbName);

    /**
     * 异步删除表单菜单
     * @param tbId
     */
    void delFormMenuAsync(String tbId);
}
