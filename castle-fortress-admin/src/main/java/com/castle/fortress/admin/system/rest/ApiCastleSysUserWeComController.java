package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.entity.CastleSysUserWeComEntity;
import com.castle.fortress.admin.system.dto.CastleSysUserWeComDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.CastleSysUserWeComService;
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
 * 用户企业微信信息表 api 控制器
 *
 * @author mjj
 * @since 2022-11-30
 */
@Api(tags="用户企业微信信息表api管理控制器")
@RestController
@RequestMapping("/api/system/castleSysUserWeCom")
public class ApiCastleSysUserWeComController {
    @Autowired
    private CastleSysUserWeComService castleSysUserWeComService;


    /**
     * 用户企业微信信息表的分页展示
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户企业微信信息表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<CastleSysUserWeComDto>> pageCastleSysUserWeCom(CastleSysUserWeComDto castleSysUserWeComDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleSysUserWeComDto> page = new Page(pageIndex, pageSize);

        IPage<CastleSysUserWeComDto> pages = castleSysUserWeComService.pageCastleSysUserWeCom(page, castleSysUserWeComDto);
        return RespBody.data(pages);
    }

    /**
     * 用户企业微信信息表保存
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表-api",operType = OperationTypeEnum.INSERT)
    @ApiOperation("用户企业微信信息表保存")
    @PostMapping("/save")
    public RespBody<String> saveCastleSysUserWeCom(@RequestBody CastleSysUserWeComDto castleSysUserWeComDto){
        if(castleSysUserWeComDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserWeComEntity castleSysUserWeComEntity = ConvertUtil.transformObj(castleSysUserWeComDto,CastleSysUserWeComEntity.class);
        if(castleSysUserWeComService.save(castleSysUserWeComEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表编辑
     * @param castleSysUserWeComDto 用户企业微信信息表实体类
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表-api",operType = OperationTypeEnum.UPDATE)
    @ApiOperation("用户企业微信信息表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateCastleSysUserWeCom(@RequestBody CastleSysUserWeComDto castleSysUserWeComDto){
        if(castleSysUserWeComDto == null || castleSysUserWeComDto.getId() == null || castleSysUserWeComDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserWeComEntity castleSysUserWeComEntity = ConvertUtil.transformObj(castleSysUserWeComDto,CastleSysUserWeComEntity.class);
        if(castleSysUserWeComService.updateById(castleSysUserWeComEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表删除
     * @param ids 用户企业微信信息表id集合
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表-api",operType = OperationTypeEnum.DELETE)
    @ApiOperation("用户企业微信信息表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteCastleSysUserWeCom(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleSysUserWeComService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 用户企业微信信息表详情
     * @param id 用户企业微信信息表id
     * @return
     */
    @CastleLog(operLocation="用户企业微信信息表-api",operType = OperationTypeEnum.QUERY)
    @ApiOperation("用户企业微信信息表详情")
    @GetMapping("/info")
    public RespBody<CastleSysUserWeComDto> infoCastleSysUserWeCom(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleSysUserWeComEntity castleSysUserWeComEntity = castleSysUserWeComService.getById(id);
            CastleSysUserWeComDto castleSysUserWeComDto = ConvertUtil.transformObj(castleSysUserWeComEntity,CastleSysUserWeComDto.class);
        return RespBody.data(castleSysUserWeComDto);
    }


}
