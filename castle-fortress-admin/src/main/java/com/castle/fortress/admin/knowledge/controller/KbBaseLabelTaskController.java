package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBaseLabelTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbBaseLabelTaskDto;
import com.castle.fortress.admin.knowledge.service.KbBaseLabelTaskService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.castle.fortress.admin.utils.ExcelUtils;
import javax.servlet.http.HttpServletResponse;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import java.util.List;

/**
 * 标签删除任务表 控制器
 *
 * @author 
 * @since 2023-06-07
 */
@Api(tags="标签删除任务表管理控制器")
@Controller
public class KbBaseLabelTaskController {
    @Autowired
    private KbBaseLabelTaskService kbBaseLabelTaskService;

    /**
     * 标签删除任务表的分页展示
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签删除任务表分页展示")
    @GetMapping("/knowledge/kbBaseLabelTask/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:pageList")
    public RespBody<IPage<KbBaseLabelTaskDto>> pageKbBaseLabelTask(KbBaseLabelTaskDto kbBaseLabelTaskDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBaseLabelTaskDto> page = new Page(pageIndex, pageSize);
        IPage<KbBaseLabelTaskDto> pages = kbBaseLabelTaskService.pageKbBaseLabelTask(page, kbBaseLabelTaskDto);

        return RespBody.data(pages);
    }

    /**
     * 标签删除任务表的列表展示
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @return
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签删除任务表列表展示")
    @GetMapping("/knowledge/kbBaseLabelTask/list")
    @ResponseBody
    public RespBody<List<KbBaseLabelTaskDto>> listKbBaseLabelTask(KbBaseLabelTaskDto kbBaseLabelTaskDto){
        List<KbBaseLabelTaskDto> list = kbBaseLabelTaskService.listKbBaseLabelTask(kbBaseLabelTaskDto);
        return RespBody.data(list);
    }

    /**
     * 标签删除任务表保存
     * @param kbBaseLabelTaskDto 标签删除任务表实体类
     * @return
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签删除任务表保存")
    @PostMapping("/knowledge/kbBaseLabelTask/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:save")
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
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签删除任务表编辑")
    @PostMapping("/knowledge/kbBaseLabelTask/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:edit")
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
     * @param id
     * @return
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签删除任务表删除")
    @PostMapping("/knowledge/kbBaseLabelTask/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:delete")
    public RespBody<String> deleteKbBaseLabelTask(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBaseLabelTaskService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 标签删除任务表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签删除任务表批量删除")
    @PostMapping("/knowledge/kbBaseLabelTask/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:deleteBatch")
    public RespBody<String> deleteKbBaseLabelTaskBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签删除任务表详情")
    @GetMapping("/knowledge/kbBaseLabelTask/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbBaseLabelTask:info")
    public RespBody<KbBaseLabelTaskDto> infoKbBaseLabelTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbBaseLabelTaskEntity kbBaseLabelTaskEntity = kbBaseLabelTaskService.getById(id);
        KbBaseLabelTaskDto kbBaseLabelTaskDto = ConvertUtil.transformObj(kbBaseLabelTaskEntity,KbBaseLabelTaskDto.class);

        return RespBody.data(kbBaseLabelTaskDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="标签删除任务表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/knowledge/kbBaseLabelTask/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<KbBaseLabelTaskDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<KbBaseLabelTaskDto> list = kbBaseLabelTaskService.listKbBaseLabelTask(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
