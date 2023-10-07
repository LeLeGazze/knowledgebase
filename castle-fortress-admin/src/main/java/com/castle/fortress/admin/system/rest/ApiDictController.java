package com.castle.fortress.admin.system.rest;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysDictDto;
import com.castle.fortress.admin.system.entity.SysDictEntity;
import com.castle.fortress.admin.system.service.SysDictService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统字典管理api
 *
 * @author castle
 */
@Api(tags = "字典api管理控制器")
@RestController
@RequestMapping("/api/system/dict")
public class ApiDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 获取指定的字符集
     * @param code
     * @return
     */
    @ApiOperation("获取指定的字典集")
    @GetMapping("/listByCode")
    public RespBody<List<Map<String, Object>>> listByCode(String code) {
        if (StrUtil.isEmpty(code)) {
            return RespBody.data(null);
        }
        List<SysDictDto> list = sysDictService.listByCode(code);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysDictDto dictDto : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("code", dictDto.getDictKey());
            map.put("name", dictDto.getDictValue());
            mapList.add(map);
        }
        return RespBody.data(mapList);
    }
}
