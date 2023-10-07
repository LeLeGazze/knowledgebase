package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ApiSecretDto;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
import com.castle.fortress.admin.system.service.ApiSecretService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OperationTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对外开放接口秘钥 控制器
 *
 * @author 
 * @since 2021-03-19
 */
@Api(tags="对外开放接口秘钥管理控制器")
@Controller
public class ApiSecretController {
    @Autowired
    private ApiSecretService apiSecretService;

    /**
     * 对外开放接口秘钥的分页展示
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @CastleLog(operLocation="对外开放接口分页",operType= OperationTypeEnum.QUERY)
    @ApiOperation("对外开放接口秘钥分页展示")
    @GetMapping("/system/apiSecret/page")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:pageList")
    public RespBody<IPage<ApiSecretDto>> pageApiSecret(ApiSecretDto apiSecretDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ApiSecretDto> page = new Page(pageIndex, pageSize);
        IPage<ApiSecretDto> pages = apiSecretService.pageApiSecret(page, apiSecretDto);

        return RespBody.data(pages);
    }

    /**
     * 对外开放接口秘钥的列表展示
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @return
     */
    @CastleLog(operLocation="对外开放接口列表",operType= OperationTypeEnum.QUERY)
    @ApiOperation("对外开放接口秘钥列表展示")
    @GetMapping("/system/apiSecret/list")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:list")
    public RespBody<List<ApiSecretDto>> listApiSecret(ApiSecretDto apiSecretDto){
        List<ApiSecretDto> list = apiSecretService.listApiSecret(apiSecretDto);
        return RespBody.data(list);
    }

    /**
     * 随机生成secret
     * @return
     */
    @CastleLog(operLocation="对外开放接口生成",operType= OperationTypeEnum.INSERT)
    @ApiOperation("随机生成secret")
    @GetMapping("/system/apiSecret/genSecret")
    @ResponseBody
    public RespBody<Map<String,String>> genSecret(){
        Map<String,String> map = new HashMap<>();
        map.put("secretId",BizCommonUtil.autoSecretId());
        map.put("secretKey",BizCommonUtil.autoSecretKey());
        return RespBody.data(map);
    }

    /**
     * 对外开放接口秘钥保存
     * @param apiSecretDto 对外开放接口秘钥实体类
     * @return
     */
    @CastleLog(operLocation="对外开放接口保存",operType= OperationTypeEnum.INSERT)
    @ApiOperation("对外开放接口秘钥保存")
    @PostMapping("/system/apiSecret/save")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:save")
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
    @CastleLog(operLocation="对外开放接口编辑",operType= OperationTypeEnum.UPDATE)
    @ApiOperation("对外开放接口秘钥编辑")
    @PostMapping("/system/apiSecret/edit")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:edit")
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
     * @param id
     * @return
     */
    @CastleLog(operLocation="对外开放接口删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("对外开放接口秘钥删除")
    @PostMapping("/system/apiSecret/delete")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:delete")
    public RespBody<String> deleteApiSecret(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(apiSecretService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 对外开放接口秘钥批量删除
     * @param ids
     * @return
     */
    @CastleLog(operLocation="对外开放接口批量删除",operType= OperationTypeEnum.DELETE)
    @ApiOperation("对外开放接口秘钥批量删除")
    @PostMapping("/system/apiSecret/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:deleteBatch")
    public RespBody<String> deleteApiSecretBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @CastleLog(operLocation="对外开放接口详情",operType= OperationTypeEnum.QUERY)
    @ApiOperation("对外开放接口秘钥详情")
    @GetMapping("/system/apiSecret/info")
    @ResponseBody
    @RequiresPermissions("system:apiSecret:info")
    public RespBody<ApiSecretDto> infoApiSecret(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ApiSecretEntity apiSecretEntity = apiSecretService.getById(id);
        ApiSecretDto apiSecretDto = ConvertUtil.transformObj(apiSecretEntity,ApiSecretDto.class);

        return RespBody.data(apiSecretDto);
    }


}
