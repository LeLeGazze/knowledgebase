package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbCollectEntity;
import com.castle.fortress.admin.knowledge.dto.KbCollectDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbCollectService;
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
 * 知识收藏表 api 控制器
 *
 * @author 
 * @since 2023-05-12
 */
@Api(tags="知识收藏表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbCollect")
public class ApiKbCollectController {
    @Autowired
    private KbCollectService kbCollectService;


    /**
     * 知识收藏表的分页展示
     * @param kbCollectDto 知识收藏表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识收藏表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识收藏表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbCollectDto>> pageKbCollect(KbCollectDto kbCollectDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbCollectDto> page = new Page(pageIndex, pageSize);

        IPage<KbCollectDto> pages = kbCollectService.pageKbCollect(page, kbCollectDto);
        return RespBody.data(pages);
    }

    /**
     * 知识收藏表保存
     * @param kbCollectDto 知识收藏表实体类
     * @return
     */
    @CastleLog(operLocation="知识收藏表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识收藏表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbCollect(@RequestBody KbCollectDto kbCollectDto){
        if(kbCollectDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCollectEntity kbCollectEntity = ConvertUtil.transformObj(kbCollectDto,KbCollectEntity.class);
        if(kbCollectService.save(kbCollectEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识收藏表编辑
     * @param kbCollectDto 知识收藏表实体类
     * @return
     */
    @CastleLog(operLocation="知识收藏表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识收藏表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbCollect(@RequestBody KbCollectDto kbCollectDto){
        if(kbCollectDto == null || kbCollectDto.getId() == null || kbCollectDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCollectEntity kbCollectEntity = ConvertUtil.transformObj(kbCollectDto,KbCollectEntity.class);
        if(kbCollectService.updateById(kbCollectEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识收藏表删除
     * @param ids 知识收藏表id集合
     * @return
     */
    @CastleLog(operLocation="知识收藏表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识收藏表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbCollect(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbCollectService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识收藏表详情
     * @param id 知识收藏表id
     * @return
     */
    @CastleLog(operLocation="知识收藏表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识收藏表详情")
    @GetMapping("/info")
    public RespBody<KbCollectDto> infoKbCollect(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbCollectEntity kbCollectEntity = kbCollectService.getById(id);
            KbCollectDto kbCollectDto = ConvertUtil.transformObj(kbCollectEntity,KbCollectDto.class);
        return RespBody.data(kbCollectDto);
    }


}
