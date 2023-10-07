package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicTrashEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicTrashDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbBasicTrashService;
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
 * 知识回收表 api 控制器
 *
 * @author 
 * @since 2023-06-01
 */
@Api(tags="知识回收表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBasicTrash")
public class ApiKbBasicTrashController {
    @Autowired
    private KbBasicTrashService kbBasicTrashService;


    /**
     * 知识回收表的分页展示
     * @param kbBaseShowDto 知识回收表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识回收表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识回收表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbModelTransmitDto>> pageKbBasicTrash(KbBaseShowDto kbBaseShowDto,
                                                                @RequestParam(required = false) Integer currentPage,
                                                                @RequestParam(required = false)Integer size){
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBasicTrashDto> page = new Page(pageIndex, pageSize);
        IPage<KbModelTransmitDto> pages = kbBasicTrashService.pageKbBasicTrash(page, kbBaseShowDto,userId);
        return RespBody.data(pages);
    }

    /**
     * 知识回收表保存
     * @param kbBasicTrashDto 知识回收表实体类
     * @return
     */
    @CastleLog(operLocation="知识回收表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识回收表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBasicTrash(@RequestBody KbBasicTrashDto kbBasicTrashDto){
        if(kbBasicTrashDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicTrashEntity kbBasicTrashEntity = ConvertUtil.transformObj(kbBasicTrashDto,KbBasicTrashEntity.class);
        if(kbBasicTrashService.save(kbBasicTrashEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表编辑
     * @param kbBasicTrashDto 知识回收表实体类
     * @return
     */
    @CastleLog(operLocation="知识回收表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识回收表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBasicTrash(@RequestBody KbBasicTrashDto kbBasicTrashDto){
        if(kbBasicTrashDto == null || kbBasicTrashDto.getId() == null || kbBasicTrashDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicTrashEntity kbBasicTrashEntity = ConvertUtil.transformObj(kbBasicTrashDto,KbBasicTrashEntity.class);
        if(kbBasicTrashService.updateById(kbBasicTrashEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表删除
     * @param ids 知识回收表id集合
     * @return
     */
    @CastleLog(operLocation="知识回收表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识回收表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBasicTrash(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBasicTrashService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识回收表详情
     * @param id 知识回收表id
     * @return
     */
    @CastleLog(operLocation="知识回收表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识回收表详情")
    @GetMapping("/info")
    public RespBody<KbBasicTrashDto> infoKbBasicTrash(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicTrashEntity kbBasicTrashEntity = kbBasicTrashService.getById(id);
            KbBasicTrashDto kbBasicTrashDto = ConvertUtil.transformObj(kbBasicTrashEntity,KbBasicTrashDto.class);
        return RespBody.data(kbBasicTrashDto);
    }


}
