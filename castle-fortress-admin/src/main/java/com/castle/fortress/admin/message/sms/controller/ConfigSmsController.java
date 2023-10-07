package com.castle.fortress.admin.message.sms.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.sms.dto.ConfigSmsDto;
import com.castle.fortress.admin.message.sms.dto.SmsPlatFormDto;
import com.castle.fortress.admin.message.sms.entity.ConfigSmsEntity;
import com.castle.fortress.admin.message.sms.service.ConfigSmsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.SmsPlatFormEnum;
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

import java.util.List;

/**
 * 短信配置表 控制器
 *
 * @author castle
 * @since 2021-04-12
 */
@Api(tags="短信配置表管理控制器")
@Controller
public class ConfigSmsController {
    @Autowired
    private ConfigSmsService configSmsService;

    /**
     * 短信配置表的分页展示
     * @param configSmsDto 短信配置表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("短信配置表分页展示")
    @GetMapping("/message/sms/configSms/page")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:pageList")
    public RespBody<IPage<ConfigSmsDto>> pageConfigSms(ConfigSmsDto configSmsDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigSmsDto> page = new Page(pageIndex, pageSize);
        IPage<ConfigSmsDto> pages = configSmsService.pageConfigSmsExtends(page, configSmsDto);

        return RespBody.data(pages);
    }

    /**
     * 短信配置表的列表展示
     * @param configSmsDto 短信配置表实体类
     * @return
     */
    @ApiOperation("短信配置表列表展示")
    @GetMapping("/message/sms/configSms/list")
    @ResponseBody
    public RespBody<List<ConfigSmsDto>> listConfigSms(ConfigSmsDto configSmsDto){
        List<ConfigSmsDto> list = configSmsService.listConfigSms(configSmsDto);
        return RespBody.data(list);
    }

    /**
     * 短信配置表保存
     * @param configSmsDto 短信配置表实体类
     * @return
     */
    @ApiOperation("短信配置表保存")
    @PostMapping("/message/sms/configSms/save")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:save")
    public RespBody<String> saveConfigSms(@RequestBody ConfigSmsDto configSmsDto){
        if(configSmsDto == null || configSmsDto.getSmsPlatFormDto() == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验参数
        if(SmsPlatFormEnum.ALIYUN.getCode().equals(configSmsDto.getPlatform())){
            if(StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunAccessKeyId())
                    ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunAccessKeySecret())
            ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunSignName())
                    ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunTemplateCode())
            ){
                throw new BizException(GlobalRespCode.PARAM_MISSED);
            }
        }
        configSmsDto.setSmsConfig(JSONUtil.toJsonStr(configSmsDto.getSmsPlatFormDto()));
        //校验重复字段
        RespBody checkResult = configSmsService.checkColumnRepeat(configSmsDto);
        if(!checkResult.isSuccess()){
            return checkResult;
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
    @PostMapping("/message/sms/configSms/edit")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:edit")
    public RespBody<String> updateConfigSms(@RequestBody ConfigSmsDto configSmsDto){
        if(configSmsDto == null || configSmsDto.getId() == null || configSmsDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //校验参数
        if(SmsPlatFormEnum.ALIYUN.getCode().equals(configSmsDto.getPlatform())){
            if(StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunAccessKeyId())
                    ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunAccessKeySecret())
                    ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunSignName())
                    ||StrUtil.isEmpty(configSmsDto.getSmsPlatFormDto().getAliyunTemplateCode())
            ){
                throw new BizException(GlobalRespCode.PARAM_MISSED);
            }
        }
        configSmsDto.setSmsConfig(JSONUtil.toJsonStr(configSmsDto.getSmsPlatFormDto()));
        //校验重复字段
        RespBody checkResult = configSmsService.checkColumnRepeat(configSmsDto);
        if(!checkResult.isSuccess()){
            return checkResult;
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
     * @param id
     * @return
     */
    @ApiOperation("短信配置表删除")
    @PostMapping("/message/sms/configSms/delete")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:delete")
    public RespBody<String> deleteConfigSms(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configSmsService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 短信配置表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("短信配置表批量删除")
    @PostMapping("/message/sms/configSms/deleteBatch")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:deleteBatch")
    public RespBody<String> deleteConfigSmsBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
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
    @GetMapping("/message/sms/configSms/info")
    @ResponseBody
    @RequiresPermissions("message:sms:configSms:info")
    public RespBody<ConfigSmsDto> infoConfigSms(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigSmsDto configSmsDto =  configSmsService.getByIdExtends(id);
        SmsPlatFormDto platFormDto = JSONUtil.toBean(configSmsDto.getSmsConfig(),SmsPlatFormDto.class);
        configSmsDto.setSmsPlatFormDto(platFormDto);
        return RespBody.data(configSmsDto);
    }


}
