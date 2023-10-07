package com.castle.fortress.admin.system.service.impl;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysRoleMenu;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.mapper.SysRoleMenuMapper;
import com.castle.fortress.admin.system.service.SysRoleMenuService;
import com.castle.fortress.admin.system.service.SysRoleService;
import com.castle.fortress.common.enums.YesNoEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色菜单关联服务类
 * @author castle
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public void delByRoleId(Long roleId) {
        baseMapper.delByRoleId(roleId);
    }

    @Override
    public void delByMenuId(Long menuId) {
        baseMapper.delByMenuId(menuId);
    }

    @Override
    public boolean menuAuthSave(List<SysRoleMenu> sysRoleMenus) {
        int rowSize = 100;
        if(sysRoleMenus!=null && !sysRoleMenus.isEmpty()){
            DefaultIdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();
            for(SysRoleMenu roleMenu:sysRoleMenus){
                roleMenu.setId(identifierGenerator.nextId(roleMenu) );
            }
            int times = sysRoleMenus.size()%rowSize==0?(sysRoleMenus.size()/rowSize):(sysRoleMenus.size()/rowSize)+1;
            for(int i=0;i<times;i++){
                List<SysRoleMenu> list = new ArrayList<>();
                int ends = (i+1)*rowSize > sysRoleMenus.size()?sysRoleMenus.size():(i+1)*rowSize;
                list = sysRoleMenus.subList(i*rowSize,ends);
                baseMapper.menuAuthSave(list);
            }
        }
        return true;
    }
    @Async
    @Override
    public void saveByCurrentUserAsync(SysUser user, SysMenu sysMenu) {
        if(user!=null && !user.getIsSuperAdmin()){
            List<SysRole> roles = user.getRoles();
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            //管理员自动绑定关系
            if(user.getIsAdmin()){
                for(SysRole r:roles){
                    if(YesNoEnum.YES.getCode().equals(r.getIsAdmin())){
                        SysRoleMenu rm = new SysRoleMenu();
                        rm.setRoleId(r.getId());
                        rm.setMenuId(sysMenu.getId());
                        sysRoleMenus.add(rm);
                        break;
                    }
                }
                //当前操作用户的所有角色、管理员 自动绑定关系
            }else{
                for(SysRole r:roles){
                    SysRoleMenu rm = new SysRoleMenu();
                    rm.setRoleId(r.getId());
                    rm.setMenuId(sysMenu.getId());
                    sysRoleMenus.add(rm);
                }
                //管理员列表
                List<SysRole> sr=sysRoleService.listAdmin();
                for(SysRole r:sr){
                    SysRoleMenu rm = new SysRoleMenu();
                    rm.setRoleId(r.getId());
                    rm.setMenuId(sysMenu.getId());
                    sysRoleMenus.add(rm);
                }
            }
            if(!sysRoleMenus.isEmpty()){
                menuAuthSave(sysRoleMenus);
            }
        }
    }
}
