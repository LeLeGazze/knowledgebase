package com.castle.fortress.admin.job.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.admin.job.entity.ConfigTaskEntity;
import com.castle.fortress.admin.job.service.ConfigTaskService;
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
 * 系统任务调度表 api 控制器
 *
 * @author 
 * @since 2021-03-24
 */
@Api(tags="系统任务调度表api管理控制器")
@RestController
@RequestMapping("/api/job/configTask")
public class ApiConfigTaskController {
    @Autowired
    private ConfigTaskService configTaskService;


    /**
     * 系统任务调度表的分页展示
     * @param configTaskDto 系统任务调度表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("系统任务调度表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<ConfigTaskDto>> pageConfigTask(ConfigTaskDto configTaskDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigTaskDto> page = new Page(pageIndex, pageSize);

        IPage<ConfigTaskDto> pages = configTaskService.pageConfigTask(page, configTaskDto);
        return RespBody.data(pages);
    }

    /**
     * 系统任务调度表保存
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @ApiOperation("系统任务调度表保存")
    @PostMapping("/save")
    public RespBody<String> saveConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.save(configTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统任务调度表编辑
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @ApiOperation("系统任务调度表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null || configTaskDto.getId() == null || configTaskDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.updateById(configTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统任务调度表删除
     * @param ids 系统任务调度表id集合
     * @return
     */
    @ApiOperation("系统任务调度表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteConfigTask(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configTaskService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统任务调度表详情
     * @param id 系统任务调度表id
     * @return
     */
    @ApiOperation("系统任务调度表详情")
    @GetMapping("/info")
    public RespBody<ConfigTaskDto> infoConfigTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigTaskEntity configTaskEntity = configTaskService.getById(id);
            ConfigTaskDto configTaskDto = ConvertUtil.transformObj(configTaskEntity,ConfigTaskDto.class);
        return RespBody.data(configTaskDto);
    }


}
