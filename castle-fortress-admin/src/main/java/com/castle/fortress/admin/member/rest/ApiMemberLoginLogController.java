package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberLoginLogDto;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import com.castle.fortress.admin.member.service.MemberLoginLogService;
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
 * 会员登录日志表 api 控制器
 *
 * @author Mgg
 * @since 2021-11-26
 */
@Api(tags="会员登录日志表api管理控制器")
@RestController
@RequestMapping("/api/member/memberLoginLog")
public class ApiMemberLoginLogController {
    @Autowired
    private MemberLoginLogService memberLoginLogService;


    /**
     * 会员登录日志表的分页展示
     * @param memberLoginLogDto 会员登录日志表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("会员登录日志表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberLoginLogDto>> pageMemberLoginLog(MemberLoginLogDto memberLoginLogDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberLoginLogDto> page = new Page(pageIndex, pageSize);

        IPage<MemberLoginLogDto> pages = memberLoginLogService.pageMemberLoginLog(page, memberLoginLogDto);
        return RespBody.data(pages);
    }

    /**
     * 会员登录日志表保存
     * @param memberLoginLogDto 会员登录日志表实体类
     * @return
     */
    @ApiOperation("会员登录日志表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberLoginLog(@RequestBody MemberLoginLogDto memberLoginLogDto){
        if(memberLoginLogDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLoginLogEntity memberLoginLogEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
        if(memberLoginLogService.save(memberLoginLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表编辑
     * @param memberLoginLogDto 会员登录日志表实体类
     * @return
     */
    @ApiOperation("会员登录日志表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberLoginLog(@RequestBody MemberLoginLogDto memberLoginLogDto){
        if(memberLoginLogDto == null || memberLoginLogDto.getId() == null || memberLoginLogDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLoginLogEntity memberLoginLogEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
        if(memberLoginLogService.updateById(memberLoginLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表删除
     * @param ids 会员登录日志表id集合
     * @return
     */
    @ApiOperation("会员登录日志表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberLoginLog(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLoginLogService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员登录日志表详情
     * @param id 会员登录日志表id
     * @return
     */
    @ApiOperation("会员登录日志表详情")
    @GetMapping("/info")
    public RespBody<MemberLoginLogDto> infoMemberLoginLog(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLoginLogEntity memberLoginLogEntity = memberLoginLogService.getById(id);
            MemberLoginLogDto memberLoginLogDto = ConvertUtil.transformObj(memberLoginLogEntity,MemberLoginLogDto.class);
        return RespBody.data(memberLoginLogDto);
    }


}
