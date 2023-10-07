package com.castle.fortress.admin.log.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import com.castle.fortress.admin.log.service.LogSmsService;
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
 * 短信发送记录 api 控制器
 *
 * @author Mgg
 * @since 2021-12-06
 */
@Api(tags="短信发送记录api管理控制器")
@RestController
@RequestMapping("/api/log/logSms")
public class ApiLogSmsController {
    @Autowired
    private LogSmsService logSmsService;


    /**
     * 短信发送记录的分页展示
     * @param logSmsDto 短信发送记录实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("短信发送记录分页展示")
    @GetMapping("/page")
    public RespBody<IPage<LogSmsDto>> pageLogSms(LogSmsDto logSmsDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogSmsDto> page = new Page(pageIndex, pageSize);

        IPage<LogSmsDto> pages = logSmsService.pageLogSms(page, logSmsDto);
        return RespBody.data(pages);
    }

    /**
     * 短信发送记录保存
     * @param logSmsDto 短信发送记录实体类
     * @return
     */
    @ApiOperation("短信发送记录保存")
    @PostMapping("/save")
    public RespBody<String> saveLogSms(@RequestBody LogSmsDto logSmsDto){
        if(logSmsDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogSmsEntity logSmsEntity = ConvertUtil.transformObj(logSmsDto,LogSmsEntity.class);
        if(logSmsService.save(logSmsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信发送记录编辑
     * @param logSmsDto 短信发送记录实体类
     * @return
     */
    @ApiOperation("短信发送记录编辑")
    @PostMapping("/edit")
    public RespBody<String> updateLogSms(@RequestBody LogSmsDto logSmsDto){
        if(logSmsDto == null || logSmsDto.getId() == null || logSmsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogSmsEntity logSmsEntity = ConvertUtil.transformObj(logSmsDto,LogSmsEntity.class);
        if(logSmsService.updateById(logSmsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信发送记录删除
     * @param ids 短信发送记录id集合
     * @return
     */
    @ApiOperation("短信发送记录删除")
    @PostMapping("/delete")
    public RespBody<String> deleteLogSms(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logSmsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信发送记录详情
     * @param id 短信发送记录id
     * @return
     */
    @ApiOperation("短信发送记录详情")
    @GetMapping("/info")
    public RespBody<LogSmsDto> infoLogSms(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogSmsEntity logSmsEntity = logSmsService.getById(id);
            LogSmsDto logSmsDto = ConvertUtil.transformObj(logSmsEntity,LogSmsDto.class);
        return RespBody.data(logSmsDto);
    }


}
