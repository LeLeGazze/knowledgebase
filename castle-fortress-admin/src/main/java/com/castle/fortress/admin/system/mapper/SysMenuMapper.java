package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单
 * @author castle
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查询角色授权的有效菜单
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityMenu(@Param("roleIds") List<Long> roleIds);

    /**
     * 查询角色授权的有效内页及按钮
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityButton(@Param("roleIds")List<Long> roleIds);
    /**
     * 获取角色列表分配的菜单及按钮权限
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityAllData(@Param("roleIds")List<Long> roleIds);

    /**
     * 查询角色授权的有效路由
     * @param roleIds
     * @return
     */
    List<SysMenu> authorityRouters(@Param("roleIds") List<Long> roleIds);
}
