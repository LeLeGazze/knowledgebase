package com.castle.fortress.develop.service;

import com.castle.fortress.develop.entity.DevColConfig;
import com.castle.fortress.develop.entity.DevTbConfig;
import org.beetl.core.GroupTemplate;

import java.util.List;
import java.util.Map;

/**
 * 代码生成服务类
 * @author castle
 */
public interface CodeService {
    /**
     * 生成菜单
     * @param gt
     * @param commMap
     * @param tbConfig
     */
    void generateMenu(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig);

    /**
     * 生成实体类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateEntity(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);

    /**
     * 生成Mapper接口类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateMapper(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);


    /**
     * 生成Mapper接口类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateMapperXml(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);

    /**
     * 生成服务接口类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateService(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);

    /**
     * 生成服务接口实现类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateServiceImpl(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);

    /**
     * 实现控制器类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateController(GroupTemplate gt, Map<String,Object> commMap, DevTbConfig tbConfig,List<DevColConfig> colConfigList);

    /**
     * 实现api接口控制器类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateApiController(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现Dto对象
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateDto(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现Pages对象
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generatePages(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现Pages对应的js对象
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generatePagesJs(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现vue页面
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateView(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现vue js页面
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateViewJs(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现vue 左树右表
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
	  void generateViewTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 实现vue 左树右表 js页面
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateViewJsTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 左树右表 实体类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateEntityTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 左树右表 dto对象
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateDtoTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    /**
     * 左树右表 Mapper接口类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateMapperXmlTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);


    /**
     * 树形表格 实现控制器类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
	  void generateControllerTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);
    /**
     * 树形表格 实现api接口控制器类
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateApiControllerTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);
    /**
     * 树形表格 实现Dto对象
     * @param gt
     * @param commMap
     * @param tbConfig
     * @param colConfigList
     */
    void generateDtoTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateViewTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateViewJsTree(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateMapperTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateServiceTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateServiceImplTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateControllerTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);

    void generateApiControllerTreeTable(GroupTemplate gt, Map<String, Object> commMap, DevTbConfig tbConfig, List<DevColConfig> colConfigList);
}
