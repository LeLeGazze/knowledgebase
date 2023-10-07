package com.castle.fortress.admin.log.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogOpenapiDto;
import com.castle.fortress.admin.log.entity.LogOpenapiEntity;
import com.castle.fortress.admin.log.service.LogOpenapiService;
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
 * 对外开放api调用日志 api 控制器
 *
 * @author castle
 * @since 2021-04-01
 */
@Api(tags="对外开放api调用日志api管理控制器")
@RestController
@RequestMapping("/api/log/logOpenapi")
public class ApiLogOpenapiController {
    @Autowired
    private LogOpenapiService logOpenapiService;


    /**
     * 对外开放api调用日志的分页展示
     * @param logOpenapiDto 对外开放api调用日志实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("对外开放api调用日志分页展示")
    @GetMapping("/page")
    public RespBody<IPage<LogOpenapiDto>> pageLogOpenapi(LogOpenapiDto logOpenapiDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogOpenapiDto> page = new Page(pageIndex, pageSize);

        IPage<LogOpenapiDto> pages = logOpenapiService.pageLogOpenapi(page, logOpenapiDto);
        return RespBody.data(pages);
    }

    /**
     * 对外开放api调用日志保存
     * @param logOpenapiDto 对外开放api调用日志实体类
     * @return
     */
    @ApiOperation("对外开放api调用日志保存")
    @PostMapping("/save")
    public RespBody<String> saveLogOpenapi(@RequestBody LogOpenapiDto logOpenapiDto){
        if(logOpenapiDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogOpenapiEntity logOpenapiEntity = ConvertUtil.transformObj(logOpenapiDto,LogOpenapiEntity.class);
        if(logOpenapiService.save(logOpenapiEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放api调用日志编辑
     * @param logOpenapiDto 对外开放api调用日志实体类
     * @return
     */
    @ApiOperation("对外开放api调用日志编辑")
    @PostMapping("/edit")
    public RespBody<String> updateLogOpenapi(@RequestBody LogOpenapiDto logOpenapiDto){
        if(logOpenapiDto == null || logOpenapiDto.getId() == null || logOpenapiDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogOpenapiEntity logOpenapiEntity = ConvertUtil.transformObj(logOpenapiDto,LogOpenapiEntity.class);
        if(logOpenapiService.updateById(logOpenapiEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放api调用日志删除
     * @param ids 对外开放api调用日志id集合
     * @return
     */
    @ApiOperation("对外开放api调用日志删除")
    @PostMapping("/delete")
    public RespBody<String> deleteLogOpenapi(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logOpenapiService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放api调用日志详情
     * @param id 对外开放api调用日志id
     * @return
     */
    @ApiOperation("对外开放api调用日志详情")
    @GetMapping("/info")
    public RespBody<LogOpenapiDto> infoLogOpenapi(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            LogOpenapiEntity logOpenapiEntity = logOpenapiService.getById(id);
            LogOpenapiDto logOpenapiDto = ConvertUtil.transformObj(logOpenapiEntity,LogOpenapiDto.class);
        return RespBody.data(logOpenapiDto);
    }


}
