package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAddressDto;
import com.castle.fortress.admin.member.entity.MemberAddressEntity;
import com.castle.fortress.admin.member.service.MemberAddressService;
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
 * 会员收货地址表 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags="会员收货地址表管理控制器")
@Controller
public class MemberAddressController {
    @Autowired
    private MemberAddressService memberAddressService;

    /**
     * 会员收货地址表的分页展示
     * @param memberAddressDto 会员收货地址表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员地址分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员收货地址表分页展示")
    @GetMapping("/member/memberAddress/page")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:pageList")
    public RespBody<IPage<MemberAddressDto>> pageMemberAddress(MemberAddressDto memberAddressDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberAddressDto> page = new Page(pageIndex, pageSize);
        IPage<MemberAddressDto> pages = memberAddressService.pageMemberAddressExtends(page, memberAddressDto);

        return RespBody.data(pages);
    }

    /**
     * 会员收货地址表的列表展示
     * @param memberAddressDto 会员收货地址表实体类
     * @return
     */
    @CastleLog(operLocation="会员地址列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员收货地址表列表展示")
    @GetMapping("/member/memberAddress/list")
    @ResponseBody
    public RespBody<List<MemberAddressDto>> listMemberAddress(MemberAddressDto memberAddressDto){
        List<MemberAddressDto> list = memberAddressService.listMemberAddress(memberAddressDto);
        return RespBody.data(list);
    }

    /**
     * 会员收货地址表保存
     * @param memberAddressDto 会员收货地址表实体类
     * @return
     */
    @CastleLog(operLocation="会员地址保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("会员收货地址表保存")
    @PostMapping("/member/memberAddress/save")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:save")
    public RespBody<String> saveMemberAddress(@RequestBody MemberAddressDto memberAddressDto){
        if(memberAddressDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto,MemberAddressEntity.class);
        if(memberAddressService.save(memberAddressEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表编辑
     * @param memberAddressDto 会员收货地址表实体类
     * @return
     */
    @CastleLog(operLocation="会员地址编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("会员收货地址表编辑")
    @PostMapping("/member/memberAddress/edit")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:edit")
    public RespBody<String> updateMemberAddress(@RequestBody MemberAddressDto memberAddressDto){
        if(memberAddressDto == null || memberAddressDto.getId() == null || memberAddressDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressEntity memberAddressEntity = ConvertUtil.transformObj(memberAddressDto,MemberAddressEntity.class);
        if(memberAddressService.updateById(memberAddressEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表删除
     * @param id
     * @return
     */
    @CastleLog(operLocation="会员地址删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("会员收货地址表删除")
    @PostMapping("/member/memberAddress/delete")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:delete")
    public RespBody<String> deleteMemberAddress(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAddressService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员收货地址表批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="会员地址批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("会员收货地址表批量删除")
    @PostMapping("/member/memberAddress/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:deleteBatch")
    public RespBody<String> deleteMemberAddressBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAddressService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员收货地址表详情
     * @param id 会员收货地址表id
     * @return
     */
    @CastleLog(operLocation="会员地址详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("会员收货地址表详情")
    @GetMapping("/member/memberAddress/info")
    @ResponseBody
    @RequiresPermissions("member:memberAddress:info")
    public RespBody<MemberAddressDto> infoMemberAddress(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAddressDto memberAddressDto =  memberAddressService.getByIdExtends(id);

        return RespBody.data(memberAddressDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @CastleLog(operLocation="会员地址导出",operType= OperationTypeEnum.EXPORT)
	@PostMapping("/member/memberAddress/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberAddressDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberAddressDto> list = memberAddressService.listMemberAddress(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
