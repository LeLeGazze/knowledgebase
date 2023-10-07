package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import com.castle.fortress.admin.knowledge.dto.KbThumbsUpDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbThumbsUpService;
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
 * 知识点赞表 api 控制器
 *
 * @author 
 * @since 2023-05-11
 */
@Api(tags="知识点赞表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbThumbsUp")
public class ApiKbThumbsUpController {
    @Autowired
    private KbThumbsUpService kbThumbsUpService;


    /**
     * 知识点赞表的分页展示
     * @param kbThumbsUpDto 知识点赞表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识点赞表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识点赞表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbThumbsUpDto>> pageKbThumbsUp(KbThumbsUpDto kbThumbsUpDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbThumbsUpDto> page = new Page(pageIndex, pageSize);

        IPage<KbThumbsUpDto> pages = kbThumbsUpService.pageKbThumbsUp(page, kbThumbsUpDto);
        return RespBody.data(pages);
    }

    /**
     * 知识点赞表保存
     * @param kbThumbsUpDto 知识点赞表实体类
     * @return
     */
    @CastleLog(operLocation="知识点赞表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识点赞表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbThumbsUp(@RequestBody KbThumbsUpDto kbThumbsUpDto){
        if(kbThumbsUpDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbThumbsUpEntity kbThumbsUpEntity = ConvertUtil.transformObj(kbThumbsUpDto,KbThumbsUpEntity.class);
        if(kbThumbsUpService.save(kbThumbsUpEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识点赞表编辑
     * @param kbThumbsUpDto 知识点赞表实体类
     * @return
     */
    @CastleLog(operLocation="知识点赞表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识点赞表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbThumbsUp(@RequestBody KbThumbsUpDto kbThumbsUpDto){
        if(kbThumbsUpDto == null || kbThumbsUpDto.getId() == null || kbThumbsUpDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbThumbsUpEntity kbThumbsUpEntity = ConvertUtil.transformObj(kbThumbsUpDto,KbThumbsUpEntity.class);
        if(kbThumbsUpService.updateById(kbThumbsUpEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识点赞表删除
     * @param ids 知识点赞表id集合
     * @return
     */
    @CastleLog(operLocation="知识点赞表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识点赞表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbThumbsUp(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbThumbsUpService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识点赞表详情
     * @param id 知识点赞表id
     * @return
     */
    @CastleLog(operLocation="知识点赞表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识点赞表详情")
    @GetMapping("/info")
    public RespBody<KbThumbsUpDto> infoKbThumbsUp(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbThumbsUpEntity kbThumbsUpEntity = kbThumbsUpService.getById(id);
            KbThumbsUpDto kbThumbsUpDto = ConvertUtil.transformObj(kbThumbsUpEntity,KbThumbsUpDto.class);
        return RespBody.data(kbThumbsUpDto);
    }


}
