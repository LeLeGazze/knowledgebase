package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysRegionDto;
import com.castle.fortress.admin.system.entity.SysRegionEntity;
import com.castle.fortress.admin.system.service.SysRegionService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行政区域 api 控制器
 *
 * @author castle
 * @since 2021-04-28
 */
@Api(tags="行政区域api管理控制器")
@RestController
@RequestMapping("/api/system/sysRegion")
public class ApiSysRegionController {
    @Autowired
    private SysRegionService sysRegionService;


    /**
     * 行政区域的分页展示
     * @param sysRegionDto 行政区域实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("行政区域分页展示")
    @GetMapping("/page")
    public RespBody<IPage<SysRegionDto>> pageSysRegion(SysRegionDto sysRegionDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysRegionDto> page = new Page(pageIndex, pageSize);

        IPage<SysRegionDto> pages = sysRegionService.pageSysRegion(page, sysRegionDto);
        return RespBody.data(pages);
    }

    /**
     * 行政区域保存
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @ApiOperation("行政区域保存")
    @PostMapping("/save")
    public RespBody<String> saveSysRegion(@RequestBody SysRegionDto sysRegionDto){
        if(sysRegionDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysRegionEntity sysRegionEntity = ConvertUtil.transformObj(sysRegionDto,SysRegionEntity.class);
        if(sysRegionService.save(sysRegionEntity)){
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
    @ApiOperation("行政区域编辑")
    @PostMapping("/edit")
    public RespBody<String> updateSysRegion(@RequestBody SysRegionDto sysRegionDto){
        if(sysRegionDto == null || sysRegionDto.getId() == null || sysRegionDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysRegionEntity sysRegionEntity = ConvertUtil.transformObj(sysRegionDto,SysRegionEntity.class);
        if(sysRegionService.updateById(sysRegionEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 行政区域删除
     * @param ids 行政区域id集合
     * @return
     */
    @ApiOperation("行政区域删除")
    @PostMapping("/delete")
    public RespBody<String> deleteSysRegion(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysRegionService.removeByIds(ids)) {
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
    @ApiOperation("行政区域详情")
    @GetMapping("/info")
    public RespBody<SysRegionDto> infoSysRegion(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysRegionEntity sysRegionEntity = sysRegionService.getById(id);
            SysRegionDto sysRegionDto = ConvertUtil.transformObj(sysRegionEntity,SysRegionDto.class);
        return RespBody.data(sysRegionDto);
    }

    /**
     * 行政区域的列表展示
     * @param sysRegionDto 行政区域实体类
     * @return
     */
    @ApiOperation("行政区域列表展示")
    @GetMapping("/list")
    @ResponseBody
    public RespBody<List<SysRegionDto>> listSysRegion(SysRegionDto sysRegionDto){
        List<SysRegionDto> list = sysRegionService.listSysRegion(sysRegionDto);
        return RespBody.data(list);
    }


}
