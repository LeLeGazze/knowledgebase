package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbBannerEntity;
import com.castle.fortress.admin.knowledge.dto.KbBannerDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbBannerService;
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
 * 知识banner图表 api 控制器
 *
 * @author 
 * @since 2023-06-17
 */
@Api(tags="知识banner图表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbBanner")
public class ApiKbBannerController {
    @Autowired
    private KbBannerService kbBannerService;


    /**
     * 知识banner图表的分页展示
     * @param kbBannerDto 知识banner图表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="知识banner图表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识banner图表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbBannerDto>> pageKbBanner(KbBannerDto kbBannerDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbBannerDto> page = new Page(pageIndex, pageSize);

        IPage<KbBannerDto> pages = kbBannerService.pageKbBanner(page, kbBannerDto);
        return RespBody.data(pages);
    }

    /**
     * 知识banner图表保存
     * @param kbBannerDto 知识banner图表实体类
     * @return
     */
    @CastleLog(operLocation="知识banner图表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("知识banner图表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbBanner(@RequestBody KbBannerDto kbBannerDto){
        if(kbBannerDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBannerEntity kbBannerEntity = ConvertUtil.transformObj(kbBannerDto,KbBannerEntity.class);
        if(kbBannerService.save(kbBannerEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表编辑
     * @param kbBannerDto 知识banner图表实体类
     * @return
     */
    @CastleLog(operLocation="知识banner图表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("知识banner图表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbBanner(@RequestBody KbBannerDto kbBannerDto){
        if(kbBannerDto == null || kbBannerDto.getId() == null || kbBannerDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBannerEntity kbBannerEntity = ConvertUtil.transformObj(kbBannerDto,KbBannerEntity.class);
        if(kbBannerService.updateById(kbBannerEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表删除
     * @param ids 知识banner图表id集合
     * @return
     */
    @CastleLog(operLocation="知识banner图表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("知识banner图表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbBanner(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbBannerService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 知识banner图表详情
     * @param id 知识banner图表id
     * @return
     */
    @CastleLog(operLocation="知识banner图表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("知识banner图表详情")
    @GetMapping("/info")
    public RespBody<KbBannerDto> infoKbBanner(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbBannerEntity kbBannerEntity = kbBannerService.getById(id);
            KbBannerDto kbBannerDto = ConvertUtil.transformObj(kbBannerEntity,KbBannerDto.class);
        return RespBody.data(kbBannerDto);
    }


}
