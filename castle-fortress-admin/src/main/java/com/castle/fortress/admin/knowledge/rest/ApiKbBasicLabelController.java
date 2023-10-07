package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBasicLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLabelDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbBasicLabelService;
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
 * 知识与标签的中间表 api 控制器
 *
 * @author 
 * @since 2023-04-28
 */
@Api(tags="知识与标签的中间表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBasicLabel")
public class ApiKbBasicLabelController {
    @Autowired
    private KbBasicLabelService kbBasicLabelService;


    /**
     * 知识与标签的中间表的分页展示
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识与标签的中间表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbBasicLabelDto>> pageKbBasicLabel(KbBasicLabelDto kbBasicLabelDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBasicLabelDto> page = new Page(pageIndex, pageSize);

        IPage<KbBasicLabelDto> pages = kbBasicLabelService.pageKbBasicLabel(page, kbBasicLabelDto);
        return RespBody.data(pages);
    }

    /**
     * 知识与标签的中间表保存
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation="知识与标签的中间表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识与标签的中间表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBasicLabel(@RequestBody KbBasicLabelDto kbBasicLabelDto){
        if(kbBasicLabelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto,KbBasicLabelEntity.class);
        if(kbBasicLabelService.save(kbBasicLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表编辑
     * @param kbBasicLabelDto 知识与标签的中间表实体类
     * @return
     */
    @CastleLog(operLocation="知识与标签的中间表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识与标签的中间表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBasicLabel(@RequestBody KbBasicLabelDto kbBasicLabelDto){
        if(kbBasicLabelDto == null || kbBasicLabelDto.getId() == null || kbBasicLabelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLabelEntity kbBasicLabelEntity = ConvertUtil.transformObj(kbBasicLabelDto,KbBasicLabelEntity.class);
        if(kbBasicLabelService.updateById(kbBasicLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表删除
     * @param ids 知识与标签的中间表id集合
     * @return
     */
    @CastleLog(operLocation="知识与标签的中间表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识与标签的中间表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBasicLabel(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBasicLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识与标签的中间表详情
     * @param id 知识与标签的中间表id
     * @return
     */
    @CastleLog(operLocation="知识与标签的中间表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识与标签的中间表详情")
    @GetMapping("/info")
    public RespBody<KbBasicLabelDto> infoKbBasicLabel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLabelEntity kbBasicLabelEntity = kbBasicLabelService.getById(id);
            KbBasicLabelDto kbBasicLabelDto = ConvertUtil.transformObj(kbBasicLabelEntity,KbBasicLabelDto.class);
        return RespBody.data(kbBasicLabelDto);
    }


}
