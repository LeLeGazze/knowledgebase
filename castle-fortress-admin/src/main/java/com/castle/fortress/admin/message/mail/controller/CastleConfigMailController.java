package com.castle.fortress.admin.message.mail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.mail.dto.CastleConfigMailDto;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import com.castle.fortress.admin.message.mail.service.CastleConfigMailService;
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
 * 邮件配置表 控制器
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Api(tags="邮件配置表管理控制器")
@Controller
public class CastleConfigMailController {
    @Autowired
    private CastleConfigMailService castleConfigMailService;

    /**
     * 邮件配置表的分页展示
     * @param castleConfigMailDto 邮件配置表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("邮件配置表分页展示")
    @GetMapping("/message/mail/castleConfigMail/page")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:pageList")
    public RespBody<IPage<CastleConfigMailDto>> pageCastleConfigMail(CastleConfigMailDto castleConfigMailDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleConfigMailDto> page = new Page(pageIndex, pageSize);
        IPage<CastleConfigMailDto> pages = castleConfigMailService.pageCastleConfigMailExtends(page, castleConfigMailDto);

        return RespBody.data(pages);
    }

    /**
     * 邮件配置表的列表展示
     * @param castleConfigMailDto 邮件配置表实体类
     * @return
     */
    @ApiOperation("邮件配置表列表展示")
    @GetMapping("/message/mail/castleConfigMail/list")
    @ResponseBody
    public RespBody<List<CastleConfigMailDto>> listCastleConfigMail(CastleConfigMailDto castleConfigMailDto){
        List<CastleConfigMailDto> list = castleConfigMailService.listCastleConfigMail(castleConfigMailDto);
        return RespBody.data(list);
    }

    /**
     * 邮件配置表保存
     * @param castleConfigMailDto 邮件配置表实体类
     * @return
     */
    @ApiOperation("邮件配置表保存")
    @PostMapping("/message/mail/castleConfigMail/save")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:save")
    public RespBody<String> saveCastleConfigMail(@RequestBody CastleConfigMailDto castleConfigMailDto){
        if(castleConfigMailDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        RespBody checkResult = castleConfigMailService.checkColumnRepeat(castleConfigMailDto);
        if(!checkResult.isSuccess()){
            return checkResult;
        }
        CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
        if(castleConfigMailService.save(castleConfigMailEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表编辑
     * @param castleConfigMailDto 邮件配置表实体类
     * @return
     */
    @ApiOperation("邮件配置表编辑")
    @PostMapping("/message/mail/castleConfigMail/edit")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:edit")
    public RespBody<String> updateCastleConfigMail(@RequestBody CastleConfigMailDto castleConfigMailDto){
        if(castleConfigMailDto == null || castleConfigMailDto.getId() == null || castleConfigMailDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        RespBody checkResult = castleConfigMailService.checkColumnRepeat(castleConfigMailDto);
        if(!checkResult.isSuccess()){
            return checkResult;
        }
        CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
        if(castleConfigMailService.updateById(castleConfigMailEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表删除
     * @param id
     * @return
     */
    @ApiOperation("邮件配置表删除")
    @PostMapping("/message/mail/castleConfigMail/delete")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:delete")
    public RespBody<String> deleteCastleConfigMail(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleConfigMailService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 邮件配置表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("邮件配置表批量删除")
    @PostMapping("/message/mail/castleConfigMail/deleteBatch")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:deleteBatch")
    public RespBody<String> deleteCastleConfigMailBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleConfigMailService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表详情
     * @param id 邮件配置表id
     * @return
     */
    @ApiOperation("邮件配置表详情")
    @GetMapping("/message/mail/castleConfigMail/info")
    @ResponseBody
    @RequiresPermissions("message:mail:castleConfigMail:info")
    public RespBody<CastleConfigMailDto> infoCastleConfigMail(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleConfigMailDto castleConfigMailDto =  castleConfigMailService.getByIdExtends(id);

        return RespBody.data(castleConfigMailDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/message/mail/castleConfigMail/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleConfigMailDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleConfigMailDto> list = castleConfigMailService.listCastleConfigMail(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
