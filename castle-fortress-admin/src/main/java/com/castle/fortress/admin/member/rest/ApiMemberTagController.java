package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.dto.MemberTagDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.member.service.MemberTagService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会员标签表 api 控制器
 *
 * @author whc
 * @since 2022-12-08
 */
@Api(tags="会员标签表api管理控制器")
@RestController
@RequestMapping("/api/member/memberTag")
public class ApiMemberTagController {
    @Autowired
    private MemberTagService memberTagService;


    /**
     * 会员标签表的分页展示
     * @param memberTagDto 会员标签表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员标签表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员标签表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberTagDto>> pageMemberTag(MemberTagDto memberTagDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberTagDto> page = new Page(pageIndex, pageSize);

        IPage<MemberTagDto> pages = memberTagService.pageMemberTag(page, memberTagDto);
        return RespBody.data(pages);
    }

    /**
     * 会员标签表保存
     * @param memberTagDto 会员标签表实体类
     * @return
     */
    @CastleLog(operLocation="会员标签表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("会员标签表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberTag(@RequestBody MemberTagDto memberTagDto){
        if(memberTagDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
        if(memberTagService.save(memberTagEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表编辑
     * @param memberTagDto 会员标签表实体类
     * @return
     */
    @CastleLog(operLocation="会员标签表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("会员标签表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberTag(@RequestBody MemberTagDto memberTagDto){
        if(memberTagDto == null || memberTagDto.getId() == null || memberTagDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberTagEntity memberTagEntity = ConvertUtil.transformObj(memberTagDto,MemberTagEntity.class);
        if(memberTagService.updateById(memberTagEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表删除
     * @param ids 会员标签表id集合
     * @return
     */
    @CastleLog(operLocation="会员标签表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员标签表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberTag(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberTagService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员标签表详情
     * @param id 会员标签表id
     * @return
     */
    @CastleLog(operLocation="会员标签表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员标签表详情")
    @GetMapping("/info")
    public RespBody<MemberTagDto> infoMemberTag(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberTagEntity memberTagEntity = memberTagService.getById(id);
            MemberTagDto memberTagDto = ConvertUtil.transformObj(memberTagEntity,MemberTagDto.class);
        return RespBody.data(memberTagDto);
    }


}
