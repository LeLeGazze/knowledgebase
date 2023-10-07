package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.system.dto.SysMenuDto;
import com.castle.fortress.admin.system.entity.SysMenu;
import com.castle.fortress.common.enums.MenuTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.InstructMenuEntity;
import com.castle.fortress.admin.system.dto.InstructMenuDto;
import com.castle.fortress.admin.system.mapper.InstructMenuMapper;
import com.castle.fortress.admin.system.service.InstructMenuService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 菜单指令配置表 服务实现类
 *
 * @author castle
 * @since 2022-08-24
 */
@Service
public class InstructMenuServiceImpl extends ServiceImpl<InstructMenuMapper, InstructMenuEntity> implements InstructMenuService {
    /**
    * 获取查询条件
    * @param instructMenuDto
    * @return
    */
    public QueryWrapper<InstructMenuEntity> getWrapper(InstructMenuDto instructMenuDto){
        QueryWrapper<InstructMenuEntity> wrapper= new QueryWrapper();
        if(instructMenuDto != null){
            InstructMenuEntity instructMenuEntity = ConvertUtil.transformObj(instructMenuDto,InstructMenuEntity.class);
            wrapper.like(StrUtil.isNotEmpty(instructMenuEntity.getInstructPre()),"instruct_pre",instructMenuEntity.getInstructPre());
            wrapper.like(StrUtil.isNotEmpty(instructMenuEntity.getInstructKeys()),"instruct_keys",instructMenuEntity.getInstructKeys());
            wrapper.like(StrUtil.isNotEmpty(instructMenuEntity.getTitle()),"title",instructMenuEntity.getTitle());
            wrapper.like(instructMenuEntity.getStatus() != null,"status",instructMenuEntity.getStatus());
        }
        return wrapper;
    }


    @Override
    public IPage<InstructMenuDto> pageInstructMenu(Page<InstructMenuDto> page, InstructMenuDto instructMenuDto) {
        QueryWrapper<InstructMenuEntity> wrapper = getWrapper(instructMenuDto);
        Page<InstructMenuEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<InstructMenuEntity> instructMenuPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<InstructMenuDto> pageDto = new Page(instructMenuPage.getCurrent(), instructMenuPage.getSize(),instructMenuPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(instructMenuPage.getRecords(),InstructMenuDto.class));
        return pageDto;
    }


    @Override
    public List<InstructMenuDto> listInstructMenu(InstructMenuDto instructMenuDto){
        QueryWrapper<InstructMenuEntity> wrapper = getWrapper(instructMenuDto);
        List<InstructMenuEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,InstructMenuDto.class);
    }

    @Override
    public List<Map> filterInstructMenu(String filterInfo,List<SysMenu> menuList) {
        List<Map> menus = new ArrayList<>();
        if(StrUtil.isNotEmpty(filterInfo)&&!menuList.isEmpty()){
            Set<Long> ids= new HashSet<>();
            //初始化数据
            Map<String,Map<String, Set<Long>>> scopeMap = new HashMap<>();
            InstructMenuDto instructMenuDto = new InstructMenuDto();
            instructMenuDto.setStatus(YesNoEnum.YES.getCode());
            List<InstructMenuDto> list =  listInstructMenu(instructMenuDto);
            for(InstructMenuDto dto:list){
                putToMap(dto,scopeMap);
            }
            //过滤匹配的指令数据
            for(String pre:scopeMap.keySet()){
                if(filterInfo.indexOf(pre)!=-1){
                    Map<String,Set<Long>> keysMap = scopeMap.get(pre);
                    for(String key:keysMap.keySet()){
                        if(filterInfo.indexOf(key)!=-1){
                            ids.addAll(keysMap.get(key));
                        }
                    }
                }
            }
            List<InstructMenuDto> filterList= new ArrayList<>();
            for(Long id:ids){
                for(InstructMenuDto ins:list){
                    if(ins.getId().equals(id)){
                        filterList.add(ins);
                    }
                }
            }
            //返回匹配菜单
            Set<Long> existIds= new HashSet<>();
            if(!filterList.isEmpty()){
                for(InstructMenuDto ins:filterList){
                    if(!existIds.contains(ins.getMenuId())){
                        existIds.add(ins.getMenuId());
                        for(SysMenu m:menuList){
                            //只支持菜单或内页
                            if(m.getId().equals(ins.getMenuId())&&!m.getType().equals(MenuTypeEnum.BUTTON)){
                                Map d = new HashMap();
                                d.put("value",m.getId()+"");
                                d.put("label",m.getName());
                                d.put("path",m.getViewPath());
                                d.put("icon",m.getIcon());
                                String parentName= "";
                                if(m.getParentId()!=null){
                                    for(SysMenu p:menuList){
                                        if(m.getParentId().equals(p.getId())){
                                            parentName = p.getName();
                                            break;
                                        }
                                    }
                                }
                                if(StrUtil.isNotEmpty(ins.getTitle())){
                                    d.put("title",ins.getTitle());
                                }else{
                                    if(m.getType().equals(MenuTypeEnum.MENU)){
                                        d.put("title",d.get("label"));
                                    }else{
                                        d.put("title",StrUtil.isEmpty(parentName)?d.get("label"):(parentName+"-"+d.get("label")));
                                    }
                                }
                                d.put("label",StrUtil.isEmpty(parentName)?d.get("label"):(parentName+"-"+d.get("label")));
                                menus.add(d);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return menus;
    }

    private void putToMap(InstructMenuDto dto,Map<String,Map<String, Set<Long>>> map){
        if(!CommonUtil.verifyParamNull(dto,dto.getInstructPre(),dto.getInstructKeys(),dto.getMenuId())){
            String[] pres= dto.getInstructPre().split(";");
            for(String pre:pres){
                if(StrUtil.isNotEmpty(pre)){
                    Map<String, Set<Long>> keysMap = map.get(pre);
                    Set<Long> ids = null;
                    if(keysMap == null){
                        keysMap = new HashMap<>();
                    }else{
                        ids = keysMap.get(dto.getInstructKeys());
                    }
                    if(ids == null){
                        ids = new HashSet<>();
                    }
                    ids.add(dto.getId());
                    keysMap.put(dto.getInstructKeys(),ids);
                    map.put(pre,keysMap);
                }
            }
        }
    }
}

