package com.castle.fortress.develop.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.MenuTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.develop.entity.DevTbConfig;
import com.castle.fortress.develop.entity.SysMenu;
import com.castle.fortress.develop.service.DevTbConfigService;
import com.castle.fortress.develop.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统菜单管理
 * @author castle
 */
@RestController
@Api(tags = "菜单管理控制器")
public class MenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private DevTbConfigService tbConfigService;


    /**
     * 菜单页面展示
     * @return
     */
    @ApiOperation("菜单页面展示")
    @GetMapping("/code/menu/list")
    public RespBody<List<SysMenu>> listMenu(){
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("type", MenuTypeEnum.MENU.getCode());
        wrapper.eq("status",YesNoEnum.YES.getCode());
        List<SysMenu> menus = sysMenuService.list(wrapper);
        List<SysMenu> menuList = ConvertUtil.listToTree(menus);
        return RespBody.data(menuList);
    }

    /**
     * 生成菜单
     * @param menuMap
     * key：
     * id: DevTbConfig.id,
     * name: 菜单名称,
     * parentId: 上级菜单id,
     * icon: 菜单图标,
     * @return
     * @throws Exception
     */
    @ApiOperation("生成菜单")
    @PostMapping("/code/generateMenu")
    public RespBody<String> generateVue(@RequestBody Map menuMap) {
        if(menuMap == null || menuMap.get("id") == null || menuMap.get("name") == null || StrUtil.isEmpty(menuMap.get("name").toString())){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Long id = Long.parseLong(menuMap.get("id").toString());
        DevTbConfig tbConfig = tbConfigService.getById(id);
        if(tbConfig == null){
            throw new ErrorException(GlobalRespCode.DB_DATA_ERROR);
        }
        String entityKey="";
        if(StrUtil.isNotEmpty(tbConfig.getTbPrefix())){
            entityKey = StrUtil.toCamelCase(tbConfig.getTbName().substring(tbConfig.getTbPrefix().length()));
        }else{
            entityKey = StrUtil.toCamelCase(tbConfig.getTbName());
        }
        String lowerEntitykey=entityKey.toLowerCase();
        Long parentId = menuMap.get("parentId") == null || StrUtil.isEmpty(menuMap.get("parentId").toString())? null:Long.parseLong(menuMap.get("parentId").toString());
        String name = menuMap.get("name").toString();
        String icon = menuMap.get("icon").toString();
        Long menuId = IdWorker.getId();
        SysMenu menu = new SysMenu(menuId,YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),parentId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":pageList;"+tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":info;",name,icon,MenuTypeEnum.MENU.getCode(),1,"/"+tbConfig.getModuleName()+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?("/"+tbConfig.getSubModuleName()):"")+"/"+ CommonUtil.emptyIfNull(lowerEntitykey),"生成菜单");
        SysMenu addButton = new SysMenu(IdWorker.getId(),YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),menuId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":save;","添加",null,MenuTypeEnum.BUTTON.getCode(),1,null,"生成添加按钮");
        SysMenu editButton = new SysMenu(IdWorker.getId(),YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),menuId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":edit;","编辑",null,MenuTypeEnum.BUTTON.getCode(),1,null,"生成编辑按钮");
        SysMenu delButton = new SysMenu(IdWorker.getId(),YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),menuId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":delete;","删除",null,MenuTypeEnum.BUTTON.getCode(),1,null,"生成删除按钮");
        SysMenu delBatchButton = new SysMenu(IdWorker.getId(),YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),menuId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":deleteBatch;","批量删除",null,MenuTypeEnum.BUTTON.getCode(),1,null,"生成批量删除按钮");
        SysMenu infoButton = new SysMenu(IdWorker.getId(),YesNoEnum.YES.getCode(),YesNoEnum.NO.getCode(),menuId,tbConfig.getModuleName()+":"+(StrUtil.isNotEmpty(tbConfig.getSubModuleName())?(tbConfig.getSubModuleName()+":"):"")+entityKey+":info;","详情",null,MenuTypeEnum.BUTTON.getCode(),1,null,"生成详情按钮");
        List<SysMenu> menuList = new ArrayList<>();
        menuList.add(menu);
        menuList.add(addButton);
        menuList.add(editButton);
        menuList.add(delButton);
        menuList.add(delBatchButton);
        menuList.add(infoButton);
        sysMenuService.saveBatch(menuList);
        return RespBody.data("生成成功");
    }



}
