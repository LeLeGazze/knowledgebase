package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import com.castle.fortress.admin.system.service.SysPostService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统职位表 api 控制器
 *
 * @author castle
 * @since 2021-01-04
 */
@Api(tags="系统职位表api管理控制器")
@RestController
@RequestMapping("/api/system/sysPost")
public class ApiSysPostController {
    @Autowired
    private SysPostService sysPostService;


    /**
     * 系统职位表的分页展示
     * @param sysPostDto 系统职位表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("系统职位表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<SysPostDto>> pageSysPost(SysPostDto sysPostDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysPostDto> page = new Page(pageIndex, pageSize);

        IPage<SysPostDto> pages = sysPostService.pageSysPost(page, sysPostDto);
        return RespBody.data(pages);
    }

    /**
     * 系统职位表保存
     * @param sysPostDto 系统职位表实体类
     * @return
     */
    @ApiOperation("系统职位表保存")
    @PostMapping("/save")
    public RespBody<String> saveSysPost(@RequestBody SysPostDto sysPostDto){
        if(sysPostDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysPostEntity sysPostEntity = ConvertUtil.transformObj(sysPostDto,SysPostEntity.class);
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
    @PostMapping("/edit")
    public RespBody<String> updateSysPost(@RequestBody SysPostDto sysPostDto){
        if(sysPostDto == null || sysPostDto.getId() == null || sysPostDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysPostEntity sysPostEntity = ConvertUtil.transformObj(sysPostDto,SysPostEntity.class);
        if(sysPostService.updateById(sysPostEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统职位表删除
     * @param id 系统职位表id
     * @return
     */
    @ApiOperation("系统职位表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteSysPost(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
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
    @GetMapping("/info")
    public RespBody<SysPostDto> infoSysPost(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysPostEntity sysPostEntity = sysPostService.getById(id);
            SysPostDto sysPostDto = ConvertUtil.transformObj(sysPostEntity,SysPostDto.class);
        return RespBody.data(sysPostDto);
    }


}
