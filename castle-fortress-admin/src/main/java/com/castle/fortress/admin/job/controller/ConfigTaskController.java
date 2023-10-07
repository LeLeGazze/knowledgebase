package com.castle.fortress.admin.job.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.job.dto.ConfigTaskDto;
import com.castle.fortress.admin.job.entity.ConfigTaskEntity;
import com.castle.fortress.admin.job.service.ConfigTaskService;
import com.castle.fortress.admin.utils.JobUtils;
import com.castle.fortress.admin.utils.SpringUtils;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.JobStatusEnum;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 系统任务调度表 控制器
 *
 * @author 
 * @since 2021-03-24
 */
@Api(tags="系统任务调度表管理控制器")
@Controller
public class ConfigTaskController {
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ConfigTaskService configTaskService;

    /**
     * 系统任务调度表的分页展示
     * @param configTaskDto 系统任务调度表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="任务调度分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统任务调度表分页展示")
    @GetMapping("/job/configTask/page")
    @ResponseBody
    @RequiresPermissions("job:configTask:pageList")
    public RespBody<IPage<ConfigTaskDto>> pageConfigTask(ConfigTaskDto configTaskDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigTaskDto> page = new Page(pageIndex, pageSize);
        IPage<ConfigTaskDto> pages = configTaskService.pageConfigTaskExtends(page, configTaskDto);

        return RespBody.data(pages);
    }

    /**
     * 系统任务调度表的列表展示
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @CastleLog(operLocation="任务调度列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统任务调度表列表展示")
    @GetMapping("/job/configTask/list")
    @ResponseBody
    @RequiresPermissions("job:configTask:list")
    public RespBody<List<ConfigTaskDto>> listConfigTask(ConfigTaskDto configTaskDto){
        List<ConfigTaskDto> list = configTaskService.listConfigTask(configTaskDto);
        return RespBody.data(list);
    }

    /**
     * 系统任务调度表保存
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @CastleLog(operLocation="任务调度保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("系统任务调度表保存")
    @PostMapping("/job/configTask/save")
    @ResponseBody
    @RequiresPermissions("job:configTask:save")
    public RespBody<String> saveConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验taskName是否重复
        ConfigTaskDto tempDto = new ConfigTaskDto();
        tempDto.setTaskName(configTaskDto.getTaskName());
        List<ConfigTaskDto> list = configTaskService.listConfigTask(tempDto);
        if(list!=null && !list.isEmpty()){
            return RespBody.fail(BizErrorCode.JOB_TASK_NAME_EXIST_ERROR);
        }
        configTaskDto.setStatus(JobStatusEnum.UN_START.getCode());
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
    @CastleLog(operLocation="任务调度编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("系统任务调度表编辑")
    @PostMapping("/job/configTask/edit")
    @ResponseBody
    @RequiresPermissions("job:configTask:edit")
    public RespBody<String> updateConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null || configTaskDto.getId() == null || configTaskDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.updateById(configTaskEntity)){
            if(!JobStatusEnum.UN_START.getCode().equals(configTaskEntity.getStatus())){
                JobUtils.createJob(scheduler,configTaskDto);
            }
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 启动任务
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @CastleLog(operLocation="任务调度启动",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("启动任务")
    @PostMapping("/job/configTask/trigger")
    @ResponseBody
    @RequiresPermissions("job:configTask:trigger")
    public RespBody<String> triggerConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null || configTaskDto.getId() == null || configTaskDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        configTaskDto.setStatus(JobStatusEnum.RUN.getCode());
        ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.updateById(configTaskEntity)){
            JobUtils.createJob(scheduler,configTaskDto);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 停止任务
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @CastleLog(operLocation="任务调度停止",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("停止任务")
    @PostMapping("/job/configTask/pause")
    @ResponseBody
    @RequiresPermissions("job:configTask:pause")
    public RespBody<String> pauseConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null || configTaskDto.getId() == null || configTaskDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        configTaskDto.setStatus(JobStatusEnum.PAUSE.getCode());
        ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.updateById(configTaskEntity)){
            JobUtils.pauseJob(scheduler,configTaskDto.getId());
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 恢复任务
     * @param configTaskDto 系统任务调度表实体类
     * @return
     */
    @CastleLog(operLocation="任务调度恢复",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("恢复任务")
    @PostMapping("/job/configTask/resume")
    @ResponseBody
    @RequiresPermissions("job:configTask:resume")
    public RespBody<String> resumeConfigTask(@RequestBody ConfigTaskDto configTaskDto){
        if(configTaskDto == null || configTaskDto.getId() == null || configTaskDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        configTaskDto.setStatus(JobStatusEnum.RUN.getCode());
        ConfigTaskEntity configTaskEntity = ConvertUtil.transformObj(configTaskDto,ConfigTaskEntity.class);
        if(configTaskService.updateById(configTaskEntity)){
            JobUtils.resumeJob(scheduler,configTaskDto.getId());
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统任务调度表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="任务调度删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("系统任务调度表删除")
    @PostMapping("/job/configTask/delete")
    @ResponseBody
    @RequiresPermissions("job:configTask:delete")
    public RespBody<String> deleteConfigTask(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configTaskService.removeById(id)) {
            JobUtils.deleteJob(scheduler,id);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 系统任务调度表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="任务调度批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("系统任务调度表批量删除")
    @PostMapping("/job/configTask/deleteBatch")
    @ResponseBody
    @RequiresPermissions("job:configTask:deleteBatch")
    public RespBody<String> deleteConfigTaskBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configTaskService.removeByIds(ids)) {
            for(Long id:ids){
                JobUtils.deleteJob(scheduler,id);
            }
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
    @CastleLog(operLocation="任务调度详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统任务调度表详情")
    @GetMapping("/job/configTask/info")
    @ResponseBody
    @RequiresPermissions("job:configTask:info")
    public RespBody<ConfigTaskDto> infoConfigTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigTaskDto configTaskDto =  configTaskService.getByIdExtends(id);

        return RespBody.data(configTaskDto);
    }

    /**
     * 调用系统任务调度表一次
     * @param id id
     * @return
     */
    @CastleLog(operLocation="任务调度单次",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("调用系统任务调度表一次")
    @GetMapping("/job/configTask/run")
    @ResponseBody
    @RequiresPermissions("job:configTask:run")
    public RespBody<String> runConfigTask(@RequestParam Long id) {
        if(id == null || id.equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigTaskEntity configTaskEntity = configTaskService.getById(id);
        if(configTaskEntity==null){
            throw new BizException(GlobalRespCode.DB_DATA_ERROR);
        }
        try {
            if(StrUtil.isNotEmpty(configTaskEntity.getTaskName())){
                Object target = SpringUtils.getBean(configTaskEntity.getTaskName());
                if(target!=null){
                    Method method = target.getClass().getDeclaredMethod("runTask", String.class);
                    method.invoke(target, configTaskEntity.getParams());
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
        return RespBody.data("调用成功");
    }


}
