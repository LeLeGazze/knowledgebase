package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbBaseLabelTaskDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbBaseLabelTaskService;
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
 * 标签删除任务表 api 控制器
 *
 * @author 
 * @since 2023-06-07
 */
@Api(tags="标签删除任务表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBaseLabelTask")
public class ApiKbBaseLabelTaskController {
    @Autowired
    private KbBaseLabelTaskService kbBaseLabelTaskService;


    /**
     * 标签删除任务表的分页展示
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签删除任务表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签删除任务表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbBaseLabelTaskDto>> pageKbBaseLabelTask(KbBaseLabelTaskDto kbBaseLabelTaskDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBaseLabelTaskDto> page = new Page(pageIndex, pageSize);

        IPage<KbBaseLabelTaskDto> pages = kbBaseLabelTaskService.pageKbBaseLabelTask(page, kbBaseLabelTaskDto);
        return RespBody.data(pages);
    }

    /**
     * 标签删除任务表保存
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @return
     */
    @CastleLog(operLocation="标签删除任务表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签删除任务表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBaseLabelTask(@RequestBody KbBaseLabelTaskDto kbBaseLabelTaskDto){
        if(kbBaseLabelTaskDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBaseLabelTaskEntity kbBaseLabelTaskEntity = ConvertUtil.transformObj(kbBaseLabelTaskDto,KbBaseLabelTaskEntity.class);
        if(kbBaseLabelTaskService.save(kbBaseLabelTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签删除任务表编辑
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @return
     */
    @CastleLog(operLocation="标签删除任务表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签删除任务表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBaseLabelTask(@RequestBody KbBaseLabelTaskDto kbBaseLabelTaskDto){
        if(kbBaseLabelTaskDto == null || kbBaseLabelTaskDto.getId() == null || kbBaseLabelTaskDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBaseLabelTaskEntity kbBaseLabelTaskEntity = ConvertUtil.transformObj(kbBaseLabelTaskDto,KbBaseLabelTaskEntity.class);
        if(kbBaseLabelTaskService.updateById(kbBaseLabelTaskEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签删除任务表删除
     * @param ids 标签删除任务表id集合
     * @return
     */
    @CastleLog(operLocation="标签删除任务表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签删除任务表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBaseLabelTask(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBaseLabelTaskService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签删除任务表详情
     * @param id 标签删除任务表id
     * @return
     */
    @CastleLog(operLocation="标签删除任务表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签删除任务表详情")
    @GetMapping("/info")
    public RespBody<KbBaseLabelTaskDto> infoKbBaseLabelTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBaseLabelTaskEntity kbBaseLabelTaskEntity = kbBaseLabelTaskService.getById(id);
            KbBaseLabelTaskDto kbBaseLabelTaskDto = ConvertUtil.transformObj(kbBaseLabelTaskEntity,KbBaseLabelTaskDto.class);
        return RespBody.data(kbBaseLabelTaskDto);
    }


}
