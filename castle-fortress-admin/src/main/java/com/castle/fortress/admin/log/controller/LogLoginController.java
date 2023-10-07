package com.castle.fortress.admin.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogLoginDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.service.LogLoginService;
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
 * 登录操作日志 控制器
 *
 * @author castle
 * @since 2021-04-01
 */
@Api(tags="登录操作日志管理控制器")
@Controller
public class LogLoginController {
    @Autowired
    private LogLoginService logLoginService;

    /**
     * 登录操作日志的分页展示
     * @param logLoginDto 登录操作日志实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("登录操作日志分页展示")
    @GetMapping("/log/logLogin/page")
    @ResponseBody
    @RequiresPermissions("log:logLogin:pageList")
    public RespBody<IPage<LogLoginDto>> pageLogLogin(LogLoginDto logLoginDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogLoginDto> page = new Page(pageIndex, pageSize);
        IPage<LogLoginDto> pages = logLoginService.pageLogLogin(page, logLoginDto);

        return RespBody.data(pages);
    }

    /**
     * 登录操作日志的列表展示
     * @param logLoginDto 登录操作日志实体类
     * @return
     */
    @ApiOperation("登录操作日志列表展示")
    @GetMapping("/log/logLogin/list")
    @ResponseBody
    @RequiresPermissions("log:logLogin:list")
    public RespBody<List<LogLoginDto>> listLogLogin(LogLoginDto logLoginDto){
        List<LogLoginDto> list = logLoginService.listLogLogin(logLoginDto);
        return RespBody.data(list);
    }

    /**
     * 登录操作日志保存
     * @param logLoginDto 登录操作日志实体类
     * @return
     */
    @ApiOperation("登录操作日志保存")
    @PostMapping("/log/logLogin/save")
    @ResponseBody
    @RequiresPermissions("log:logLogin:save")
    public RespBody<String> saveLogLogin(@RequestBody LogLoginDto logLoginDto){
        if(logLoginDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogLoginEntity logLoginEntity = ConvertUtil.transformObj(logLoginDto,LogLoginEntity.class);
        if(logLoginService.save(logLoginEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 登录操作日志编辑
     * @param logLoginDto 登录操作日志实体类
     * @return
     */
    @ApiOperation("登录操作日志编辑")
    @PostMapping("/log/logLogin/edit")
    @ResponseBody
    @RequiresPermissions("log:logLogin:edit")
    public RespBody<String> updateLogLogin(@RequestBody LogLoginDto logLoginDto){
        if(logLoginDto == null || logLoginDto.getId() == null || logLoginDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogLoginEntity logLoginEntity = ConvertUtil.transformObj(logLoginDto,LogLoginEntity.class);
        if(logLoginService.updateById(logLoginEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 登录操作日志删除
     * @param id
     * @return
     */
    @ApiOperation("登录操作日志删除")
    @PostMapping("/log/logLogin/delete")
    @ResponseBody
    @RequiresPermissions("log:logLogin:delete")
    public RespBody<String> deleteLogLogin(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logLoginService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 登录操作日志批量删除
     * @param ids
     * @return
     */
    @ApiOperation("登录操作日志批量删除")
    @PostMapping("/log/logLogin/deleteBatch")
    @ResponseBody
    @RequiresPermissions("log:logLogin:deleteBatch")
    public RespBody<String> deleteLogLoginBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logLoginService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 登录操作日志详情
     * @param id 登录操作日志id
     * @return
     */
    @ApiOperation("登录操作日志详情")
    @GetMapping("/log/logLogin/info")
    @ResponseBody
    @RequiresPermissions("log:logLogin:info")
    public RespBody<LogLoginDto> infoLogLogin(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogLoginEntity logLoginEntity = logLoginService.getById(id);
        LogLoginDto logLoginDto = ConvertUtil.transformObj(logLoginEntity,LogLoginDto.class);

        return RespBody.data(logLoginDto);
    }


}
