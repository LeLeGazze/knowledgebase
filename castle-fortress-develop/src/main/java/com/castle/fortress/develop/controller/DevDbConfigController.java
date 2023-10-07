package com.castle.fortress.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.develop.common.DevConstant;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.service.DevDbConfigService;
import com.castle.fortress.develop.utils.DbUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * 开发 数据源 配置控制器
 * @author castle
 */
@RestController
@RequestMapping("/dbconfig")
@Api(tags="数据源管理控制器")
public class DevDbConfigController {
    @Autowired
    private DevDbConfigService devDbConfigService;

    /**
     * 获取生效的数据源列表
     * @return
     */
    @ApiOperation("获取生效的数据源列表")
    @GetMapping("/list")
    public RespBody<List<DevDbConfig>> list(){
        List<DevDbConfig> devDbConfigs=devDbConfigService.queryEffectiveDbConfigs();
        return RespBody.data(devDbConfigs);
    }

    /**
     * 数据源的分页展示
     * @param DevDbConfig 数据源实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("数据源的分页展示")
    @ApiParam
    @GetMapping("/page")
    public RespBody<IPage<DevDbConfig>> pageDbConfig(DevDbConfig DevDbConfig,@RequestParam(required = false) Integer current,@RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? DevConstant.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? DevConstant.DEFAULT_PAGE_SIZE:size;
        Page<DevDbConfig> page = new Page<>(pageIndex, pageSize);
        IPage<DevDbConfig> pages = devDbConfigService.pageDbConfig(page, DevDbConfig);
        return RespBody.data(pages);
    }

    /**
     * 数据源保存
     * @param dbConfig 数据源实体类
     * @return
     */
    @ApiOperation("数据源保存")
    @PostMapping("/save")
    public RespBody<String> saveDbConfig(@RequestBody DevDbConfig dbConfig){
        if(dbConfig == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        dbConfig.setCreateTime(new Date());
        if(devDbConfigService.save(dbConfig)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 测试数据源连接
     * @param dbConfig 数据源实体类
     * @return
     */
    @ApiOperation("测试数据源连接")
    @PostMapping("/testConnection")
    public RespBody<String> testConnection(@RequestBody DevDbConfig dbConfig){
        if(dbConfig == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        try {
            Connection conn = DbUtils.getConnection(dbConfig);
            conn.close();
            return RespBody.data("连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RespBody.fail("连接失败,请检查配置");
        }
    }

    /**
     * 数据源修改
     * @param dbConfig 数据源实体类
     * @return
     */
    @ApiOperation("数据源编辑")
    @PostMapping("/edit")
    public RespBody<String> updateDbConfig(@RequestBody DevDbConfig dbConfig){
        if(dbConfig == null || dbConfig.getId() == null || dbConfig.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devDbConfigService.updateById(dbConfig)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 数据源删除
     * @param id 数据源id
     * @return
     */
    @ApiOperation("数据源删除")
    @PostMapping("/delete")
    public RespBody<String> deleteDbConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devDbConfigService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 数据源详情
     * @param id 数据源id
     * @return
     */
    @ApiOperation("数据源详情")
    @GetMapping("/info")
    public RespBody<DevDbConfig> infoDbConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevDbConfig dbConfig = devDbConfigService.getById(id);
        return RespBody.data(dbConfig);
    }
}
