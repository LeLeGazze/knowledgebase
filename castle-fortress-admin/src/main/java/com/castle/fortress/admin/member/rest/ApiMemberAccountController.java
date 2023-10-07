package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberAccountDto;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;
import com.castle.fortress.admin.member.service.MemberAccountService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员账户表 api 控制器
 *
 * @author Mgg
 * @since 2021-12-02
 */
@Api(tags="会员账户表api管理控制器")
@RestController
@RequestMapping("/api/member/memberAccount")
public class ApiMemberAccountController {
    @Autowired
    private MemberAccountService memberAccountService;


    /**
     * 会员账户表的分页展示
     * @param memberAccountDto 会员账户表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员账户表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberAccountDto>> pageMemberAccount(MemberAccountDto memberAccountDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberAccountDto> page = new Page(pageIndex, pageSize);

        IPage<MemberAccountDto> pages = memberAccountService.pageMemberAccount(page, memberAccountDto);
        return RespBody.data(pages);
    }

    /**
     * 会员账户表保存
     * @param memberAccountDto 会员账户表实体类
     * @return
     */
    @ApiOperation("会员账户表保存")
    @PostMapping("/save")
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
    @PostMapping("/edit")
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
     * @param ids 会员账户表id集合
     * @return
     */
    @ApiOperation("会员账户表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberAccount(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<MemberAccountDto> infoMemberAccount(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberAccountEntity memberAccountEntity = memberAccountService.getById(id);
            MemberAccountDto memberAccountDto = ConvertUtil.transformObj(memberAccountEntity,MemberAccountDto.class);
        return RespBody.data(memberAccountDto);
    }


}
