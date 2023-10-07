package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbCommentEntity;
import com.castle.fortress.admin.knowledge.dto.KbCommentDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbCommentService;
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
 * 知识评论管理表 api 控制器
 *
 * @author 
 * @since 2023-05-09
 */
@Api(tags="知识评论管理表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbComment")
public class ApiKbCommentController {
    @Autowired
    private KbCommentService kbCommentService;


    /**
     * 知识评论管理表的分页展示
     * @param kbCommentDto 知识评论管理表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识评论管理表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbCommentDto>> pageKbComment(KbCommentDto kbCommentDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbCommentDto> page = new Page(pageIndex, pageSize);

        IPage<KbCommentDto> pages = kbCommentService.pageKbComment(page, kbCommentDto);
        return RespBody.data(pages);
    }

    /**
     * 知识评论管理表保存
     * @param kbCommentDto 知识评论管理表实体类
     * @return
     */
    @CastleLog(operLocation="知识评论管理表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识评论管理表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbComment(@RequestBody KbCommentDto kbCommentDto){
        if(kbCommentDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCommentEntity kbCommentEntity = ConvertUtil.transformObj(kbCommentDto,KbCommentEntity.class);
        if(kbCommentService.save(kbCommentEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表编辑
     * @param kbCommentDto 知识评论管理表实体类
     * @return
     */
    @CastleLog(operLocation="知识评论管理表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识评论管理表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbComment(@RequestBody KbCommentDto kbCommentDto){
        if(kbCommentDto == null || kbCommentDto.getId() == null || kbCommentDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCommentEntity kbCommentEntity = ConvertUtil.transformObj(kbCommentDto,KbCommentEntity.class);
        if(kbCommentService.updateById(kbCommentEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表删除
     * @param ids 知识评论管理表id集合
     * @return
     */
    @CastleLog(operLocation="知识评论管理表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识评论管理表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbComment(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbCommentService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识评论管理表详情
     * @param id 知识评论管理表id
     * @return
     */
    @CastleLog(operLocation="知识评论管理表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识评论管理表详情")
    @GetMapping("/info")
    public RespBody<KbCommentDto> infoKbComment(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCommentEntity kbCommentEntity = kbCommentService.getById(id);
            KbCommentDto kbCommentDto = ConvertUtil.transformObj(kbCommentEntity,KbCommentDto.class);
        return RespBody.data(kbCommentDto);
    }


}
