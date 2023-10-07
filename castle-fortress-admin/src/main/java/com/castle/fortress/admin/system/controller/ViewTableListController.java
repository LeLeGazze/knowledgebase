package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.system.entity.DevViewListConfig;
import com.castle.fortress.admin.system.service.DevViewListConfigService;
import com.castle.fortress.common.entity.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图列表字段 控制器
 * @author castle
 */
@Api(tags = "视图列表字段")
@RestController
@RequestMapping("/system/viewtable")
public class ViewTableListController {
    @Autowired
    private DevViewListConfigService devViewListConfigService;
    /**
     * 获取指定表的视图列表字段
     * @param tbName 表名
     * @return
     */
    @ApiOperation("获取指定表的视图列表字段")
    @GetMapping("/list")
    public RespBody<List> queryList(String tbName){
        List<DevViewListConfig> list=new ArrayList<>();
        if(StrUtil.isNotEmpty(tbName)){
            QueryWrapper<DevViewListConfig> wrapper = new QueryWrapper<>();
            wrapper.eq("tb_name",tbName);
            list = devViewListConfigService.list(wrapper);
        }
        return RespBody.data(list);
    }
}
