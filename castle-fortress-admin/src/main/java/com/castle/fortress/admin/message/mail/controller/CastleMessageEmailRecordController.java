package com.castle.fortress.admin.message.mail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.mail.dto.CastleMessageEmailRecordDto;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;
import com.castle.fortress.admin.message.mail.service.CastleMessageEmailRecordService;
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
 * 邮件发送记录表 控制器
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Api(tags="邮件发送记录表管理控制器")
@Controller
public class CastleMessageEmailRecordController {
    @Autowired
    private CastleMessageEmailRecordService castleMessageEmailRecordService;

    /**
     * 邮件发送记录表的分页展示
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("邮件发送记录表分页展示")
    @GetMapping("/message/mail/castleMessageEmailRecord/page")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:pageList")
    public RespBody<IPage<CastleMessageEmailRecordDto>> pageCastleMessageEmailRecord(CastleMessageEmailRecordDto castleMessageEmailRecordDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleMessageEmailRecordDto> page = new Page(pageIndex, pageSize);
        IPage<CastleMessageEmailRecordDto> pages = castleMessageEmailRecordService.pageCastleMessageEmailRecordExtends(page, castleMessageEmailRecordDto);

        return RespBody.data(pages);
    }

    /**
     * 邮件发送记录表的列表展示
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @return
     */
    @ApiOperation("邮件发送记录表列表展示")
    @GetMapping("/message/mail/castleMessageEmailRecord/list")
    @ResponseBody
    public RespBody<List<CastleMessageEmailRecordDto>> listCastleMessageEmailRecord(CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        List<CastleMessageEmailRecordDto> list = castleMessageEmailRecordService.listCastleMessageEmailRecord(castleMessageEmailRecordDto);
        return RespBody.data(list);
    }

    /**
     * 邮件发送记录表保存
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @return
     */
    @ApiOperation("邮件发送记录表保存")
    @PostMapping("/message/mail/castleMessageEmailRecord/save")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:save")
    public RespBody<String> saveCastleMessageEmailRecord(@RequestBody CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        if(castleMessageEmailRecordDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
        if(castleMessageEmailRecordService.save(castleMessageEmailRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表编辑
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @return
     */
    @ApiOperation("邮件发送记录表编辑")
    @PostMapping("/message/mail/castleMessageEmailRecord/edit")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:edit")
    public RespBody<String> updateCastleMessageEmailRecord(@RequestBody CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        if(castleMessageEmailRecordDto == null || castleMessageEmailRecordDto.getId() == null || castleMessageEmailRecordDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
        if(castleMessageEmailRecordService.updateById(castleMessageEmailRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表删除
     * @param id
     * @return
     */
    @ApiOperation("邮件发送记录表删除")
    @PostMapping("/message/mail/castleMessageEmailRecord/delete")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:delete")
    public RespBody<String> deleteCastleMessageEmailRecord(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleMessageEmailRecordService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 邮件发送记录表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("邮件发送记录表批量删除")
    @PostMapping("/message/mail/castleMessageEmailRecord/deleteBatch")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:deleteBatch")
    public RespBody<String> deleteCastleMessageEmailRecordBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleMessageEmailRecordService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表详情
     * @param id 邮件发送记录表id
     * @return
     */
    @ApiOperation("邮件发送记录表详情")
    @GetMapping("/message/mail/castleMessageEmailRecord/info")
    @ResponseBody
    @RequiresPermissions("message:mail:castleMessageEmailRecord:info")
    public RespBody<CastleMessageEmailRecordDto> infoCastleMessageEmailRecord(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleMessageEmailRecordDto castleMessageEmailRecordDto =  castleMessageEmailRecordService.getByIdExtends(id);

        return RespBody.data(castleMessageEmailRecordDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/message/mail/castleMessageEmailRecord/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<CastleMessageEmailRecordDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<CastleMessageEmailRecordDto> list = castleMessageEmailRecordService.listCastleMessageEmailRecord(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
