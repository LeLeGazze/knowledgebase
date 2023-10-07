package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.CastleVersionDto;
import com.castle.fortress.admin.system.entity.CastleVersionEntity;
import com.castle.fortress.admin.system.service.CastleVersionService;
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
 * 版本管理 控制器
 *
 * @author 
 * @since 2022-02-14
 */
@Api(tags="版本管理管理控制器")
@Controller
public class CastleVersionController {
    @Autowired
    private CastleVersionService castleVersionService;

    /**
     * 版本管理的分页展示
     * @param castleVersionDto 版本管理实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("版本管理分页展示")
    @GetMapping("/system/castleVersion/page")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:pageList")
    public RespBody<IPage<CastleVersionDto>> pageCastleVersion(CastleVersionDto castleVersionDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleVersionDto> page = new Page(pageIndex, pageSize);
        IPage<CastleVersionDto> pages = castleVersionService.pageCastleVersionExtends(page, castleVersionDto);

        return RespBody.data(pages);
    }

    /**
     * 版本管理的列表展示
     * @param castleVersionDto 版本管理实体类
     * @return
     */
    @ApiOperation("版本管理列表展示")
    @GetMapping("/system/castleVersion/list")
    @ResponseBody
    public RespBody<List<CastleVersionDto>> listCastleVersion(CastleVersionDto castleVersionDto){
        List<CastleVersionDto> list = castleVersionService.listCastleVersion(castleVersionDto);
        return RespBody.data(list);
    }

    /**
     * 版本管理保存
     * @param castleVersionDto 版本管理实体类
     * @return
     */
    @ApiOperation("版本管理保存")
    @PostMapping("/system/castleVersion/save")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:save")
    public RespBody<String> saveCastleVersion(@RequestBody CastleVersionDto castleVersionDto){
        if(castleVersionDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleVersionEntity castleVersionEntity = ConvertUtil.transformObj(castleVersionDto,CastleVersionEntity.class);
        if(castleVersionService.save(castleVersionEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 版本管理编辑
     * @param castleVersionDto 版本管理实体类
     * @return
     */
    @ApiOperation("版本管理编辑")
    @PostMapping("/system/castleVersion/edit")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:edit")
    public RespBody<String> updateCastleVersion(@RequestBody CastleVersionDto castleVersionDto){
        if(castleVersionDto == null || castleVersionDto.getId() == null || castleVersionDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleVersionEntity castleVersionEntity = ConvertUtil.transformObj(castleVersionDto,CastleVersionEntity.class);
        if(castleVersionService.updateById(castleVersionEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 版本管理删除
     * @param id
     * @return
     */
    @ApiOperation("版本管理删除")
    @PostMapping("/system/castleVersion/delete")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:delete")
    public RespBody<String> deleteCastleVersion(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleVersionService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 版本管理批量删除
     * @param ids
     * @return
     */
    @ApiOperation("版本管理批量删除")
    @PostMapping("/system/castleVersion/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:deleteBatch")
    public RespBody<String> deleteCastleVersionBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleVersionService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 版本管理详情
     * @param id 版本管理id
     * @return
     */
    @ApiOperation("版本管理详情")
    @GetMapping("/system/castleVersion/info")
    @ResponseBody
    @RequiresPermissions("system:castleVersion:info")
    public RespBody<CastleVersionDto> infoCastleVersion(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleVersionDto castleVersionDto =  castleVersionService.getByIdExtends(id);

        return RespBody.data(castleVersionDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/castleVersion/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleVersionDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleVersionDto> list = castleVersionService.listCastleVersion(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
