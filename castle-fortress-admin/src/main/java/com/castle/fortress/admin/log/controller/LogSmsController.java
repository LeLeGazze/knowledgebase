package com.castle.fortress.admin.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import com.castle.fortress.admin.log.service.LogSmsService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 短信发送记录 控制器
 *
 * @author Mgg
 * @since 2021-12-06
 */
@Api(tags="短信发送记录管理控制器")
@Controller
public class LogSmsController {
    @Autowired
    private LogSmsService logSmsService;

    /**
     * 短信发送记录的分页展示
     * @param logSmsDto 短信发送记录实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("短信发送记录分页展示")
    @GetMapping("/log/logSms/page")
    @ResponseBody
    @RequiresPermissions("log:logSms:pageList")
    public RespBody<IPage<LogSmsDto>> pageLogSms(LogSmsDto logSmsDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<LogSmsDto> page = new Page(pageIndex, pageSize);
        IPage<LogSmsDto> pages = logSmsService.pageLogSmsExtends(page, logSmsDto);

        return RespBody.data(pages);
    }

    /**
     * 短信发送记录的列表展示
     * @param logSmsDto 短信发送记录实体类
     * @return
     */
    @ApiOperation("短信发送记录列表展示")
    @GetMapping("/log/logSms/list")
    @ResponseBody
    public RespBody<List<LogSmsDto>> listLogSms(LogSmsDto logSmsDto){
        List<LogSmsDto> list = logSmsService.listLogSms(logSmsDto);
        return RespBody.data(list);
    }

    /**
     * 短信发送记录保存
     * @param logSmsDto 短信发送记录实体类
     * @return
     */
    @ApiOperation("短信发送记录保存")
    @PostMapping("/log/logSms/save")
    @ResponseBody
    @RequiresPermissions("log:logSms:save")
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
    @PostMapping("/log/logSms/edit")
    @ResponseBody
    @RequiresPermissions("log:logSms:edit")
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
     * @param id
     * @return
     */
    @ApiOperation("短信发送记录删除")
    @PostMapping("/log/logSms/delete")
    @ResponseBody
    @RequiresPermissions("log:logSms:delete")
    public RespBody<String> deleteLogSms(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(logSmsService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 短信发送记录批量删除
     * @param ids
     * @return
     */
    @ApiOperation("短信发送记录批量删除")
    @PostMapping("/log/logSms/deleteBatch")
    @ResponseBody
    @RequiresPermissions("log:logSms:deleteBatch")
    public RespBody<String> deleteLogSmsBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @GetMapping("/log/logSms/info")
    @ResponseBody
    @RequiresPermissions("log:logSms:info")
    public RespBody<LogSmsDto> infoLogSms(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        LogSmsDto logSmsDto =  logSmsService.getByIdExtends(id);

        return RespBody.data(logSmsDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/log/logSms/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<LogSmsDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<LogSmsDto> list = logSmsService.listLogSms(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
