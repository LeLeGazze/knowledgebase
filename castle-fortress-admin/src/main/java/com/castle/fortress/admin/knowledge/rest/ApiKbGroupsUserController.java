package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbGroupsUserService;
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
 * 知识库用户组与用户关联关系管理 api 控制器
 *
 * @author 
 * @since 2023-04-22
 */
@Api(tags="知识库用户组与用户关联关系管理api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbGroupsUser")
public class ApiKbGroupsUserController {
    @Autowired
    private KbGroupsUserService kbGroupsUserService;


    /**
     * 知识库用户组与用户关联关系管理的分页展示
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识库用户组与用户关联关系管理-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组与用户关联关系管理分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbGroupsUserDto>> pageKbGroupsUser(KbGroupsUserDto kbGroupsUserDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbGroupsUserDto> page = new Page(pageIndex, pageSize);

        IPage<KbGroupsUserDto> pages = kbGroupsUserService.pageKbGroupsUser(page, kbGroupsUserDto);
        return RespBody.data(pages);
    }

    /**
     * 知识库用户组与用户关联关系管理保存
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @return
     */
    @CastleLog(operLocation="知识库用户组与用户关联关系管理-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库用户组与用户关联关系管理保存")
    @PostMapping("/save")
    public RespBody<String> saveKbGroupsUser(@RequestBody KbGroupsUserDto kbGroupsUserDto){
        if(kbGroupsUserDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbGroupsUserEntity kbGroupsUserEntity = ConvertUtil.transformObj(kbGroupsUserDto,KbGroupsUserEntity.class);
        if(kbGroupsUserService.save(kbGroupsUserEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组与用户关联关系管理编辑
     * @param kbGroupsUserDto 知识库用户组与用户关联关系管理实体类
     * @return
     */
    @CastleLog(operLocation="知识库用户组与用户关联关系管理-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库用户组与用户关联关系管理编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbGroupsUser(@RequestBody KbGroupsUserDto kbGroupsUserDto){
        if(kbGroupsUserDto == null || kbGroupsUserDto.getId() == null || kbGroupsUserDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbGroupsUserEntity kbGroupsUserEntity = ConvertUtil.transformObj(kbGroupsUserDto,KbGroupsUserEntity.class);
        if(kbGroupsUserService.updateById(kbGroupsUserEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组与用户关联关系管理删除
     * @param ids 知识库用户组与用户关联关系管理id集合
     * @return
     */
    @CastleLog(operLocation="知识库用户组与用户关联关系管理-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库用户组与用户关联关系管理删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbGroupsUser(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbGroupsUserService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库用户组与用户关联关系管理详情
     * @param id 知识库用户组与用户关联关系管理id
     * @return
     */
    @CastleLog(operLocation="知识库用户组与用户关联关系管理-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库用户组与用户关联关系管理详情")
    @GetMapping("/info")
    public RespBody<KbGroupsUserDto> infoKbGroupsUser(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbGroupsUserEntity kbGroupsUserEntity = kbGroupsUserService.getById(id);
            KbGroupsUserDto kbGroupsUserDto = ConvertUtil.transformObj(kbGroupsUserEntity,KbGroupsUserDto.class);
        return RespBody.data(kbGroupsUserDto);
    }


}
