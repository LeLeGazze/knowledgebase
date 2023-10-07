package com.castle.fortress.admin.system.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysCaptchaDto;
import com.castle.fortress.admin.system.entity.SysCaptchaEntity;
import com.castle.fortress.admin.system.service.SysCaptchaService;
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
 * 手机验证码 api 控制器
 *
 * @author castle
 * @since 2021-07-13
 */
@Api(tags="手机验证码api管理控制器")
@RestController
@RequestMapping("/api/system/sysCaptcha")
public class ApiSysCaptchaController {
    @Autowired
    private SysCaptchaService sysCaptchaService;


    /**
     * 手机验证码的分页展示
     * @param sysCaptchaDto 手机验证码实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("手机验证码分页展示")
    @GetMapping("/page")
    public RespBody<IPage<SysCaptchaDto>> pageSysCaptcha(SysCaptchaDto sysCaptchaDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<SysCaptchaDto> page = new Page(pageIndex, pageSize);

        IPage<SysCaptchaDto> pages = sysCaptchaService.pageSysCaptcha(page, sysCaptchaDto);
        return RespBody.data(pages);
    }

    /**
     * 手机验证码保存
     * @param sysCaptchaDto 手机验证码实体类
     * @return
     */
    @ApiOperation("手机验证码保存")
    @PostMapping("/save")
    public RespBody<String> saveSysCaptcha(@RequestBody SysCaptchaDto sysCaptchaDto){
        if(sysCaptchaDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysCaptchaEntity sysCaptchaEntity = ConvertUtil.transformObj(sysCaptchaDto,SysCaptchaEntity.class);
        if(sysCaptchaService.save(sysCaptchaEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 手机验证码编辑
     * @param sysCaptchaDto 手机验证码实体类
     * @return
     */
    @ApiOperation("手机验证码编辑")
    @PostMapping("/edit")
    public RespBody<String> updateSysCaptcha(@RequestBody SysCaptchaDto sysCaptchaDto){
        if(sysCaptchaDto == null || sysCaptchaDto.getId() == null || sysCaptchaDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysCaptchaEntity sysCaptchaEntity = ConvertUtil.transformObj(sysCaptchaDto,SysCaptchaEntity.class);
        if(sysCaptchaService.updateById(sysCaptchaEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 手机验证码删除
     * @param ids 手机验证码id集合
     * @return
     */
    @ApiOperation("手机验证码删除")
    @PostMapping("/delete")
    public RespBody<String> deleteSysCaptcha(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
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
    @GetMapping("/info")
    public RespBody<SysCaptchaDto> infoSysCaptcha(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            SysCaptchaEntity sysCaptchaEntity = sysCaptchaService.getById(id);
            SysCaptchaDto sysCaptchaDto = ConvertUtil.transformObj(sysCaptchaEntity,SysCaptchaDto.class);
        return RespBody.data(sysCaptchaDto);
    }


}
