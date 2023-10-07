package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberLevelEntity;
import com.castle.fortress.admin.member.dto.MemberLevelDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.member.service.MemberLevelService;
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
 * 会员等级表 api 控制器
 *
 * @author whc
 * @since 2022-12-29
 */
@Api(tags="会员等级表api管理控制器")
@RestController
@RequestMapping("/api/member/memberLevel")
public class ApiMemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;


    /**
     * 会员等级表的分页展示
     * @param memberLevelDto 会员等级表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="会员等级表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员等级表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberLevelDto>> pageMemberLevel(MemberLevelDto memberLevelDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberLevelDto> page = new Page(pageIndex, pageSize);

        IPage<MemberLevelDto> pages = memberLevelService.pageMemberLevel(page, memberLevelDto);
        return RespBody.data(pages);
    }

    /**
     * 会员等级表保存
     * @param memberLevelDto 会员等级表实体类
     * @return
     */
    @CastleLog(operLocation="会员等级表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("会员等级表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberLevel(@RequestBody MemberLevelDto memberLevelDto){
        if(memberLevelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
        if(memberLevelService.save(memberLevelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表编辑
     * @param memberLevelDto 会员等级表实体类
     * @return
     */
    @CastleLog(operLocation="会员等级表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("会员等级表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberLevel(@RequestBody MemberLevelDto memberLevelDto){
        if(memberLevelDto == null || memberLevelDto.getId() == null || memberLevelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLevelEntity memberLevelEntity = ConvertUtil.transformObj(memberLevelDto,MemberLevelEntity.class);
        if(memberLevelService.updateById(memberLevelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表删除
     * @param ids 会员等级表id集合
     * @return
     */
    @CastleLog(operLocation="会员等级表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("会员等级表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberLevel(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberLevelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 会员等级表详情
     * @param id 会员等级表id
     * @return
     */
    @CastleLog(operLocation="会员等级表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("会员等级表详情")
    @GetMapping("/info")
    public RespBody<MemberLevelDto> infoMemberLevel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberLevelEntity memberLevelEntity = memberLevelService.getById(id);
            MemberLevelDto memberLevelDto = ConvertUtil.transformObj(memberLevelEntity,MemberLevelDto.class);
        return RespBody.data(memberLevelDto);
    }


}
