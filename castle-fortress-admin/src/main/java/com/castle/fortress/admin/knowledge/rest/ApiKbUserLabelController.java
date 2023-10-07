package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbUserLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbUserLabelDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbUserLabelService;
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
 * 用户与表签关联表 api 控制器
 *
 * @author 
 * @since 2023-05-17
 */
@Api(tags="用户与表签关联表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbUserLabel")
public class ApiKbUserLabelController {
    @Autowired
    private KbUserLabelService kbUserLabelService;


    /**
     * 用户与表签关联表的分页展示
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="用户与表签关联表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户与表签关联表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbUserLabelDto>> pageKbUserLabel(KbUserLabelDto kbUserLabelDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbUserLabelDto> page = new Page(pageIndex, pageSize);

        IPage<KbUserLabelDto> pages = kbUserLabelService.pageKbUserLabel(page, kbUserLabelDto);
        return RespBody.data(pages);
    }

    /**
     * 用户与表签关联表保存
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @return
     */
    @CastleLog(operLocation="用户与表签关联表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户与表签关联表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbUserLabel(@RequestBody KbUserLabelDto kbUserLabelDto){
        if(kbUserLabelDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbUserLabelEntity kbUserLabelEntity = ConvertUtil.transformObj(kbUserLabelDto,KbUserLabelEntity.class);
        if(kbUserLabelService.save(kbUserLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表编辑
     * @param kbUserLabelDto 用户与表签关联表实体类
     * @return
     */
    @CastleLog(operLocation="用户与表签关联表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户与表签关联表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbUserLabel(@RequestBody KbUserLabelDto kbUserLabelDto){
        if(kbUserLabelDto == null || kbUserLabelDto.getId() == null || kbUserLabelDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbUserLabelEntity kbUserLabelEntity = ConvertUtil.transformObj(kbUserLabelDto,KbUserLabelEntity.class);
        if(kbUserLabelService.updateById(kbUserLabelEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表删除
     * @param ids 用户与表签关联表id集合
     * @return
     */
    @CastleLog(operLocation="用户与表签关联表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户与表签关联表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbUserLabel(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbUserLabelService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户与表签关联表详情
     * @param id 用户与表签关联表id
     * @return
     */
    @CastleLog(operLocation="用户与表签关联表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户与表签关联表详情")
    @GetMapping("/info")
    public RespBody<KbUserLabelDto> infoKbUserLabel(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbUserLabelEntity kbUserLabelEntity = kbUserLabelService.getById(id);
            KbUserLabelDto kbUserLabelDto = ConvertUtil.transformObj(kbUserLabelEntity,KbUserLabelDto.class);
        return RespBody.data(kbUserLabelDto);
    }


}
