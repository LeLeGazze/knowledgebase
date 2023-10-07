package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbWarehouseAuthService;
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
 * 主题知识仓库权限表 api 控制器
 *
 * @author 
 * @since 2023-04-24
 */
@Api(tags="主题知识仓库权限表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbWarehouseAuth")
public class ApiKbWarehouseAuthController {
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;


    /**
     * 主题知识仓库权限表的分页展示
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="主题知识仓库权限表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbWarehouseAuthDto>> pageKbWarehouseAuth(KbWarehouseAuthDto kbWarehouseAuthDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbWarehouseAuthDto> page = new Page(pageIndex, pageSize);

        IPage<KbWarehouseAuthDto> pages = kbWarehouseAuthService.pageKbWarehouseAuth(page, kbWarehouseAuthDto);
        return RespBody.data(pages);
    }

    /**
     * 主题知识仓库权限表保存
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @return
     */
    @CastleLog(operLocation="主题知识仓库权限表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("主题知识仓库权限表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbWarehouseAuth(@RequestBody KbWarehouseAuthDto kbWarehouseAuthDto){
        if(kbWarehouseAuthDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthDto,KbWarehouseAuthEntity.class);
        if(kbWarehouseAuthService.save(kbWarehouseAuthEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表编辑
     * @param kbWarehouseAuthDto 主题知识仓库权限表实体类
     * @return
     */
    @CastleLog(operLocation="主题知识仓库权限表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("主题知识仓库权限表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbWarehouseAuth(@RequestBody KbWarehouseAuthDto kbWarehouseAuthDto){
        if(kbWarehouseAuthDto == null || kbWarehouseAuthDto.getId() == null || kbWarehouseAuthDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbWarehouseAuthEntity kbWarehouseAuthEntity = ConvertUtil.transformObj(kbWarehouseAuthDto,KbWarehouseAuthEntity.class);
        if(kbWarehouseAuthService.updateById(kbWarehouseAuthEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表删除
     * @param ids 主题知识仓库权限表id集合
     * @return
     */
    @CastleLog(operLocation="主题知识仓库权限表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("主题知识仓库权限表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbWarehouseAuth(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbWarehouseAuthService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 主题知识仓库权限表详情
     * @param id 主题知识仓库权限表id
     * @return
     */
    @CastleLog(operLocation="主题知识仓库权限表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("主题知识仓库权限表详情")
    @GetMapping("/info")
    public RespBody<KbWarehouseAuthDto> infoKbWarehouseAuth(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbWarehouseAuthEntity kbWarehouseAuthEntity = kbWarehouseAuthService.getById(id);
            KbWarehouseAuthDto kbWarehouseAuthDto = ConvertUtil.transformObj(kbWarehouseAuthEntity,KbWarehouseAuthDto.class);
        return RespBody.data(kbWarehouseAuthDto);
    }


}
