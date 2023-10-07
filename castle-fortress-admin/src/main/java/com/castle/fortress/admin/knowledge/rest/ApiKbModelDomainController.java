package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbModelDomainEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelDomainDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbModelDomainService;
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
 * 值域字典表 api 控制器
 *
 * @author 
 * @since 2023-04-20
 */
@Api(tags="值域字典表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbModelDomain")
public class ApiKbModelDomainController {
    @Autowired
    private KbModelDomainService kbModelDomainService;


    /**
     * 值域字典表的分页展示
     * @param kbModelDomainDto 值域字典表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="值域字典表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("值域字典表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbModelDomainDto>> pageKbModelDomain(KbModelDomainDto kbModelDomainDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbModelDomainDto> page = new Page(pageIndex, pageSize);

        IPage<KbModelDomainDto> pages = kbModelDomainService.pageKbModelDomain(page, kbModelDomainDto);
        return RespBody.data(pages);
    }

    /**
     * 值域字典表保存
     * @param kbModelDomainDto 值域字典表实体类
     * @return
     */
    @CastleLog(operLocation="值域字典表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("值域字典表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbModelDomain(@RequestBody KbModelDomainDto kbModelDomainDto){
        if(kbModelDomainDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelDomainEntity kbModelDomainEntity = ConvertUtil.transformObj(kbModelDomainDto,KbModelDomainEntity.class);
        if(kbModelDomainService.save(kbModelDomainEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表编辑
     * @param kbModelDomainDto 值域字典表实体类
     * @return
     */
    @CastleLog(operLocation="值域字典表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("值域字典表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbModelDomain(@RequestBody KbModelDomainDto kbModelDomainDto){
        if(kbModelDomainDto == null || kbModelDomainDto.getId() == null || kbModelDomainDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelDomainEntity kbModelDomainEntity = ConvertUtil.transformObj(kbModelDomainDto,KbModelDomainEntity.class);
        if(kbModelDomainService.updateById(kbModelDomainEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表删除
     * @param ids 值域字典表id集合
     * @return
     */
    @CastleLog(operLocation="值域字典表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("值域字典表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbModelDomain(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelDomainService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 值域字典表详情
     * @param id 值域字典表id
     * @return
     */
    @CastleLog(operLocation="值域字典表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("值域字典表详情")
    @GetMapping("/info")
    public RespBody<KbModelDomainDto> infoKbModelDomain(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelDomainEntity kbModelDomainEntity = kbModelDomainService.getById(id);
            KbModelDomainDto kbModelDomainDto = ConvertUtil.transformObj(kbModelDomainEntity,KbModelDomainDto.class);
        return RespBody.data(kbModelDomainDto);
    }


}
