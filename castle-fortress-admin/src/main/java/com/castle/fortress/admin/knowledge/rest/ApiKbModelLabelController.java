package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbModelLabelDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbModelLabelService;
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
 * 标签管理表 api 控制器
 *
 * @author 
 * @since 2023-04-26
 */
@Api(tags="标签管理表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbModelLabel")
public class ApiKbModelLabelController {
    @Autowired
    private KbModelLabelService kbModelLabelService;


    /**
     * 标签管理表的分页展示
     * @param kbModelLabelDto 标签管理表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="标签管理表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签管理表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbModelLabelDto>> pageKbModelLabel(KbModelLabelDto kbModelLabelDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbModelLabelDto> page = new Page(pageIndex, pageSize);

        IPage<KbModelLabelDto> pages = kbModelLabelService.pageKbModelLabel(page, kbModelLabelDto);
        return RespBody.data(pages);
    }

    /**
     * 标签管理表保存
     * @param kbModelLabelDto 标签管理表实体类
     * @return
     */
    @CastleLog(operLocation="标签管理表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("标签管理表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbModelLabel(@RequestBody KbModelLabelDto kbModelLabelDto){
        if(kbModelLabelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelLabelEntity kbModelLabelEntity = ConvertUtil.transformObj(kbModelLabelDto,KbModelLabelEntity.class);
        if(kbModelLabelService.save(kbModelLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签管理表编辑
     * @param kbModelLabelDto 标签管理表实体类
     * @return
     */
    @CastleLog(operLocation="标签管理表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("标签管理表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbModelLabel(@RequestBody KbModelLabelDto kbModelLabelDto){
        if(kbModelLabelDto == null || kbModelLabelDto.getId() == null || kbModelLabelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelLabelEntity kbModelLabelEntity = ConvertUtil.transformObj(kbModelLabelDto,KbModelLabelEntity.class);
        if(kbModelLabelService.updateById(kbModelLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签管理表删除
     * @param ids 标签管理表id集合
     * @return
     */
    @CastleLog(operLocation="标签管理表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("标签管理表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbModelLabel(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbModelLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 标签管理表详情
     * @param id 标签管理表id
     * @return
     */
    @CastleLog(operLocation="标签管理表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("标签管理表详情")
    @GetMapping("/info")
    public RespBody<KbModelLabelDto> infoKbModelLabel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbModelLabelEntity kbModelLabelEntity = kbModelLabelService.getById(id);
            KbModelLabelDto kbModelLabelDto = ConvertUtil.transformObj(kbModelLabelEntity,KbModelLabelDto.class);
        return RespBody.data(kbModelLabelDto);
    }


}
