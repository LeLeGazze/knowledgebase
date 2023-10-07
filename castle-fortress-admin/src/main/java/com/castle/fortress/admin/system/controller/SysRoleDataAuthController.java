package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysRoleDataAuthDto;
import com.castle.fortress.admin.system.entity.SysRoleDataAuthEntity;
import com.castle.fortress.admin.system.service.SysRoleDataAuthService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 角色数据权限表-细化到部门 控制器
 *
 * @author castle
 * @since 2021-01-04
 */
@Api(tags="角色数据权限表-细化到部门管理控制器")
@Controller
public class SysRoleDataAuthController {
    @Autowired
    private SysRoleDataAuthService sysRoleDataAuthService;

    /**
     * 角色数据权限表-细化到部门的分页展示
     * @param sysRoleDataAuthDto 角色数据权限表-细化到部门实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("角色数据权限表-细化到部门分页展示")
    @GetMapping("/system/sysRoleDataAuth/page")
    @ResponseBody
    @RequiresPermissions("system:sysRoleDataAuth:pageList")
    public RespBody<IPage<SysRoleDataAuthDto>> pageSysRoleDataAuth(SysRoleDataAuthDto sysRoleDataAuthDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysRoleDataAuthDto> page = new Page(pageIndex, pageSize);

        IPage<SysRoleDataAuthDto> pages = sysRoleDataAuthService.pageSysRoleDataAuth(page, sysRoleDataAuthDto);
        return RespBody.data(pages);
    }

    /**
     * 角色数据权限表-细化到部门保存
     * @param sysRoleDataAuthDto 角色数据权限表-细化到部门实体类
     * @return
     */
    @ApiOperation("角色数据权限表-细化到部门保存")
    @PostMapping("/system/sysRoleDataAuth/save")
    @ResponseBody
    @RequiresPermissions("system:sysRoleDataAuth:save")
    public RespBody<String> saveSysRoleDataAuth(@RequestBody SysRoleDataAuthDto sysRoleDataAuthDto){
        if(sysRoleDataAuthDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRoleDataAuthEntity sysRoleDataAuthEntity = ConvertUtil.transformObj(sysRoleDataAuthDto,SysRoleDataAuthEntity.class);
        if(sysRoleDataAuthService.save(sysRoleDataAuthEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 角色数据权限表-细化到部门编辑
     * @param sysRoleDataAuthDto 角色数据权限表-细化到部门实体类
     * @return
     */
    @ApiOperation("角色数据权限表-细化到部门编辑")
    @PostMapping("/system/sysRoleDataAuth/edit")
    @ResponseBody
    @RequiresPermissions("system:sysRoleDataAuth:edit")
    public RespBody<String> updateSysRoleDataAuth(@RequestBody SysRoleDataAuthDto sysRoleDataAuthDto){
        if(sysRoleDataAuthDto == null || sysRoleDataAuthDto.getId() == null || sysRoleDataAuthDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRoleDataAuthEntity sysRoleDataAuthEntity = ConvertUtil.transformObj(sysRoleDataAuthDto,SysRoleDataAuthEntity.class);
        if(sysRoleDataAuthService.updateById(sysRoleDataAuthEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 角色数据权限表-细化到部门删除
     * @param ids 角色数据权限表-细化到部门id集合
     * @return
     */
    @ApiOperation("角色数据权限表-细化到部门删除")
    @PostMapping("/system/sysRoleDataAuth/delete")
    @ResponseBody
    @RequiresPermissions("system:sysRoleDataAuth:delete")
    public RespBody<String> deleteSysRoleDataAuth(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysRoleDataAuthService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 角色数据权限表-细化到部门详情
     * @param id 角色数据权限表-细化到部门id
     * @return
     */
    @ApiOperation("角色数据权限表-细化到部门详情")
    @GetMapping("/system/sysRoleDataAuth/info")
    @ResponseBody
    @RequiresPermissions("system:sysRoleDataAuth:info")
    public RespBody<SysRoleDataAuthDto> infoSysRoleDataAuth(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRoleDataAuthEntity sysRoleDataAuthEntity = sysRoleDataAuthService.getById(id);
        SysRoleDataAuthDto sysRoleDataAuthDto = ConvertUtil.transformObj(sysRoleDataAuthEntity,SysRoleDataAuthDto.class);
        return RespBody.data(sysRoleDataAuthDto);
    }


}
