package com.castle.fortress.admin.system.rest;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.system.dto.ProtocolDto;
import com.castle.fortress.admin.system.service.ProtocolService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 协议管理 api 控制器
 *
 * @author majunjie
 * @since 2022-01-28
 */
@Api(tags="协议管理api管理控制器")
@RestController
@RequestMapping("/api/system/protocol")
public class ApiProtocolController {
    @Autowired
    private ProtocolService protocolService;

    /**
     * 协议管理详情
     * @param code 编号
     * @return
     */
    @ApiOperation("协议管理详情")
    @GetMapping("/getByCode")
    public RespBody<ProtocolDto> infoProtocol(@RequestParam String code){
        if(StrUtil.isEmpty(code)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ProtocolDto protocolDto = protocolService.getByCode(code);
        return RespBody.data(protocolDto);
    }


}
