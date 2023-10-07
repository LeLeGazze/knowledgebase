package com.castle.fortress.admin.knowledge.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbModelDomainEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelDomainDto;
import com.castle.fortress.admin.knowledge.service.KbModelDomainService;
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
 * 值域字典表 控制器
 *
 * @author sunhr
 * @since 2023-04-20
 */
@Api(tags="值域字典表管理控制器")
@Controller
public class KbModelDomainController {
    @Autowired
    private KbModelDomainService kbModelDomainService;

    /**
     * 值域字典表的分页展示
     * @param kbModelDomainDto 值域字典表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("值域字典表分页展示")
    @GetMapping("/knowledge/kbModelDomain/page")
    @ResponseBody
    public RespBody<IPage<KbModelDomainDto>> pageKbModelDomain(KbModelDomainDto kbModelDomainDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbModelDomainDto> page = new Page(pageIndex, pageSize);
        IPage<KbModelDomainDto> pages = kbModelDomainService.pageKbModelDomainExtends(page, kbModelDomainDto);

        return RespBody.data(pages);
    }

    /**
     * 值域字典表的列表展示
     * @param
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("值域字典表列表展示")
    @GetMapping("/knowledge/kbModelDomain/list")
    @ResponseBody
    public RespBody<List<KbModelDomainEntity>> listKbModelDomain(){
        List<KbModelDomainEntity> list = kbModelDomainService.selectAll();
        return RespBody.data(list);
    }

    /**
     * 值域字典表保存
     * @param kbModelDomainEntity 值域字典表实体类
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("值域字典表保存")
    @PostMapping("/knowledge/kbModelDomain/save")
    @ResponseBody
    public RespBody<String> saveKbModelDomain(@RequestBody KbModelDomainEntity kbModelDomainEntity){
        if(kbModelDomainEntity == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelDomainService.save(kbModelDomainEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表编辑
     * @param kbModelDomainDto 值域字典表实体类
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("值域字典表编辑")
    @PostMapping("/knowledge/kbModelDomain/edit")
    @ResponseBody
    public RespBody<String> updateKbModelDomain(@RequestBody KbModelDomainDto kbModelDomainDto){
        if(kbModelDomainDto == null || kbModelDomainDto.getId() == null || kbModelDomainDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelDomainEntity kbModelDomainEntity = ConvertUtil.transformObj(kbModelDomainDto,KbModelDomainEntity.class);
        if(kbModelDomainService.updateById(kbModelDomainEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("值域字典表删除")
    @PostMapping("/knowledge/kbModelDomain/delete")
    @ResponseBody
    public RespBody<String> deleteKbModelDomain(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelDomainService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 值域字典表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("值域字典表批量删除")
    @PostMapping("/knowledge/kbModelDomain/deleteBatch")
    @ResponseBody
    public RespBody<String> deleteKbModelDomainBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelDomainService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表详情
     * @param id 值域字典表id
     * @return
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("值域字典表详情")
    @GetMapping("/knowledge/kbModelDomain/info")
    @ResponseBody
    public RespBody<KbModelDomainDto> infoKbModelDomain(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        KbModelDomainDto kbModelDomainDto =  kbModelDomainService.getByIdExtends(id);

        return RespBody.data(kbModelDomainDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="值域字典表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/knowledge/kbModelDomain/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<KbModelDomainDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<KbModelDomainDto> list = kbModelDomainService.listKbModelDomain(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
