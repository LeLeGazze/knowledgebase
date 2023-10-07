package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbCategoryLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryLabelDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbCategoryLabelService;
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
 * 标签分组和标签关联表 api 控制器
 *
 * @author 
 * @since 2023-06-14
 */
@Api(tags="标签分组和标签关联表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbCategoryLabel")
public class ApiKbCategoryLabelController {
    @Autowired
    private KbCategoryLabelService kbCategoryLabelService;


    /**
     * 标签分组和标签关联表的分页展示
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签分组和标签关联表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分组和标签关联表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbCategoryLabelDto>> pageKbCategoryLabel(KbCategoryLabelDto kbCategoryLabelDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbCategoryLabelDto> page = new Page(pageIndex, pageSize);

        IPage<KbCategoryLabelDto> pages = kbCategoryLabelService.pageKbCategoryLabel(page, kbCategoryLabelDto);
        return RespBody.data(pages);
    }

    /**
     * 标签分组和标签关联表保存
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @return
     */
    @CastleLog(operLocation="标签分组和标签关联表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签分组和标签关联表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbCategoryLabel(@RequestBody KbCategoryLabelDto kbCategoryLabelDto){
        if(kbCategoryLabelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCategoryLabelEntity kbCategoryLabelEntity = ConvertUtil.transformObj(kbCategoryLabelDto,KbCategoryLabelEntity.class);
        if(kbCategoryLabelService.save(kbCategoryLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表编辑
     * @param kbCategoryLabelDto 标签分组和标签关联表实体类
     * @return
     */
    @CastleLog(operLocation="标签分组和标签关联表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签分组和标签关联表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbCategoryLabel(@RequestBody KbCategoryLabelDto kbCategoryLabelDto){
        if(kbCategoryLabelDto == null || kbCategoryLabelDto.getId() == null || kbCategoryLabelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCategoryLabelEntity kbCategoryLabelEntity = ConvertUtil.transformObj(kbCategoryLabelDto,KbCategoryLabelEntity.class);
        if(kbCategoryLabelService.updateById(kbCategoryLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表删除
     * @param ids 标签分组和标签关联表id集合
     * @return
     */
    @CastleLog(operLocation="标签分组和标签关联表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签分组和标签关联表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbCategoryLabel(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbCategoryLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签分组和标签关联表详情
     * @param id 标签分组和标签关联表id
     * @return
     */
    @CastleLog(operLocation="标签分组和标签关联表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签分组和标签关联表详情")
    @GetMapping("/info")
    public RespBody<KbCategoryLabelDto> infoKbCategoryLabel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCategoryLabelEntity kbCategoryLabelEntity = kbCategoryLabelService.getById(id);
            KbCategoryLabelDto kbCategoryLabelDto = ConvertUtil.transformObj(kbCategoryLabelEntity,KbCategoryLabelDto.class);
        return RespBody.data(kbCategoryLabelDto);
    }


}
