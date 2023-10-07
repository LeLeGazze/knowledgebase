package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbModelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelCategoryDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbModelCategoryService;
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
 * 模型分类管理 api 控制器
 *
 * @author Pan Chen
 * @since 2023-04-10
 */
@Api(tags="模型分类管理api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbModelCategory")
public class ApiKbModelCategoryController {
    @Autowired
    private KbModelCategoryService kbModelCategoryService;


    /**
     * 模型分类管理的分页展示
     * @param kbModelCategoryDto 模型分类管理实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="模型分类管理-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("模型分类管理分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbModelCategoryDto>> pageKbModelCategory(KbModelCategoryDto kbModelCategoryDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbModelCategoryDto> page = new Page(pageIndex, pageSize);

        IPage<KbModelCategoryDto> pages = kbModelCategoryService.pageKbModelCategory(page, kbModelCategoryDto);
        return RespBody.data(pages);
    }

    /**
     * 模型分类管理保存
     * @param kbModelCategoryDto 模型分类管理实体类
     * @return
     */
    @CastleLog(operLocation="模型分类管理-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("模型分类管理保存")
    @PostMapping("/save")
    public RespBody<String> saveKbModelCategory(@RequestBody KbModelCategoryDto kbModelCategoryDto){
        if(kbModelCategoryDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto,KbModelCategoryEntity.class);
        if(kbModelCategoryService.save(kbModelCategoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理编辑
     * @param kbModelCategoryDto 模型分类管理实体类
     * @return
     */
    @CastleLog(operLocation="模型分类管理-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("模型分类管理编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbModelCategory(@RequestBody KbModelCategoryDto kbModelCategoryDto){
        if(kbModelCategoryDto == null || kbModelCategoryDto.getId() == null || kbModelCategoryDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelCategoryEntity kbModelCategoryEntity = ConvertUtil.transformObj(kbModelCategoryDto,KbModelCategoryEntity.class);
        if(kbModelCategoryService.updateById(kbModelCategoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理删除
     * @param ids 模型分类管理id集合
     * @return
     */
    @CastleLog(operLocation="模型分类管理-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("模型分类管理删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbModelCategory(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelCategoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 模型分类管理详情
     * @param id 模型分类管理id
     * @return
     */
    @CastleLog(operLocation="模型分类管理-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("模型分类管理详情")
    @GetMapping("/info")
    public RespBody<KbModelCategoryDto> infoKbModelCategory(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelCategoryEntity kbModelCategoryEntity = kbModelCategoryService.getById(id);
            KbModelCategoryDto kbModelCategoryDto = ConvertUtil.transformObj(kbModelCategoryEntity,KbModelCategoryDto.class);
        return RespBody.data(kbModelCategoryDto);
    }


}
