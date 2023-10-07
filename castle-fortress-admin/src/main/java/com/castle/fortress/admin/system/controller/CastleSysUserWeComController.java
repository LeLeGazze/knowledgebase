package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.CastleSysUserWeComService;
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
 * 用户企业微信信息表 控制器
 *
 * @author mjj
 * @since 2022-11-30
 */
@Api(tags="用户企业微信信息表管理控制器")
@Controller
public class CastleSysUserWeComController {
    @Autowired
    private CastleSysUserWeComService castleSysUserWeComService;

    /**
     * 用户企业微信信息表的分页展示
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户企业微信信息表分页展示")
    @GetMapping("/system/castleSysUserWeCom/page")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:pageList")
    public RespBody<IPage<CastleSysUserWeComDto>> pageCastleSysUserWeCom(CastleSysUserWeComDto castleSysUserWeComDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysUserWeComDto> page = new Page(pageIndex, pageSize);
        IPage<CastleSysUserWeComDto> pages = castleSysUserWeComService.pageCastleSysUserWeCom(page, castleSysUserWeComDto);

        return RespBody.data(pages);
    }

    /**
     * 用户企业微信信息表的列表展示
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户企业微信信息表列表展示")
    @GetMapping("/system/castleSysUserWeCom/list")
    @ResponseBody
    public RespBody<List<CastleSysUserWeComDto>> listCastleSysUserWeCom(CastleSysUserWeComDto castleSysUserWeComDto){
        List<CastleSysUserWeComDto> list = castleSysUserWeComService.listCastleSysUserWeCom(castleSysUserWeComDto);
        return RespBody.data(list);
    }

    /**
     * 用户企业微信信息表保存
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户企业微信信息表保存")
    @PostMapping("/system/castleSysUserWeCom/save")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:save")
    public RespBody<String> saveCastleSysUserWeCom(@RequestBody CastleSysUserWeComDto castleSysUserWeComDto){
        if(castleSysUserWeComDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserWeComEntity castleSysUserWeComEntity = ConvertUtil.transformObj(castleSysUserWeComDto,CastleSysUserWeComEntity.class);
        if(castleSysUserWeComService.save(castleSysUserWeComEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表编辑
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户企业微信信息表编辑")
    @PostMapping("/system/castleSysUserWeCom/edit")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:edit")
    public RespBody<String> updateCastleSysUserWeCom(@RequestBody CastleSysUserWeComDto castleSysUserWeComDto){
        if(castleSysUserWeComDto == null || castleSysUserWeComDto.getId() == null || castleSysUserWeComDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserWeComEntity castleSysUserWeComEntity = ConvertUtil.transformObj(castleSysUserWeComDto,CastleSysUserWeComEntity.class);
        if(castleSysUserWeComService.updateById(castleSysUserWeComEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户企业微信信息表删除")
    @PostMapping("/system/castleSysUserWeCom/delete")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:delete")
    public RespBody<String> deleteCastleSysUserWeCom(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserWeComService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 用户企业微信信息表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户企业微信信息表批量删除")
    @PostMapping("/system/castleSysUserWeCom/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:deleteBatch")
    public RespBody<String> deleteCastleSysUserWeComBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserWeComService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表详情
     * @param id 用户企业微信信息表id
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户企业微信信息表详情")
    @GetMapping("/system/castleSysUserWeCom/info")
    @ResponseBody
    @RequiresPermissions("system:castleSysUserWeCom:info")
    public RespBody<CastleSysUserWeComDto> infoCastleSysUserWeCom(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserWeComEntity castleSysUserWeComEntity = castleSysUserWeComService.getById(id);
        CastleSysUserWeComDto castleSysUserWeComDto = ConvertUtil.transformObj(castleSysUserWeComEntity,CastleSysUserWeComDto.class);

        return RespBody.data(castleSysUserWeComDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="用户企业微信信息表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/system/castleSysUserWeCom/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleSysUserWeComDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleSysUserWeComDto> list = castleSysUserWeComService.listCastleSysUserWeCom(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}

    /**
     * 获取用户企微信息
     * @param userId 系统userid
     * @return
     */
    @ApiOperation("获取用户企微信息")
    @GetMapping("/system/castleSysUserWeCom/getByUserId")
    @ResponseBody
//    @RequiresPermissions("system:castleSysUserWeCom:getByUserId")
    public RespBody<CastleSysUserWeComDto> updateCastleSysUserWeCom(Long userId){
        if(userId == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleSysUserWeComDto dto = castleSysUserWeComService.getByUserId(userId);
        return RespBody.data(dto);
    }


}
