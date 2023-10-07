package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.CastleBannerDto;
import com.castle.fortress.admin.system.entity.CastleBannerEntity;
import com.castle.fortress.admin.system.service.CastleBannerService;
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
 * banner图 控制器
 *
 * @author majunjie
 * @since 2022-02-14
 */
@Api(tags="banner图管理控制器")
@Controller
public class CastleBannerController {
    @Autowired
    private CastleBannerService castleBannerService;

    /**
     * banner图的分页展示
     * @param castleBannerDto banner图实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("banner图分页展示")
    @GetMapping("/system/castleBanner/page")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:pageList")
    public RespBody<IPage<CastleBannerDto>> pageCastleBanner(CastleBannerDto castleBannerDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleBannerDto> page = new Page(pageIndex, pageSize);
        IPage<CastleBannerDto> pages = castleBannerService.pageCastleBannerExtends(page, castleBannerDto);

        return RespBody.data(pages);
    }

    /**
     * banner图的列表展示
     * @param castleBannerDto banner图实体类
     * @return
     */
    @ApiOperation("banner图列表展示")
    @GetMapping("/system/castleBanner/list")
    @ResponseBody
    public RespBody<List<CastleBannerDto>> listCastleBanner(CastleBannerDto castleBannerDto){
        List<CastleBannerDto> list = castleBannerService.listCastleBanner(castleBannerDto);
        return RespBody.data(list);
    }

    /**
     * banner图保存
     * @param castleBannerDto banner图实体类
     * @return
     */
    @ApiOperation("banner图保存")
    @PostMapping("/system/castleBanner/save")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:save")
    public RespBody<String> saveCastleBanner(@RequestBody CastleBannerDto castleBannerDto){
        if(castleBannerDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleBannerEntity castleBannerEntity = ConvertUtil.transformObj(castleBannerDto,CastleBannerEntity.class);
        if(castleBannerService.save(castleBannerEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * banner图编辑
     * @param castleBannerDto banner图实体类
     * @return
     */
    @ApiOperation("banner图编辑")
    @PostMapping("/system/castleBanner/edit")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:edit")
    public RespBody<String> updateCastleBanner(@RequestBody CastleBannerDto castleBannerDto){
        if(castleBannerDto == null || castleBannerDto.getId() == null || castleBannerDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleBannerEntity castleBannerEntity = ConvertUtil.transformObj(castleBannerDto,CastleBannerEntity.class);
        if(castleBannerService.updateById(castleBannerEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * banner图删除
     * @param id
     * @return
     */
    @ApiOperation("banner图删除")
    @PostMapping("/system/castleBanner/delete")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:delete")
    public RespBody<String> deleteCastleBanner(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleBannerService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * banner图批量删除
     * @param ids
     * @return
     */
    @ApiOperation("banner图批量删除")
    @PostMapping("/system/castleBanner/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:deleteBatch")
    public RespBody<String> deleteCastleBannerBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleBannerService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * banner图详情
     * @param id banner图id
     * @return
     */
    @ApiOperation("banner图详情")
    @GetMapping("/system/castleBanner/info")
    @ResponseBody
    @RequiresPermissions("system:castleBanner:info")
    public RespBody<CastleBannerDto> infoCastleBanner(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleBannerDto castleBannerDto =  castleBannerService.getByIdExtends(id);

        return RespBody.data(castleBannerDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/castleBanner/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleBannerDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleBannerDto> list = castleBannerService.listCastleBanner(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
