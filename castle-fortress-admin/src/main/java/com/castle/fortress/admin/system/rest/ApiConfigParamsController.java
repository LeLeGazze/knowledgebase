package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ConfigParamsDto;
import com.castle.fortress.admin.system.entity.ConfigParamsEntity;
import com.castle.fortress.admin.system.service.ConfigParamsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统参数表 api 控制器
 *
 * @author castle
 * @since 2022-05-07
 */
@Api(tags="系统参数表api管理控制器")
@RestController
@RequestMapping("/api/system/configParams")
public class ApiConfigParamsController {
    @Autowired
    private ConfigParamsService configParamsService;


    /**
     * 系统参数表的分页展示
     * @param configParamsDto 系统参数表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("系统参数表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<ConfigParamsDto>> pageConfigParams(ConfigParamsDto configParamsDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigParamsDto> page = new Page(pageIndex, pageSize);

        IPage<ConfigParamsDto> pages = configParamsService.pageConfigParams(page, configParamsDto);
        return RespBody.data(pages);
    }

    /**
     * 系统参数表保存
     * @param configParamsDto 系统参数表实体类
     * @return
     */
    @ApiOperation("系统参数表保存")
    @PostMapping("/save")
    public RespBody<String> saveConfigParams(@RequestBody ConfigParamsDto configParamsDto){
        if(configParamsDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
        if(configParamsService.save(configParamsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表编辑
     * @param configParamsDto 系统参数表实体类
     * @return
     */
    @ApiOperation("系统参数表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateConfigParams(@RequestBody ConfigParamsDto configParamsDto){
        if(configParamsDto == null || configParamsDto.getId() == null || configParamsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigParamsEntity configParamsEntity = ConvertUtil.transformObj(configParamsDto,ConfigParamsEntity.class);
        if(configParamsService.updateById(configParamsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表删除
     * @param ids 系统参数表id集合
     * @return
     */
    @ApiOperation("系统参数表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteConfigParams(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configParamsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统参数表详情
     * @param id 系统参数表id
     * @return
     */
    @ApiOperation("系统参数表详情")
    @GetMapping("/info")
    public RespBody<ConfigParamsDto> infoConfigParams(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigParamsEntity configParamsEntity = configParamsService.getById(id);
            ConfigParamsDto configParamsDto = ConvertUtil.transformObj(configParamsEntity,ConfigParamsDto.class);
        return RespBody.data(configParamsDto);
    }


}
