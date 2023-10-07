package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.CastleHelpArticleTypeDto;
import com.castle.fortress.admin.system.entity.CastleHelpArticleTypeEntity;
import com.castle.fortress.admin.system.service.CastleHelpArticleTypeService;
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
import java.util.Map;

/**
 * 帮助中心文章类型 控制器
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Api(tags="帮助中心文章类型管理控制器")
@Controller
public class CastleHelpArticleTypeController {
    @Autowired
    private CastleHelpArticleTypeService castleHelpArticleTypeService;

    /**
     * 帮助中心文章类型的分页展示
     * @param castleHelpArticleTypeDto 帮助中心文章类型实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("帮助中心文章类型分页展示")
    @GetMapping("/system/castleHelpArticleType/page")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:pageList")
    public RespBody<IPage<CastleHelpArticleTypeDto>> pageCastleHelpArticleType(CastleHelpArticleTypeDto castleHelpArticleTypeDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleHelpArticleTypeDto> page = new Page(pageIndex, pageSize);
        IPage<CastleHelpArticleTypeDto> pages = castleHelpArticleTypeService.pageCastleHelpArticleTypeExtends(page, castleHelpArticleTypeDto);

        return RespBody.data(pages);
    }

    /**
     * 帮助中心文章类型的列表展示
     * @param castleHelpArticleTypeDto 帮助中心文章类型实体类
     * @return
     */
    @ApiOperation("帮助中心文章类型列表展示")
    @GetMapping("/system/castleHelpArticleType/list")
    @ResponseBody
    public RespBody<List<CastleHelpArticleTypeDto>> listCastleHelpArticleType(CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        List<CastleHelpArticleTypeDto> list = castleHelpArticleTypeService.listCastleHelpArticleType(castleHelpArticleTypeDto);
        return RespBody.data(list);
    }

    /**
     * 帮助中心文章类型保存
     * @param castleHelpArticleTypeDto 帮助中心文章类型实体类
     * @return
     */
    @ApiOperation("帮助中心文章类型保存")
    @PostMapping("/system/castleHelpArticleType/save")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:save")
    public RespBody<String> saveCastleHelpArticleType(@RequestBody CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        if(castleHelpArticleTypeDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleTypeEntity castleHelpArticleTypeEntity = ConvertUtil.transformObj(castleHelpArticleTypeDto,CastleHelpArticleTypeEntity.class);
        if(castleHelpArticleTypeService.save(castleHelpArticleTypeEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章类型编辑
     * @param castleHelpArticleTypeDto 帮助中心文章类型实体类
     * @return
     */
    @ApiOperation("帮助中心文章类型编辑")
    @PostMapping("/system/castleHelpArticleType/edit")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:edit")
    public RespBody<String> updateCastleHelpArticleType(@RequestBody CastleHelpArticleTypeDto castleHelpArticleTypeDto){
        if(castleHelpArticleTypeDto == null || castleHelpArticleTypeDto.getId() == null || castleHelpArticleTypeDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleTypeEntity castleHelpArticleTypeEntity = ConvertUtil.transformObj(castleHelpArticleTypeDto,CastleHelpArticleTypeEntity.class);
        if(castleHelpArticleTypeService.updateById(castleHelpArticleTypeEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章类型删除
     * @param id
     * @return
     */
    @ApiOperation("帮助中心文章类型删除")
    @PostMapping("/system/castleHelpArticleType/delete")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:delete")
    public RespBody<String> deleteCastleHelpArticleType(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleHelpArticleTypeService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 帮助中心文章类型批量删除
     * @param ids
     * @return
     */
    @ApiOperation("帮助中心文章类型批量删除")
    @PostMapping("/system/castleHelpArticleType/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:deleteBatch")
    public RespBody<String> deleteCastleHelpArticleTypeBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleHelpArticleTypeService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 帮助中心文章类型详情
     * @param id 帮助中心文章类型id
     * @return
     */
    @ApiOperation("帮助中心文章类型详情")
    @GetMapping("/system/castleHelpArticleType/info")
    @ResponseBody
    @RequiresPermissions("system:castleHelpArticleType:info")
    public RespBody<CastleHelpArticleTypeDto> infoCastleHelpArticleType(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleTypeDto castleHelpArticleTypeDto =  castleHelpArticleTypeService.getByIdExtends(id);

        return RespBody.data(castleHelpArticleTypeDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/castleHelpArticleType/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleHelpArticleTypeDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleHelpArticleTypeDto> list = castleHelpArticleTypeService.listCastleHelpArticleType(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


    /**
     * 帮助中心文章类型列表
     * @return
     */
    @ApiOperation("帮助中心文章类型列表")
    @GetMapping("/system/castleHelpArticleType/allList")
    @ResponseBody
    public RespBody<List<CastleHelpArticleTypeDto>> list(@RequestParam Map<String , Object> params){
        List<CastleHelpArticleTypeDto> list =  castleHelpArticleTypeService.getDataList(params);
        return RespBody.data(list);
    }


}
