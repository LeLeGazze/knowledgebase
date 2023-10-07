package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbDataSynchronizationTaskDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbDataSynchronizationTaskService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据同步 api 控制器
 *
 * @author 
 * @since 2023-06-29
 */
@Api(tags="数据同步api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbDataSynchronizationTask")
public class ApiKbDataSynchronizationTaskController {
    @Autowired
    private KbDataSynchronizationTaskService kbDataSynchronizationTaskService;


    /**
     * 数据同步的分页展示
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="数据同步-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("数据同步分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbDataSynchronizationTaskDto>> pageKbDataSynchronizationTask(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbDataSynchronizationTaskDto> page = new Page(pageIndex, pageSize);

        IPage<KbDataSynchronizationTaskDto> pages = kbDataSynchronizationTaskService.pageKbDataSynchronizationTask(page, kbDataSynchronizationTaskDto);
        return RespBody.data(pages);
    }

    /**
     * 数据同步保存
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @return
     */
    @CastleLog(operLocation="数据同步-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("数据同步保存")
    @PostMapping("/save")
    public RespBody<String> saveKbDataSynchronizationTask(@RequestBody KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto){
        if(kbDataSynchronizationTaskDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity = ConvertUtil.transformObj(kbDataSynchronizationTaskDto,KbDataSynchronizationTaskEntity.class);
        if(kbDataSynchronizationTaskService.save(kbDataSynchronizationTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 数据同步编辑
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @return
     */
    @CastleLog(operLocation="数据同步-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("数据同步编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbDataSynchronizationTask(@RequestBody KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto){
        if(kbDataSynchronizationTaskDto == null || kbDataSynchronizationTaskDto.getId() == null || kbDataSynchronizationTaskDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity = ConvertUtil.transformObj(kbDataSynchronizationTaskDto,KbDataSynchronizationTaskEntity.class);
        if(kbDataSynchronizationTaskService.updateById(kbDataSynchronizationTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 数据同步删除
     * @param ids 数据同步id集合
     * @return
     */
    @CastleLog(operLocation="数据同步-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("数据同步删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbDataSynchronizationTask(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbDataSynchronizationTaskService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 数据同步详情
     * @param id 数据同步id
     * @return
     */
    @CastleLog(operLocation="数据同步-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("数据同步详情")
    @GetMapping("/info")
    public RespBody<KbDataSynchronizationTaskDto> infoKbDataSynchronizationTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity = kbDataSynchronizationTaskService.getById(id);
            KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto = ConvertUtil.transformObj(kbDataSynchronizationTaskEntity,KbDataSynchronizationTaskDto.class);
        return RespBody.data(kbDataSynchronizationTaskDto);
    }


}
