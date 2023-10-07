package com.castle.fortress.admin.message.mail.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.mail.dto.CastleConfigMailDto;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import com.castle.fortress.admin.message.mail.service.CastleConfigMailService;
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
 * 邮件配置表 api 控制器
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Api(tags="邮件配置表api管理控制器")
@RestController
@RequestMapping("/api/message/mail/castleConfigMail")
public class ApiCastleConfigMailController {
    @Autowired
    private CastleConfigMailService castleConfigMailService;


    /**
     * 邮件配置表的分页展示
     * @param castleConfigMailDto 邮件配置表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("邮件配置表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<CastleConfigMailDto>> pageCastleConfigMail(CastleConfigMailDto castleConfigMailDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleConfigMailDto> page = new Page(pageIndex, pageSize);

        IPage<CastleConfigMailDto> pages = castleConfigMailService.pageCastleConfigMail(page, castleConfigMailDto);
        return RespBody.data(pages);
    }

    /**
     * 邮件配置表保存
     * @param castleConfigMailDto 邮件配置表实体类
     * @return
     */
    @ApiOperation("邮件配置表保存")
    @PostMapping("/save")
    public RespBody<String> saveCastleConfigMail(@RequestBody CastleConfigMailDto castleConfigMailDto){
        if(castleConfigMailDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
        if(castleConfigMailService.save(castleConfigMailEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表编辑
     * @param castleConfigMailDto 邮件配置表实体类
     * @return
     */
    @ApiOperation("邮件配置表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateCastleConfigMail(@RequestBody CastleConfigMailDto castleConfigMailDto){
        if(castleConfigMailDto == null || castleConfigMailDto.getId() == null || castleConfigMailDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleConfigMailEntity castleConfigMailEntity = ConvertUtil.transformObj(castleConfigMailDto,CastleConfigMailEntity.class);
        if(castleConfigMailService.updateById(castleConfigMailEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表删除
     * @param ids 邮件配置表id集合
     * @return
     */
    @ApiOperation("邮件配置表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteCastleConfigMail(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleConfigMailService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件配置表详情
     * @param id 邮件配置表id
     * @return
     */
    @ApiOperation("邮件配置表详情")
    @GetMapping("/info")
    public RespBody<CastleConfigMailDto> infoCastleConfigMail(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleConfigMailEntity castleConfigMailEntity = castleConfigMailService.getById(id);
            CastleConfigMailDto castleConfigMailDto = ConvertUtil.transformObj(castleConfigMailEntity,CastleConfigMailDto.class);
        return RespBody.data(castleConfigMailDto);
    }


}
