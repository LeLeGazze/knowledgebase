package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberBankCardDto;
import com.castle.fortress.admin.member.entity.MemberBankCardEntity;
import com.castle.fortress.admin.member.service.MemberBankCardService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
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
 * 会员银行卡表 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员银行卡表管理控制器")
@Controller
public class MemberBankCardController {
    @Autowired
    private MemberBankCardService memberBankCardService;

    /**
     * 会员银行卡表的分页展示
     * @param memberBankCardDto 会员银行卡表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员银行卡分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员银行卡表分页展示")
    @GetMapping("/member/memberBankCard/page")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:pageList")
    public RespBody<IPage<MemberBankCardDto>> pageMemberBankCard(MemberBankCardDto memberBankCardDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberBankCardDto> page = new Page(pageIndex, pageSize);
        IPage<MemberBankCardDto> pages = memberBankCardService.pageMemberBankCardExtends(page, memberBankCardDto);

        return RespBody.data(pages);
    }

    /**
     * 会员银行卡表的列表展示
     * @param memberBankCardDto 会员银行卡表实体类
     * @return
     */
    @CastleLog(operLocation="会员银行卡列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员银行卡表列表展示")
    @GetMapping("/member/memberBankCard/list")
    @ResponseBody
    public RespBody<List<MemberBankCardDto>> listMemberBankCard(MemberBankCardDto memberBankCardDto){
        List<MemberBankCardDto> list = memberBankCardService.listMemberBankCard(memberBankCardDto);
        return RespBody.data(list);
    }

    /**
     * 会员银行卡表保存
     * @param memberBankCardDto 会员银行卡表实体类
     * @return
     */
    @CastleLog(operLocation="会员银行卡保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("会员银行卡表保存")
    @PostMapping("/member/memberBankCard/save")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:save")
    public RespBody<String> saveMemberBankCard(@RequestBody MemberBankCardDto memberBankCardDto){
        if(memberBankCardDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberBankCardEntity memberBankCardEntity = ConvertUtil.transformObj(memberBankCardDto,MemberBankCardEntity.class);
        if(memberBankCardService.save(memberBankCardEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员银行卡表编辑
     * @param memberBankCardDto 会员银行卡表实体类
     * @return
     */
    @CastleLog(operLocation="会员银行卡编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("会员银行卡表编辑")
    @PostMapping("/member/memberBankCard/edit")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:edit")
    public RespBody<String> updateMemberBankCard(@RequestBody MemberBankCardDto memberBankCardDto){
        if(memberBankCardDto == null || memberBankCardDto.getId() == null || memberBankCardDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberBankCardEntity memberBankCardEntity = ConvertUtil.transformObj(memberBankCardDto,MemberBankCardEntity.class);
        if(memberBankCardService.updateById(memberBankCardEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员银行卡表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="会员银行卡删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("会员银行卡表删除")
    @PostMapping("/member/memberBankCard/delete")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:delete")
    public RespBody<String> deleteMemberBankCard(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberBankCardService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员银行卡表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="会员银行卡批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("会员银行卡表批量删除")
    @PostMapping("/member/memberBankCard/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:deleteBatch")
    public RespBody<String> deleteMemberBankCardBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberBankCardService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员银行卡表详情
     * @param id 会员银行卡表id
     * @return
     */
    @CastleLog(operLocation="会员银行卡详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员银行卡表详情")
    @GetMapping("/member/memberBankCard/info")
    @ResponseBody
    @RequiresPermissions("member:memberBankCard:info")
    public RespBody<MemberBankCardDto> infoMemberBankCard(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberBankCardDto memberBankCardDto =  memberBankCardService.getByIdExtends(id);

        return RespBody.data(memberBankCardDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="会员银行卡导出",operType= OperationTypeEnum.EXPORT)
	@PostMapping("/member/memberBankCard/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberBankCardDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberBankCardDto> list = memberBankCardService.listMemberBankCard(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
