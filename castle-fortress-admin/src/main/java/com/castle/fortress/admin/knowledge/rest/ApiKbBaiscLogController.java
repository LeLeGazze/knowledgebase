package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicLogEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicLogDto;
import com.castle.fortress.admin.knowledge.service.KbBasicLogService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.admin.core.annotation.CastleLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * 知识移动日志表 api 控制器
 *
 * @author 
 * @since 2023-06-02
 */
@Api(tags="知识移动日志表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBaiscLog")
public class ApiKbBaiscLogController {
    @Autowired
    private KbBasicLogService kbBasicLogService;


    /**
     * 知识移动日志表的分页展示
     * @param kbBaseShowDto 知识移动日志表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识移动日志表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识移动日志表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbModelTransmitDto>> pageKbBaiscLog(KbBaseShowDto kbBaseShowDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size) throws Exception {
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBasicLogDto> page = new Page<>(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        IPage<KbModelTransmitDto> pages = kbBasicLogService.pageKbBasicLog(page, kbBaseShowDto,sysUser);
        return RespBody.data(pages);
    }

    /**
     * 知识移动日志表保存
     * @param kbBasicLogDto 知识移动日志表实体类
     * @return
     */
    @CastleLog(operLocation="知识移动日志表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识移动日志表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBasicLog(@RequestBody KbBasicLogDto kbBasicLogDto){
        if(kbBasicLogDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLogEntity kbBasicLogEntity = ConvertUtil.transformObj(kbBasicLogDto,KbBasicLogEntity.class);
        if(kbBasicLogService.save(kbBasicLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表编辑
     * @param kbBasicLogDto 知识移动日志表实体类
     * @return
     */
    @CastleLog(operLocation="知识移动日志表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识移动日志表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBasicLog(@RequestBody KbBasicLogDto kbBasicLogDto){
        if(kbBasicLogDto == null || kbBasicLogDto.getId() == null || kbBasicLogDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLogEntity kbBasicLogEntity = ConvertUtil.transformObj(kbBasicLogDto,KbBasicLogEntity.class);
        if(kbBasicLogService.updateById(kbBasicLogEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表删除
     * @param ids 知识移动日志表id集合
     * @return
     */
    @CastleLog(operLocation="知识移动日志表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识移动日志表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBasicLog(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBasicLogService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识移动日志表详情
     * @param id 知识移动日志表id
     * @return
     */
    @CastleLog(operLocation="知识移动日志表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识移动日志表详情")
    @GetMapping("/info")
    public RespBody<KbBasicLogDto> infoKbBasicLog(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBasicLogEntity kbBasicLogEntity = kbBasicLogService.getById(id);
            KbBasicLogDto kbBasicLogDto = ConvertUtil.transformObj(kbBasicLogEntity,KbBasicLogDto.class);
        return RespBody.data(kbBasicLogDto);
    }


}
