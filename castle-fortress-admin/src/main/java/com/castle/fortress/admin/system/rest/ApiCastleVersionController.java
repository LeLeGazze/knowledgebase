package com.castle.fortress.admin.system.rest;

import com.castle.fortress.admin.system.dto.CastleVersionDto;
import com.castle.fortress.admin.system.service.CastleVersionService;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 版本管理 api 控制器
 *
 * @author 
 * @since 2022-02-14
 */
@Api(tags="版本管理api管理控制器")
@RestController
@RequestMapping("/api/system/castleVersion")
public class ApiCastleVersionController {
    @Autowired
    private CastleVersionService castleVersionService;


    /**
     * 版本管理详情
     * @return
     */
    @ApiOperation("获取最新版本号")
    @GetMapping("/newVersion")
    public RespBody<CastleVersionDto> newVersion(){
        CastleVersionDto dto = castleVersionService.getNewVersion();
        return RespBody.data(dto);
    }

    /**
     * 版本管理详情
     * @return
     */
    @ApiOperation("所有历史版本信息")
    @GetMapping("/list")
    public RespBody<List<CastleVersionDto>> list(){
        List<CastleVersionDto> list = castleVersionService.getDataList();
        return RespBody.data(list);
    }


}
