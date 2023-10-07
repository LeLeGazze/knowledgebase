package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.service.MemberService;
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
 * 会员表 api 控制器
 *
 * @author Mgg
 * @since 2021-11-25
 */
@Api(tags="会员表api管理控制器")
@RestController
@RequestMapping("/api/member/member")
public class ApiMemberController {
    @Autowired
    private MemberService memberService;


    /**
     * 会员表的分页展示
     * @param memberDto 会员表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberDto>> pageMember(MemberDto memberDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberDto> page = new Page(pageIndex, pageSize);

        IPage<MemberDto> pages = memberService.pageMember(page, memberDto);
        return RespBody.data(pages);
    }

    /**
     * 会员表保存
     * @param memberDto 会员表实体类
     * @return
     */
    @ApiOperation("会员表保存")
    @PostMapping("/save")
    public RespBody<String> saveMember(@RequestBody MemberDto memberDto){
        if(memberDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberEntity memberEntity = ConvertUtil.transformObj(memberDto,MemberEntity.class);
        if(memberService.save(memberEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员表编辑
     * @param memberDto 会员表实体类
     * @return
     */
    @ApiOperation("会员表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMember(@RequestBody MemberDto memberDto){
        if(memberDto == null || memberDto.getId() == null || memberDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberEntity memberEntity = ConvertUtil.transformObj(memberDto,MemberEntity.class);
        if(memberService.updateById(memberEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员表删除
     * @param ids 会员表id集合
     * @return
     */
    @ApiOperation("会员表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMember(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员表详情
     * @param id 会员表id
     * @return
     */
    @ApiOperation("会员表详情")
    @GetMapping("/info")
    public RespBody<MemberDto> infoMember(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberEntity memberEntity = memberService.getById(id);
            MemberDto memberDto = ConvertUtil.transformObj(memberEntity,MemberDto.class);
        return RespBody.data(memberDto);
    }



}
