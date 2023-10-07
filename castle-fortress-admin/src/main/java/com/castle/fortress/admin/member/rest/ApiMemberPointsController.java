package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberPointsDto;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.service.MemberPointsService;
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
 * 会员积分表 api 控制器
 *
 * @author Mgg
 * @since 2021-11-27
 */
@Api(tags="会员积分表api管理控制器")
@RestController
@RequestMapping("/api/member/memberPoints")
public class ApiMemberPointsController {
    @Autowired
    private MemberPointsService memberPointsService;


    /**
     * 会员积分表的分页展示
     * @param memberPointsDto 会员积分表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员积分表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberPointsDto>> pageMemberPoints(MemberPointsDto memberPointsDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberPointsDto> page = new Page(pageIndex, pageSize);

        IPage<MemberPointsDto> pages = memberPointsService.pageMemberPoints(page, memberPointsDto);
        return RespBody.data(pages);
    }

    /**
     * 会员积分表保存
     * @param memberPointsDto 会员积分表实体类
     * @return
     */
    @ApiOperation("会员积分表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberPoints(@RequestBody MemberPointsDto memberPointsDto){
        if(memberPointsDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberPointsEntity memberPointsEntity = ConvertUtil.transformObj(memberPointsDto,MemberPointsEntity.class);
        if(memberPointsService.save(memberPointsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表编辑
     * @param memberPointsDto 会员积分表实体类
     * @return
     */
    @ApiOperation("会员积分表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberPoints(@RequestBody MemberPointsDto memberPointsDto){
        if(memberPointsDto == null || memberPointsDto.getId() == null || memberPointsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberPointsEntity memberPointsEntity = ConvertUtil.transformObj(memberPointsDto,MemberPointsEntity.class);
        if(memberPointsService.updateById(memberPointsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表删除
     * @param ids 会员积分表id集合
     * @return
     */
    @ApiOperation("会员积分表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberPoints(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberPointsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员积分表详情
     * @param id 会员积分表id
     * @return
     */
    @ApiOperation("会员积分表详情")
    @GetMapping("/info")
    public RespBody<MemberPointsDto> infoMemberPoints(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberPointsEntity memberPointsEntity = memberPointsService.getById(id);
            MemberPointsDto memberPointsDto = ConvertUtil.transformObj(memberPointsEntity,MemberPointsDto.class);
        return RespBody.data(memberPointsDto);
    }


}
