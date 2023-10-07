package com.castle.fortress.admin.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.entity.ConfigApiEntity;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 框架绑定api配置管理 控制器
 *
 * @author castle
 * @since 2022-04-12
 */
@Api(tags="框架绑定api配置管理管理控制器")
@Controller
public class ConfigApiController {
    @Autowired
    private ConfigApiService configApiService;
    @Autowired
    private BindApiUtils bindApiUtils;

    /**
     * 框架绑定api配置管理的分页展示
     * @param configApiDto 框架绑定api配置管理实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("框架绑定api配置管理分页展示")
    @GetMapping("/system/configApi/page")
    @ResponseBody
    @RequiresPermissions("system:configApi:pageList")
    public RespBody<IPage<ConfigApiDto>> pageConfigApi(ConfigApiDto configApiDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<ConfigApiDto> page = new Page(pageIndex, pageSize);
        IPage<ConfigApiDto> pages = configApiService.pageConfigApi(page, configApiDto);

        return RespBody.data(pages);
    }

    /**
     * 框架绑定api配置管理的列表展示
     * @param configApiDto 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理列表展示")
    @GetMapping("/system/configApi/list")
    @ResponseBody
    public RespBody<List<ConfigApiDto>> listConfigApi(ConfigApiDto configApiDto){
        List<ConfigApiDto> list = configApiService.listConfigApi(configApiDto);
        return RespBody.data(list);
    }

    /**
     * 框架绑定api配置管理保存
     * @param configApiDto 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理保存")
    @PostMapping("/system/configApi/save")
    @ResponseBody
    @RequiresPermissions("system:configApi:save")
    public RespBody<String> saveConfigApi(@RequestBody ConfigApiDto configApiDto){
        if(configApiDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigApiEntity configApiEntity = ConvertUtil.transformObj(configApiDto,ConfigApiEntity.class);
        if(configApiService.save(configApiEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理编辑
     * @param configApiDto 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理编辑")
    @PostMapping("/system/configApi/edit")
    @ResponseBody
    @RequiresPermissions("system:configApi:edit")
    public RespBody<String> updateConfigApi(@RequestBody ConfigApiDto configApiDto){
        if(configApiDto == null || configApiDto.getId() == null || configApiDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigApiEntity configApiEntity = ConvertUtil.transformObj(configApiDto,ConfigApiEntity.class);
        if(configApiService.updateById(configApiEntity)){
            bindApiUtils.refreshOne(configApiDto);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理编辑批量
     * @param configApiDtos 框架绑定api配置管理实体类
     * @return
     */
    @ApiOperation("框架绑定api配置管理编辑批量")
    @PostMapping("/system/configApi/editBatch")
    @ResponseBody
    @RequiresPermissions("system:configApi:edit")
    public RespBody<String> updateConfigApiBatch(@RequestBody List<ConfigApiDto> configApiDtos){
        if(configApiDtos == null || configApiDtos.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<ConfigApiEntity> configApiEntitys = ConvertUtil.transformObjList(configApiDtos,ConfigApiEntity.class);
        if(configApiService.updateBatchById(configApiEntitys)){
            bindApiUtils.refreshSome(configApiDtos);
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理删除
     * @param id
     * @return
     */
    @ApiOperation("框架绑定api配置管理删除")
    @PostMapping("/system/configApi/delete")
    @ResponseBody
    @RequiresPermissions("system:configApi:delete")
    public RespBody<String> deleteConfigApi(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configApiService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 框架绑定api配置管理批量删除
     * @param ids
     * @return
     */
    @ApiOperation("框架绑定api配置管理批量删除")
    @PostMapping("/system/configApi/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:configApi:deleteBatch")
    public RespBody<String> deleteConfigApiBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(configApiService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 框架绑定api配置管理详情
     * @param id 框架绑定api配置管理id
     * @return
     */
    @ApiOperation("框架绑定api配置管理详情")
    @GetMapping("/system/configApi/info")
    @ResponseBody
    @RequiresPermissions("system:configApi:info")
    public RespBody<ConfigApiDto> infoConfigApi(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        ConfigApiEntity configApiEntity = configApiService.getById(id);
        ConfigApiDto configApiDto = ConvertUtil.transformObj(configApiEntity,ConfigApiDto.class);

        return RespBody.data(configApiDto);
    }

	/**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
	@PostMapping("/system/configApi/exportDynamic")
	@ApiOperation("动态表头导出，依据展示的字段导出对应报表")
	public void exportDynamic(@RequestBody DynamicExcelEntity<ConfigApiDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
		List<ConfigApiDto> list = configApiService.listConfigApi(dynamicExcelEntity.getDto());
		//字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
		List<List<Object>> dataList = null;
		/**
        * 根据实际情况初始化dataList,可参照 com.castle.fortress.admin.system.controller.TmpDemoController类中方法：exportDynamic
        */
		ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
	}





    /**
     * 框架绑定api配置  登录和同步相关的配置  判断是否登录和是否同步
     * @return
     */
    @ApiOperation("框架绑定api配置管理详情")
    @GetMapping("/system/configApi/getSetting")
    @ResponseBody
    public RespBody<Map<String, Object>> getSetting(){
        Map<String,Object> map = new HashMap<>();
        ConfigApiDto wx = bindApiUtils.getData("WX_LOGIN");
        ConfigApiDto qq = bindApiUtils.getData("QQ_LOGIN");
        ConfigApiDto weCom = bindApiUtils.getData("SELF_WECOM");
        ConfigApiDto ding= bindApiUtils.getData("DING_LOGIN");
        // 配置不为空 并且配置的参数不为空
        if (wx!=null && (wx.getParamMap() !=null || !wx.getParamMap().isEmpty())){
            Map<String, Object> wxMap = wx.getParamMap();
            String wxIsShow = (String) wxMap.get("isShow");
            map.put("wxIsShow",wxIsShow);
        }
        if (qq!=null && (qq.getParamMap() !=null || !qq.getParamMap().isEmpty())){
            Map<String, Object> qqMap = qq.getParamMap();
            String qqIsShow = (String) qqMap.get("isShow");
            map.put("qqIsShow",qqIsShow);
        }
        if (weCom!=null && (weCom.getParamMap() !=null || !weCom.getParamMap().isEmpty())){
            Map<String, Object> weComMap = weCom.getParamMap();
            String weComIsShow = (String) weComMap.get("isShow");
            String weComSyncStatus = (String) weComMap.get("syncStatus");
            map.put("weComIsShow",weComIsShow);
            map.put("weComSyncStatus",weComSyncStatus);
        }
        if (ding!=null && (ding.getParamMap() !=null || !ding.getParamMap().isEmpty())){
            Map<String, Object> dingMap = ding.getParamMap();
            String dingIsShow = (String) dingMap.get("isShow");
            String dingSyncStatus = (String) dingMap.get("syncStatus");
            map.put("dingIsShow",dingIsShow);
            map.put("dingSyncStatus",dingSyncStatus);
        }
        return RespBody.data(map);
    }
}
