package com.castle.fortress.admin.system.controller;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.system.dto.SysDictDto;
import com.castle.fortress.admin.system.dto.TmpDemoDto;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.TmpDemoEntity;
import com.castle.fortress.admin.system.excel.TmpDemoExcel;
import com.castle.fortress.admin.system.service.SysDictService;
import com.castle.fortress.admin.system.service.SysRoleService;
import com.castle.fortress.admin.system.service.TmpDemoService;
import com.castle.fortress.admin.utils.ExcelUtils;
import com.castle.fortress.common.entity.DynamicExcelEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.DataPermissionPostEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 组件示例表 控制器
 *
 * @author castle
 * @since 2021-06-02
 */
@Api(tags="组件示例表管理控制器")
@Controller
public class TmpDemoController {
    @Autowired
    private TmpDemoService tmpDemoService;

    /**
     * 测试网络流畅
     * @return
     */
    @ApiOperation("测试网络流畅")
    @GetMapping("/system/tmpDemo/hello")
    @ResponseBody
    public RespBody<String> hello(){
        return RespBody.data("hello,成功了呢");
    }

    /**
     * 组件示例表的分页展示
     * @param tmpDemoDto 组件示例表实体类
     * @param current 当前页
     * @param size  每页记录数
     * @return
     */
    @ApiOperation("组件示例表分页展示")
    @GetMapping("/system/tmpDemo/page")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:pageList")
    public RespBody<IPage<TmpDemoDto>> pageTmpDemo(TmpDemoDto tmpDemoDto, @RequestParam(required = false) Integer current, @RequestParam(required = false)Integer size){
        Integer pageIndex= current==null? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        Integer pageSize= size==null? GlobalConstants.DEFAULT_PAGE_SIZE:size;
        Page<TmpDemoDto> page = new Page(pageIndex, pageSize);
        IPage<TmpDemoDto> pages = tmpDemoService.pageTmpDemoExtends(page, tmpDemoDto);

        return RespBody.data(pages);
    }

    /**
     * 组件示例表的列表展示
     * @param tmpDemoDto 组件示例表实体类
     * @return
     */
    @ApiOperation("组件示例表列表展示")
    @GetMapping("/system/tmpDemo/list")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:list")
    public RespBody<List<TmpDemoDto>> listTmpDemo(TmpDemoDto tmpDemoDto){
        List<TmpDemoDto> list = tmpDemoService.listTmpDemo(tmpDemoDto);
        return RespBody.data(list);
    }

    /**
     * 组件示例表保存
     * @param tmpDemoDto 组件示例表实体类
     * @return
     */
    @ApiOperation("组件示例表保存")
    @PostMapping("/system/tmpDemo/save")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:save")
    public RespBody<String> saveTmpDemo(@RequestBody TmpDemoDto tmpDemoDto){
        if(tmpDemoDto == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
        if(tmpDemoService.save(tmpDemoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表编辑
     * @param tmpDemoDto 组件示例表实体类
     * @return
     */
    @ApiOperation("组件示例表编辑")
    @PostMapping("/system/tmpDemo/edit")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:edit")
    public RespBody<String> updateTmpDemo(@RequestBody TmpDemoDto tmpDemoDto){
        if(tmpDemoDto == null || tmpDemoDto.getId() == null || tmpDemoDto.getId().equals(0L)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoEntity tmpDemoEntity = ConvertUtil.transformObj(tmpDemoDto,TmpDemoEntity.class);
        if(tmpDemoService.updateById(tmpDemoEntity)){
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表删除
     * @param id
     * @return
     */
    @ApiOperation("组件示例表删除")
    @PostMapping("/system/tmpDemo/delete")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:delete")
    public RespBody<String> deleteTmpDemo(@RequestParam Long id){
        if(id == null ){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoService.removeById(id)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 组件示例表批量删除
     * @param ids
     * @return
     */
    @ApiOperation("组件示例表批量删除")
    @PostMapping("/system/tmpDemo/deleteBatch")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:deleteBatch")
    public RespBody<String> deleteTmpDemoBatch(@RequestBody List<Long> ids){
        if(ids == null || ids.size()<1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(tmpDemoService.removeByIds(ids)) {
            return RespBody.data("保存成功");
        }else{
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 组件示例表详情
     * @param id 组件示例表id
     * @return
     */
    @ApiOperation("组件示例表详情")
    @GetMapping("/system/tmpDemo/info")
    @ResponseBody
    @RequiresPermissions("system:tmpDemo:info")
    public RespBody<TmpDemoDto> infoTmpDemo(@RequestParam Long id){
        if(id == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        TmpDemoDto tmpDemoDto =  tmpDemoService.getByIdExtends(id);

        return RespBody.data(tmpDemoDto);
    }
    /**
     * 将dto 或 entity集合转换为对应excel集合后导出
     * @param params
     * @param response
     * @throws Exception
     */
    @GetMapping("/system/tmpDemo/export")
    @ApiOperation("普通导出")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TmpDemoDto> list = tmpDemoService.listTmpDemo(null);
        ExcelUtils.export(response,"示例文件.xlsx",null,list,TmpDemoExcel.class,true);
    }

    /**
     * 直接将excel集合后导出
     * @param params
     * @param response
     * @throws Exception
     */
    @GetMapping("/system/tmpDemo/export2")
    @ApiOperation("普通导出(数据列表可再次处理)")
    public void export2(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<TmpDemoDto> list = tmpDemoService.listTmpDemo(null);
        //可对excelList二次操作后导出
        List<TmpDemoExcel> excelList = ConvertUtil.transformObjList(list, TmpDemoExcel.class);
        ExcelUtils.export(response,"示例文件.xlsx",null,excelList,TmpDemoExcel.class,false);
    }

    /**
     * 查询动态表及字段
     * @return
     */
    @ApiOperation("查询动态表及字段")
    @GetMapping("/system/tmpDemo/demo")
    @ResponseBody
    public RespBody<List<Map>> infoTmpDemo(String tb1,String tb2,String col1,String col2,String col3){

        List<Map> map = tmpDemoService.infoTmpDemo(tb1,tb2,col1,col2,col3);

        return RespBody.data(map);
    }

    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 动态表头导出 依据展示的字段导出对应报表
     * @param dynamicExcelEntity
     * @param response
     * @throws Exception
     */
    @PostMapping("/system/tmpDemo/exportDynamic")
    @ApiOperation("动态表头导出，依据展示的字段导出对应报表")
    public void exportDynamic(@RequestBody DynamicExcelEntity<TmpDemoDto> dynamicExcelEntity, HttpServletResponse response) throws Exception {
        List<TmpDemoDto> list = tmpDemoService.listTmpDemo(dynamicExcelEntity.getDto());
        //字典、枚举、接口、json常量等转义后的列表数据 根据实际情况初始化该对象，null为list数据直接导出
        List<List<Object>> dataList = null;
        //字典、枚举、接口、json常量的处理
        Map<String,Map<String,Object>> convertMap = new HashMap<>();

        //字典类处理 该示例中 vueRadio、vueCheckbox、vueRadio 字段使用字典（dictDemo）转义
        Map<String,Object> dictTranslateMap=new HashMap<>();
        List<SysDictDto> dictDemoList = sysDictService.listByCode("dictDemo");
        for(SysDictDto dictDto:dictDemoList){
            dictTranslateMap.put(dictDto.getDictKey(),dictDto.getDictValue());
        }
        convertMap.put("vueRadio",dictTranslateMap);
        convertMap.put("vueCheckbox",dictTranslateMap);
        convertMap.put("vueSelect",dictTranslateMap);

        //枚举处理 selectEnum 字段使用枚举（DataPermissionPostEnum） 转义
        Map<String,Object> enumTranslateMap=new HashMap<>();
        for(DataPermissionPostEnum e:DataPermissionPostEnum.values()){
            enumTranslateMap.put(e.getCode().toString(),e.getName());
        }
        convertMap.put("selectEnum",enumTranslateMap);

        //接口处理 selectUrl 字段使用 接口（{"urlName":"/system/sysRole/list","code":"id","name":"name"}） 转义
        Map<String,Object> urlTranslateMap=new HashMap<>();
        List<SysRole> roleList = sysRoleService.list(null);
        for(SysRole r:roleList){
            urlTranslateMap.put(r.getId().toString(),r.getName());
        }
        convertMap.put("selectUrl",urlTranslateMap);

        //json处理 selectJson 字段使用json数据（[{"code":"1","name":"a"},{"code":"2","name":"b"},{"code":"3","name":"c"}]） 转义
        Map<String,Object> jsonTranslateMap=new HashMap<>();
        JSONArray ja = new JSONArray("[{\"code\":\"1\",\"name\":\"a\"},{\"code\":\"2\",\"name\":\"b\"},{\"code\":\"3\",\"name\":\"c\"}]");
        for(int i=0;i<ja.size();i++){
            JSONObject o = (JSONObject)ja.get(i);
            jsonTranslateMap.put(o.get("code").toString(),o.get("name"));
        }
        convertMap.put("selectJson",jsonTranslateMap);

        //特殊字段 status 1 启用 2 禁用
        Map<String,Object> statusTranslateMap=new HashMap<>();
        statusTranslateMap.put("1","启用");
        statusTranslateMap.put("2","禁用");
        convertMap.put("status",statusTranslateMap);

        //如果对象中有复选字段需要标记
        Set<String> checkColumnSet= new HashSet<>();
        checkColumnSet.add("vueCheckbox");
        dataList = ExcelUtils.convertToDataList(list,dynamicExcelEntity.getHeaderList(),convertMap,checkColumnSet);
        ExcelUtils.exportDynamic(response,dynamicExcelEntity.getFileName()+".xlsx",null,list,dynamicExcelEntity.getHeaderList(),dataList);
    }

}
