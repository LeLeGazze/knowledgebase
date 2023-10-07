package com.castle.fortress.admin.system.controller;

import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysMenuService;
import com.castle.fortress.admin.system.service.SysRoleMenuService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单管理
 * @author castle
 */
@Controller
@Api(tags = "菜单管理控制器")
public class MenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取当前登录用户的菜单权限
     * @return
     */
    @GetMapping("/system/menu/authority")
    @ResponseBody
    @ApiOperation("获取当前登录用户的菜单权限")
    public RespBody<List<SysMenu>> authority(){
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsSuperAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(new ArrayList<>());
            }
            if(roleList!=null&& roleList.size()>0){
                roleIds=new ArrayList<>();
                for(SysRole role:roleList){
                    roleIds.add(role.getId());
                }
            }
        }
        List<SysMenu> menuList= ConvertUtil.listToTree(sysMenuService.authorityMenu(roleIds));
        return RespBody.data(menuList);
    }

    /**
     * 获取当前登录用户的内页及按钮权限
     * @return
     */
    @ApiOperation("获取当前登录用户的内页及按钮权限")
    @GetMapping("/system/menu/authorityButton")
    @ResponseBody
    public RespBody<List<SysMenu>> authorityButton(){
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<SysMenu> menuList=null;
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(new ArrayList<>());
            }
            roleIds=new ArrayList<>();
            for(SysRole role:roleList){
                roleIds.add(role.getId());
            }
        }
        menuList=sysMenuService.authorityButton(roleIds);
        return RespBody.data(menuList);
    }

    /**
     * 菜单页面展示
     * @param sysMenu 菜单实体类
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.QUERY)
    @ApiOperation("菜单页面展示")
    @GetMapping("/system/menu/list")
    @ResponseBody
    public RespBody<List<SysMenu>> listMenu(SysMenu sysMenu){
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsSuperAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(null);
            }
            if(roleList!=null&& roleList.size()>0){
                roleIds=new ArrayList<>();
                for(SysRole role:roleList){
                    roleIds.add(role.getId());
                }
            }
        }
        List<SysMenu> menuList = ConvertUtil.listToTree(sysMenuService.authorityAllData(roleIds));
        return RespBody.data(menuList);
    }

    /**
     * 菜单列表展示
     * @param sysMenu 菜单实体类
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.QUERY)
    @ApiOperation("菜单列表展示")
    @GetMapping("/system/menu/listAll")
    @ResponseBody
    public RespBody<List<SysMenu>> listMenuAll(SysMenu sysMenu){
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsSuperAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(null);
            }
            if(roleList!=null&& roleList.size()>0){
                roleIds=new ArrayList<>();
                for(SysRole role:roleList){
                    roleIds.add(role.getId());
                }
            }
        }
        List<SysMenu> menuList = sysMenuService.authorityAllData(roleIds);
        return RespBody.data(menuList);
    }

    /**
     * 菜单保存
     * @param sysMenu 菜单实体类
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.INSERT)
    @ApiOperation("菜单保存")
    @PostMapping("/system/menu/save")
    @ResponseBody
    @RequiresPermissions("system:menuManage:save")
    public RespBody<String> saveMenu(@RequestBody SysMenu sysMenu){
        if(sysMenu == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        sysMenu.setStatus(YesNoEnum.YES.getCode());
        if(sysMenuService.save(sysMenu)){
            sysRoleMenuService.saveByCurrentUserAsync(WebUtil.currentUser(),sysMenu);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单编辑
     * @param sysMenu 菜单实体类
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("菜单编辑")
    @PostMapping("/system/menu/edit")
    @ResponseBody
    @RequiresPermissions("system:menuManage:edit")
    public RespBody<String> updateMenu(@RequestBody SysMenu sysMenu){
        if(sysMenu == null || sysMenu.getId() == null || sysMenu.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysMenuService.updateById(sysMenu)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单删除
     * @param id 菜单id
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.DELETE)
    @ApiOperation("菜单删除")
    @PostMapping("/system/menu/delete")
    @ResponseBody
    @RequiresPermissions("system:menuManage:delete")
    public RespBody<String> deleteMenu(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验该菜单是否叶子节点
        List<SysMenu> children = sysMenuService.children(id);
        if(children!=null && !children.isEmpty()){
            return RespBody.fail(GlobalRespCode.DEL_HAS_CHILDREN_ERROR);
        }
        if(sysMenuService.removeById(id)) {
            sysRoleMenuService.delByMenuId(id);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 菜单详情
     * @param id 菜单id
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.QUERY)
    @ApiOperation("菜单详情")
    @GetMapping("/system/menu/info")
    @ResponseBody
    @RequiresPermissions("system:menuManage:info")
    public RespBody<SysMenu> infoMenu(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysMenu menu = sysMenuService.getById(id);
        return RespBody.data(menu);
    }

    /**
     * 获取当前登录用户的路由列表
     * @return
     */
    @CastleLog(operLocation="菜单管理",operType= OperationTypeEnum.QUERY)
    @GetMapping("/system/menu/routers")
    @ResponseBody
    @ApiOperation("获取当前登录用户的路由列表")
    public RespBody<List<SysMenu>> queryRoutersList(){
        SysUser sysUser= WebUtil.currentUser();
        if(sysUser == null){
            return RespBody.data(new ArrayList<>());
        }
        List<Long> roleIds=null;
        //非超级管理员
        if(!sysUser.getIsSuperAdmin()){
            List<SysRole> roleList=sysUser.getRoles();
            if(roleList == null || roleList.isEmpty()){
                return RespBody.data(new ArrayList<>());
            }
            if(roleList!=null&& roleList.size()>0){
                roleIds=new ArrayList<>();
                for(SysRole role:roleList){
                    roleIds.add(role.getId());
                }
            }
        }
        List<SysMenu> routerList= sysMenuService.authorityRouters(roleIds);
        return RespBody.data(routerList);
    }


}
