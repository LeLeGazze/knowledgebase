package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.service.SysDeptService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统部门表 api 控制器
 *
 * @author castle
 * @since 2021-01-04
 */
@Api(tags="系统部门表api管理控制器")
@RestController
@RequestMapping("/api/system/sysDept")
public class ApiSysDeptController {
    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 系统部门表的分页展示
     * @param sysDeptDto 系统部门表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("系统部门表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<SysDeptDto>> pageSysDept(SysDeptDto sysDeptDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysDeptDto> page = new Page(pageIndex, pageSize);

        IPage<SysDeptDto> pages = sysDeptService.pageSysDept(page, sysDeptDto);
        return RespBody.data(pages);
    }

    /**
     * 系统部门表保存
     * @param sysDeptDto 系统部门表实体类
     * @return
     */
    @ApiOperation("系统部门表保存")
    @PostMapping("/save")
    public RespBody<String> saveSysDept(@RequestBody SysDeptDto sysDeptDto){
        if(sysDeptDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysDeptEntity sysDeptEntity = ConvertUtil.transformObj(sysDeptDto,SysDeptEntity.class);
        if(sysDeptService.save(sysDeptEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统部门表编辑
     * @param sysDeptDto 系统部门表实体类
     * @return
     */
    @ApiOperation("系统部门表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateSysDept(@RequestBody SysDeptDto sysDeptDto){
        if(sysDeptDto == null || sysDeptDto.getId() == null || sysDeptDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysDeptEntity sysDeptEntity = ConvertUtil.transformObj(sysDeptDto,SysDeptEntity.class);
        if(sysDeptService.updateById(sysDeptEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统部门表删除
     * @param id 系统部门表id
     * @return
     */
    @ApiOperation("系统部门表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteSysDept(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysDeptService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统部门表详情
     * @param id 系统部门表id
     * @return
     */
    @ApiOperation("系统部门表详情")
    @GetMapping("/info")
    public RespBody<SysDeptDto> infoSysDept(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysDeptEntity sysDeptEntity = sysDeptService.getById(id);
            SysDeptDto sysDeptDto = ConvertUtil.transformObj(sysDeptEntity,SysDeptDto.class);
        return RespBody.data(sysDeptDto);
    }


}
