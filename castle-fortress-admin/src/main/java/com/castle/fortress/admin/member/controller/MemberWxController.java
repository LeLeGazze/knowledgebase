package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import com.castle.fortress.admin.member.dto.MemberWxDto;
import com.castle.fortress.admin.member.service.MemberWxService;
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
 * 微信会员表 控制器
 *
 * @author whc
 * @since 2022-11-28
 */
@Api(tags="微信会员表管理控制器")
@Controller
public class MemberWxController {
    @Autowired
    private MemberWxService memberWxService;

    /**
     * 微信会员表的分页展示
     * @param memberWxDto 微信会员表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("微信会员表分页展示")
    @GetMapping("/member/memberWx/page")
    @ResponseBody
    @RequiresPermissions("member:memberWx:pageList")
    public RespBody<IPage<MemberWxDto>> pageMemberWx(MemberWxDto memberWxDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberWxDto> page = new Page(pageIndex, pageSize);
        IPage<MemberWxDto> pages = memberWxService.pageMemberWxExtends(page, memberWxDto);

        return RespBody.data(pages);
    }

    /**
     * 微信会员表的列表展示
     * @param memberWxDto 微信会员表实体类
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("微信会员表列表展示")
    @GetMapping("/member/memberWx/list")
    @ResponseBody
    public RespBody<List<MemberWxDto>> listMemberWx(MemberWxDto memberWxDto){
        List<MemberWxDto> list = memberWxService.listMemberWx(memberWxDto);
        return RespBody.data(list);
    }

    /**
     * 微信会员表保存
     * @param memberWxDto 微信会员表实体类
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.INSERT)
    @ApiOperation("微信会员表保存")
    @PostMapping("/member/memberWx/save")
    @ResponseBody
    @RequiresPermissions("member:memberWx:save")
    public RespBody<String> saveMemberWx(@RequestBody MemberWxDto memberWxDto){
        if(memberWxDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto,MemberWxEntity.class);
        if(memberWxService.save(memberWxEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表编辑
     * @param memberWxDto 微信会员表实体类
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("微信会员表编辑")
    @PostMapping("/member/memberWx/edit")
    @ResponseBody
    @RequiresPermissions("member:memberWx:edit")
    public RespBody<String> updateMemberWx(@RequestBody MemberWxDto memberWxDto){
        if(memberWxDto == null || memberWxDto.getId() == null || memberWxDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto,MemberWxEntity.class);
        if(memberWxService.updateById(memberWxEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("微信会员表删除")
    @PostMapping("/member/memberWx/delete")
    @ResponseBody
    @RequiresPermissions("member:memberWx:delete")
    public RespBody<String> deleteMemberWx(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberWxService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 微信会员表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.DELETE)
    @ApiOperation("微信会员表批量删除")
    @PostMapping("/member/memberWx/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberWx:deleteBatch")
    public RespBody<String> deleteMemberWxBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberWxService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表详情
     * @param id 微信会员表id
     * @return
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.QUERY)
    @ApiOperation("微信会员表详情")
    @GetMapping("/member/memberWx/info")
    @ResponseBody
    @RequiresPermissions("member:memberWx:info")
    public RespBody<MemberWxDto> infoMemberWx(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberWxDto memberWxDto =  memberWxService.getByIdExtends(id);

        return RespBody.data(memberWxDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="微信会员表",operType = OperationTypeEnum.EXPORT)
	@PostMapping("/member/memberWx/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberWxDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberWxDto> list = memberWxService.listMemberWx(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
