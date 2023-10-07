package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.ConfigApiEntity;
import com.castle.fortress.admin.system.service.ConfigApiService;
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
 * 框架绑定api配置管理 api 控制器
 *
 * @author castle
 * @since 2022-04-12
 */
@Api(tags="框架绑定api配置管理api管理控制器")
@RestController
@RequestMapping("/api/system/configApi")
public class ApiConfigApiController {
    @Autowired
    private ConfigApiService configApiService;


    /**
     * 框架绑定api配置管理的分页展示
     * @param configApiDto 框架绑定api配置管理实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("框架绑定api配置管理分页展示")
    @GetMapping("/page")
    public RespBody<IPage<ConfigApiDto>> pageConfigApi(ConfigApiDto configApiDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigApiDto> page = new Page(pageIndex, pageSize);

        IPage<ConfigApiDto> pages = configApiService.pageConfigApi(page, configApiDto);
        return RespBody.data(pages);
    }

    /**
     * 框架绑定api配置管理保存
     * @param configApiDto 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理保存")
    @PostMapping("/save")
    public RespBody<String> saveConfigApi(@RequestBody ConfigApiDto configApiDto){
        if(configApiDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigApiEntity configApiEntity = ConvertUtil.transformObj(configApiDto,ConfigApiEntity.class);
        if(configApiService.save(configApiEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理编辑
     * @param configApiDto 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理编辑")
    @PostMapping("/edit")
    public RespBody<String> updateConfigApi(@RequestBody ConfigApiDto configApiDto){
        if(configApiDto == null || configApiDto.getId() == null || configApiDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigApiEntity configApiEntity = ConvertUtil.transformObj(configApiDto,ConfigApiEntity.class);
        if(configApiService.updateById(configApiEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理删除
     * @param ids 框架绑定api配置管理id集合
     * @return
     */
    @ApiOperation("框架绑定api配置管理删除")
    @PostMapping("/delete")
    public RespBody<String> deleteConfigApi(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configApiService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理详情
     * @param id 框架绑定api配置管理id
     * @return
     */
    @ApiOperation("框架绑定api配置管理详情")
    @GetMapping("/info")
    public RespBody<ConfigApiDto> infoConfigApi(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigApiEntity configApiEntity = configApiService.getById(id);
            ConfigApiDto configApiDto = ConvertUtil.transformObj(configApiEntity,ConfigApiDto.class);
        return RespBody.data(configApiDto);
    }


}
