package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.dto.SysMenuDto;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.mapper.SysMenuMapper;
import com.castle.fortress.admin.system.mapper.SysRoleMenuMapper;
import com.castle.fortress.admin.system.service.SysMenuService;
import com.castle.fortress.common.enums.MenuTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统菜单服务实现类
 * @author castle
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysMenu> authorityMenu(List<Long> roleIds) {
        return baseMapper.authorityMenu(roleIds);
    }

    @Override
    public List<SysMenu> authorityAllData(List<Long> roleIds) {
        return baseMapper.authorityAllData(roleIds);
    }

    @Override
    public List<SysMenu> authorityButton(List<Long> roleIds) {
        return baseMapper.authorityButton(roleIds);
    }

    @Override
    public IPage<SysMenu> pageMenu(Page<SysMenu> page, SysMenu sysMenu) {
        QueryWrapper<SysMenu> wrapper= new QueryWrapper<>();
        Page<SysMenu> sysMenuPage=baseMapper.selectPage(page,wrapper);
        return sysMenuPage;
    }

    @Override
    public List<SysMenu> children(Long id) {
        QueryWrapper<SysMenu> queryWrapper=new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        queryWrapper.eq("status",YesNoEnum.YES.getCode());
        queryWrapper.eq("is_deleted",YesNoEnum.NO.getCode());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SysMenu> showCheckedData(Long roleId, List<SysMenuDto> menuTree) {
        List<SysMenu> checkedData = new ArrayList<>();
        if(menuTree!=null&&!menuTree.isEmpty()){
            List<Long> id= new ArrayList<>();
            id.add(roleId);
            List<SysMenu> checkedMenuList = authorityAllData(id);
            if(checkedMenuList!=null && !checkedMenuList.isEmpty()){
                for(SysMenu menu:checkedMenuList){
                    Set<SysMenuDto>  selfAndChildren = TreeUtil.findSelfAndChildren(menuTree,menu.getId());
                    //有子节点
                    if(selfAndChildren.size()>1){
                        if(this.allChildrenIsChecked(selfAndChildren,checkedMenuList)){
                            checkedData.add(menu);
                        }
                    //无子节点
                    }else{
                        checkedData.add(menu);
                    }
                }
            }
        }
        return checkedData;
    }

    @Override
    public List<SysMenu> authorityRouters(List<Long> roleIds) {
        return baseMapper.authorityRouters(roleIds);
    }

    /**
     * 是否所有的子节点都被选中
     * @param menuDtoSet
     * @param checkedMenuList
     * @return
     */
    private boolean allChildrenIsChecked(Set<SysMenuDto> menuDtoSet,List<SysMenu> checkedMenuList){
        boolean  flag= true;
        for(SysMenuDto menuDto:menuDtoSet){
            boolean childFlag = false;
            for(SysMenu checked:checkedMenuList){
                if(menuDto.getId().equals(checked.getId())){
                    childFlag=true;
                    break;
                }
            }
            if(!childFlag){
                flag=false;
                break;
            }
        }
        return flag;
    }
    @Override
    public Set<String> getPermissions(CastleUserDetail userDetail) {
        if(CommonUtil.verifyParamNull(userDetail)){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        // 赋予权限
        List<SysMenu> resourcesList = null;
        //超管
        if(userDetail.getIsSuperAdmin()){
            resourcesList = authorityAllData(null);
        }else{
            Integer roleLevel = 3;
            // 赋予角色
            List<Long> roleIds=new ArrayList<>();
            for (SysRole role : userDetail.getRoles()) {
                roleIds.add(role.getId());
            }
            // 系统菜单权限
            resourcesList = authorityAllData(roleIds);
        }
        Set<String> permission=new HashSet<>();
        for(SysMenu menu:resourcesList){
            if(StrUtil.isNotEmpty(menu.getPermissions())){
                for(String per:menu.getPermissions().split(";")){
                    if(StrUtil.isNotEmpty(per)){
                        permission.add(per);
                    }
                }
            }
        }
        return permission;
    }
    @Async
    @Override
    public void saveFormMenuAsync(String tbId,String tbName) {
        SysMenu menu=new SysMenu();
        menu.setParentId(1456077089904738306L);
        menu.setName("表单-"+tbName);
        menu.setIcon("el-icon-ship");
        menu.setViewPath("/form/formdata/"+tbId);
        menu.setType(MenuTypeEnum.MENU.getCode());
        menu.setSort(1);
        menu.setStatus(YesNoEnum.YES.getCode());
        save(menu);
    }

    @Override
    public void delFormMenuAsync(String tbId) {
        QueryWrapper<SysMenu> wrapper=new QueryWrapper<>();
        wrapper.eq("view_path","/form/formdata/"+tbId);
        List<SysMenu> menus = list(wrapper);
        if(menus!=null && !menus.isEmpty()){
            for(SysMenu m:menus){
                sysRoleMenuMapper.delByMenuId(m.getId());
            }
        }
        remove(wrapper);
    }

}
