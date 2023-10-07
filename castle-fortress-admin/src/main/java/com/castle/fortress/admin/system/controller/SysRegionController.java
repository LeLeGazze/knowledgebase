package com.castle.fortress.admin.system.controller;

import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.RedisKeyConstants;
import com.castle.fortress.admin.system.dto.SysRegionDto;
import com.castle.fortress.admin.system.entity.SysRegionEntity;
import com.castle.fortress.admin.system.service.SysRegionService;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 行政区域 控制器
 *
 * @author castle
 * @since 2021-04-28
 */
@Api(tags="行政区域管理控制器")
@Controller
public class SysRegionController {
    @Autowired
    private SysRegionService sysRegionService;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 行政区域的分页展示
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @CastleLog(operLocation="行政区域分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("行政区域分页展示")
    @GetMapping("/system/sysRegion/page")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:pageList")
    public RespBody<List<SysRegionDto>> pageSysRegion(SysRegionDto sysRegionDto){
        return RespBody.data(sysRegionService.listSysRegion(sysRegionDto));
    }

    /**
     * 行政区域的列表展示
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @CastleLog(operLocation="行政区域列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("行政区域列表展示")
    @GetMapping("/system/sysRegion/list")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:list")
    public RespBody<List<SysRegionDto>> listSysRegion(SysRegionDto sysRegionDto){
        List<SysRegionDto> list = sysRegionService.listSysRegion(sysRegionDto);
        return RespBody.data(list);
    }

    /**
     * 行政区域保存
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @CastleLog(operLocation="行政区域保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("行政区域保存")
    @PostMapping("/system/sysRegion/save")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:save")
    public RespBody<String> saveSysRegion(@RequestBody SysRegionDto sysRegionDto){
        if(sysRegionDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysRegionDto.getParentId()==null){
            sysRegionDto.setParentId(0L);
            sysRegionDto.setTreeLevel(1);
            sysRegionDto.setLeaf(0);
        }else{
            SysRegionEntity parent= sysRegionService.getById(sysRegionDto.getParentId());
            sysRegionDto.setTreeLevel(parent.getTreeLevel()+1);
            if(sysRegionDto.getTreeLevel() == 3){
                sysRegionDto.setLeaf(1);
            }else{
                sysRegionDto.setLeaf(0);
            }
        }
        SysRegionEntity sysRegionEntity = ConvertUtil.transformObj(sysRegionDto,SysRegionEntity.class);
        if(sysRegionService.save(sysRegionEntity)){
            sysRegionService.initRegionTree();
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行政区域编辑
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @CastleLog(operLocation="行政区域编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("行政区域编辑")
    @PostMapping("/system/sysRegion/edit")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:edit")
    public RespBody<String> updateSysRegion(@RequestBody SysRegionDto sysRegionDto){
        if(sysRegionDto == null || sysRegionDto.getId() == null || sysRegionDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRegionEntity sysRegionEntity = ConvertUtil.transformObj(sysRegionDto,SysRegionEntity.class);
        if(sysRegionService.updateById(sysRegionEntity)){
            sysRegionService.initRegionTree();
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行政区域删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="行政区域删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("行政区域删除")
    @PostMapping("/system/sysRegion/delete")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:delete")
    public RespBody<String> deleteSysRegion(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysRegionService.removeById(id)) {
            sysRegionService.initRegionTree();
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 行政区域批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="行政区域批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("行政区域批量删除")
    @PostMapping("/system/sysRegion/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:deleteBatch")
    public RespBody<String> deleteSysRegionBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysRegionService.removeByIds(ids)) {
            sysRegionService.initRegionTree();
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行政区域详情
     * @param id 行政区域id
     * @return
     */
    @CastleLog(operLocation="行政区域详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("行政区域详情")
    @GetMapping("/system/sysRegion/info")
    @ResponseBody
    @RequiresPermissions("system:sysRegion:info")
    public RespBody<SysRegionDto> infoSysRegion(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysRegionEntity sysRegionEntity = sysRegionService.getByIdExtends(id);
        SysRegionDto sysRegionDto = ConvertUtil.transformObj(sysRegionEntity,SysRegionDto.class);

        return RespBody.data(sysRegionDto);
    }

    /**
     * 行政区域树形展示
     * @return
     */
    @CastleLog(operLocation="行政区域展示",operType= OperationTypeEnum.QUERY)
    @ApiOperation("行政区域树形展示")
    @GetMapping("/system/sysRegion/tree")
    @ResponseBody
    public RespBody<List<SysRegionDto>> treeRegion(){
        Object obj = redisUtils.get(RedisKeyConstants.REGION_TREE);
        List<SysRegionDto> list = new ArrayList<>();
        if(obj!=null){
            list= (List<SysRegionDto>)obj;
        }else{
            sysRegionService.initRegionTree();
        }
        return RespBody.data(list);
    }
}
