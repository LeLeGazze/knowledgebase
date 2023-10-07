package com.castle.fortress.admin.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogOperationDto;
import com.castle.fortress.admin.log.entity.LogOperationEntity;
import com.castle.fortress.admin.log.service.LogOperationService;
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

import java.util.List;

/**
 * 用户操作记录日志表 控制器
 *
 * @author castle
 * @since 2021-03-31
 */
@Api(tags="用户操作记录日志表管理控制器")
@Controller
public class LogOperationController {
    @Autowired
    private LogOperationService logOperationService;

    /**
     * 用户操作记录日志表的分页展示
     * @param logOperationDto 用户操作记录日志表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("用户操作记录日志表分页展示")
    @GetMapping("/log/logOperation/page")
    @ResponseBody
    @RequiresPermissions("log:logOperation:pageList")
    public RespBody<IPage<LogOperationDto>> pageLogOperation(LogOperationDto logOperationDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogOperationDto> page = new Page(pageIndex, pageSize);
        IPage<LogOperationDto> pages = logOperationService.pageLogOperation(page, logOperationDto);

        return RespBody.data(pages);
    }

    /**
     * 用户操作记录日志表的列表展示
     * @param logOperationDto 用户操作记录日志表实体类
     * @return
     */
    @ApiOperation("用户操作记录日志表列表展示")
    @GetMapping("/log/logOperation/list")
    @ResponseBody
    @RequiresPermissions("log:logOperation:list")
    public RespBody<List<LogOperationDto>> listLogOperation(LogOperationDto logOperationDto){
        List<LogOperationDto> list = logOperationService.listLogOperation(logOperationDto);
        return RespBody.data(list);
    }

    /**
     * 用户操作记录日志表保存
     * @param logOperationDto 用户操作记录日志表实体类
     * @return
     */
    @ApiOperation("用户操作记录日志表保存")
    @PostMapping("/log/logOperation/save")
    @ResponseBody
    @RequiresPermissions("log:logOperation:save")
    public RespBody<String> saveLogOperation(@RequestBody LogOperationDto logOperationDto){
        if(logOperationDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogOperationEntity logOperationEntity = ConvertUtil.transformObj(logOperationDto,LogOperationEntity.class);
        if(logOperationService.save(logOperationEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户操作记录日志表编辑
     * @param logOperationDto 用户操作记录日志表实体类
     * @return
     */
    @ApiOperation("用户操作记录日志表编辑")
    @PostMapping("/log/logOperation/edit")
    @ResponseBody
    @RequiresPermissions("log:logOperation:edit")
    public RespBody<String> updateLogOperation(@RequestBody LogOperationDto logOperationDto){
        if(logOperationDto == null || logOperationDto.getId() == null || logOperationDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogOperationEntity logOperationEntity = ConvertUtil.transformObj(logOperationDto,LogOperationEntity.class);
        if(logOperationService.updateById(logOperationEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户操作记录日志表删除
     * @param id
     * @return
     */
    @ApiOperation("用户操作记录日志表删除")
    @PostMapping("/log/logOperation/delete")
    @ResponseBody
    @RequiresPermissions("log:logOperation:delete")
    public RespBody<String> deleteLogOperation(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logOperationService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 用户操作记录日志表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("用户操作记录日志表批量删除")
    @PostMapping("/log/logOperation/deleteBatch")
    @ResponseBody
    @RequiresPermissions("log:logOperation:deleteBatch")
    public RespBody<String> deleteLogOperationBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logOperationService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户操作记录日志表详情
     * @param id 用户操作记录日志表id
     * @return
     */
    @ApiOperation("用户操作记录日志表详情")
    @GetMapping("/log/logOperation/info")
    @ResponseBody
    @RequiresPermissions("log:logOperation:info")
    public RespBody<LogOperationDto> infoLogOperation(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogOperationEntity logOperationEntity = logOperationService.getById(id);
        LogOperationDto logOperationDto = ConvertUtil.transformObj(logOperationEntity,LogOperationDto.class);

        return RespBody.data(logOperationDto);
    }


}
