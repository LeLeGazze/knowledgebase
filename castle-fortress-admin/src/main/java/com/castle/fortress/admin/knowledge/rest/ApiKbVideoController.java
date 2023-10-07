package com.castle.fortress.admin.knowledge.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideoDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.knowledge.service.KbVideoService;
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
 * 视频库 api 控制器
 *
 * @author 
 * @since 2023-05-13
 */
@Api(tags="视频库api管理控制器")
@RestController
@RequestMapping("/api/knowledge/kbVideo")
public class ApiKbVideoController {
    @Autowired
    private KbVideoService kbVideoService;


    /**
     * 视频库的分页展示
     * @param kbVideoDto 视频库实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="视频库-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频库分页展示")
    @GetMapping("/page")
    public RespBody<IPage<KbVideoDto>> pageKbVideo(KbVideoDto kbVideoDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<KbVideoDto> page = new Page(pageIndex, pageSize);

//        IPage<KbVideoDto> pages = kbVideoService.pageKbVideo(page, kbVideoDto);
        return RespBody.data(null);
    }

    /**
     * 视频库保存
     * @param kbVideoDto 视频库实体类
     * @return
     */
    @CastleLog(operLocation="视频库-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("视频库保存")
    @PostMapping("/save")
    public RespBody<String> saveKbVideo(@RequestBody KbVideoDto kbVideoDto){
        if(kbVideoDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto,KbVideoEntity.class);
        if(kbVideoService.save(kbVideoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 视频库编辑
     * @param kbVideoDto 视频库实体类
     * @return
     */
    @CastleLog(operLocation="视频库-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("视频库编辑")
    @PostMapping("/edit")
    public RespBody<String> updateKbVideo(@RequestBody KbVideoDto kbVideoDto){
        if(kbVideoDto == null || kbVideoDto.getId() == null || kbVideoDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideoEntity kbVideoEntity = ConvertUtil.transformObj(kbVideoDto,KbVideoEntity.class);
        if(kbVideoService.updateById(kbVideoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 视频库删除
     * @param ids 视频库id集合
     * @return
     */
    @CastleLog(operLocation="视频库-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("视频库删除")
    @PostMapping("/delete")
    public RespBody<String> deleteKbVideo(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(kbVideoService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 视频库详情
     * @param id 视频库id
     * @return
     */
    @CastleLog(operLocation="视频库-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("视频库详情")
    @GetMapping("/info")
    public RespBody<KbVideoDto> infoKbVideo(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            KbVideoEntity kbVideoEntity = kbVideoService.getById(id);
            KbVideoDto kbVideoDto = ConvertUtil.transformObj(kbVideoEntity,KbVideoDto.class);
        return RespBody.data(kbVideoDto);
    }


}
