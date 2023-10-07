package com.castle.fortress.admin.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.demo.dto.TmpDemoGenerateDto;
import com.castle.fortress.admin.demo.entity.TmpDemoGenerateEntity;
import com.castle.fortress.admin.demo.service.TmpDemoGenerateService;
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
 * 代码生成示例表 控制器
 *
 * @author castle
 * @since 2021-11-08
 */
@Api(tags="代码生成示例表管理控制器")
@Controller
public class TmpDemoGenerateController {
    @Autowired
    private TmpDemoGenerateService tmpDemoGenerateService;

    /**
     * 代码生成示例表的分页展示
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("代码生成示例表分页展示")
    @GetMapping("/demo/tmpDemoGenerate/page")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:pageList")
    public RespBody<IPage<TmpDemoGenerateDto>> pageTmpDemoGenerate(TmpDemoGenerateDto tmpDemoGenerateDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<TmpDemoGenerateDto> page = new Page(pageIndex, pageSize);
        IPage<TmpDemoGenerateDto> pages = tmpDemoGenerateService.pageTmpDemoGenerateExtends(page, tmpDemoGenerateDto);

        return RespBody.data(pages);
    }

    /**
     * 代码生成示例表的列表展示
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @return
     */
    @ApiOperation("代码生成示例表列表展示")
    @GetMapping("/demo/tmpDemoGenerate/list")
    @ResponseBody
    public RespBody<List<TmpDemoGenerateDto>> listTmpDemoGenerate(TmpDemoGenerateDto tmpDemoGenerateDto){
        List<TmpDemoGenerateDto> list = tmpDemoGenerateService.listTmpDemoGenerate(tmpDemoGenerateDto);
        return RespBody.data(list);
    }

    /**
     * 代码生成示例表保存
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @return
     */
    @ApiOperation("代码生成示例表保存")
    @PostMapping("/demo/tmpDemoGenerate/save")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:save")
    public RespBody<String> saveTmpDemoGenerate(@RequestBody TmpDemoGenerateDto tmpDemoGenerateDto){
        if(tmpDemoGenerateDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
        if(tmpDemoGenerateService.save(tmpDemoGenerateEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表编辑
     * @param tmpDemoGenerateDto 代码生成示例表实体类
     * @return
     */
    @ApiOperation("代码生成示例表编辑")
    @PostMapping("/demo/tmpDemoGenerate/edit")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:edit")
    public RespBody<String> updateTmpDemoGenerate(@RequestBody TmpDemoGenerateDto tmpDemoGenerateDto){
        if(tmpDemoGenerateDto == null || tmpDemoGenerateDto.getId() == null || tmpDemoGenerateDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoGenerateEntity tmpDemoGenerateEntity = ConvertUtil.transformObj(tmpDemoGenerateDto,TmpDemoGenerateEntity.class);
        if(tmpDemoGenerateService.updateById(tmpDemoGenerateEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表删除
     * @param id
     * @return
     */
    @ApiOperation("代码生成示例表删除")
    @PostMapping("/demo/tmpDemoGenerate/delete")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:delete")
    public RespBody<String> deleteTmpDemoGenerate(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoGenerateService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 代码生成示例表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("代码生成示例表批量删除")
    @PostMapping("/demo/tmpDemoGenerate/deleteBatch")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:deleteBatch")
    public RespBody<String> deleteTmpDemoGenerateBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoGenerateService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 代码生成示例表详情
     * @param id 代码生成示例表id
     * @return
     */
    @ApiOperation("代码生成示例表详情")
    @GetMapping("/demo/tmpDemoGenerate/info")
    @ResponseBody
    @RequiresPermissions("demo:tmpDemoGenerate:info")
    public RespBody<TmpDemoGenerateDto> infoTmpDemoGenerate(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoGenerateDto tmpDemoGenerateDto =  tmpDemoGenerateService.getByIdExtends(id);

        return RespBody.data(tmpDemoGenerateDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/demo/tmpDemoGenerate/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<TmpDemoGenerateDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<TmpDemoGenerateDto> list = tmpDemoGenerateService.listTmpDemoGenerate(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
