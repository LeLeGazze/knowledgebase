package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbDataSynchronizationTaskEntity;
import com.castle.fortress.admin.knowledge.dto.KbDataSynchronizationTaskDto;
import com.castle.fortress.admin.knowledge.service.KbDataSynchronizationTaskService;
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
 * 数据同步 控制器
 *
 * @author 
 * @since 2023-06-29
 */
@Api(tags="数据同步管理控制器")
@Controller
public class KbDataSynchronizationTaskController {
    @Autowired
    private KbDataSynchronizationTaskService kbDataSynchronizationTaskService;

    /**
     * 数据同步的分页展示
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.QUERY)
    @ApiOperation("数据同步分页展示")
    @GetMapping("/knowledge/kbDataSynchronizationTask/page")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:pageList")
    public RespBody<IPage<KbDataSynchronizationTaskDto>> pageKbDataSynchronizationTask(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbDataSynchronizationTaskDto> page = new Page(pageIndex, pageSize);
        IPage<KbDataSynchronizationTaskDto> pages = kbDataSynchronizationTaskService.pageKbDataSynchronizationTask(page, kbDataSynchronizationTaskDto);

        return RespBody.data(pages);
    }

    /**
     * 数据同步的列表展示
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @return
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.QUERY)
    @ApiOperation("数据同步列表展示")
    @GetMapping("/knowledge/kbDataSynchronizationTask/list")
    @ResponseBody
    public RespBody<List<KbDataSynchronizationTaskDto>> listKbDataSynchronizationTask(KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto){
        List<KbDataSynchronizationTaskDto> list = kbDataSynchronizationTaskService.listKbDataSynchronizationTask(kbDataSynchronizationTaskDto);
        return RespBody.data(list);
    }

    /**
     * 数据同步保存
     * @param kbDataSynchronizationTaskDto 数据同步实体类
     * @return
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.INSERT)
    @ApiOperation("数据同步保存")
    @PostMapping("/knowledge/kbDataSynchronizationTask/save")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:save")
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
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("数据同步编辑")
    @PostMapping("/knowledge/kbDataSynchronizationTask/edit")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:edit")
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
     * @param id
     * @return
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.DELETE)
    @ApiOperation("数据同步删除")
    @PostMapping("/knowledge/kbDataSynchronizationTask/delete")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:delete")
    public RespBody<String> deleteKbDataSynchronizationTask(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbDataSynchronizationTaskService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 数据同步批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.DELETE)
    @ApiOperation("数据同步批量删除")
    @PostMapping("/knowledge/kbDataSynchronizationTask/deleteBatch")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:deleteBatch")
    public RespBody<String> deleteKbDataSynchronizationTaskBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.QUERY)
    @ApiOperation("数据同步详情")
    @GetMapping("/knowledge/kbDataSynchronizationTask/info")
    @ResponseBody
    @RequiresPermissions("knowledge:kbDataSynchronizationTask:info")
    public RespBody<KbDataSynchronizationTaskDto> infoKbDataSynchronizationTask(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbDataSynchronizationTaskEntity kbDataSynchronizationTaskEntity = kbDataSynchronizationTaskService.getById(id);
        KbDataSynchronizationTaskDto kbDataSynchronizationTaskDto = ConvertUtil.transformObj(kbDataSynchronizationTaskEntity,KbDataSynchronizationTaskDto.class);

        return RespBody.data(kbDataSynchronizationTaskDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="数据同步",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/knowledge/kbDataSynchronizationTask/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<KbDataSynchronizationTaskDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<KbDataSynchronizationTaskDto> list = kbDataSynchronizationTaskService.listKbDataSynchronizationTask(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
