package com.castle.fortress.admin.message.sms.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.sms.dto.ConfigSmsDto;
import com.castle.fortress.admin.message.sms.entity.ConfigSmsEntity;
import com.castle.fortress.admin.message.sms.service.ConfigSmsService;
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
 * 短信配置表 api 控制器
 *
 * @author castle
 * @since 2021-04-12
 */
@Api(tags="短信配置表api管理控制器")
@RestController
@RequestMapping("/api/message/sms/configSms")
public class ApiConfigSmsController {
    @Autowired
    private ConfigSmsService configSmsService;


    /**
     * 短信配置表的分页展示
     * @param configSmsDto 短信配置表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("短信配置表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<ConfigSmsDto>> pageConfigSms(ConfigSmsDto configSmsDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigSmsDto> page = new Page(pageIndex, pageSize);

        IPage<ConfigSmsDto> pages = configSmsService.pageConfigSms(page, configSmsDto);
        return RespBody.data(pages);
    }

    /**
     * 短信配置表保存
     * @param configSmsDto 短信配置表实体类
     * @return
     */
    @ApiOperation("短信配置表保存")
    @PostMapping("/save")
    public RespBody<String> saveConfigSms(@RequestBody ConfigSmsDto configSmsDto){
        if(configSmsDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigSmsEntity configSmsEntity = ConvertUtil.transformObj(configSmsDto,ConfigSmsEntity.class);
        if(configSmsService.save(configSmsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信配置表编辑
     * @param configSmsDto 短信配置表实体类
     * @return
     */
    @ApiOperation("短信配置表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateConfigSms(@RequestBody ConfigSmsDto configSmsDto){
        if(configSmsDto == null || configSmsDto.getId() == null || configSmsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigSmsEntity configSmsEntity = ConvertUtil.transformObj(configSmsDto,ConfigSmsEntity.class);
        if(configSmsService.updateById(configSmsEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信配置表删除
     * @param ids 短信配置表id集合
     * @return
     */
    @ApiOperation("短信配置表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteConfigSms(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configSmsService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 短信配置表详情
     * @param id 短信配置表id
     * @return
     */
    @ApiOperation("短信配置表详情")
    @GetMapping("/info")
    public RespBody<ConfigSmsDto> infoConfigSms(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            ConfigSmsEntity configSmsEntity = configSmsService.getById(id);
            ConfigSmsDto configSmsDto = ConvertUtil.transformObj(configSmsEntity,ConfigSmsDto.class);
        return RespBody.data(configSmsDto);
    }


}
