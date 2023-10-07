package com.castle.fortress.admin.member.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.entity.MemberExtendFieldConfigEntity;
import com.castle.fortress.admin.member.dto.MemberExtendFieldConfigDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.member.service.MemberExtendFieldConfigService;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
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
 * 用户扩展字段配置表 api 控制器
 *
 * @author whc
 * @since 2022-11-23
 */
@Api(tags="用户扩展字段配置表api管理控制器")
@RestController
@RequestMapping("/api/member/memberExtendFieldConfig")
public class ApiMemberExtendFieldConfigController {
    @Autowired
    private MemberExtendFieldConfigService memberExtendFieldConfigService;


    /**
     * 用户扩展字段配置表的分页展示
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("用户扩展字段配置表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<MemberExtendFieldConfigDto>> pageMemberExtendFieldConfig(MemberExtendFieldConfigDto memberExtendFieldConfigDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<MemberExtendFieldConfigDto> page = new Page(pageIndex, pageSize);

        IPage<MemberExtendFieldConfigDto> pages = memberExtendFieldConfigService.pageMemberExtendFieldConfig(page, memberExtendFieldConfigDto);
        return RespBody.data(pages);
    }

    /**
     * 用户扩展字段配置表保存
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @return
     */
    @ApiOperation("用户扩展字段配置表保存")
    @PostMapping("/save")
    public RespBody<String> saveMemberExtendFieldConfig(@RequestBody MemberExtendFieldConfigDto memberExtendFieldConfigDto){
        if(memberExtendFieldConfigDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = ConvertUtil.transformObj(memberExtendFieldConfigDto,MemberExtendFieldConfigEntity.class);
        if(memberExtendFieldConfigService.save(memberExtendFieldConfigEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表编辑
     * @param memberExtendFieldConfigDto 用户扩展字段配置表实体类
     * @return
     */
    @ApiOperation("用户扩展字段配置表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateMemberExtendFieldConfig(@RequestBody MemberExtendFieldConfigDto memberExtendFieldConfigDto){
        if(memberExtendFieldConfigDto == null || memberExtendFieldConfigDto.getId() == null || memberExtendFieldConfigDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = ConvertUtil.transformObj(memberExtendFieldConfigDto,MemberExtendFieldConfigEntity.class);
        if(memberExtendFieldConfigService.updateById(memberExtendFieldConfigEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表删除
     * @param ids 用户扩展字段配置表id集合
     * @return
     */
    @ApiOperation("用户扩展字段配置表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteMemberExtendFieldConfig(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(memberExtendFieldConfigService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户扩展字段配置表详情
     * @param id 用户扩展字段配置表id
     * @return
     */
    @ApiOperation("用户扩展字段配置表详情")
    @GetMapping("/info")
    public RespBody<MemberExtendFieldConfigDto> infoMemberExtendFieldConfig(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            MemberExtendFieldConfigEntity memberExtendFieldConfigEntity = memberExtendFieldConfigService.getById(id);
            MemberExtendFieldConfigDto memberExtendFieldConfigDto = ConvertUtil.transformObj(memberExtendFieldConfigEntity,MemberExtendFieldConfigDto.class);
        return RespBody.data(memberExtendFieldConfigDto);
    }


}
