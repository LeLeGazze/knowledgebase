package com.castle.fortress.admin.system.rest;

import com.castle.fortress.admin.system.dto.CastleHelpArticleDto;
import com.castle.fortress.admin.system.service.CastleHelpArticleService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帮助中心文章 api 控制器
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Api(tags="帮助中心文章api管理控制器")
@RestController
@RequestMapping("/api/system/castleHelpArticle")
public class ApiCastleHelpArticleController {
    @Autowired
    private CastleHelpArticleService castleHelpArticleService;

    /**
     * 帮助中心文章详情
     * @param id 帮助中心文章id
     * @return
     */
    @ApiOperation("帮助中心文章详情")
    @GetMapping("/info")
    @ResponseBody
    public RespBody<CastleHelpArticleDto> infoCastleHelpArticle(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        CastleHelpArticleDto castleHelpArticleDto =  castleHelpArticleService.getByIdExtends(id);

        return RespBody.data(castleHelpArticleDto);
    }

    /**
     * 根据类型id获取帮助文章
     * @param typeId 帮助中心文章类型id
     * @return
     */
    @ApiOperation("根据类型id获取帮助文章")
    @GetMapping("/listByType")
    public RespBody<List<CastleHelpArticleDto>> infoCastleHelpArticleType( Long typeId){
        if(typeId == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<CastleHelpArticleDto> list = castleHelpArticleService.listByTypeId(typeId);
        return RespBody.data(list);
    }

}
