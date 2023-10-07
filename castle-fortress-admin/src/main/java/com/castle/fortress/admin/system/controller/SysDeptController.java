package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysDeptService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * 系统部门表 控制器
 *
 * @author castle
 * @since 2021-01-04
 */
@Api(tags = "系统部门表管理控制器")
@Controller
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 系统部门表的页面展示
     *
     * @param sysDeptDto
     * @return
     */
    @ApiOperation("系统部门表列表展示")
    @GetMapping("/system/sysDept/list")
    @ResponseBody
//    @RequiresPermissions(value = {"system:sysDept:list","oa:attendanceRecordDept:pageList"},logical= Logical.OR)
    public RespBody<List<SysDeptDto>> listSysDept(SysDeptDto sysDeptDto) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非管理员
        if (!sysUser.getIsAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(null);
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        List<SysDeptDto> sysDeptDtos = sysDeptService.authorityAllDept(roleIds, sysDeptDto.getParentId(), sysDeptDto.getName());
        if (sysUser.getDeptId() != null) {
            HashSet<SysDeptDto> dtoHashSet = new HashSet<>(sysDeptDtos);
            dtoHashSet.addAll(sysDeptService.myselfBelowDept(sysUser.getDeptId()));
            sysDeptDtos= new ArrayList<>(dtoHashSet);
        }
        if (StrUtil.isNotEmpty(sysDeptDto.getName())) {
            return RespBody.data(sysDeptDtos);
        }
        List<SysDeptDto> deptList = ConvertUtil.listToTree(sysDeptDtos);
        return RespBody.data(deptList);
    }

    /**
     * 系统部门表的页面展示
     *
     * @param sysDeptDto
     * @return
     */
    @ApiOperation("系统部门表列表展示")
    @GetMapping("/system/sysDept/all")
    @ResponseBody
    @RequiresPermissions("system:sysDept:list")
    public RespBody<List<SysDeptDto>> allSysDept(SysDeptDto sysDeptDto) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非管理员
        if (!sysUser.getIsAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(null);
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        return RespBody.data(sysDeptService.authorityAllDept(roleIds, sysDeptDto.getParentId(), sysDeptDto.getName()));
    }

    /**
     * 系统部门表保存
     *
     * @param sysDeptDto 系统部门表实体类
     * @return
     */
    @ApiOperation("系统部门表保存")
    @PostMapping("/system/sysDept/save")
    @ResponseBody
    @RequiresPermissions("system:sysDept:save")
    public RespBody<String> saveSysDept(@RequestBody SysDeptDto sysDeptDto) {
        if (sysDeptDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDeptEntity sysDeptEntity = ConvertUtil.transformObj(sysDeptDto, SysDeptEntity.class);
        //校验字段重复
        if (sysDeptService.checkColumnRepeat(sysDeptEntity)) {
            return RespBody.fail(BizErrorCode.DEPT_NAME_EXIST_ERROR);
        }
        if (sysDeptEntity.getParentId() != null) {
            SysDeptEntity parent = sysDeptService.getById(sysDeptEntity.getParentId());
            if (StrUtil.isEmpty(parent.getParents())) {
                sysDeptEntity.setParents(parent.getId() + "");
            } else {
                sysDeptEntity.setParents(parent.getParents() + "," + parent.getId());
            }
        } else {
            sysDeptEntity.setParents(null);
        }
        if (sysDeptService.save(sysDeptEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统部门表编辑
     *
     * @param sysDeptDto 系统部门表实体类
     * @return
     */
    @ApiOperation("系统部门表编辑")
    @PostMapping("/system/sysDept/edit")
    @ResponseBody
    @RequiresPermissions("system:sysDept:edit")
    public RespBody<String> updateSysDept(@RequestBody SysDeptDto sysDeptDto) {
        if (sysDeptDto == null || sysDeptDto.getId() == null || sysDeptDto.getId().equals(0)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDeptEntity sysDeptEntity = ConvertUtil.transformObj(sysDeptDto, SysDeptEntity.class);
        //校验字段重复
        if (sysDeptService.checkColumnRepeat(sysDeptEntity)) {
            return RespBody.fail(BizErrorCode.DEPT_NAME_EXIST_ERROR);
        }
        if (sysDeptEntity.getParentId() != null) {
            SysDeptEntity parent = sysDeptService.getById(sysDeptEntity.getParentId());
            if (StrUtil.isEmpty(parent.getParents())) {
                sysDeptEntity.setParents(parent.getId() + "");
            } else {
                sysDeptEntity.setParents(parent.getParents() + "," + parent.getId());
            }
        } else {
            sysDeptEntity.setParents(null);
        }
        if (sysDeptService.updateById(sysDeptEntity)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 系统部门表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("系统部门表删除")
    @PostMapping("/system/sysDept/delete")
    @ResponseBody
    @RequiresPermissions("system:sysDept:delete")
    public RespBody<String> deleteSysDept(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验该数据是否叶子节点
        List<SysDeptDto> children = sysDeptService.children(id);
        if (children != null && !children.isEmpty()) {
            return RespBody.fail(GlobalRespCode.DEL_HAS_CHILDREN_ERROR);
        }
        //该部门是否绑定用户
        List<SysUserDto> userDtos = sysUserService.listByDeptId(id);
        if (userDtos != null && !userDtos.isEmpty()) {
            return RespBody.fail(BizErrorCode.DEL_DEPT_EXIST_USER_ERROR);
        }
        if (sysDeptService.removeById(id)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统部门表详情
     *
     * @param id 系统部门表id
     * @return
     */
    @ApiOperation("系统部门表详情")
    @GetMapping("/system/sysDept/info")
    @ResponseBody
    @RequiresPermissions("system:sysDept:info")
    public RespBody<SysDeptDto> infoSysDept(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysDeptEntity sysDeptEntity = sysDeptService.getById(id);
        SysDeptDto sysDeptDto = ConvertUtil.transformObj(sysDeptEntity, SysDeptDto.class);
        return RespBody.data(sysDeptDto);
    }


    /**
     * 组织架构及人员组件列表展示
     *
     * @param id       组织架构id,空的时候查顶级
     * @param authFlag 00不限制权限 01 限制权限（默认）
     * @return
     */
    @ApiOperation("组织架构及人员组件列表展示")
    @GetMapping("/system/sysDept/componentList")
    @ResponseBody
    public RespBody<List<Map<String, String>>> componentList(@RequestParam(required = false) Long id, @RequestParam(required = false) String authFlag) {
        if (StrUtil.isEmpty(authFlag)) {
            authFlag = "01";
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        if ("01".equals(authFlag)) {
            //非管理员
            if (!sysUser.getIsAdmin()) {
                List<SysRole> roleList = sysUser.getRoles();
                if (roleList == null || roleList.isEmpty()) {
                    return RespBody.data(null);
                }
                if (roleList != null && roleList.size() > 0) {
                    roleIds = new ArrayList<>();
                    for (SysRole role : roleList) {
                        roleIds.add(role.getId());
                    }
                }
            }
        }
        return RespBody.data(sysDeptService.authorityComponentList(roleIds, id));
    }

    /**
     * 组织架构及人员组件列表查询展示
     *
     * @param map type：类型 ；name：名称 ; authFlag 00不限制权限 01 限制权限（默认）
     * @return
     */
    @ApiOperation("组织架构及人员组件列表查询展示")
    @GetMapping("/system/sysDept/searchForCom")
    @ResponseBody
    public RespBody<List<Map<String, String>>> searchForComponent(@RequestParam Map<String, String> map) {
        if (CommonUtil.verifyParamNull(map, map.get("type"), map.get("name"))) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (StrUtil.isEmpty(map.get("authFlag"))) {
            map.put("authFlag", "01");
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        if ("01".equals(map.get("authFlag"))) {
            //非管理员
            if (!sysUser.getIsAdmin()) {
                List<SysRole> roleList = sysUser.getRoles();
                if (roleList == null || roleList.isEmpty()) {
                    return RespBody.data(null);
                }
                if (roleList != null && roleList.size() > 0) {
                    roleIds = new ArrayList<>();
                    for (SysRole role : roleList) {
                        roleIds.add(role.getId());
                    }
                }
            }
        } else {
            if (!sysUser.getIsAdmin()) {
                sysUser.setIsAdmin(true);
            }
        }
        return RespBody.data(sysDeptService.authoritySearchForComponent(roleIds, map, sysUser));
    }

    /**
     * 组织架构及人员组件列表id转name展示
     *
     * @param map {dept: [],user: []}
     * @return
     */
    @ApiOperation("组织架构及人员组件列表id转name展示")
    @PostMapping("/system/sysDept/nameForCom")
    @ResponseBody
    public RespBody<List<Map<String, String>>> nameForComponent(@RequestBody Map<String, Object> map) {
        if (CommonUtil.verifyParamNull(map, map.get("dept"), map.get("user"))) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
//        List<Long> roleIds=null;
//        //非管理员
//        if(!sysUser.getIsAdmin()){
//            List<SysRole> roleList=sysUser.getRoles();
//            if(roleList == null || roleList.isEmpty()){
//                return RespBody.data(null);
//            }
//            if(roleList!=null&& roleList.size()>0){
//                roleIds=new ArrayList<>();
//                for(SysRole role:roleList){
//                    roleIds.add(role.getId());
//                }
//            }
//        }
        return RespBody.data(sysDeptService.nameForComponent(map));
    }


}
