package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ConfigParamsDto;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;
import com.castle.fortress.admin.system.service.ConfigParamsService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
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
 * 系统参数表 控制器
 *
 * @author castle
 * @since 2022-05-07
 */
@Api(tags="系统参数表管理控制器")
@Controller
public class ConfigParamsController {
    @Autowired
    private ConfigParamsService configParamsService;

    /**
     * 系统参数表的分页展示
     * @param configParamsDto 系统参数表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="系统参数分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统参数表分页展示")
    @GetMapping("/system/configParams/page")
    @ResponseBody
    @RequiresPermissions("system:configParams:pageList")
    public RespBody<IPage<ConfigParamsDto>> pageConfigParams(ConfigParamsDto configParamsDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigParamsDto> page = new Page(pageIndex, pageSize);
        IPage<ConfigParamsDto> pages = configParamsService.pageConfigParamsExtends(page, configParamsDto);

        return RespBody.data(pages);
    }

    /**
     * 系统参数表的列表展示
     * @param configParamsDto 系统参数表实体类
     * @return
     */
    @CastleLog(operLocation="系统参数列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统参数表列表展示")
    @GetMapping("/system/configParams/list")
    @ResponseBody
    public RespBody<List<ConfigParamsDto>> listConfigParams(ConfigParamsDto configParamsDto){
        List<ConfigParamsDto> list = configParamsService.listConfigParams(configParamsDto);
        return RespBody.data(list);
    }

    /**
     * 系统参数表保存
     * @param configParamsDto 系统参数表实体类
     * @return
     */
    @CastleLog(operLocation="系统参数保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("系统参数表保存")
    @PostMapping("/system/configParams/save")
    @ResponseBody
    @RequiresPermissions("system:configParams:save")
    public RespBody<String> saveConfigParams(@RequestBody ConfigParamsDto configParamsDto){
        if(configParamsDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
        if(configParamsService.save(configParamsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表编辑
     * @param configParamsDto 系统参数表实体类
     * @return
     */
    @CastleLog(operLocation="系统参数编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("系统参数表编辑")
    @PostMapping("/system/configParams/edit")
    @ResponseBody
    @RequiresPermissions("system:configParams:edit")
    public RespBody<String> updateConfigParams(@RequestBody ConfigParamsDto configParamsDto){
        if(configParamsDto == null || configParamsDto.getId() == null || configParamsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
        if(configParamsService.updateById(configParamsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="系统参数删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("系统参数表删除")
    @PostMapping("/system/configParams/delete")
    @ResponseBody
    @RequiresPermissions("system:configParams:delete")
    public RespBody<String> deleteConfigParams(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configParamsService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 系统参数表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="系统参数批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("系统参数表批量删除")
    @PostMapping("/system/configParams/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:configParams:deleteBatch")
    public RespBody<String> deleteConfigParamsBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configParamsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表详情
     * @param id 系统参数表id
     * @return
     */
    @CastleLog(operLocation="系统参数详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("系统参数表详情")
    @GetMapping("/system/configParams/info")
    @ResponseBody
    @RequiresPermissions("system:configParams:info")
    public RespBody<ConfigParamsDto> infoConfigParams(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigParamsDto configParamsDto =  configParamsService.getByIdExtends(id);

        return RespBody.data(configParamsDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="系统参数导出",operType= OperationTypeEnum.EXPORT)
	@PostMapping("/system/configParams/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<ConfigParamsDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<ConfigParamsDto> list = configParamsService.listConfigParams(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
