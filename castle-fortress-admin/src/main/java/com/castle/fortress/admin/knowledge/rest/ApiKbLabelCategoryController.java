package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbLabelCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbLabelCategoryDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbLabelCategoryService;
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
 * 标签分类表 api 控制器
 *
 * @author 
 * @since 2023-06-14
 */
@Api(tags="标签分类表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbLabelCategory")
public class ApiKbLabelCategoryController {
    @Autowired
    private KbLabelCategoryService kbLabelCategoryService;


    /**
     * 标签分类表的分页展示
     * @param kbLabelCategoryDto 标签分类表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签分类表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分类表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbLabelCategoryDto>> pageKbLabelCategory(KbLabelCategoryDto kbLabelCategoryDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbLabelCategoryDto> page = new Page(pageIndex, pageSize);

        IPage<KbLabelCategoryDto> pages = kbLabelCategoryService.pageKbLabelCategory(page, kbLabelCategoryDto);
        return RespBody.data(pages);
    }

    /**
     * 标签分类表保存
     * @param kbLabelCategoryDto 标签分类表实体类
     * @return
     */
    @CastleLog(operLocation="标签分类表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签分类表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbLabelCategory(@RequestBody KbLabelCategoryDto kbLabelCategoryDto){
        if(kbLabelCategoryDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbLabelCategoryEntity kbLabelCategoryEntity = ConvertUtil.transformObj(kbLabelCategoryDto,KbLabelCategoryEntity.class);
        if(kbLabelCategoryService.save(kbLabelCategoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分类表编辑
     * @param kbLabelCategoryDto 标签分类表实体类
     * @return
     */
    @CastleLog(operLocation="标签分类表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签分类表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbLabelCategory(@RequestBody KbLabelCategoryDto kbLabelCategoryDto){
        if(kbLabelCategoryDto == null || kbLabelCategoryDto.getId() == null || kbLabelCategoryDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbLabelCategoryEntity kbLabelCategoryEntity = ConvertUtil.transformObj(kbLabelCategoryDto,KbLabelCategoryEntity.class);
        if(kbLabelCategoryService.updateById(kbLabelCategoryEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分类表删除
     * @param ids 标签分类表id集合
     * @return
     */
    @CastleLog(operLocation="标签分类表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分类表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbLabelCategory(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbLabelCategoryService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分类表详情
     * @param id 标签分类表id
     * @return
     */
    @CastleLog(operLocation="标签分类表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分类表详情")
    @GetMapping("/info")
    public RespBody<KbLabelCategoryDto> infoKbLabelCategory(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbLabelCategoryEntity kbLabelCategoryEntity = kbLabelCategoryService.getById(id);
            KbLabelCategoryDto kbLabelCategoryDto = ConvertUtil.transformObj(kbLabelCategoryEntity,KbLabelCategoryDto.class);
        return RespBody.data(kbLabelCategoryDto);
    }


}
