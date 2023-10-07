package com.castle.fortress.admin.system.rest;

import com.castle.fortress.admin.system.dto.CastleHelpArticleTypeDto;
import com.castle.fortress.admin.system.service.CastleHelpArticleTypeService;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心文章类型 api 控制器
 *
 * @author majunjie
 * @since 2022-02-09
 */
@Api(tags="帮助中心文章类型api管理控制器")
@RestController
@RequestMapping("/api/system/castleHelpArticleType")
public class ApiCastleHelpArticleTypeController {
    @Autowired
    private CastleHelpArticleTypeService castleHelpArticleTypeService;


    /**
     * 帮助中心文章类型列表
     * @return
     */
    @ApiOperation("帮助中心文章类型列表")
    @GetMapping("list")
    @ResponseBody
    public RespBody<List<CastleHelpArticleTypeDto>> list(@RequestParam Map<String , Object> params){
        List<CastleHelpArticleTypeDto> list =  castleHelpArticleTypeService.getDataList(params);
        return RespBody.data(list);
    }

    /**
     * 获取包含某关键词标题的文章的类型
     * @return
     */
    @ApiOperation("帮助中心文章类型列表")
    @GetMapping("searchList")
    @ResponseBody
    public RespBody<List<CastleHelpArticleTypeDto>> searchList(@RequestParam Map<String , Object> params){
        List<CastleHelpArticleTypeDto> list =  castleHelpArticleTypeService.listByArticleTitle(params);
        return RespBody.data(list);
    }
}
