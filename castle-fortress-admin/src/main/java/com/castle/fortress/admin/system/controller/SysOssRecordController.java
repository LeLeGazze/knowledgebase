package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysOssRecordDto;
import com.castle.fortress.admin.system.entity.SysOssRecordEntity;
import com.castle.fortress.admin.system.service.SysOssRecordService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * oss上传记录 控制器
 *
 * @author castle
 * @since 2022-03-01
 */
@Api(tags="oss上传记录管理控制器")
@Controller
public class SysOssRecordController {
    @Autowired
    private SysOssRecordService sysOssRecordService;

    /**
     * oss上传记录的分页展示
     * @param sysOssRecordDto oss上传记录实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="上传记录分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("oss上传记录分页展示")
    @GetMapping("/system/sysOssRecord/page")
    @ResponseBody
    public RespBody<IPage<SysOssRecordDto>> pageSysOssRecord(SysOssRecordDto sysOssRecordDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysOssRecordDto> page = new Page(pageIndex, pageSize);
        IPage<SysOssRecordDto> pages = sysOssRecordService.pageSysOssRecord(page, sysOssRecordDto);

        return RespBody.data(pages);
    }

    /**
     * oss上传记录的列表展示
     * @param sysOssRecordDto oss上传记录实体类
     * @return
     */
    @CastleLog(operLocation="上传记录列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("oss上传记录列表展示")
    @GetMapping("/system/sysOssRecord/list")
    @ResponseBody
    public RespBody<List<SysOssRecordDto>> listSysOssRecord(SysOssRecordDto sysOssRecordDto){
        List<SysOssRecordDto> list = sysOssRecordService.listSysOssRecord(sysOssRecordDto);
        return RespBody.data(list);
    }

    /**
     * oss上传记录保存
     * @param sysOssRecordDto oss上传记录实体类
     * @return
     */
    @CastleLog(operLocation="上传记录保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("oss上传记录保存")
    @PostMapping("/system/sysOssRecord/save")
    @ResponseBody
    public RespBody<String> saveSysOssRecord(@RequestBody SysOssRecordDto sysOssRecordDto){
        if(sysOssRecordDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysOssRecordEntity sysOssRecordEntity = ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class);
        if(sysOssRecordService.save(sysOssRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录编辑
     * @param sysOssRecordDto oss上传记录实体类
     * @return
     */
    @CastleLog(operLocation="上传记录编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("oss上传记录编辑")
    @PostMapping("/system/sysOssRecord/edit")
    @ResponseBody
    public RespBody<String> updateSysOssRecord(@RequestBody SysOssRecordDto sysOssRecordDto){
        if(sysOssRecordDto == null || sysOssRecordDto.getId() == null || sysOssRecordDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysOssRecordEntity sysOssRecordEntity = ConvertUtil.transformObj(sysOssRecordDto,SysOssRecordEntity.class);
        if(sysOssRecordService.updateById(sysOssRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="上传记录删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("oss上传记录删除")
    @PostMapping("/system/sysOssRecord/delete")
    @ResponseBody
    public RespBody<String> deleteSysOssRecord(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysOssRecordService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * oss上传记录批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="上传记录批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("oss上传记录批量删除")
    @PostMapping("/system/sysOssRecord/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteSysOssRecordBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysOssRecordService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * oss上传记录详情
     * @param id oss上传记录id
     * @return
     */
    @CastleLog(operLocation="上传记录详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("oss上传记录详情")
    @GetMapping("/system/sysOssRecord/info")
    @ResponseBody
    public RespBody<SysOssRecordDto> infoSysOssRecord(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysOssRecordEntity sysOssRecordEntity = sysOssRecordService.getById(id);
        SysOssRecordDto sysOssRecordDto = ConvertUtil.transformObj(sysOssRecordEntity,SysOssRecordDto.class);

        return RespBody.data(sysOssRecordDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="上传记录导出",operType= OperationTypeEnum.EXPORT)
	@PostMapping("/system/sysOssRecord/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<SysOssRecordDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<SysOssRecordDto> list = sysOssRecordService.listSysOssRecord(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
