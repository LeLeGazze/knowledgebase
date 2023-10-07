package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBasicHistoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicHistoryDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbBasicHistoryService;
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
 * 知识基本表历史表 api 控制器
 *
 * @author 
 * @since 2023-07-03
 */
@Api(tags="知识基本表历史表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBasicHistory")
public class ApiKbBasicHistoryController {
    @Autowired
    private KbBasicHistoryService kbBasicHistoryService;


    /**
     * 知识基本表历史表的分页展示
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识基本表历史表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本表历史表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbBasicHistoryDto>> pageKbBasicHistory(KbBasicHistoryDto kbBasicHistoryDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBasicHistoryDto> page = new Page(pageIndex, pageSize);

        IPage<KbBasicHistoryDto> pages = kbBasicHistoryService.pageKbBasicHistory(page, kbBasicHistoryDto);
        return RespBody.data(pages);
    }

    /**
     * 知识基本表历史表保存
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @return
     */
    @CastleLog(operLocation="知识基本表历史表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识基本表历史表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBasicHistory(@RequestBody KbBasicHistoryDto kbBasicHistoryDto){
        if(kbBasicHistoryDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto,KbBasicHistoryEntity.class);
        if(kbBasicHistoryService.save(kbBasicHistoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表编辑
     * @param kbBasicHistoryDto 知识基本表历史表实体类
     * @return
     */
    @CastleLog(operLocation="知识基本表历史表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识基本表历史表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBasicHistory(@RequestBody KbBasicHistoryDto kbBasicHistoryDto){
        if(kbBasicHistoryDto == null || kbBasicHistoryDto.getId() == null || kbBasicHistoryDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicHistoryEntity kbBasicHistoryEntity = ConvertUtil.transformObj(kbBasicHistoryDto,KbBasicHistoryEntity.class);
        if(kbBasicHistoryService.updateById(kbBasicHistoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表删除
     * @param ids 知识基本表历史表id集合
     * @return
     */
    @CastleLog(operLocation="知识基本表历史表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识基本表历史表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBasicHistory(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBasicHistoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识基本表历史表详情
     * @param id 知识基本表历史表id
     * @return
     */
    @CastleLog(operLocation="知识基本表历史表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识基本表历史表详情")
    @GetMapping("/info")
    public RespBody<KbBasicHistoryDto> infoKbBasicHistory(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicHistoryEntity kbBasicHistoryEntity = kbBasicHistoryService.getById(id);
            KbBasicHistoryDto kbBasicHistoryDto = ConvertUtil.transformObj(kbBasicHistoryEntity,KbBasicHistoryDto.class);
        return RespBody.data(kbBasicHistoryDto);
    }


}
