package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import com.castle.fortress.admin.member.dto.MemberWxDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.member.service.MemberWxService;
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
 * 微信会员表 api 控制器
 *
 * @author whc
 * @since 2022-11-28
 */
@Api(tags="微信会员表api管理控制器")
@RestController
@RequestMapping("/api/member/memberWx")
public class ApiMemberWxController {
    @Autowired
    private MemberWxService memberWxService;


    /**
     * 微信会员表的分页展示
     * @param memberWxDto 微信会员表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="微信会员表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("微信会员表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberWxDto>> pageMemberWx(MemberWxDto memberWxDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberWxDto> page = new Page(pageIndex, pageSize);

        IPage<MemberWxDto> pages = memberWxService.pageMemberWx(page, memberWxDto);
        return RespBody.data(pages);
    }

    /**
     * 微信会员表保存
     * @param memberWxDto 微信会员表实体类
     * @return
     */
    @CastleLog(operLocation="微信会员表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("微信会员表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberWx(@RequestBody MemberWxDto memberWxDto){
        if(memberWxDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto,MemberWxEntity.class);
        if(memberWxService.save(memberWxEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表编辑
     * @param memberWxDto 微信会员表实体类
     * @return
     */
    @CastleLog(operLocation="微信会员表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("微信会员表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberWx(@RequestBody MemberWxDto memberWxDto){
        if(memberWxDto == null || memberWxDto.getId() == null || memberWxDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberWxEntity memberWxEntity = ConvertUtil.transformObj(memberWxDto,MemberWxEntity.class);
        if(memberWxService.updateById(memberWxEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表删除
     * @param ids 微信会员表id集合
     * @return
     */
    @CastleLog(operLocation="微信会员表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("微信会员表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberWx(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberWxService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 微信会员表详情
     * @param id 微信会员表id
     * @return
     */
    @CastleLog(operLocation="微信会员表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("微信会员表详情")
    @GetMapping("/info")
    public RespBody<MemberWxDto> infoMemberWx(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberWxEntity memberWxEntity = memberWxService.getById(id);
            MemberWxDto memberWxDto = ConvertUtil.transformObj(memberWxEntity,MemberWxDto.class);
        return RespBody.data(memberWxDto);
    }


}
