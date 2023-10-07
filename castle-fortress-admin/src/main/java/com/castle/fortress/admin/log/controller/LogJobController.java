package com.castle.fortress.admin.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogJobDto;
import com.castle.fortress.admin.log.entity.LogJobEntity;
import com.castle.fortress.admin.log.service.LogJobService;
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
 * 定时任务调用日志 控制器
 *
 * @author castle
 * @since 2021-04-02
 */
@Api(tags="定时任务调用日志管理控制器")
@Controller
public class LogJobController {
    @Autowired
    private LogJobService logJobService;

    /**
     * 定时任务调用日志的分页展示
     * @param logJobDto 定时任务调用日志实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("定时任务调用日志分页展示")
    @GetMapping("/log/logJob/page")
    @ResponseBody
    @RequiresPermissions("log:logJob:pageList")
    public RespBody<IPage<LogJobDto>> pageLogJob(LogJobDto logJobDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogJobDto> page = new Page(pageIndex, pageSize);
        IPage<LogJobDto> pages = logJobService.pageLogJob(page, logJobDto);

        return RespBody.data(pages);
    }

    /**
     * 定时任务调用日志的列表展示
     * @param logJobDto 定时任务调用日志实体类
     * @return
     */
    @ApiOperation("定时任务调用日志列表展示")
    @GetMapping("/log/logJob/list")
    @ResponseBody
    @RequiresPermissions("log:logJob:list")
    public RespBody<List<LogJobDto>> listLogJob(LogJobDto logJobDto){
        List<LogJobDto> list = logJobService.listLogJob(logJobDto);
        return RespBody.data(list);
    }

    /**
     * 定时任务调用日志保存
     * @param logJobDto 定时任务调用日志实体类
     * @return
     */
    @ApiOperation("定时任务调用日志保存")
    @PostMapping("/log/logJob/save")
    @ResponseBody
    @RequiresPermissions("log:logJob:save")
    public RespBody<String> saveLogJob(@RequestBody LogJobDto logJobDto){
        if(logJobDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogJobEntity logJobEntity = ConvertUtil.transformObj(logJobDto,LogJobEntity.class);
        if(logJobService.save(logJobEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 定时任务调用日志编辑
     * @param logJobDto 定时任务调用日志实体类
     * @return
     */
    @ApiOperation("定时任务调用日志编辑")
    @PostMapping("/log/logJob/edit")
    @ResponseBody
    @RequiresPermissions("log:logJob:edit")
    public RespBody<String> updateLogJob(@RequestBody LogJobDto logJobDto){
        if(logJobDto == null || logJobDto.getId() == null || logJobDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogJobEntity logJobEntity = ConvertUtil.transformObj(logJobDto,LogJobEntity.class);
        if(logJobService.updateById(logJobEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 定时任务调用日志删除
     * @param id
     * @return
     */
    @ApiOperation("定时任务调用日志删除")
    @PostMapping("/log/logJob/delete")
    @ResponseBody
    @RequiresPermissions("log:logJob:delete")
    public RespBody<String> deleteLogJob(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logJobService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 定时任务调用日志批量删除
     * @param ids
     * @return
     */
    @ApiOperation("定时任务调用日志批量删除")
    @PostMapping("/log/logJob/deleteBatch")
    @ResponseBody
    @RequiresPermissions("log:logJob:deleteBatch")
    public RespBody<String> deleteLogJobBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logJobService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 定时任务调用日志详情
     * @param id 定时任务调用日志id
     * @return
     */
    @ApiOperation("定时任务调用日志详情")
    @GetMapping("/log/logJob/info")
    @ResponseBody
    @RequiresPermissions("log:logJob:info")
    public RespBody<LogJobDto> infoLogJob(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogJobEntity logJobEntity = logJobService.getById(id);
        LogJobDto logJobDto = ConvertUtil.transformObj(logJobEntity,LogJobDto.class);

        return RespBody.data(logJobDto);
    }


}
