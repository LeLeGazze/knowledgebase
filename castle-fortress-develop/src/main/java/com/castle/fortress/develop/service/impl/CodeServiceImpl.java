package com.castle.fortress.develop.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.castle.fortress.common.enums.ValidateTypeEnum;
import com.castle.fortress.common.enums.ViewFormTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.develop.common.ListDataTypeEnum;
import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.service.CodeService;
import com.castle.fortress.develop.utils.DbUtils;
import org.apache.logging.log4j.util.Strings;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *  代码生成服务实现类
 *  @author castle
 */
@Service
public class CodeServiceImpl implements CodeService {
    @Override
    public void generateMenu(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig) {
        //菜单的sql语句
        Template menu=gt.getTemplate("menu.sql.btl");
        menu.binding(commMap);
        menu.binding("menuId", IdWorker.getId());
        menu.binding("pageMenuId", IdWorker.getId());
        menu.binding("saveMenuId", IdWorker.getId());
        menu.binding("editMenuId", IdWorker.getId());
        menu.binding("removeMenuId", IdWorker.getId());
        menu.binding("infoMenuId", IdWorker.getId());
        FileUtil.writeUtf8String(menu.render(), tbConfig.getBackOutPutDir()
                +"/sql/"+commMap.get("entityKey")+".menu.sql");
    }

    @Override
    public void generateEntity(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //entity对象 开始
        Template entity=gt.getTemplate("entity.java.btl");
        entity.binding(commMap);
        //默认添加lombok
        imports.add("lombok.Data");
        imports.add("com.baomidou.mybatisplus.annotation.*");
        String superEntity= commMap.get("superEntity") == null?null:commMap.get("superEntity").toString();
        //没有父类
        if (!Strings.isNotEmpty(tbConfig.getSuperEntityClass())) {
            imports.add("java.io.Serializable");
        }else{
            imports.add("lombok.EqualsAndHashCode");
            if(StrUtil.isNotEmpty(superEntity)){
                imports.add("com.castle.fortress.admin.core.entity."+superEntity);
            }
        }
        //表字段需要导入的包
        for(DevColConfig colConfig:colConfigList){
            if("Long".equals(colConfig.getPropType())){
                imports.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                imports.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }
            String proClass= DbUtils.getProperPackages(colConfig.getPropType());
            if(proClass!=null){
                imports.add(proClass);
            }
        }
        List<DevColConfig> entityColList=new ArrayList<>();
        //移除父类的字段
        if(Strings.isNotEmpty(tbConfig.getSuperEntityClass())){
            if("BaseEntity".equals(superEntity)){
                for(DevColConfig colConfig:colConfigList){
                    if(!"id".equals(colConfig.getPropName())
                            && !"status".equals(colConfig.getPropName())
                            && !"createUser".equals(colConfig.getPropName())
                            && !"createTime".equals(colConfig.getPropName())
                            && !"updateUser".equals(colConfig.getPropName())
                            && !"updateTime".equals(colConfig.getPropName())
                            && !"isDeleted".equals(colConfig.getPropName())
                    ){
                        entityColList.add(colConfig);
                    }
                }
            }else if("DataAuthBaseEntity".equals(superEntity)){
                for(DevColConfig colConfig:colConfigList){
                    if(!"id".equals(colConfig.getPropName())
                            && !"status".equals(colConfig.getPropName())
                            && !"createUser".equals(colConfig.getPropName())
                            && !"createDept".equals(colConfig.getPropName())
                            && !"createPost".equals(colConfig.getPropName())
                            && !"createTime".equals(colConfig.getPropName())
                            && !"updateUser".equals(colConfig.getPropName())
                            && !"updateTime".equals(colConfig.getPropName())
                            && !"isDeleted".equals(colConfig.getPropName())
                    ){
                        entityColList.add(colConfig);
                    }
                }
            }
        }else{
            entityColList.addAll(colConfigList);
        }
        entity.binding("colList",entityColList);
        entity.binding("imports",imports);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(entity.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/entity/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Entity.java");
        //entity对象 结束
    }

    @Override
    public void generateMapper(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //Mapper对象 开始
        Template mapper=gt.getTemplate("mapper.java.btl");
        mapper.binding(commMap);
        imports.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        imports.add("org.apache.ibatis.annotations.Param");
        mapper.binding("imports",imports);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(mapper.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/mapper/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Mapper.java");
        //Mapper对象 结束
    }

    @Override
    public void generateMapperXml(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //MapperXml对象 开始
        Template mapperXml=gt.getTemplate("mapper.xml.btl");
        mapperXml.binding(commMap);
        mapperXml.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(mapperXml.render(), tbConfig.getBackOutPutDir()+"/src/main/resources"
                +"/mapper/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Mapper.xml");
        //MapperXml对象 结束
    }

    @Override
    public void generateService(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //service对象 开始
        Template service=gt.getTemplate("service.java.btl");
        service.binding(commMap);
        service.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(service.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/service/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Service.java");
        //service对象 结束
    }

    @Override
    public void generateServiceImpl(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //serviceImpl对象 开始
        Template serviceImpl=gt.getTemplate("serviceImpl.java.btl");
        serviceImpl.binding(commMap);
        for(DevColConfig colConfig:colConfigList){
            if(colConfig.getIsQuery().equals(YesNoEnum.YES.getCode())){
                colConfig.setPropNameUpperFirst(StrUtil.upperFirst(colConfig.getPropName()));
            }
        }
        serviceImpl.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(serviceImpl.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/service/impl/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"ServiceImpl.java");
        //serviceImpl对象 结束
    }

    @Override
    public void generateController(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //controller对象 开始
        Template controller=gt.getTemplate("controller.java.btl");
        controller.binding(commMap);
        controller.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(controller.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/controller/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //controller对象 结束
    }

    @Override
    public void generateApiController(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //apiController对象 开始
        Template restController=gt.getTemplate("apiController.java.btl");
        restController.binding(commMap);
        restController.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(restController.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/rest/"+ "Api"+CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //apiController对象 结束
    }

    @Override
    public void generateDto(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        List<DevColConfig> tempColConfigList= new ArrayList<>();
        tempColConfigList.addAll(colConfigList);
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //dto对象 开始
        Template dto=gt.getTemplate("dto.java.btl");
        dto.binding(commMap);
        //默认添加lombok
        imports.add("lombok.Data");
        imports.add("java.io.Serializable");
        //表字段需要导入的包
        for(DevColConfig colConfig:tempColConfigList){
            if("Long".equals(colConfig.getPropType())){
                imports.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                imports.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }else if("Date".equals(colConfig.getPropType())){
                imports.add("org.springframework.format.annotation.DateTimeFormat");
                imports.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            String proClass= DbUtils.getProperPackages(colConfig.getPropType());
            if(proClass!=null){
                imports.add(proClass);
            }
        }
        //如果继承BaseEntity需要添加扩展成员变量
        if("com.castle.fortress.admin.core.entity.BaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
        }else if("com.castle.fortress.admin.core.entity.DataAuthBaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
            DevColConfig createDeptName=new DevColConfig();
            createDeptName.setPropDesc("创建部门名称");
            createDeptName.setPropType("String");
            createDeptName.setPropName("createDeptName");
            tempColConfigList.add(createDeptName);
            DevColConfig createPostName=new DevColConfig();
            createPostName.setPropDesc("创建职位名称");
            createPostName.setPropType("String");
            createPostName.setPropName("createPostName");
            tempColConfigList.add(createPostName);
            DevColConfig dataAuthFlag=new DevColConfig();
            dataAuthFlag.setPropDesc("数据权限校验标识");
            dataAuthFlag.setPropType("Boolean");
            dataAuthFlag.setPropName("dataAuthFlag");
            tempColConfigList.add(dataAuthFlag);
        }
        dto.binding("colList",tempColConfigList);
        dto.binding("imports",imports);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(dto.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/dto/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Dto.java");
        //dto对象 结束
    }

    @Override
    public void generatePages(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //pages对象 开始
        Template page=gt.getTemplate("pages.html.btl");
        page.binding(commMap);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(page.render(), tbConfig.getFrontOutPutDir()+"/src/main/resources/templates/admin"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/pages-"+ CommonUtil.emptyIfNull(commMap.get("entityKey"))+".html");
        //pages对象 结束
    }

    @Override
    public void generatePagesJs(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //pages JS对象 开始
        Template pageJs=gt.getTemplate("pages.js.btl");
        pageJs.binding(commMap);
        pageJs.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "."+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(pageJs.render(), tbConfig.getFrontOutPutDir()+"/src/main/resources/static/js"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"."+ CommonUtil.emptyIfNull(commMap.get("entityKey"))+".js");
        //pages JS对象 结束
    }

    @Override
    public void generateView(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //vue列表对象 开始
        Template view=gt.getTemplate("vueView.vue.btl");
        view.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        view.binding("lowerEntitykey",lowerEntitykey);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        //是否有数据集合配置
        boolean hasDataList = false;
        //是否有字典值
        boolean hasDictName = false;
        //是否有枚举
        boolean hasEnumName = false;
        //是否有接口
        boolean hasUrlName = false;
        //是否有常量json
        boolean hasJsonName = false;
        //是否有富文本
        boolean hasUEditor = false;
        //是否有自定义校验
        boolean hasValidator = false;
        //是否有视频组件
        boolean hasListVideo = false;
        //是否有视频组件
        boolean hasFormVideo = false;
        Set<String> dictNameSet = new HashSet<>();
        List<Map> enumNameList = new ArrayList<>();
        List<Map> urlNameList = new ArrayList<>();
        List<Map> jsonNameList = new ArrayList<>();
        for(DevColConfig colConfig:colConfigList){
            colConfig.setDictName(colConfig.getListdataConfig());
            colConfig.setJsonObj(colConfig.getListdataConfig());
            Map<String,String> map = new HashMap<>();
            map.put("enumName","");
            map.put("moduleName","");
            map.put("enumTypeListName","");
            colConfig.setEnumObj(map);
            map = new HashMap<>();
            map.put("urlName","");
            map.put("code","");
            map.put("name","");
            colConfig.setUrlObj(map);
            if(colConfig.getListdataType()!=null){
                //字典
                if(ListDataTypeEnum.DICT_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasDictName){
                        hasDictName = true;
                    }
                    colConfig.setDataListName(colConfig.getListdataConfig()+"List");
                    dictNameSet.add(colConfig.getListdataConfig());
                //枚举
                }else if(ListDataTypeEnum.ENUM_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasEnumName){
                        hasEnumName = true;
                    }
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    String enumName = jo.getStr("enumName");
                    String moduleName = jo.getStr("moduleName");
                    Map urlMap = new HashMap();
                    urlMap.put("enumName",enumName);
                    urlMap.put("moduleName",moduleName);
                    String enumTypeList= enumName;
                    if(StrUtil.isNotEmpty(moduleName)){
                        enumTypeList=enumTypeList+"_"+moduleName;
                    }
                    colConfig.setDataListName(enumTypeList+"List");
                    urlMap.put("dataListName",enumTypeList+"List");

                    if(!existEnum(enumNameList,enumTypeList+"List")){
                        enumNameList.add(urlMap);
                    }
                //接口
                }else if(ListDataTypeEnum.URL_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasUrlName){
                        hasUrlName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    Map urlMap = new HashMap();
                    urlMap.put("dataListName",colConfig.getPropName()+"List");
                    urlMap.put("urlName",jo.getStr("urlName"));
                    urlMap.put("code",jo.getStr("code"));
                    urlMap.put("name",jo.getStr("name"));
                    urlNameList.add(urlMap);
                //json
                }else if(ListDataTypeEnum.JSON_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasJsonName){
                        hasJsonName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    Map jsonMap = new HashMap();
                    jsonMap.put("dataListName",colConfig.getPropName()+"List");
                    jsonMap.put("data",colConfig.getListdataConfig());
                    jsonNameList.add(jsonMap);
                }
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.RICH_TEXT.getCode().equals(colConfig.getFormType())){
                hasUEditor=true;
            }
            if(colConfig.getValidateType()!=null){
                if(!hasValidator){
                    hasValidator = true;
                }
                colConfig.setValidateTypeName(ValidateTypeEnum.getValueByCode(colConfig.getValidateType()));
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsList()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasListVideo = true;
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasFormVideo = true;
            }
        }
        if(hasDictName || hasEnumName ||hasUrlName ||hasJsonName){
            hasDataList = true;
        }
        view.binding("colList",colConfigList);
        view.binding("hasDataList",hasDataList);
        view.binding("hasDictName",hasDictName);
        view.binding("dictNameSet",dictNameSet);
        view.binding("hasEnumName",hasEnumName);
        view.binding("enumNameList",enumNameList);
        view.binding("hasUrlName",hasUrlName);
        view.binding("urlNameList",urlNameList);
        view.binding("hasJsonName",hasJsonName);
        view.binding("jsonNameList",jsonNameList);
        view.binding("hasListVideo",hasListVideo);
        FileUtil.writeUtf8String(view.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".vue");
        //vue列表对象 结束

        //vue编辑框对象 开始
        Template viewEdit=gt.getTemplate("vueView-edit.vue.btl");
        viewEdit.binding(commMap);
        viewEdit.binding("lowerEntitykey",lowerEntitykey);
        viewEdit.binding("colList",colConfigList);
        viewEdit.binding("hasUEditor",hasUEditor);
        viewEdit.binding("hasValidator",hasValidator);
        viewEdit.binding("hasDataList",hasDataList);
        viewEdit.binding("hasDictName",hasDictName);
        viewEdit.binding("dictNameSet",dictNameSet);
        viewEdit.binding("hasEnumName",hasEnumName);
        viewEdit.binding("enumNameList",enumNameList);
        viewEdit.binding("hasUrlName",hasUrlName);
        viewEdit.binding("urlNameList",urlNameList);
        viewEdit.binding("hasJsonName",hasJsonName);
        viewEdit.binding("jsonNameList",jsonNameList);
        FileUtil.writeUtf8String(viewEdit.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-edit.vue");
        //vue编辑框对象 结束

        //vue对象详情 开始
        Template viewInfo=gt.getTemplate("vueView-info.vue.btl");
        viewInfo.binding(commMap);
        viewInfo.binding("lowerEntitykey",lowerEntitykey);
        viewInfo.binding("colList",colConfigList);
        viewInfo.binding("hasUEditor",hasUEditor);
        viewInfo.binding("hasDataList",hasDataList);
        viewInfo.binding("hasDictName",hasDictName);
        viewInfo.binding("dictNameSet",dictNameSet);
        viewInfo.binding("hasEnumName",hasEnumName);
        viewInfo.binding("enumNameList",enumNameList);
        viewInfo.binding("hasUrlName",hasUrlName);
        viewInfo.binding("urlNameList",urlNameList);
        viewInfo.binding("hasJsonName",hasJsonName);
        viewInfo.binding("jsonNameList",jsonNameList);
        viewInfo.binding("hasFormVideo",hasFormVideo);
        FileUtil.writeUtf8String(viewInfo.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-info.vue");
        //vue对象详情 结束
    }

    @Override
    public void generateViewJs(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //vue api 脚本 开始
        Template viewJs=gt.getTemplate("vueView.js.btl");
        viewJs.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        viewJs.binding("lowerEntitykey",lowerEntitykey);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(viewJs.render(), tbConfig.getFrontOutPutDir()+"/src/api"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".js");
        //vue api 脚本 开始
    }

    @Override
    public void generateViewTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //vue列表对象 开始
        Template view=gt.getTemplate("vueViewTreeTable.vue.btl");
        view.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        view.binding("lowerEntitykey",lowerEntitykey);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        //是否有数据集合配置
        boolean hasDataList = false;
        //是否有字典值
        boolean hasDictName = false;
        //是否有枚举
        boolean hasEnumName = false;
        //是否有接口
        boolean hasUrlName = false;
        //是否有常量json
        boolean hasJsonName = false;
        //是否有富文本
        boolean hasUEditor = false;
        //是否有自定义校验
        boolean hasValidator = false;
        //是否有视频组件
        boolean hasListVideo = false;
        //是否有视频组件
        boolean hasFormVideo = false;
        Set<String> dictNameSet = new HashSet<>();
        List<Map> enumNameList = new ArrayList<>();
        List<Map> urlNameList = new ArrayList<>();
        List<Map> jsonNameList = new ArrayList<>();
        for(DevColConfig colConfig:colConfigList){
            colConfig.setDictName(colConfig.getListdataConfig());
            colConfig.setJsonObj(colConfig.getListdataConfig());
            Map<String,String> map = new HashMap<>();
            map.put("enumName","");
            map.put("moduleName","");
            map.put("enumTypeListName","");
            colConfig.setEnumObj(map);
            map = new HashMap<>();
            map.put("urlName","");
            map.put("code","");
            map.put("name","");
            colConfig.setUrlObj(map);
            if(colConfig.getListdataType()!=null){
                //字典
                if(ListDataTypeEnum.DICT_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasDictName){
                        hasDictName = true;
                    }
                    colConfig.setDataListName(colConfig.getListdataConfig()+"List");
                    dictNameSet.add(colConfig.getListdataConfig());
                    //枚举
                }else if(ListDataTypeEnum.ENUM_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasEnumName){
                        hasEnumName = true;
                    }
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    String enumName = jo.getStr("enumName");
                    String moduleName = jo.getStr("moduleName");
                    Map urlMap = new HashMap();
                    urlMap.put("enumName",enumName);
                    urlMap.put("moduleName",moduleName);
                    String enumTypeList= enumName;
                    if(StrUtil.isNotEmpty(moduleName)){
                        enumTypeList=enumTypeList+"_"+moduleName;
                    }
                    colConfig.setDataListName(enumTypeList+"List");
                    urlMap.put("dataListName",enumTypeList+"List");

                    if(!existEnum(enumNameList,enumTypeList+"List")){
                        enumNameList.add(urlMap);
                    }
                    //接口
                }else if(ListDataTypeEnum.URL_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasUrlName){
                        hasUrlName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    Map urlMap = new HashMap();
                    urlMap.put("dataListName",colConfig.getPropName()+"List");
                    urlMap.put("urlName",jo.getStr("urlName"));
                    urlMap.put("code",jo.getStr("code"));
                    urlMap.put("name",jo.getStr("name"));
                    urlNameList.add(urlMap);
                    //json
                }else if(ListDataTypeEnum.JSON_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasJsonName){
                        hasJsonName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    Map jsonMap = new HashMap();
                    jsonMap.put("dataListName",colConfig.getPropName()+"List");
                    jsonMap.put("data",colConfig.getListdataConfig());
                    jsonNameList.add(jsonMap);
                }
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.RICH_TEXT.getCode().equals(colConfig.getFormType())){
                hasUEditor=true;
            }
            if(colConfig.getValidateType()!=null){
                if(!hasValidator){
                    hasValidator = true;
                }
                colConfig.setValidateTypeName(ValidateTypeEnum.getValueByCode(colConfig.getValidateType()));
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsList()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasListVideo = true;
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasFormVideo = true;
            }
        }
        if(hasDictName || hasEnumName ||hasUrlName ||hasJsonName){
            hasDataList = true;
        }
        view.binding("colList",colConfigList);
        view.binding("hasDataList",hasDataList);
        view.binding("hasDictName",hasDictName);
        view.binding("dictNameSet",dictNameSet);
        view.binding("hasEnumName",hasEnumName);
        view.binding("enumNameList",enumNameList);
        view.binding("hasUrlName",hasUrlName);
        view.binding("urlNameList",urlNameList);
        view.binding("hasJsonName",hasJsonName);
        view.binding("jsonNameList",jsonNameList);
        view.binding("hasListVideo",hasListVideo);
        view.binding("treeUrl",tbConfig.getTreeUrl());
        view.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        view.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        view.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(view.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".vue");
        //vue列表对象 结束

        //vue编辑框对象 开始
        Template viewEdit=gt.getTemplate("vueViewTreeTable-edit.vue.btl");
        viewEdit.binding(commMap);
        viewEdit.binding("lowerEntitykey",lowerEntitykey);
        viewEdit.binding("colList",colConfigList);
        viewEdit.binding("hasUEditor",hasUEditor);
        viewEdit.binding("hasValidator",hasValidator);
        viewEdit.binding("hasDataList",hasDataList);
        viewEdit.binding("hasDictName",hasDictName);
        viewEdit.binding("dictNameSet",dictNameSet);
        viewEdit.binding("hasEnumName",hasEnumName);
        viewEdit.binding("enumNameList",enumNameList);
        viewEdit.binding("hasUrlName",hasUrlName);
        viewEdit.binding("urlNameList",urlNameList);
        viewEdit.binding("hasJsonName",hasJsonName);
        viewEdit.binding("jsonNameList",jsonNameList);
        viewEdit.binding("treeUrl",tbConfig.getTreeUrl());
        viewEdit.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        viewEdit.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        viewEdit.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(viewEdit.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-edit.vue");
        //vue编辑框对象 结束

        //vue对象详情 开始
        Template viewInfo=gt.getTemplate("vueViewTreeTable-info.vue.btl");
        viewInfo.binding(commMap);
        viewInfo.binding("lowerEntitykey",lowerEntitykey);
        viewInfo.binding("colList",colConfigList);
        viewInfo.binding("hasUEditor",hasUEditor);
        viewInfo.binding("hasDataList",hasDataList);
        viewInfo.binding("hasDictName",hasDictName);
        viewInfo.binding("dictNameSet",dictNameSet);
        viewInfo.binding("hasEnumName",hasEnumName);
        viewInfo.binding("enumNameList",enumNameList);
        viewInfo.binding("hasUrlName",hasUrlName);
        viewInfo.binding("urlNameList",urlNameList);
        viewInfo.binding("hasJsonName",hasJsonName);
        viewInfo.binding("jsonNameList",jsonNameList);
        viewInfo.binding("hasFormVideo",hasFormVideo);
        viewInfo.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(viewInfo.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-info.vue");
        //vue对象详情 结束
    }

    @Override
    public void generateViewJsTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//vue api 脚本 开始
        Template viewJs=gt.getTemplate("vueViewTreeTable.js.btl");
        viewJs.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        viewJs.binding("lowerEntitykey",lowerEntitykey);
        viewJs.binding("treeUrl",tbConfig.getTreeUrl());
        viewJs.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        viewJs.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        viewJs.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(viewJs.render(), tbConfig.getFrontOutPutDir()+"/src/api"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".js");
        //vue api 脚本 开始
    }

    @Override
    public void generateEntityTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //entity对象 开始
        Template entity=gt.getTemplate("entityTreeTable.java.btl");
        entity.binding(commMap);
        //默认添加lombok
        imports.add("lombok.Data");
        imports.add("com.baomidou.mybatisplus.annotation.*");
        String superEntity= commMap.get("superEntity") == null?null:commMap.get("superEntity").toString();
        //没有父类
        if (!Strings.isNotEmpty(tbConfig.getSuperEntityClass())) {
            imports.add("java.io.Serializable");
        }else{
            imports.add("lombok.EqualsAndHashCode");
            if(StrUtil.isNotEmpty(superEntity)){
                imports.add("com.castle.fortress.admin.core.entity."+superEntity);
            }
        }
        //表字段需要导入的包
        for(DevColConfig colConfig:colConfigList){
            if("Long".equals(colConfig.getPropType())){
                imports.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                imports.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }
            String proClass= DbUtils.getProperPackages(colConfig.getPropType());
            if(proClass!=null){
                imports.add(proClass);
            }
        }
        List<DevColConfig> entityColList=new ArrayList<>();
        //移除父类的字段
        if(Strings.isNotEmpty(tbConfig.getSuperEntityClass())){
            if("BaseEntity".equals(superEntity)){
                for(DevColConfig colConfig:colConfigList){
                    if(!"id".equals(colConfig.getPropName())
                            && !"status".equals(colConfig.getPropName())
                            && !"createUser".equals(colConfig.getPropName())
                            && !"createTime".equals(colConfig.getPropName())
                            && !"updateUser".equals(colConfig.getPropName())
                            && !"updateTime".equals(colConfig.getPropName())
                            && !"isDeleted".equals(colConfig.getPropName())
                    ){
                        entityColList.add(colConfig);
                    }
                }
            }else if("DataAuthBaseEntity".equals(superEntity)){
                for(DevColConfig colConfig:colConfigList){
                    if(!"id".equals(colConfig.getPropName())
                            && !"status".equals(colConfig.getPropName())
                            && !"createUser".equals(colConfig.getPropName())
                            && !"createDept".equals(colConfig.getPropName())
                            && !"createPost".equals(colConfig.getPropName())
                            && !"createTime".equals(colConfig.getPropName())
                            && !"updateUser".equals(colConfig.getPropName())
                            && !"updateTime".equals(colConfig.getPropName())
                            && !"isDeleted".equals(colConfig.getPropName())
                    ){
                        entityColList.add(colConfig);
                    }
                }
            }
        }else{
            entityColList.addAll(colConfigList);
        }
        entity.binding("colList",entityColList);
        entity.binding("imports",imports);
        entity.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(entity.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/entity/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Entity.java");
        //entity对象 结束
    }

    @Override
    public void generateDtoTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        List<DevColConfig> tempColConfigList= new ArrayList<>();
        tempColConfigList.addAll(colConfigList);
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //dto对象 开始
        Template dto=gt.getTemplate("dtoTreeTable.java.btl");
        dto.binding(commMap);
        //默认添加lombok
        imports.add("lombok.Data");
        imports.add("java.io.Serializable");
        //表字段需要导入的包
        for(DevColConfig colConfig:tempColConfigList){
            if("Long".equals(colConfig.getPropType())){
                imports.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                imports.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }else if("Date".equals(colConfig.getPropType())){
                imports.add("org.springframework.format.annotation.DateTimeFormat");
                imports.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            String proClass= DbUtils.getProperPackages(colConfig.getPropType());
            if(proClass!=null){
                imports.add(proClass);
            }
        }
        //如果继承BaseEntity需要添加扩展成员变量
        if("com.castle.fortress.admin.core.entity.BaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
        }else if("com.castle.fortress.admin.core.entity.DataAuthBaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
            DevColConfig createDeptName=new DevColConfig();
            createDeptName.setPropDesc("创建部门名称");
            createDeptName.setPropType("String");
            createDeptName.setPropName("createDeptName");
            tempColConfigList.add(createDeptName);
            DevColConfig createPostName=new DevColConfig();
            createPostName.setPropDesc("创建职位名称");
            createPostName.setPropType("String");
            createPostName.setPropName("createPostName");
            tempColConfigList.add(createPostName);
            DevColConfig dataAuthFlag=new DevColConfig();
            dataAuthFlag.setPropDesc("数据权限校验标识");
            dataAuthFlag.setPropType("Boolean");
            dataAuthFlag.setPropName("dataAuthFlag");
            tempColConfigList.add(dataAuthFlag);
        }
        dto.binding("colList",tempColConfigList);
        dto.binding("imports",imports);
        dto.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(dto.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/dto/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Dto.java");
        //dto对象 结束
    }

    @Override
    public void generateMapperXmlTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //MapperXml对象 开始
        Template mapperXml=gt.getTemplate("mapperTreeTable.xml.btl");
        mapperXml.binding(commMap);
        mapperXml.binding("colList",colConfigList);
        mapperXml.binding("treeTableName",tbConfig.getTreeTableName());
        mapperXml.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        mapperXml.binding("tableColDb",tbConfig.getTableCol());
        mapperXml.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        mapperXml.binding("treeValueDb",tbConfig.getTreeValue());
        mapperXml.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        mapperXml.binding("treeLabelDb",tbConfig.getTreeLabel());
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(mapperXml.render(), tbConfig.getBackOutPutDir()+"/src/main/resources"
                +"/mapper/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Mapper.xml");
        //MapperXml对象 结束
    }

    @Override
    public void generateControllerTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //controller对象 开始
        Template controller=gt.getTemplate("controllerTree.java.btl");
        controller.binding(commMap);
        controller.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(controller.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/controller/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //controller对象 结束
    }

    @Override
    public void generateApiControllerTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//apiController对象 开始
        Template restController=gt.getTemplate("apiControllerTree.java.btl");
        restController.binding(commMap);
        restController.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(restController.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/rest/"+ "Api"+CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //apiController对象 结束
    }

    @Override
    public void generateDtoTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        List<DevColConfig> tempColConfigList= new ArrayList<>();
        tempColConfigList.addAll(colConfigList);
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //dto对象 开始
        Template dto=gt.getTemplate("dtoTree.java.btl");
        dto.binding(commMap);
        //默认添加lombok
        imports.add("lombok.Data");
        imports.add("java.io.Serializable");
        imports.add("java.util.List");
        //表字段需要导入的包
        for(DevColConfig colConfig:tempColConfigList){
            if("Long".equals(colConfig.getPropType())){
                imports.add("com.fasterxml.jackson.databind.annotation.JsonSerialize");
                imports.add("com.fasterxml.jackson.databind.ser.std.ToStringSerializer");
            }else if("Date".equals(colConfig.getPropType())){
                imports.add("org.springframework.format.annotation.DateTimeFormat");
                imports.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            String proClass= DbUtils.getProperPackages(colConfig.getPropType());
            if(proClass!=null){
                imports.add(proClass);
            }
        }
        //如果继承BaseEntity需要添加扩展成员变量
        if("com.castle.fortress.admin.core.entity.BaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
        }else if("com.castle.fortress.admin.core.entity.DataAuthBaseEntity".equals(tbConfig.getSuperEntityClass())){
            DevColConfig createUserName=new DevColConfig();
            createUserName.setPropDesc("创建者姓名");
            createUserName.setPropType("String");
            createUserName.setPropName("createUserName");
            tempColConfigList.add(createUserName);
            DevColConfig createDeptName=new DevColConfig();
            createDeptName.setPropDesc("创建部门名称");
            createDeptName.setPropType("String");
            createDeptName.setPropName("createDeptName");
            tempColConfigList.add(createDeptName);
            DevColConfig createPostName=new DevColConfig();
            createPostName.setPropDesc("创建职位名称");
            createPostName.setPropType("String");
            createPostName.setPropName("createPostName");
            tempColConfigList.add(createPostName);
            DevColConfig dataAuthFlag=new DevColConfig();
            dataAuthFlag.setPropDesc("数据权限校验标识");
            dataAuthFlag.setPropType("Boolean");
            dataAuthFlag.setPropName("dataAuthFlag");
            tempColConfigList.add(dataAuthFlag);
        }
        dto.binding("colList",tempColConfigList);
        dto.binding("imports",imports);
        dto.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(dto.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/dto/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Dto.java");
        //dto对象 结束
    }

    @Override
    public void generateViewTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //vue列表对象 开始
        Template view=gt.getTemplate("vueViewTree.vue.btl");
        view.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        view.binding("lowerEntitykey",lowerEntitykey);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        //是否有数据集合配置
        boolean hasDataList = false;
        //是否有字典值
        boolean hasDictName = false;
        //是否有枚举
        boolean hasEnumName = false;
        //是否有接口
        boolean hasUrlName = false;
        //是否有常量json
        boolean hasJsonName = false;
        //是否有富文本
        boolean hasUEditor = false;
        //是否有自定义校验
        boolean hasValidator = false;
        //是否有视频组件
        boolean hasListVideo = false;
        //是否有视频组件
        boolean hasFormVideo = false;
        Set<String> dictNameSet = new HashSet<>();
        List<Map> enumNameList = new ArrayList<>();
        List<Map> urlNameList = new ArrayList<>();
        List<Map> jsonNameList = new ArrayList<>();
        for(DevColConfig colConfig:colConfigList){
            colConfig.setDictName(colConfig.getListdataConfig());
            colConfig.setJsonObj(colConfig.getListdataConfig());
            Map<String,String> map = new HashMap<>();
            map.put("enumName","");
            map.put("moduleName","");
            map.put("enumTypeListName","");
            colConfig.setEnumObj(map);
            map = new HashMap<>();
            map.put("urlName","");
            map.put("code","");
            map.put("name","");
            colConfig.setUrlObj(map);
            if(colConfig.getListdataType()!=null){
                //字典
                if(ListDataTypeEnum.DICT_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasDictName){
                        hasDictName = true;
                    }
                    colConfig.setDataListName(colConfig.getListdataConfig()+"List");
                    dictNameSet.add(colConfig.getListdataConfig());
                    //枚举
                }else if(ListDataTypeEnum.ENUM_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasEnumName){
                        hasEnumName = true;
                    }
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    String enumName = jo.getStr("enumName");
                    String moduleName = jo.getStr("moduleName");
                    Map urlMap = new HashMap();
                    urlMap.put("enumName",enumName);
                    urlMap.put("moduleName",moduleName);
                    String enumTypeList= enumName;
                    if(StrUtil.isNotEmpty(moduleName)){
                        enumTypeList=enumTypeList+"_"+moduleName;
                    }
                    colConfig.setDataListName(enumTypeList+"List");
                    urlMap.put("dataListName",enumTypeList+"List");

                    if(!existEnum(enumNameList,enumTypeList+"List")){
                        enumNameList.add(urlMap);
                    }
                    //接口
                }else if(ListDataTypeEnum.URL_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasUrlName){
                        hasUrlName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    JSONObject jo = new JSONObject(colConfig.getListdataConfig());
                    Map urlMap = new HashMap();
                    urlMap.put("dataListName",colConfig.getPropName()+"List");
                    urlMap.put("urlName",jo.getStr("urlName"));
                    urlMap.put("code",jo.getStr("code"));
                    urlMap.put("name",jo.getStr("name"));
                    urlNameList.add(urlMap);
                    //json
                }else if(ListDataTypeEnum.JSON_TYPE.getCode().equals(colConfig.getListdataType()) && StrUtil.isNotEmpty(colConfig.getListdataConfig())){
                    if(!hasJsonName){
                        hasJsonName = true;
                    }
                    colConfig.setDataListName(colConfig.getPropName()+"List");
                    Map jsonMap = new HashMap();
                    jsonMap.put("dataListName",colConfig.getPropName()+"List");
                    jsonMap.put("data",colConfig.getListdataConfig());
                    jsonNameList.add(jsonMap);
                }
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.RICH_TEXT.getCode().equals(colConfig.getFormType())){
                hasUEditor=true;
            }
            if(colConfig.getValidateType()!=null){
                if(!hasValidator){
                    hasValidator = true;
                }
                colConfig.setValidateTypeName(ValidateTypeEnum.getValueByCode(colConfig.getValidateType()));
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsList()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasListVideo = true;
            }
            if(YesNoEnum.YES.getCode().equals(colConfig.getIsForm()) && ViewFormTypeEnum.VIDEO_UPLOAD.getCode().equals(colConfig.getFormType())){
                hasFormVideo = true;
            }
        }
        if(hasDictName || hasEnumName ||hasUrlName ||hasJsonName){
            hasDataList = true;
        }
        view.binding("colList",colConfigList);
        view.binding("hasDataList",hasDataList);
        view.binding("hasDictName",hasDictName);
        view.binding("dictNameSet",dictNameSet);
        view.binding("hasEnumName",hasEnumName);
        view.binding("enumNameList",enumNameList);
        view.binding("hasUrlName",hasUrlName);
        view.binding("urlNameList",urlNameList);
        view.binding("hasJsonName",hasJsonName);
        view.binding("jsonNameList",jsonNameList);
        view.binding("hasListVideo",hasListVideo);
        view.binding("treeUrl",tbConfig.getTreeUrl());
        view.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        view.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        view.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(view.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".vue");
        //vue列表对象 结束

        //vue编辑框对象 开始
        Template viewEdit=gt.getTemplate("vueViewTree-edit.vue.btl");
        viewEdit.binding(commMap);
        viewEdit.binding("lowerEntitykey",lowerEntitykey);
        viewEdit.binding("colList",colConfigList);
        viewEdit.binding("hasUEditor",hasUEditor);
        viewEdit.binding("hasValidator",hasValidator);
        viewEdit.binding("hasDataList",hasDataList);
        viewEdit.binding("hasDictName",hasDictName);
        viewEdit.binding("dictNameSet",dictNameSet);
        viewEdit.binding("hasEnumName",hasEnumName);
        viewEdit.binding("enumNameList",enumNameList);
        viewEdit.binding("hasUrlName",hasUrlName);
        viewEdit.binding("urlNameList",urlNameList);
        viewEdit.binding("hasJsonName",hasJsonName);
        viewEdit.binding("jsonNameList",jsonNameList);
        viewEdit.binding("treeUrl",tbConfig.getTreeUrl());
        viewEdit.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        viewEdit.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        viewEdit.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(viewEdit.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-edit.vue");
        //vue编辑框对象 结束

        //vue对象详情 开始
        Template viewInfo=gt.getTemplate("vueViewTree-info.vue.btl");
        viewInfo.binding(commMap);
        viewInfo.binding("lowerEntitykey",lowerEntitykey);
        viewInfo.binding("colList",colConfigList);
        viewInfo.binding("hasUEditor",hasUEditor);
        viewInfo.binding("hasDataList",hasDataList);
        viewInfo.binding("hasDictName",hasDictName);
        viewInfo.binding("dictNameSet",dictNameSet);
        viewInfo.binding("hasEnumName",hasEnumName);
        viewInfo.binding("enumNameList",enumNameList);
        viewInfo.binding("hasUrlName",hasUrlName);
        viewInfo.binding("urlNameList",urlNameList);
        viewInfo.binding("hasJsonName",hasJsonName);
        viewInfo.binding("jsonNameList",jsonNameList);
        viewInfo.binding("hasFormVideo",hasFormVideo);
        viewInfo.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        FileUtil.writeUtf8String(viewInfo.render(), tbConfig.getFrontOutPutDir()+"/src/views"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+"-info.vue");
        //vue对象详情 结束
    }

    @Override
    public void generateViewJsTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//vue api 脚本 开始
        Template viewJs=gt.getTemplate("vueViewTree.js.btl");
        viewJs.binding(commMap);
        String lowerEntitykey=commMap.get("entityKey").toString().toLowerCase();
        viewJs.binding("lowerEntitykey",lowerEntitykey);
        viewJs.binding("treeUrl",tbConfig.getTreeUrl());
        viewJs.binding("treeValue",StrUtil.toCamelCase(tbConfig.getTreeValue()));
        viewJs.binding("treeLabel",StrUtil.toCamelCase(tbConfig.getTreeLabel()));
        viewJs.binding("tableCol",StrUtil.toCamelCase(tbConfig.getTableCol()));
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(viewJs.render(), tbConfig.getFrontOutPutDir()+"/src/api"
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/"+ CommonUtil.emptyIfNull(lowerEntitykey)+".js");
        //vue api 脚本 开始
    }

    @Override
    public void generateMapperTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
        //导入的包列表
        Set<String> imports = new HashSet<>();
        //Mapper对象 开始
        Template mapper=gt.getTemplate("mapperTreeTable.java.btl");
        mapper.binding(commMap);
        imports.add("com.baomidou.mybatisplus.core.mapper.BaseMapper");
        imports.add("org.apache.ibatis.annotations.Param");
        mapper.binding("imports",imports);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(mapper.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/mapper/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Mapper.java");
        //Mapper对象 结束
    }

    @Override
    public void generateServiceTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//service对象 开始
        Template service=gt.getTemplate("serviceTreeTable.java.btl");
        service.binding(commMap);
        service.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(service.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/service/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Service.java");
        //service对象 结束
    }

    @Override
    public void generateServiceImplTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//serviceImpl对象 开始
        Template serviceImpl=gt.getTemplate("serviceImplTreeTable.java.btl");
        serviceImpl.binding(commMap);
        for(DevColConfig colConfig:colConfigList){
            if(colConfig.getIsQuery().equals(YesNoEnum.YES.getCode())){
                colConfig.setPropNameUpperFirst(StrUtil.upperFirst(colConfig.getPropName()));
            }
        }
        serviceImpl.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(serviceImpl.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/service/impl/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"ServiceImpl.java");
        //serviceImpl对象 结束
    }

    @Override
    public void generateControllerTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//controller对象 开始
        Template controller=gt.getTemplate("controllerTreeTable.java.btl");
        controller.binding(commMap);
        controller.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(controller.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/controller/"+ CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //controller对象 结束
    }

    @Override
    public void generateApiControllerTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList) {
//apiController对象 开始
        Template restController=gt.getTemplate("apiControllerTreeTable.java.btl");
        restController.binding(commMap);
        restController.binding("colList",colConfigList);
        String subModuleName="";
        if(StrUtil.isNotEmpty(tbConfig.getSubModuleName())){
            subModuleName = "/"+tbConfig.getSubModuleName();
        }
        FileUtil.writeUtf8String(restController.render(), tbConfig.getBackOutPutDir()+"/src/main/java/"
                +tbConfig.getPackageBase().replace(".", "/")
                +"/"+tbConfig.getModuleName()+(CommonUtil.emptyIfNull(subModuleName))+"/rest/"+ "Api"+CommonUtil.emptyIfNull(commMap.get("className"))+"Controller.java");
        //apiController对象 结束
    }

    private boolean existEnum(List<Map> enumNameList,String name){
        if(enumNameList.isEmpty()){
            return false;
        }
        for(Map m:enumNameList){
            if(name.equals(m.get("dataListName"))){
                return true;
            }
        }
        return false;
    }
}
