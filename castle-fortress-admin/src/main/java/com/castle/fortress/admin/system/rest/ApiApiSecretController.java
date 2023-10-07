package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ApiSecretDto;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
import com.castle.fortress.admin.system.service.ApiSecretService;
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
 * 对外开放接口秘钥 api 控制器
 *
 * @author 
 * @since 2021-03-19
 */
@Api(tags="对外开放接口秘钥api管理控制器")
@RestController
@RequestMapping("/api/system/apiSecret")
public class ApiApiSecretController {
    @Autowired
    private ApiSecretService apiSecretService;


    /**
     * 对外开放接口秘钥的分页展示
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("对外开放接口秘钥分页展示")
    @GetMapping("/page")
    public RespBody<IPage<ApiSecretDto>> pageApiSecret(ApiSecretDto apiSecretDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ApiSecretDto> page = new Page(pageIndex, pageSize);

        IPage<ApiSecretDto> pages = apiSecretService.pageApiSecret(page, apiSecretDto);
        return RespBody.data(pages);
    }

    /**
     * 对外开放接口秘钥保存
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @return
     */
    @ApiOperation("对外开放接口秘钥保存")
    @PostMapping("/save")
    public RespBody<String> saveApiSecret(@RequestBody ApiSecretDto apiSecretDto){
        if(apiSecretDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ApiSecretEntity apiSecretEntity = ConvertUtil.transformObj(apiSecretDto,ApiSecretEntity.class);
        if(apiSecretService.save(apiSecretEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放接口秘钥编辑
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @return
     */
    @ApiOperation("对外开放接口秘钥编辑")
    @PostMapping("/edit")
    public RespBody<String> updateApiSecret(@RequestBody ApiSecretDto apiSecretDto){
        if(apiSecretDto == null || apiSecretDto.getId() == null || apiSecretDto.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ApiSecretEntity apiSecretEntity = ConvertUtil.transformObj(apiSecretDto,ApiSecretEntity.class);
        if(apiSecretService.updateById(apiSecretEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放接口秘钥删除
     * @param ids 对外开放接口秘钥id集合
     * @return
     */
    @ApiOperation("对外开放接口秘钥删除")
    @PostMapping("/delete")
    public RespBody<String> deleteApiSecret(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(apiSecretService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 对外开放接口秘钥详情
     * @param id 对外开放接口秘钥id
     * @return
     */
    @ApiOperation("对外开放接口秘钥详情")
    @GetMapping("/info")
    public RespBody<ApiSecretDto> infoApiSecret(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ApiSecretEntity apiSecretEntity = apiSecretService.getById(id);
            ApiSecretDto apiSecretDto = ConvertUtil.transformObj(apiSecretEntity,ApiSecretDto.class);
        return RespBody.data(apiSecretDto);
    }


}
