package com.castle.fortress.admin.log.rest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户操作记录日志表 api 控制器
 *
 * @author castle
 * @since 2021-03-31
 */
@Api(tags="用户操作记录日志表api管理控制器")
@RestController
@RequestMapping("/api/log/logOperation")
public class ApiLogOperationController {
    @Autowired
    private LogOperationService logOperationService;


    /**
     * 用户操作记录日志表的分页展示
     * @param logOperationDto 用户操作记录日志表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("用户操作记录日志表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<LogOperationDto>> pageLogOperation(LogOperationDto logOperationDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogOperationDto> page = new Page(pageIndex, pageSize);

        IPage<LogOperationDto> pages = logOperationService.pageLogOperation(page, logOperationDto);
        return RespBody.data(pages);
    }

    /**
     * 用户操作记录日志表保存
     * @param logOperationDto 用户操作记录日志表实体类
     * @return
     */
    @ApiOperation("用户操作记录日志表保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 用户操作记录日志表id集合
     * @return
     */
    @ApiOperation("用户操作记录日志表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteLogOperation(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<LogOperationDto> infoLogOperation(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogOperationEntity logOperationEntity = logOperationService.getById(id);
            LogOperationDto logOperationDto = ConvertUtil.transformObj(logOperationEntity,LogOperationDto.class);
        return RespBody.data(logOperationDto);
    }


}
