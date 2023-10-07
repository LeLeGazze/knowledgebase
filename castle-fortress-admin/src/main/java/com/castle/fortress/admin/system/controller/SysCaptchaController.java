package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysCaptchaDto;
import com.castle.fortress.admin.system.entity.SysCaptchaEntity;
import com.castle.fortress.admin.system.service.SysCaptchaService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 手机验证码 控制器
 *
 * @author castle
 * @since 2021-07-13
 */
@Api(tags="手机验证码管理控制器")
@Controller
public class SysCaptchaController {
    @Autowired
    private SysCaptchaService sysCaptchaService;

    /**
     * 手机验证码的分页展示
     * @param sysCaptchaDto 手机验证码实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("手机验证码分页展示")
    @GetMapping("/system/sysCaptcha/page")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:pageList")
    public RespBody<IPage<SysCaptchaDto>> pageSysCaptcha(SysCaptchaDto sysCaptchaDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysCaptchaDto> page = new Page(pageIndex, pageSize);
        IPage<SysCaptchaDto> pages = sysCaptchaService.pageSysCaptcha(page, sysCaptchaDto);

        return RespBody.data(pages);
    }

    /**
     * 手机验证码的列表展示
     * @param sysCaptchaDto 手机验证码实体类
     * @return
     */
    @ApiOperation("手机验证码列表展示")
    @GetMapping("/system/sysCaptcha/list")
    @ResponseBody
    public RespBody<List<SysCaptchaDto>> listSysCaptcha(SysCaptchaDto sysCaptchaDto){
        List<SysCaptchaDto> list = sysCaptchaService.listSysCaptcha(sysCaptchaDto);
        return RespBody.data(list);
    }

    /**
     * 手机验证码保存
     * @param sysCaptchaDto 手机验证码实体类
     * @return
     */
    @ApiOperation("手机验证码保存")
    @PostMapping("/system/sysCaptcha/save")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:save")
    public RespBody<String> saveSysCaptcha(@RequestBody SysCaptchaDto sysCaptchaDto){
        if(CommonUtil.verifyParamNull(sysCaptchaDto,sysCaptchaDto.getCaptcha(),sysCaptchaDto.getPhone())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysCaptchaService.editByPhone(sysCaptchaDto)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 修改验证码及生效时间
     * @param sysCaptchaDto 手机验证码实体类
     * @return
     */
    @ApiOperation("修改验证码及生效时间")
    @PostMapping("/system/sysCaptcha/edit")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:edit")
    public RespBody<String> updateSysCaptcha(@RequestBody SysCaptchaDto sysCaptchaDto){
        if(CommonUtil.verifyParamNull(sysCaptchaDto,sysCaptchaDto.getCaptcha(),sysCaptchaDto.getPhone())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysCaptchaService.editByPhone(sysCaptchaDto)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 手机验证码删除
     * @param id
     * @return
     */
    @ApiOperation("手机验证码删除")
    @PostMapping("/system/sysCaptcha/delete")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:delete")
    public RespBody<String> deleteSysCaptcha(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysCaptchaService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 手机验证码批量删除
     * @param ids
     * @return
     */
    @ApiOperation("手机验证码批量删除")
    @PostMapping("/system/sysCaptcha/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:deleteBatch")
    public RespBody<String> deleteSysCaptchaBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(sysCaptchaService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 手机验证码详情
     * @param id 手机验证码id
     * @return
     */
    @ApiOperation("手机验证码详情")
    @GetMapping("/system/sysCaptcha/info")
    @ResponseBody
    @RequiresPermissions("system:sysCaptcha:info")
    public RespBody<SysCaptchaDto> infoSysCaptcha(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysCaptchaEntity sysCaptchaEntity = sysCaptchaService.getById(id);
        SysCaptchaDto sysCaptchaDto = ConvertUtil.transformObj(sysCaptchaEntity,SysCaptchaDto.class);

        return RespBody.data(sysCaptchaDto);
    }

    /**
     * 获取验证码
     * @param phone 手机号
     * @return
     */
    @ApiOperation("获取验证码")
    @GetMapping("/system/sysCaptcha/init")
    @ResponseBody
    public RespBody<String> initSysCaptcha(@RequestParam String phone){
        if(CommonUtil.verifyParamNull(phone)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        return RespBody.data(sysCaptchaService.initCaptcha(phone));
    }

    /**
     * 校验验证码
     * @param phone 手机号
     * @return
     */
    @ApiOperation("校验验证码")
    @GetMapping("/system/sysCaptcha/verify")
    @ResponseBody
    public RespBody<String> verifySysCaptcha(@RequestParam String phone,@RequestParam String captcha){
        if(CommonUtil.verifyParamNull(phone)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        return RespBody.data(sysCaptchaService.verifySysCaptcha(phone,captcha));
    }


}
