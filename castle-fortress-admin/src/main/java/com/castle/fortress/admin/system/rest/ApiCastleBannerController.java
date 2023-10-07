package com.castle.fortress.admin.system.rest;

import com.castle.fortress.admin.system.dto.CastleBannerDto;
import com.castle.fortress.admin.system.service.CastleBannerService;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * banner图 api 控制器
 *
 * @author majunjie
 * @since 2022-02-14
 */
@Api(tags="banner图api管理控制器")
@RestController
@RequestMapping("/api/system/castleBanner")
public class ApiCastleBannerController {
    @Autowired
    private CastleBannerService castleBannerService;



    /**
     * banner图详情
     * @return
     */
    @ApiOperation("启用的banner图列表")
    @GetMapping("/list")
    public RespBody<List<CastleBannerDto>> list(){
        List<CastleBannerDto> list = castleBannerService.getDataList();
        return RespBody.data(list);
    }


}
