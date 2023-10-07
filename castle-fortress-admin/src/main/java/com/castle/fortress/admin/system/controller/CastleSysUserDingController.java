package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.castle.fortress.admin.system.service.CastleSysUserDingService;
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
 * 用户钉钉信息表 控制器
 *
 * @author Mgg
 * @since 2022-12-13
 */
@Api(tags="用户钉钉信息表管理控制器")
@Controller
public class CastleSysUserDingController {
    @Autowired
    private CastleSysUserDingService castleSysUserDingService;

    /**
     * 用户钉钉信息表的分页展示
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表分页展示")
    @GetMapping("/system/castleSysUserDing/page")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:pageList")
    public RespBody<IPage<CastleSysUserDingDto>> pageCastleSysUserDing(CastleSysUserDingDto castleSysUserDingDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysUserDingDto> page = new Page(pageIndex, pageSize);
        IPage<CastleSysUserDingDto> pages = castleSysUserDingService.pageCastleSysUserDing(page, castleSysUserDingDto);

        return RespBody.data(pages);
    }

    /**
     * 用户钉钉信息表的列表展示
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表列表展示")
    @GetMapping("/system/castleSysUserDing/list")
    @ResponseBody
    public RespBody<List<CastleSysUserDingDto>> listCastleSysUserDing(CastleSysUserDingDto castleSysUserDingDto){
        List<CastleSysUserDingDto> list = castleSysUserDingService.listCastleSysUserDing(castleSysUserDingDto);
        return RespBody.data(list);
    }

    /**
     * 用户钉钉信息表保存
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户钉钉信息表保存")
    @PostMapping("/system/castleSysUserDing/save")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:save")
    public RespBody<String> saveCastleSysUserDing(@RequestBody CastleSysUserDingDto castleSysUserDingDto){
        if(castleSysUserDingDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserDingEntity castleSysUserDingEntity = ConvertUtil.transformObj(castleSysUserDingDto,CastleSysUserDingEntity.class);
        if(castleSysUserDingService.save(castleSysUserDingEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表编辑
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户钉钉信息表编辑")
    @PostMapping("/system/castleSysUserDing/edit")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:edit")
    public RespBody<String> updateCastleSysUserDing(@RequestBody CastleSysUserDingDto castleSysUserDingDto){
        if(castleSysUserDingDto == null || castleSysUserDingDto.getId() == null || castleSysUserDingDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserDingEntity castleSysUserDingEntity = ConvertUtil.transformObj(castleSysUserDingDto,CastleSysUserDingEntity.class);
        if(castleSysUserDingService.updateById(castleSysUserDingEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户钉钉信息表删除")
    @PostMapping("/system/castleSysUserDing/delete")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:delete")
    public RespBody<String> deleteCastleSysUserDing(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserDingService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 用户钉钉信息表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户钉钉信息表批量删除")
    @PostMapping("/system/castleSysUserDing/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:deleteBatch")
    public RespBody<String> deleteCastleSysUserDingBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserDingService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表详情
     * @param id 用户钉钉信息表id
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表详情")
    @GetMapping("/system/castleSysUserDing/info")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserDing:info")
    public RespBody<CastleSysUserDingDto> infoCastleSysUserDing(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserDingEntity castleSysUserDingEntity = castleSysUserDingService.getById(id);
        CastleSysUserDingDto castleSysUserDingDto = ConvertUtil.transformObj(castleSysUserDingEntity,CastleSysUserDingDto.class);

        return RespBody.data(castleSysUserDingDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/system/castleSysUserDing/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleSysUserDingDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleSysUserDingDto> list = castleSysUserDingService.listCastleSysUserDing(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


    /**
     * 用户钉钉信息表详情
     * @param userId 用户表id
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表详情")
    @GetMapping("/system/castleSysUserDing/getByUserId")
    @ResponseBody
//    @RequiresPermissions("system:castleSysUserDing:getByUserId")
    public RespBody<CastleSysUserDingDto> getByUserId(@RequestParam Long userId){
        if(userId == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        QueryWrapper<CastleSysUserDingEntity> wrapper= new QueryWrapper();
        wrapper.eq("user_id",userId);
        wrapper.last("limit 1");
        CastleSysUserDingEntity castleSysUserDingEntity = castleSysUserDingService.getOne(wrapper);
        CastleSysUserDingDto castleSysUserDingDto = ConvertUtil.transformObj(castleSysUserDingEntity,CastleSysUserDingDto.class);
        return RespBody.data(castleSysUserDingDto);
    }

}
