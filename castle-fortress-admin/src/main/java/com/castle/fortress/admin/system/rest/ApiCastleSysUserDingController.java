package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.CastleSysUserDingEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserDingDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.CastleSysUserDingService;
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
 * 用户钉钉信息表 api 控制器
 *
 * @author Mgg
 * @since 2022-12-13
 */
@Api(tags="用户钉钉信息表api管理控制器")
@RestController
@RequestMapping("/api/system/castleSysUserDing")
public class ApiCastleSysUserDingController {
    @Autowired
    private CastleSysUserDingService castleSysUserDingService;


    /**
     * 用户钉钉信息表的分页展示
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<CastleSysUserDingDto>> pageCastleSysUserDing(CastleSysUserDingDto castleSysUserDingDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysUserDingDto> page = new Page(pageIndex, pageSize);

        IPage<CastleSysUserDingDto> pages = castleSysUserDingService.pageCastleSysUserDing(page, castleSysUserDingDto);
        return RespBody.data(pages);
    }

    /**
     * 用户钉钉信息表保存
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户钉钉信息表保存")
    @PostMapping("/save")
    public RespBody<String> saveCastleSysUserDing(@RequestBody CastleSysUserDingDto castleSysUserDingDto){
        if(castleSysUserDingDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserDingEntity castleSysUserDingEntity = ConvertUtil.transformObj(castleSysUserDingDto,CastleSysUserDingEntity.class);
        if(castleSysUserDingService.save(castleSysUserDingEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表编辑
     * @param castleSysUserDingDto 用户钉钉信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户钉钉信息表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateCastleSysUserDing(@RequestBody CastleSysUserDingDto castleSysUserDingDto){
        if(castleSysUserDingDto == null || castleSysUserDingDto.getId() == null || castleSysUserDingDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserDingEntity castleSysUserDingEntity = ConvertUtil.transformObj(castleSysUserDingDto,CastleSysUserDingEntity.class);
        if(castleSysUserDingService.updateById(castleSysUserDingEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表删除
     * @param ids 用户钉钉信息表id集合
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户钉钉信息表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteCastleSysUserDing(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserDingService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户钉钉信息表详情
     * @param id 用户钉钉信息表id
     * @return
     */
    @CastleLog(operLocation="用户钉钉信息表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户钉钉信息表详情")
    @GetMapping("/info")
    public RespBody<CastleSysUserDingDto> infoCastleSysUserDing(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserDingEntity castleSysUserDingEntity = castleSysUserDingService.getById(id);
            CastleSysUserDingDto castleSysUserDingDto = ConvertUtil.transformObj(castleSysUserDingEntity,CastleSysUserDingDto.class);
        return RespBody.data(castleSysUserDingDto);
    }


}
