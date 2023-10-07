package com.castle.fortress.develop.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.develop.common.DevConstant;
import com.castle.fortress.develop.common.ListDataTypeEnum;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.service.DevColConfigService;
import com.castle.fortress.develop.service.DevTbConfigService;
import com.castle.fortress.develop.service.DevViewListConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 开发 数据源 配置控制器
 * @author castle
 */
@RestController
@RequestMapping("/colconfig")
@Api(tags="表字段管理控制器")
public class DevColConfigController {
    @Autowired
    private DevColConfigService devColConfigService;
    @Autowired
    private DevTbConfigService devTbConfigService;
    @Autowired
    private DevViewListConfigService devViewListConfigService;

    /**
     * 表字段分页展示
     * @param colConfig 表字段实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("表字段分页展示")
    @GetMapping("/page")
    public RespBody<IPage<DevColConfig>> pageColConfig(DevColConfig colConfig, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? DevConstant.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? DevConstant.DEFAULT_PAGE_SIZE:size;
        Page<DevColConfig> page = new Page<>(pageIndex, pageSize);
        IPage<DevColConfig> pages = devColConfigService.pageColConfig(page, colConfig);
        return RespBody.data(pages);
    }

    /**
     * 表字段列表展示
     * @param tbId
     * @return
     */
    @ApiOperation("表字段列表展示")
    @GetMapping("/list")
    public RespBody<List<DevColConfig>> listColConfig(Long tbId){
        if(tbId == null){
            return RespBody.data(null);
        }
        List<DevColConfig> list = devColConfigService.listColConfig(tbId);
        for(DevColConfig colConfig:list){
            colConfig.setDictName(colConfig.getListdataConfig());
            colConfig.setJsonObj(colConfig.getListdataConfig());
            Map<String,String> map = new HashMap<>();
            map.put("enumName","");
            map.put("moduleName","");
            colConfig.setEnumObj(map);
            map = new HashMap<>();
            map.put("urlName","");
            map.put("code","");
            map.put("name","");
            colConfig.setUrlObj(map);
            //enum配置
            if(ListDataTypeEnum.ENUM_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                map = new HashMap<>();
                map.put("enumName",jo.getStr("enumName"));
                map.put("moduleName",jo.getStr("moduleName"));
                colConfig.setEnumObj(map);
            //接口配置
            }else if(ListDataTypeEnum.URL_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                map = new HashMap<>();
                map.put("urlName",jo.getStr("urlName"));
                map.put("code",jo.getStr("code"));
                map.put("name",jo.getStr("name"));
                colConfig.setUrlObj(map);
            }
        }
        return RespBody.data(list);
    }


    /**
     * 表字段保存
     * @param colConfig 表字段实体类
     * @return
     */
    @ApiOperation("表字段保存")
    @PostMapping("/save")
    public RespBody<String> saveColConfig(@RequestBody DevColConfig colConfig){
        if(colConfig == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devColConfigService.save(colConfig)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 表字段批量编辑
     * @param colConfigList 表字段实体类
     * @return
     */
    @ApiOperation("表字段编辑")
    @PostMapping("/editBatch")
    public RespBody<String> updateColConfigBatch(@RequestBody List<DevColConfig> colConfigList){
        if(colConfigList == null || colConfigList.size() <1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevTbConfig tbConfig = devTbConfigService.getById(colConfigList.get(0).getTbId());
        devColConfigService.delByTbId(colConfigList.get(0).getTbId());
        devViewListConfigService.delByTbName(tbConfig.getTbName());
        if(devColConfigService.saveBatch(colConfigList)){
            devViewListConfigService.init(tbConfig.getTbName());
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 表字段删除
     * @param id 表字段id
     * @return
     */
    @ApiOperation("表字段删除")
    @PostMapping("/delete")
    public RespBody<String> deleteColConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(devColConfigService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 表字段详情
     * @param id 表字段id
     * @return
     */
    @ApiOperation("表字段详情")
    @GetMapping("/info")
    public RespBody<DevColConfig> infoColConfig(@RequestParam String id){
        if(Strings.isEmpty(id)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        DevColConfig colConfig = devColConfigService.getById(id);
        return RespBody.data(colConfig);
    }
}
