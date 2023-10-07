package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbDownloadConfEntity;
import com.castle.fortress.admin.knowledge.dto.KbDownloadConfDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbDownloadConfService;
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
 * 文件下载配置表 api 控制器
 *
 * @author 
 * @since 2023-06-25
 */
@Api(tags="文件下载配置表api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbDownloadConf")
public class ApiKbDownloadConfController {
    @Autowired
    private KbDownloadConfService kbDownloadConfService;


    /**
     * 文件下载配置表的分页展示
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="文件下载配置表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("文件下载配置表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbDownloadConfDto>> pageKbDownloadConf(KbDownloadConfDto kbDownloadConfDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbDownloadConfDto> page = new Page(pageIndex, pageSize);

        IPage<KbDownloadConfDto> pages = kbDownloadConfService.pageKbDownloadConf(page, kbDownloadConfDto);
        return RespBody.data(pages);
    }

    /**
     * 文件下载配置表保存
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @return
     */
    @CastleLog(operLocation="文件下载配置表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("文件下载配置表保存")
    @PostMapping("/save")
    public RespBody<String> saveKbDownloadConf(@RequestBody KbDownloadConfDto kbDownloadConfDto){
        if(kbDownloadConfDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto,KbDownloadConfEntity.class);
        if(kbDownloadConfService.save(kbDownloadConfEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表编辑
     * @param kbDownloadConfDto 文件下载配置表实体类
     * @return
     */
    @CastleLog(operLocation="文件下载配置表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("文件下载配置表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbDownloadConf(@RequestBody KbDownloadConfDto kbDownloadConfDto){
        if(kbDownloadConfDto == null || kbDownloadConfDto.getId() == null || kbDownloadConfDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto,KbDownloadConfEntity.class);
        if(kbDownloadConfService.updateById(kbDownloadConfEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表删除
     * @param ids 文件下载配置表id集合
     * @return
     */
    @CastleLog(operLocation="文件下载配置表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("文件下载配置表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbDownloadConf(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbDownloadConfService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 文件下载配置表详情
     * @param id 文件下载配置表id
     * @return
     */
    @CastleLog(operLocation="文件下载配置表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("文件下载配置表详情")
    @GetMapping("/info")
    public RespBody<KbDownloadConfDto> infoKbDownloadConf(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbDownloadConfEntity kbDownloadConfEntity = kbDownloadConfService.getById(id);
            KbDownloadConfDto kbDownloadConfDto = ConvertUtil.transformObj(kbDownloadConfEntity,KbDownloadConfDto.class);
        return RespBody.data(kbDownloadConfDto);
    }


}
