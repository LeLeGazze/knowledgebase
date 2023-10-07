package com.castle.fortress.admin.message.mail.rest;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.message.mail.dto.CastleMessageEmailRecordDto;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;
import com.castle.fortress.admin.message.mail.service.CastleMessageEmailRecordService;
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
 * 邮件发送记录表 api 控制器
 *
 * @author Mgg
 * @since 2021-10-27
 */
@Api(tags="邮件发送记录表api管理控制器")
@RestController
@RequestMapping("/api/message/mail/castleMessageEmailRecord")
public class ApiCastleMessageEmailRecordController {
    @Autowired
    private CastleMessageEmailRecordService castleMessageEmailRecordService;


    /**
     * 邮件发送记录表的分页展示
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @param currentPage 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("邮件发送记录表分页展示")
    @GetMapping("/page")
    public RespBody<IPage<CastleMessageEmailRecordDto>> pageCastleMessageEmailRecord(CastleMessageEmailRecordDto castleMessageEmailRecordDto, @RequestParam(required = false) Integer currentPage, @RequestParam(required = false)Integer size){
        Integer pageIndex= currentPage==null? GlobalConstants.DEFAULT_PAGE_INDEX:currentPage;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<CastleMessageEmailRecordDto> page = new Page(pageIndex, pageSize);

        IPage<CastleMessageEmailRecordDto> pages = castleMessageEmailRecordService.pageCastleMessageEmailRecord(page, castleMessageEmailRecordDto);
        return RespBody.data(pages);
    }

    /**
     * 邮件发送记录表保存
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @return
     */
    @ApiOperation("邮件发送记录表保存")
    @PostMapping("/save")
    public RespBody<String> saveCastleMessageEmailRecord(@RequestBody CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        if(castleMessageEmailRecordDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
        if(castleMessageEmailRecordService.save(castleMessageEmailRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表编辑
     * @param castleMessageEmailRecordDto 邮件发送记录表实体类
     * @return
     */
    @ApiOperation("邮件发送记录表编辑")
    @PostMapping("/edit")
    public RespBody<String> updateCastleMessageEmailRecord(@RequestBody CastleMessageEmailRecordDto castleMessageEmailRecordDto){
        if(castleMessageEmailRecordDto == null || castleMessageEmailRecordDto.getId() == null || castleMessageEmailRecordDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = ConvertUtil.transformObj(castleMessageEmailRecordDto,CastleMessageEmailRecordEntity.class);
        if(castleMessageEmailRecordService.updateById(castleMessageEmailRecordEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表删除
     * @param ids 邮件发送记录表id集合
     * @return
     */
    @ApiOperation("邮件发送记录表删除")
    @PostMapping("/delete")
    public RespBody<String> deleteCastleMessageEmailRecord(@RequestBody List<Long> ids){
        if(ids == null || ids.isEmpty()){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(castleMessageEmailRecordService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 邮件发送记录表详情
     * @param id 邮件发送记录表id
     * @return
     */
    @ApiOperation("邮件发送记录表详情")
    @GetMapping("/info")
    public RespBody<CastleMessageEmailRecordDto> infoCastleMessageEmailRecord(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
            CastleMessageEmailRecordEntity castleMessageEmailRecordEntity = castleMessageEmailRecordService.getById(id);
            CastleMessageEmailRecordDto castleMessageEmailRecordDto = ConvertUtil.transformObj(castleMessageEmailRecordEntity,CastleMessageEmailRecordDto.class);
        return RespBody.data(castleMessageEmailRecordDto);
    }


}
