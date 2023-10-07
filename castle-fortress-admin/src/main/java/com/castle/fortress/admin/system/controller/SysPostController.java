package com.castle.fortress.admin.system.controller;

import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import com.castle.fortress.admin.system.service.SysPostService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统职位表 控制器
 *
 * @author castle
 * @since 2021-01-04
 */
@Api(tags="系统职位表管理控制器")
@Controller
public class SysPostController {
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 系统职位表的页面展示
     * @param sysPostDto
     * @return
     */
    @ApiOperation("系统职位表的页面展示")
    @GetMapping("/system/sysPost/list")
    @ResponseBody
    @RequiresPermissions("system:sysPost:list")
    public RespBody<List<SysPostDto>> listSysPost(SysPostDto sysPostDto){
        List<SysPostDto> postList= new ArrayList<>();
        if(sysPostDto == null || sysPostDto.getDeptId() == null){
            return RespBody.data(postList);
        }
        postList= ConvertUtil.listToTree(sysPostService.listPost(sysPostDto));
        return RespBody.data(postList);
    }

    /**
     * 系统职位表的列表展示
     * @param sysPostDto
     * @return
     */
    @ApiOperation("系统职位表的列表展示")
    @GetMapping("/system/sysPost/all")
    @ResponseBody
    @RequiresPermissions("system:sysPost:list")
    public RespBody<List<SysPostDto>> allSysPost(SysPostDto sysPostDto){
        List<SysPostDto> postList= new ArrayList<>();
        if(sysPostDto == null || sysPostDto.getDeptId() == null){
            return RespBody.data(postList);
        }
        postList= sysPostService.listPost(sysPostDto);
        return RespBody.data(postList);
    }

    /**
     * 系统职位表保存
     * @param sysPostDto 系统职位表实体类
     * @return
     */
    @ApiOperation("系统职位表保存")
    @PostMapping("/system/sysPost/save")
    @ResponseBody
    @RequiresPermissions("system:sysPost:save")
    public RespBody<String> saveSysPost(@RequestBody SysPostDto sysPostDto){
        if(sysPostDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysPostEntity sysPostEntity = ConvertUtil.transformObj(sysPostDto,SysPostEntity.class);
        //校验字段重复
        if(sysPostService.checkColumnRepeat(sysPostEntity)){
            return RespBody.fail(BizErrorCode.POST_NAME_EXIST_ERROR);
        }
        if(sysPostService.save(sysPostEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统职位表编辑
     * @param sysPostDto 系统职位表实体类
     * @return
     */
    @ApiOperation("系统职位表编辑")
    @PostMapping("/system/sysPost/edit")
    @ResponseBody
    @RequiresPermissions("system:sysPost:edit")
    public RespBody<String> updateSysPost(@RequestBody SysPostDto sysPostDto){
        if(sysPostDto == null || sysPostDto.getId() == null || sysPostDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysPostDto.getId().equals(sysPostDto.getParentId())){
            throw  new BizException(BizErrorCode.POST_SELF_ERROR);
        }
        SysPostEntity sysPostEntity = ConvertUtil.transformObj(sysPostDto,SysPostEntity.class);
        //校验字段重复
        if(sysPostService.checkColumnRepeat(sysPostEntity)){
            return RespBody.fail(BizErrorCode.DEPT_NAME_EXIST_ERROR);
        }
        if(sysPostService.updateById(sysPostEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统职位表删除
     * @param id
     * @return
     */
    @ApiOperation("系统职位表删除")
    @PostMapping("/system/sysPost/delete")
    @ResponseBody
    @RequiresPermissions("system:sysPost:delete")
    public RespBody<String> deleteSysPost(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验该数据是否叶子节点
        List<SysPostDto> children = sysPostService.children(id);
        if(children!=null && !children.isEmpty()){
            return RespBody.fail(GlobalRespCode.DEL_HAS_CHILDREN_ERROR);
        }
        //该部门是否绑定用户
        List<SysUserDto> userDtos = sysUserService.listByPostId(id);
        if(userDtos!=null && !userDtos.isEmpty()){
            return RespBody.fail(BizErrorCode.DEL_POST_EXIST_USER_ERROR);
        }
        if(sysPostService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统职位表详情
     * @param id 系统职位表id
     * @return
     */
    @ApiOperation("系统职位表详情")
    @GetMapping("/system/sysPost/info")
    @ResponseBody
    @RequiresPermissions("system:sysPost:info")
    public RespBody<SysPostDto> infoSysPost(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysPostEntity sysPostEntity = sysPostService.getById(id);
        SysPostDto sysPostDto = ConvertUtil.transformObj(sysPostEntity,SysPostDto.class);
        return RespBody.data(sysPostDto);
    }


}
