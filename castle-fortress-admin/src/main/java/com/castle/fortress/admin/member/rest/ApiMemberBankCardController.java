package com.castle.fortress.admin.member.rest;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberBankCardDto;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberBankCardEntity;
import com.castle.fortress.admin.member.service.MemberBankCardService;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员银行卡表 api 控制器
 *
 * @author Mgg
 * @since 2021-12-03
 */
@Api(tags="会员银行卡表api管理控制器")
@RestController
@RequestMapping("/api/member/memberBankCard")
public class ApiMemberBankCardController {
    @Autowired
    private MemberBankCardService memberBankCardService;


    /**
     * 会员银行卡表的分页展示
     * @param memberBankCardDto 会员银行卡表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员银行卡表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberBankCardDto>> pageMemberBankCard(MemberBankCardDto memberBankCardDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberBankCardDto> page = new Page(pageIndex, pageSize);

        IPage<MemberBankCardDto> pages = memberBankCardService.pageMemberBankCard(page, memberBankCardDto);
        return RespBody.data(pages);
    }

    /**
     * 会员银行卡表保存
     * @param memberBankCardDto 会员银行卡表实体类
     * @return
     */
    @ApiOperation("会员银行卡表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberBankCard(@RequestBody MemberBankCardDto memberBankCardDto){
        if(memberBankCardDto == null || StrUtil.isEmpty(memberBankCardDto.getCardNum())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        MemberBankCardDto bankCardDto = memberBankCardService.getByCardNum(memberBankCardDto.getCardNum());
        if(bankCardDto != null){
            return RespBody.fail("该银行卡已被绑定");
        }
        MemberDto memberDto = WebUtil.currentMember();
        if (memberDto == null || memberDto.getId() == null){
            return RespBody.fail(BizErrorCode.MEMBER_INFO_PAST);
        }
        memberBankCardDto.setMemberId(memberDto.getId());
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
    @ApiOperation("会员银行卡表编辑")
    @PostMapping("/edit")
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
     * @param ids 会员银行卡表id集合
     * @return
     */
    @ApiOperation("会员银行卡表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberBankCard(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @ApiOperation("会员银行卡表详情")
    @GetMapping("/info")
    public RespBody<MemberBankCardDto> infoMemberBankCard(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberBankCardEntity memberBankCardEntity = memberBankCardService.getById(id);
            MemberBankCardDto memberBankCardDto = ConvertUtil.transformObj(memberBankCardEntity,MemberBankCardDto.class);
        return RespBody.data(memberBankCardDto);
    }

    /**
     * 会员银行卡表详情
     * @return
     */
    @ApiOperation("会员银行卡表详情")
    @GetMapping("/listByMember")
    public RespBody<List<MemberBankCardDto>> infoMemberBankCard(){
        return RespBody.data(memberBankCardService.listByMemberId(WebUtil.currentMember().getId()));
    }

}
