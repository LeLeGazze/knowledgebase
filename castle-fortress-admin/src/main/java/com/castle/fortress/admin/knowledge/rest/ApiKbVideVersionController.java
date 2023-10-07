package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbVideVersionService;
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
 * PDF/word 等转换成PDF api 控制器
 *
 * @author 
 * @since 2023-05-08
 */
@Api(tags="PDF/word 等转换成PDFapi管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbVideVersion")
public class ApiKbVideVersionController {
    @Autowired
    private KbVideVersionService kbVideVersionService;


    /**
     * PDF/word 等转换成PDF的分页展示
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="PDF/word 等转换成PDF-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbVideVersionDto>> pageKbVideVersion(KbVideVersionDto kbVideVersionDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbVideVersionDto> page = new Page(pageIndex, pageSize);

        IPage<KbVideVersionDto> pages = kbVideVersionService.pageKbVideVersion(page, kbVideVersionDto);
        return RespBody.data(pages);
    }

    /**
     * PDF/word 等转换成PDF保存
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @return
     */
    @CastleLog(operLocation="PDF/word 等转换成PDF-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("PDF/word 等转换成PDF保存")
    @PostMapping("/save")
    public RespBody<String> saveKbVideVersion(@RequestBody KbVideVersionDto kbVideVersionDto){
        if(kbVideVersionDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto,KbVideVersionEntity.class);
        if(kbVideVersionService.save(kbVideVersionEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF编辑
     * @param kbVideVersionDto PDF/word 等转换成PDF实体类
     * @return
     */
    @CastleLog(operLocation="PDF/word 等转换成PDF-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("PDF/word 等转换成PDF编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbVideVersion(@RequestBody KbVideVersionDto kbVideVersionDto){
        if(kbVideVersionDto == null || kbVideVersionDto.getId() == null || kbVideVersionDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto,KbVideVersionEntity.class);
        if(kbVideVersionService.updateById(kbVideVersionEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF删除
     * @param ids PDF/word 等转换成PDFid集合
     * @return
     */
    @CastleLog(operLocation="PDF/word 等转换成PDF-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("PDF/word 等转换成PDF删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbVideVersion(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbVideVersionService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * PDF/word 等转换成PDF详情
     * @param id PDF/word 等转换成PDFid
     * @return
     */
    @CastleLog(operLocation="PDF/word 等转换成PDF-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("PDF/word 等转换成PDF详情")
    @GetMapping("/info")
    public RespBody<KbVideVersionDto> infoKbVideVersion(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideVersionEntity kbVideVersionEntity = kbVideVersionService.getById(id);
            KbVideVersionDto kbVideVersionDto = ConvertUtil.transformObj(kbVideVersionEntity,KbVideVersionDto.class);
        return RespBody.data(kbVideVersionDto);
    }


}
