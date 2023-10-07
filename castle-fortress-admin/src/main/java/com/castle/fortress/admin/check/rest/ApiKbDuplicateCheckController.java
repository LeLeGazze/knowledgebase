package com.castle.fortress.admin.check.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.check.entity.KbDuplicateCheckEntity;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
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
 * 知识库查重表 api 控制器
 *
 * @author 
 * @since 2023-07-15
 */
@Api(tags="知识库查重表api管理控制器")
@RestController
@RequestMapping("/api/check/kbDuplicateCheck")
public class ApiKbDuplicateCheckController {
    @Autowired
    private KbDuplicateCheckService kbDuplicateCheckService;


    /**
     * 知识库查重表的分页展示
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识库查重表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库查重表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbDuplicateCheckDto>> pageKbDuplicateCheck(KbDuplicateCheckDto kbDuplicateCheckDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbDuplicateCheckDto> page = new Page(pageIndex, pageSize);

        IPage<KbDuplicateCheckDto> pages = kbDuplicateCheckService.pageKbDuplicateCheck(page, kbDuplicateCheckDto);
        return RespBody.data(pages);
    }

    /**
     * 知识库查重表保存
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @return
     */
    @CastleLog(operLocation="知识库查重表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识库查重表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbDuplicateCheck(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto){
        if(kbDuplicateCheckDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto,KbDuplicateCheckEntity.class);
        if(kbDuplicateCheckService.save(kbDuplicateCheckEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库查重表编辑
     * @param kbDuplicateCheckDto 知识库查重表实体类
     * @return
     */
    @CastleLog(operLocation="知识库查重表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识库查重表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbDuplicateCheck(@RequestBody KbDuplicateCheckDto kbDuplicateCheckDto){
        if(kbDuplicateCheckDto == null || kbDuplicateCheckDto.getId() == null || kbDuplicateCheckDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto,KbDuplicateCheckEntity.class);
        if(kbDuplicateCheckService.updateById(kbDuplicateCheckEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库查重表删除
     * @param ids 知识库查重表id集合
     * @return
     */
    @CastleLog(operLocation="知识库查重表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识库查重表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbDuplicateCheck(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbDuplicateCheckService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识库查重表详情
     * @param id 知识库查重表id
     * @return
     */
    @CastleLog(operLocation="知识库查重表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识库查重表详情")
    @GetMapping("/info")
    public RespBody<KbDuplicateCheckDto> infoKbDuplicateCheck(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDuplicateCheckEntity kbDuplicateCheckEntity = kbDuplicateCheckService.getById(id);
            KbDuplicateCheckDto kbDuplicateCheckDto = ConvertUtil.transformObj(kbDuplicateCheckEntity,KbDuplicateCheckDto.class);
        return RespBody.data(kbDuplicateCheckDto);
    }


}
