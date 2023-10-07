package com.castle.fortress.admin.log.rest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录操作日志 api 控制器
 *
 * @author castle
 * @since 2021-04-01
 */
@Api(tags="登录操作日志api管理控制器")
@RestController
@RequestMapping("/api/log/logLogin")
public class ApiLogLoginController {
    @Autowired
    private LogLoginService logLoginService;


    /**
     * 登录操作日志的分页展示
     * @param logLoginDto 登录操作日志实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("登录操作日志分页展示")
    @GetMapping("/page")
    public RespBody<IPage<LogLoginDto>> pageLogLogin(LogLoginDto logLoginDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogLoginDto> page = new Page(pageIndex, pageSize);

        IPage<LogLoginDto> pages = logLoginService.pageLogLogin(page, logLoginDto);
        return RespBody.data(pages);
    }

    /**
     * 登录操作日志保存
     * @param logLoginDto 登录操作日志实体类
     * @return
     */
    @ApiOperation("登录操作日志保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 登录操作日志id集合
     * @return
     */
    @ApiOperation("登录操作日志删除")
    @PostMapping("/delete")
    public RespBody<String> deleteLogLogin(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<LogLoginDto> infoLogLogin(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogLoginEntity logLoginEntity = logLoginService.getById(id);
            LogLoginDto logLoginDto = ConvertUtil.transformObj(logLoginEntity,LogLoginDto.class);
        return RespBody.data(logLoginDto);
    }


}
