package com.castle.fortress.admin.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAccountDto;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;
import com.castle.fortress.admin.member.service.MemberAccountService;
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
 * 会员账户表 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags="会员账户表管理控制器")
@Controller
public class MemberAccountController {
    @Autowired
    private MemberAccountService memberAccountService;

    /**
     * 会员账户表的分页展示
     * @param memberAccountDto 会员账户表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员账户表分页展示")
    @GetMapping("/member/memberAccount/page")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:pageList")
    public RespBody<IPage<MemberAccountDto>> pageMemberAccount(MemberAccountDto memberAccountDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberAccountDto> page = new Page(pageIndex, pageSize);
        IPage<MemberAccountDto> pages = memberAccountService.pageMemberAccount(page, memberAccountDto);

        return RespBody.data(pages);
    }

    /**
     * 会员账户表的列表展示
     * @param memberAccountDto 会员账户表实体类
     * @return
     */
    @ApiOperation("会员账户表列表展示")
    @GetMapping("/member/memberAccount/list")
    @ResponseBody
    public RespBody<List<MemberAccountDto>> listMemberAccount(MemberAccountDto memberAccountDto){
        List<MemberAccountDto> list = memberAccountService.listMemberAccount(memberAccountDto);
        return RespBody.data(list);
    }

    /**
     * 会员账户表保存
     * @param memberAccountDto 会员账户表实体类
     * @return
     */
    @ApiOperation("会员账户表保存")
    @PostMapping("/member/memberAccount/save")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:save")
    public RespBody<String> saveMemberAccount(@RequestBody MemberAccountDto memberAccountDto){
        if(memberAccountDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountEntity memberAccountEntity = ConvertUtil.transformObj(memberAccountDto,MemberAccountEntity.class);
        if(memberAccountService.save(memberAccountEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户表编辑
     * @param memberAccountDto 会员账户表实体类
     * @return
     */
    @ApiOperation("会员账户表编辑")
    @PostMapping("/member/memberAccount/edit")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:edit")
    public RespBody<String> updateMemberAccount(@RequestBody MemberAccountDto memberAccountDto){
        if(memberAccountDto == null || memberAccountDto.getId() == null || memberAccountDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountEntity memberAccountEntity = ConvertUtil.transformObj(memberAccountDto,MemberAccountEntity.class);
        if(memberAccountService.updateById(memberAccountEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户表删除
     * @param id
     * @return
     */
    @ApiOperation("会员账户表删除")
    @PostMapping("/member/memberAccount/delete")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:delete")
    public RespBody<String> deleteMemberAccount(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAccountService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 会员账户表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("会员账户表批量删除")
    @PostMapping("/member/memberAccount/deleteBatch")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:deleteBatch")
    public RespBody<String> deleteMemberAccountBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberAccountService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员账户表详情
     * @param id 会员账户表id
     * @return
     */
    @ApiOperation("会员账户表详情")
    @GetMapping("/member/memberAccount/info")
    @ResponseBody
    @RequiresPermissions("member:memberAccount:info")
    public RespBody<MemberAccountDto> infoMemberAccount(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberAccountEntity memberAccountEntity = memberAccountService.getById(id);
        MemberAccountDto memberAccountDto = ConvertUtil.transformObj(memberAccountEntity,MemberAccountDto.class);

        return RespBody.data(memberAccountDto);
    }
    /**
     * 根据memberId获取账户详情
     * @param memberId
     * @return
     */
    @ApiOperation("根据memberId获取账户详情")
    @GetMapping("/member/memberAccount/infoByMemberId")
    @ResponseBody
    public RespBody<MemberAccountDto> infoMemberAccountByMemberId(@RequestParam Long memberId){
        if(memberId == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        final QueryWrapper<MemberAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.last("limit 1");
        MemberAccountEntity memberAccountEntity = memberAccountService.getOne(queryWrapper);
        MemberAccountDto memberAccountDto = ConvertUtil.transformObj(memberAccountEntity,MemberAccountDto.class);

        return RespBody.data(memberAccountDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/member/memberAccount/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<MemberAccountDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<MemberAccountDto> list = memberAccountService.listMemberAccount(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}


}
