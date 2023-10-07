package com.castle.fortress.develop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.develop.common.DevConstant;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevDbConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.service.DevColConfigService;
import com.castle.fortress.develop.service.DevDbConfigService;
import com.castle.fortress.develop.service.DevTbConfigService;
import com.castle.fortress.develop.service.DevViewListConfigService;
import com.castle.fortress.develop.utils.DbUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 开发 表信息 配置控制器
 * @author castle
 */
@Api(tags="表信息管理控制器")
@RestController
@RequestMapping("/tbconfig")
public class DevTbConfigController {
    @Autowired
    private DevTbConfigService devTbConfigService;
    @Autowired
    private DevDbConfigService devDbConfigService;
    @Autowired
    private DevColConfigService devColConfigService;
    @Autowired
    private DevViewListConfigService devViewListConfigService;

    /**
     * 获取指定数据源下的表列表
     * @param dbId 数据源id
     * @return
     */
    @ApiOperation("获取指定数据源下的表列表")
    @GetMapping("/list")
    public RespBody<List<DevTbConfig>> queryTablesByDb(@RequestParam String dbId){
        if(CommonUtil.verifyParamNull(dbId)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
       DevDbConfig dbConfig = devDbConfigService.getById(dbId);
       if(dbConfig == null){
           throw new BizException(GlobalRespCode.DB_DATA_ERROR);
       }
       List<DevTbConfig> tbConfigList= DbUtils.getAllTables(dbConfig);
       return RespBody.data(tbConfigList);
    }

    /**
     * 表信息分页展示
     * @param tbConfig 表信息实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("表信息分页展示")
    @GetMapping("/page")
    public RespBody<IPage<DevTbConfig>> pageTbConfig(DevTbConfig tbConfig,@RequestParam(required = false) Integer current,@RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? DevConstant.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? DevConstant.DEFAULT_PAGE_SIZE:size;
        Page<DevTbConfig> page = new Page<>(pageIndex, pageSize);
        IPage<DevTbConfig> pages = devTbConfigService.pageTbConfig(page, tbConfig);
        return RespBody.data(pages);
    }

    /**
     * 依据数据源id和表名初始化表信息
     * @param tbConfig 数据源id和表名必填
     * @return
     */
    @ApiOperation("依据数据源id和表名初始化表信息")
    @PostMapping("/init")
    @Transactional(rollbackFor = BizException.class)
    public RespBody<String> initTbConfig(@RequestBody DevTbConfig tbConfig){
        if(tbConfig == null || tbConfig.getDbId() == null || Strings.isEmpty(tbConfig.getTbName())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevDbConfig dbConfig=devDbConfigService.getById(tbConfig.getDbId());
        if(dbConfig == null ){
            throw new BizException(GlobalRespCode.DB_DATA_ERROR);
        }
        //删除该数据源对应的表的所有信息
        devTbConfigService.delByTbName(tbConfig.getDbId(),tbConfig.getTbName());
        //删除视图字段展示记录
        devViewListConfigService.delByTbName(tbConfig.getTbName());
        DevTbConfig tbConfig1=DbUtils.getTableInfo(dbConfig,tbConfig.getTbName());
        tbConfig1.setCreateTime(new Date());
        //保存表结构
        if(devTbConfigService.save(tbConfig1)){
            //初始化表字段信息
            if(devColConfigService.initColumns(dbConfig,tbConfig1)){
                //初始化列表显示字段
                devViewListConfigService.init(tbConfig.getTbName());
                return RespBody.data("保存成功");
            }else{
                throw new BizException(GlobalRespCode.OPERATE_ERROR);
            }
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 同步表最新结构
     * @param tbConfig 数据源id和表名必填
     * @return
     */
    @ApiOperation("同步表最新结构")
    @PostMapping("/syn")
    @Transactional(rollbackFor = BizException.class)
    public RespBody<String> synTbConfig(@RequestBody DevTbConfig tbConfig){
        if(tbConfig == null || tbConfig.getDbId() == null || Strings.isEmpty(tbConfig.getTbName())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevDbConfig dbConfig=devDbConfigService.getById(tbConfig.getDbId());
        if(dbConfig == null ){
            throw new BizException(GlobalRespCode.DB_DATA_ERROR);
        }
        List<DevColConfig> oldCols = devColConfigService.listColConfig(tbConfig.getId());
        List<DevColConfig> newCols= DbUtils.getTableColumns(dbConfig,tbConfig.getId(),tbConfig.getTbName());
        //更新表字段结构
        if(devColConfigService.synColumns(oldCols,newCols)){
            return RespBody.data("同步成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     *修改表信息
     * @param tbConfig 表信息实体类
     * @return
     */
    @ApiOperation("修改表信息")
    @PostMapping("/edit")
    public RespBody<String> updateTbConfig(@RequestBody DevTbConfig tbConfig){
        if(tbConfig == null || tbConfig.getId() == null || tbConfig.getId().equals(0)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devTbConfigService.updateById(tbConfig)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 删除表信息
     * @param id 表信息id
     * @return
     */
    @ApiOperation("删除表信息")
    @PostMapping("/delete")
    public RespBody<String> deleteTbConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devTbConfigService.removeById(id)) {
            devColConfigService.delByTbId(Long.valueOf(id));
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 表信息详情
     * @param id 表信息id
     * @return
     */
    @ApiOperation("表信息详情")
    @GetMapping("/info")
    public RespBody<DevTbConfig> infoTbConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevTbConfig tbConfig = devTbConfigService.getById(id);
        return RespBody.data(tbConfig);
    }
}
