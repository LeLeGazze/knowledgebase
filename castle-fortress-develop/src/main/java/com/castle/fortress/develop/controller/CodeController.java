package com.castle.fortress.develop.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.common.entity.EnumEntity;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.ValidateTypeEnum;
import com.castle.fortress.common.enums.ViewFormTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.develop.common.DbQueryConditionEnum;
import com.castle.fortress.develop.common.ListDataTypeEnum;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.service.CodeService;
import com.castle.fortress.develop.service.DevColConfigService;
import com.castle.fortress.develop.service.DevTbConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 生成控制器
 *
 * @author castle
 */
@RequestMapping("/code")
@Controller
@Api(tags = "代码生成控制器")
public class CodeController {

    @Autowired
    private DevColConfigService colConfigService;
    @Autowired
    private CodeService codeService;
    @Autowired
    private DevTbConfigService tbConfigService;


    /**
     * 依据表的配置信息生成VUE版本代码
     *
     * @param tbConfig 表配置实体类
     * @return
     * @throws Exception
     */
    @ApiOperation("生成代码")
    @PostMapping("/generateVue")
    @ResponseBody
    public RespBody<String> generateVue(@RequestBody DevTbConfig tbConfig) throws Exception {
        if(tbConfig == null || tbConfig.getId() == null){
            throw new ErrorException(GlobalRespCode.PARAM_MISSED);
        }
        tbConfigService.updateById(tbConfig);
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("templates");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Map<String, Object> commMap = new HashMap<>();
        //通用
        commMap.put("backOutPutDir", CommonUtil.emptyIfNull(tbConfig.getBackOutPutDir()));
        commMap.put("frontOutPutDir", CommonUtil.emptyIfNull(tbConfig.getFrontOutPutDir()));
        //实体父类
        if (Strings.isNotEmpty(tbConfig.getSuperEntityClass())) {
            commMap.put("hasSuperEntity", true);
            commMap.put("superEntity", tbConfig.getSuperEntityClass().substring(tbConfig.getSuperEntityClass().lastIndexOf(".") + 1));
        } else {
            commMap.put("hasSuperEntity", false);
        }
        commMap.put("swagger2", YesNoEnum.YES.getCode().equals(tbConfig.getSwagger2Flag()));
        if(StrUtil.isNotEmpty(tbConfig.getTbPrefix())){
            commMap.put("entityKey", StrUtil.toCamelCase(tbConfig.getTbName().substring(tbConfig.getTbPrefix().length())));
            commMap.put("className", StrUtil.upperFirst(StrUtil.toCamelCase(tbConfig.getTbName().substring(tbConfig.getTbPrefix().length()))));
        }else{
            commMap.put("entityKey", StrUtil.toCamelCase(tbConfig.getTbName()));
            commMap.put("className", StrUtil.upperFirst(StrUtil.toCamelCase(tbConfig.getTbName())));
        }

        String classDdsc = StrUtil.emptyIfNull(tbConfig.getTbDesc()).equals("") ? commMap.get("enetityKey").toString() : tbConfig.getTbDesc();
        commMap.put("classDdsc", classDdsc);

        //开发者信息
        commMap.put("author", tbConfig.getAuthor());
        commMap.put("datetime", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        commMap.put("date", DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN));

        //表信息
        commMap.put("tableName", tbConfig.getTbName().toLowerCase());
        commMap.put("tbConfig", tbConfig);
        //表字段信息
        QueryWrapper<DevColConfig> colQueryWrapper = new QueryWrapper<>();
        colQueryWrapper.eq("tb_id", tbConfig.getId());
        List<DevColConfig> colConfigList = colConfigService.list(colQueryWrapper);


        if(StrUtil.isNotEmpty(tbConfig.getBackOutPutDir())){
            //表格
            if(1 == tbConfig.getViewType()){
                codeService.generateEntity(gt, commMap, tbConfig, colConfigList);
                codeService.generateDto(gt, commMap, tbConfig, colConfigList);
                codeService.generateMapper(gt, commMap, tbConfig, colConfigList);
                codeService.generateMapperXml(gt, commMap, tbConfig, colConfigList);
                codeService.generateService(gt, commMap, tbConfig, colConfigList);
                codeService.generateServiceImpl(gt, commMap, tbConfig, colConfigList);
                codeService.generateController(gt, commMap, tbConfig, colConfigList);
                codeService.generateApiController(gt, commMap, tbConfig, colConfigList);
                //左树右表
            }else if (2 == tbConfig.getViewType()){
                //调用扩展的分页等方法
                codeService.generateMapperTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateServiceTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateServiceImplTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateControllerTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateApiControllerTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateEntityTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateDtoTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateMapperXmlTreeTable(gt, commMap, tbConfig, colConfigList);
            //树形结构的表
            }else if(3 == tbConfig.getViewType()){
                //调用扩展的分页等方法
                codeService.generateEntity(gt, commMap, tbConfig, colConfigList);
                codeService.generateMapper(gt, commMap, tbConfig, colConfigList);
                codeService.generateMapperXml(gt, commMap, tbConfig, colConfigList);
                codeService.generateService(gt, commMap, tbConfig, colConfigList);
                codeService.generateServiceImpl(gt, commMap, tbConfig, colConfigList);
                codeService.generateControllerTree(gt, commMap, tbConfig, colConfigList);
                codeService.generateApiControllerTree(gt, commMap, tbConfig, colConfigList);
                codeService.generateDtoTree(gt, commMap, tbConfig, colConfigList);

            }
        }
        if(StrUtil.isNotEmpty(tbConfig.getFrontOutPutDir())){
            //表格
            if(1 == tbConfig.getViewType()){
                codeService.generateView(gt, commMap, tbConfig, colConfigList);
                codeService.generateViewJs(gt, commMap, tbConfig, colConfigList);
            //左树右表
            }else if (2 == tbConfig.getViewType()){
                codeService.generateViewTreeTable(gt, commMap, tbConfig, colConfigList);
                codeService.generateViewJsTreeTable(gt, commMap, tbConfig, colConfigList);
            //树形结构的表
            }else if(3 == tbConfig.getViewType()){
                codeService.generateViewTree(gt, commMap, tbConfig, colConfigList);
                codeService.generateViewJsTree(gt, commMap, tbConfig, colConfigList);
            }

        }
        return RespBody.data("生成成功");
    }

    /**
     * 依据枚举名获取列表
     *
     * @param enumName ：DbQueryCondition 数据库的运算符号；ViewFormType 视图表单类型；
     * @return
     */
    @ApiOperation("获取枚举类型列表")
    @GetMapping("/enums")
    @ResponseBody
    public RespBody<List> queryEnumsList(String enumName) {
        List<EnumEntity> list = new ArrayList<>();
        if ("DbQueryCondition".equals(enumName)) {
            for (DbQueryConditionEnum queryConditionEnum : DbQueryConditionEnum.values()) {
                list.add(new EnumEntity(queryConditionEnum.getCode().toString(), queryConditionEnum.getName()));
            }
        } else if ("ViewFormType".equals(enumName)) {
            for (ViewFormTypeEnum viewFormTypeEnum : ViewFormTypeEnum.values()) {
                list.add(new EnumEntity(viewFormTypeEnum.getCode().toString(), viewFormTypeEnum.getName()));
            }
        } else if ("ListDataType".equals(enumName)) {
            for (ListDataTypeEnum listDataTypeEnum : ListDataTypeEnum.values()) {
                list.add(new EnumEntity(listDataTypeEnum.getCode().toString(), listDataTypeEnum.getName()));
            }
        } else if ("ValidateType".equals(enumName)) {
            for (ValidateTypeEnum validateTypeEnum : ValidateTypeEnum.values()) {
                list.add(new EnumEntity(validateTypeEnum.getCode().toString(), validateTypeEnum.getName()));
            }
        }
        return RespBody.data(list);
    }


}
