package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.*;
import com.castle.fortress.admin.system.entity.*;
import com.castle.fortress.admin.system.service.*;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.github.classgraph.utils.Join;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统角色管理
 *
 * @author castle
 */
@Api(tags = "系统角色管理控制器")
@Controller
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService SysUserRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleDataAuthService sysRoleDataAuthService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 系统角色表的分页展示
     *
     * @param sysRoleDto 系统角色表实体类
     * @param current    当前页
     * @param size       每页记录数
     * @return
     */
    @ApiOperation("系统角色表分页展示")
    @GetMapping("/system/sysRole/page")
    @ResponseBody
    @RequiresPermissions(value = {"system:sysRole:pageList", "system:sysRole:channelPageList"}, logical = Logical.OR)
    public RespBody<IPage<SysRoleDto>> pageSysRoleDto(SysRoleDto sysRoleDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        //不是超管 只能查看普通角色，不可查看超管角色
        if (!sysUser.getIsAdmin()) {
            sysRoleDto.setIsAdmin(YesNoEnum.NO.getCode());
        }
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<SysRoleDto> page = new Page(pageIndex, pageSize);
        IPage<SysRoleDto> pages = sysRoleService.pageSysRole(page, sysRoleDto);
        return RespBody.data(pages);
    }

    /**
     * 系统角色表列表展示
     *
     * @param sysRoleDto
     * @return
     */
    @ApiOperation("系统角色表列表展示")
    @GetMapping("/system/sysRole/list")
    @ResponseBody
    public RespBody<List<SysRoleDto>> listSysRoleDto(SysRoleDto sysRoleDto) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        //不是超管 只能查看普通角色，不可查看超管角色
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!sysUser.getIsAdmin()) {
            wrapper.eq("is_admin", YesNoEnum.NO.getCode());
        }
        List<SysRole> roleList = sysRoleService.list(wrapper);
        return RespBody.data(ConvertUtil.transformObjList(roleList, SysRoleDto.class));
    }
    // TODO 批量删除

    /**
     * 系统角色表保存
     *
     * @param sysRoleDto 系统角色表实体类
     * @return
     */
    @ApiOperation("系统角色表保存")
    @PostMapping("/system/sysRole/save")
    @ResponseBody
    @RequiresPermissions("system:sysRole:save")
    public RespBody<String> saveSysRoleDto(@RequestBody SysRoleDto sysRoleDto) {
        if (sysRoleDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRole sysRole = ConvertUtil.transformObj(sysRoleDto, SysRole.class);
        //校验字段重复
        if (sysRoleService.checkColumnRepeat(sysRole)) {
            return RespBody.fail(BizErrorCode.ROLE_NAME_EXIST_ERROR);
        }
        if (sysRoleService.save(sysRole)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统角色表保存
     *
     * @param sysRoleDto 系统角色表实体类
     * @return
     */
    @ApiOperation("系统角色表保存")
    @PostMapping("/system/sysRole/saveToAuth")
    @ResponseBody
    @RequiresPermissions("system:sysRole:saveToAuth")
    public RespBody<String> saveToAuthSysRoleDto(@RequestBody SysRoleDto sysRoleDto) {
        if (sysRoleDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRole sysRole = ConvertUtil.transformObj(sysRoleDto, SysRole.class);
        //校验字段重复
        if (sysRoleService.checkColumnRepeat(sysRole)) {
            return RespBody.fail(BizErrorCode.ROLE_NAME_EXIST_ERROR);
        }
        if (sysRoleService.save(sysRole)) {
            //写入成功获取id在写入权限
            Long roleId = sysRole.getId();
            long startTime = System.currentTimeMillis();
            List<Long> roleMenuDtoList = sysRoleDto.getRoleMenuDtoList();
            if (roleMenuDtoList != null) {
                List<SysRoleMenu> sysRoleMenus = roleMenuDtoList.stream().map(menuId -> {
                            SysRoleMenu rm = new SysRoleMenu();
                            rm.setRoleId(roleId);
                            rm.setMenuId(menuId);
                            return rm;
                        }
                ).collect(Collectors.toList());
                if (sysRoleMenuService.menuAuthSave(sysRoleMenus)) {
                    System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
                } else {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
            }
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统角色表保存
     *
     * @param sysRoleDto 系统角色表实体类
     * @return
     */
    @ApiOperation("系统角色表保存")
    @PostMapping("/system/sysRole/editToAuth")
    @ResponseBody
    @RequiresPermissions("system:sysRole:editToAuth")
    public RespBody<String> editToAuthSysRoleDto(@RequestBody SysRoleDto sysRoleDto) {

        if (sysRoleDto == null || sysRoleDto.getId() == null || sysRoleDto.getId().equals(0)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //关闭的时候需要校验该角色是否绑定用户
        if (YesNoEnum.NO.getCode().equals(sysRoleDto.getStatus())) {
            //校验该角色是否绑定角色
            List<SysUserRole> userList = SysUserRoleService.queryByRole(sysRoleDto.getId());
            if (userList != null && !userList.isEmpty()) {
                return RespBody.fail(BizErrorCode.DEL_ROLE_EXIST_USER_ERROR);
            }
        }
        SysRole sysRole = ConvertUtil.transformObj(sysRoleDto, SysRole.class);
        //校验字段重复
        if (sysRoleService.checkColumnRepeat(sysRole)) {
            return RespBody.fail(BizErrorCode.ROLE_NAME_EXIST_ERROR);
        }
        if (sysRoleService.updateById(sysRole)) {
            // 删除已经存在的
            sysRoleMenuService.delByRoleId(sysRole.getId());
            //写入成功获取id在写入权限
            long startTime = System.currentTimeMillis();
            List<Long> roleMenuDtoList = sysRoleDto.getRoleMenuDtoList();
            if (roleMenuDtoList != null) {
                List<SysRoleMenu> sysRoleMenus = roleMenuDtoList.stream().map(menuId -> {
                            SysRoleMenu rm = new SysRoleMenu();
                            rm.setRoleId(sysRole.getId());
                            rm.setMenuId(menuId);
                            return rm;
                        }
                ).collect(Collectors.toList());
                if (sysRoleMenuService.menuAuthSave(sysRoleMenus)) {
                    System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
                } else {
                    return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
                }
                return RespBody.data("修改成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 系统角色表编辑
     *
     * @param sysRoleDto 系统角色表实体类
     * @return
     */
    @ApiOperation("系统角色表编辑")
    @PostMapping("/system/sysRole/edit")
    @ResponseBody
    @RequiresPermissions("system:sysRole:edit")
    public RespBody<String> updateSysRole(@RequestBody SysRoleDto sysRoleDto) {
        if (sysRoleDto == null || sysRoleDto.getId() == null || sysRoleDto.getId().equals(0)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //关闭的时候需要校验该角色是否绑定用户
        if (YesNoEnum.NO.getCode().equals(sysRoleDto.getStatus())) {
            //校验该角色是否绑定角色
            List<SysUserRole> userList = SysUserRoleService.queryByRole(sysRoleDto.getId());
            if (userList != null && !userList.isEmpty()) {
                return RespBody.fail(BizErrorCode.DEL_ROLE_EXIST_USER_ERROR);
            }
        }
        SysRole sysRole = ConvertUtil.transformObj(sysRoleDto, SysRole.class);
        //校验字段重复
        if (sysRoleService.checkColumnRepeat(sysRole)) {
            return RespBody.fail(BizErrorCode.ROLE_NAME_EXIST_ERROR);
        }
        if (sysRoleService.updateById(sysRole)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 系统角色表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("系统角色表删除")
    @PostMapping("/system/sysRole/delete")
    @ResponseBody
    @RequiresPermissions("system:sysRole:delete")
    public RespBody<String> deleteSysRole(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验该角色是否绑定角色
        List<SysUserRole> userList = SysUserRoleService.queryByRole(id);
        if (userList != null && !userList.isEmpty()) {
            return RespBody.fail(BizErrorCode.DEL_ROLE_EXIST_USER_ERROR);
        }
        if (sysRoleService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统角色表删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("批量系统角色表删除")
    @PostMapping("/system/sysRole/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:sysRole:deleteBatch")
    public RespBody<String> deleteBatchSysRole(@RequestBody List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        boolean flag = true;
        StringBuilder str = new StringBuilder();
        str.append(BizErrorCode.DEL_ROLE_EXIST_USER_ERROR.getMsg()).append(" 绑定账号【");
        for (Long id : ids) {
            //校验该角色是否绑定角色
            List<SysUserRole> userList = SysUserRoleService.queryByRole(id);
            if (userList != null && !userList.isEmpty()) {
                flag = false;
                List<String> userServiceByUidList = sysUserService.findByUidList(userList.stream().map(item -> item.getUserId()).collect(Collectors.toList()));
                str.append(String.join(",", userServiceByUidList));
            } else {
                sysRoleService.removeById(id);
            }
        }
        return flag ? RespBody.data("删除成功") : RespBody.fail(str.append("】").toString());
    }

    /**
     * 系统角色表详情
     *
     * @param id 系统角色表id
     * @return
     */
    @ApiOperation("系统角色表详情")
    @GetMapping("/system/sysRole/info")
    @ResponseBody
    @RequiresPermissions("system:sysRole:info")
    public RespBody<SysRoleDto> infoSysRole(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRole sysRole = sysRoleService.getById(id);
        SysRoleDto sysRoleDto = ConvertUtil.transformObj(sysRole, SysRoleDto.class);
        return RespBody.data(sysRoleDto);
    }


    /**
     * 系统角色表详情
     *
     * @param id 系统角色表id
     * @return
     */
    @ApiOperation("系统角色表详情")
    @GetMapping("/system/sysRole/menuAuthInfoToRole")
    @ResponseBody
    @RequiresPermissions("system:sysRole:menuAuthInfoToRole")
    public RespBody<SysRoleDto> menuAuthInfoToRole(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRole sysRole = sysRoleService.findById(id);
        SysRoleDto sysRoleDto = ConvertUtil.transformObj(sysRole, SysRoleDto.class);
        RespBody<Map<String, List>> mapRespBody = menuAuthInfo(id);
        sysRoleDto.setMapMenu(mapRespBody.getData());
        return RespBody.data(sysRoleDto);
    }

    /**
     * 系统角色表菜单权限详情
     *
     * @param roleId 系统角色表id
     * @return
     */
    @ApiOperation("系统角色表菜单权限详情")
    @GetMapping("/system/sysRole/menuAuthInfo")
    @ResponseBody
    @RequiresPermissions("system:sysRole:menuAuth")
    public RespBody<Map<String, List>> menuAuthInfo(@RequestParam Long roleId) {
        if (roleId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非超级管理员
        if (!sysUser.getIsSuperAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(new HashMap<>());
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        Map<String, List> map = new HashMap<>();
        List<SysMenu> menuList = sysMenuService.authorityAllData(roleIds);
        List<SysMenuDto> menuDtos = ConvertUtil.transformObjList(menuList, SysMenuDto.class);
        List<SysMenuDto> allData = ConvertUtil.listToTree(menuDtos);
        map.put("allData", allData);
        map.put("checkedData", sysMenuService.showCheckedData(roleId, allData));
        return RespBody.data(map);
    }

    /**
     * 添加角色获取菜单权限
     *
     * @return
     */
    @ApiOperation("添加角色获取菜单权限")
    @GetMapping("/system/sysRole/menuAuthList")
    @ResponseBody
    @RequiresPermissions("system:sysRole:menuAuthList")
    public RespBody<Map<String, List>> menuAuth() {

        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非超级管理员
        if (!sysUser.getIsSuperAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(new HashMap<>());
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        Map<String, List> map = new HashMap<>();
        List<SysMenu> menuList = sysMenuService.authorityAllData(roleIds);
        List<SysMenuDto> menuDtos = ConvertUtil.transformObjList(menuList, SysMenuDto.class);
        List<SysMenuDto> allData = ConvertUtil.listToTree(menuDtos);
        map.put("allData", allData);
        return RespBody.data(map);
    }


    /**
     * 系统角色表菜单权限保存
     *
     * @param roleMenuDtoList 系统角色菜单关联列表
     * @return
     */
    @ApiOperation("系统角色表菜单权限保存")
    @PostMapping("/system/sysRole/menuAuthSave")
    @ResponseBody
    @RequiresPermissions("system:sysRole:menuAuth")
    public RespBody<String> menuAuthSave(@RequestBody List<SysRoleMenuDto> roleMenuDtoList) {
        long startTime = System.currentTimeMillis();
        if (roleMenuDtoList == null || roleMenuDtoList.isEmpty()) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Long roleId = roleMenuDtoList.get(0).getRoleId();
        sysRoleMenuService.delByRoleId(roleId);
        List<SysRoleMenu> sysRoleMenus = ConvertUtil.transformObjList(roleMenuDtoList, SysRoleMenu.class);
        if (sysRoleMenuService.menuAuthSave(sysRoleMenus)) {
            System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统角色表数据权限详情
     *
     * @param roleId 系统角色表id
     * @return
     */
    @ApiOperation("系统角色表数据权限详情")
    @GetMapping("/system/sysRole/dataAuthInfo")
    @ResponseBody
    @RequiresPermissions("system:sysRole:dataAuth")
    public RespBody<Map<String, List>> dataAuthInfo(@RequestParam Long roleId) {
        if (roleId == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非管理员
        if (!sysUser.getIsAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(new HashMap<>());
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        Map<String, List> map = new HashMap<>();
        List<SysDeptDto> deptList = sysDeptService.authorityAllDept(roleIds, null, null);
        List<SysDeptDto> allData = ConvertUtil.listToTree(deptList);
        map.put("allData", allData);
        List<Long> checkedIds = new ArrayList<>();
        checkedIds.add(roleId);
        map.put("checkedData", sysDeptService.authorityAllDept(checkedIds, null, null));
        return RespBody.data(map);
    }

    /**
     * 系统角色表数据权限保存
     *
     * @param roleDataDtoList 系统角色数据关联列表
     * @return
     */
    @ApiOperation("系统角色表数据权限保存")
    @PostMapping("/system/sysRole/dataAuthSave/{roleId}")
    @ResponseBody
    @RequiresPermissions("system:sysRole:dataAuth")
    public RespBody<String> dataAuthSave(@PathVariable("roleId") Long roleId, @RequestBody List<SysRoleDataAuthDto> roleDataDtoList) {
        if (roleId == null || roleId == 0L) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        sysRoleDataAuthService.delByRoleId(roleId);
        if (roleDataDtoList == null || roleDataDtoList.isEmpty()) {
            return RespBody.data("保存成功");
        }
        List<SysRoleDataAuthEntity> sysRoleDatas = ConvertUtil.transformObjList(roleDataDtoList, SysRoleDataAuthEntity.class);
        if (sysRoleDataAuthService.saveBatch(sysRoleDatas)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }
}
