package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.mapper.SysDeptMapper;
import com.castle.fortress.admin.system.service.SysDeptService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统部门表 服务实现类
 *
 * @author castle
 * @since 2021-01-04
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDeptEntity> implements SysDeptService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public IPage<SysDeptDto> pageSysDept(Page<SysDeptDto> page, SysDeptDto sysDeptDto) {
        QueryWrapper<SysDeptEntity> wrapper= new QueryWrapper();
        wrapper.like(StrUtil.isNotEmpty(sysDeptDto.getName()), "name", sysDeptDto.getName());
        Page<SysDeptEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<SysDeptEntity> sysDeptPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<SysDeptDto> pageDto = new Page(sysDeptPage.getCurrent(), sysDeptPage.getSize(),sysDeptPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(sysDeptPage.getRecords(),SysDeptDto.class));
        return pageDto;
    }

    @Override
    public List<SysDeptDto> authorityAllDept(List<Long> roleIds,Long parentId,String name) {
        List<SysDeptEntity> deptEntities = baseMapper.authorityAllDept(roleIds,parentId,name);

        return ConvertUtil.transformObjList(deptEntities,SysDeptDto.class);
    }

    @Override
    public List<Map<String, String>> authorityComponentList(List<Long> roleIds, Long deptId) {
        List<SysDeptEntity> deptEntities = baseMapper.authorityComponentList(roleIds,deptId);
        List<Long> ids= new ArrayList<>();
        for(SysDeptEntity entity:deptEntities){
            ids.add(entity.getId());
        }
        List<Map<String,Object>> childs = new ArrayList<>();
        Map<String,Integer> childMap = new HashMap<>();
        if(!ids.isEmpty()){
            childs = baseMapper.countChild(ids);
            for(Map<String,Object> m:childs){
                childMap.put(m.get("id").toString(),Integer.parseInt(m.get("num").toString()));
            }
        }

        List<SysUserDto> userList = new ArrayList<>();
        if(deptId!=null){
            userList = sysUserService.listByDeptId(deptId);
        }
        List<Map<String, String>> result = new ArrayList<>();
        //部门
        for(SysDeptEntity dept:deptEntities){
            Map<String, String> map = new HashMap<>();
            map.put("id",dept.getId()+"");
            map.put("name",dept.getName());
            //部门
            map.put("type","00");
            if(childMap.get(map.get("id"))==null || childMap.get(map.get("id"))<1){
                //无子集
                map.put("childFlag","00");
            }else{
                //有子集
                map.put("childFlag","01");
            }
            result.add(map);
        }
        //人员
        for(SysUserDto dto:userList){
            Map<String, String> map = new HashMap<>();
            map.put("id",dto.getId() +"");
            map.put("name",dto.getRealName());
            //人员
            map.put("type","01");
            //无子集
            map.put("childFlag","00");
            result.add(map);
        }
        return result;
    }

    @Override
    public List<SysDeptDto> children(Long id) {
        QueryWrapper<SysDeptEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("parent_id",id);
        queryWrapper.eq("is_deleted",YesNoEnum.NO.getCode());
        return ConvertUtil.transformObjList(baseMapper.selectList(queryWrapper),SysDeptDto.class);
    }

    @Override
    public boolean checkColumnRepeat(SysDeptEntity sysDeptEntity) {
        boolean existFlag = false;
        //校验名称是否重复
        QueryWrapper<SysDeptEntity> queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",sysDeptEntity.getName());
        queryWrapper.eq(sysDeptEntity.getParentId()!=null,"parent_id",sysDeptEntity.getParentId());
        List<SysDeptEntity> list = baseMapper.selectList(queryWrapper);
        if(list == null || list.isEmpty()){
            return existFlag;
        }
        //新增
        if(sysDeptEntity.getId() == null){
            existFlag = true;
        //修改
        }else{
            for(SysDeptEntity deptEntity:list){
                if(!deptEntity.getId().equals(sysDeptEntity.getId())){
                    existFlag = true;
                    break;
                }
            }
        }
        return existFlag;
    }

    @Override
    public List<Map<String, String>> authoritySearchForComponent(List<Long> roleIds, Map<String, String> map, SysUser sysUser) {
        if(CommonUtil.verifyParamNull(map,map.get("type"),map.get("name"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //可见部门列表转map
        List<SysDeptDto> allDept =  authorityAllDept(roleIds,null,null);
        Map<Long,SysDeptDto> allDeptMap = new HashMap<>();
        for(SysDeptDto dto:allDept){
            allDeptMap.put(dto.getId(),dto);
        }
        List<Map<String, String>> result = new ArrayList<>();
        //部门检索
        if("00".equals(map.get("type"))){
            List<SysDeptEntity> deptEntities = baseMapper.authoritySearchForComponent(roleIds,map.get("name"));
            //部门
            for(SysDeptEntity dept:deptEntities){
                Map<String, String> m = new HashMap<>();
                m.put("id",dept.getId()+"");
                m.put("name",dept.getName());
                //部门
                m.put("type","00");
                m.put("parents",compuParentsPath(allDeptMap,dept.getId(),"00"));
                result.add(m);
            }
        //人员检索
        }else if("01".equals(map.get("type"))){
            SysUserDto sysUserDto= new SysUserDto();
            sysUserDto.setRealName(map.get("name"));
            List<SysUserDto> userList = sysUserService.listExtendsSysUser(sysUserDto,sysUser);
            //人员
            for(SysUserDto dto:userList){
                Map<String, String> m = new HashMap<>();
                m.put("id",dto.getId() +"");
                m.put("name",dto.getRealName());
                //人员
                m.put("type","01");
                m.put("parents",compuParentsPath(allDeptMap,dto.getDeptId(),"01"));
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, String>> authorityNameForComponent(List<Long> roleIds, Map<String,Object> map, SysUser sysUser) {
        if(CommonUtil.verifyParamNull(map,map.get("dept"),map.get("user"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<Long> deptIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        try {
            List<String> depts= (List<String>)map.get("dept");
            List<String> users= (List<String>)map.get("user");
            for(String d:depts){
                deptIds.add(Long.parseLong(d));
            }
            for(String u:users){
                userIds.add(Long.parseLong(u));
            }
        } catch (Exception e) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //可见部门列表转map
        List<SysDeptDto> allDept =  authorityAllDept(roleIds,null,null);
        Map<Long,SysDeptDto> allDeptMap = new HashMap<>();
        for(SysDeptDto dto:allDept){
            allDeptMap.put(dto.getId(),dto);
        }
        List<Map<String, String>> result = new ArrayList<>();
        //解析部门名称
        if(!deptIds.isEmpty()){
            List<SysDeptEntity> deptEntities = baseMapper.authorityNameForComponent(roleIds,deptIds);
            for(Long id:deptIds){
                Map<String, String> m = new HashMap<>();
                m.put("id",id+"");
                //部门
                for(SysDeptEntity dept:deptEntities){
                    if(id.equals(dept.getId())){
                        m.put("name",dept.getName());
                        //部门
                        m.put("type","00");
                        m.put("parents",compuParentsPath(allDeptMap,dept.getId(),"00"));
                        break;
                    }
                }
                result.add(m);
            }
        }
        if(!userIds.isEmpty()){
            List<SysUserDto> userList = sysUserService.listSysUser(userIds,sysUser);
            for(Long id:userIds){
                Map<String, String> m = new HashMap<>();
                m.put("id",id+"");
                //人员
                for(SysUserDto dto:userList){
                    if(id.equals(dto.getId())){
                        m.put("name",dto.getRealName());
                        //人员
                        m.put("type","01");
                        m.put("parents",compuParentsPath(allDeptMap,dto.getDeptId(),"01"));
                        break;
                    }
                }
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public List<Map<String, String>> nameForComponent(Map<String, Object> map) {
        if(CommonUtil.verifyParamNull(map,map.get("dept"),map.get("user"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        List<Long> deptIds = new ArrayList<>();
        List<Long> userIds = new ArrayList<>();
        try {
            List<String> depts= (List<String>)map.get("dept");
            List<String> users= (List<String>)map.get("user");
            for(String d:depts){
                deptIds.add(Long.parseLong(d));
            }
            for(String u:users){
                userIds.add(Long.parseLong(u));
            }
        } catch (Exception e) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        //可见部门列表转map
        List<SysDeptDto> allDept =  ConvertUtil.transformObjList(this.list(),SysDeptDto.class);
        Map<Long,SysDeptDto> allDeptMap = new HashMap<>();
        for(SysDeptDto dto:allDept){
            allDeptMap.put(dto.getId(),dto);
        }
        List<Map<String, String>> result = new ArrayList<>();
        //解析部门名称
        if(!deptIds.isEmpty()){
//            List<SysDeptEntity> deptEntities = baseMapper.authorityNameForComponent(roleIds,deptIds);
            for(Long id:deptIds){
                Map<String, String> m = new HashMap<>();
                m.put("id",id+"");
                //部门
                if(allDeptMap.get(id)!=null){
                    SysDeptDto dept = allDeptMap.get(id);
                    m.put("name",dept.getName());
                    //部门
                    m.put("type","00");
                    m.put("parents",compuParentsPath(allDeptMap,dept.getId(),"00"));
                    result.add(m);
                    break;
                }
            }
        }
        if(!userIds.isEmpty()){
            List<SysUserDto> userList = ConvertUtil.transformObjList(sysUserService.list(),SysUserDto.class);
            for(Long id:userIds){
                Map<String, String> m = new HashMap<>();
                m.put("id",id+"");
                //人员
                for(SysUserDto dto:userList){
                    if(id.equals(dto.getId())){
                        m.put("name",dto.getRealName());
                        //人员
                        m.put("type","01");
                        m.put("parents",compuParentsPath(allDeptMap,dto.getDeptId(),"01"));
                        break;
                    }
                }
                result.add(m);
            }
        }
        return result;
    }

    @Override
    public List<SysDeptDto> allChildren(Long parentId) {
        QueryWrapper<SysDeptEntity> queryWrapper=new QueryWrapper();
        queryWrapper.like("parents",parentId);
        queryWrapper.eq("is_deleted",YesNoEnum.NO.getCode());
        return ConvertUtil.transformObjList(baseMapper.selectList(queryWrapper),SysDeptDto.class);
    }

    @Override
    public List<SysDeptEntity> listToName(List<Long> roleIds, String name) {
        return baseMapper.authoritySearchForComponent(roleIds,name);
    }

    @Override
    public List<SysDeptDto> myselfBelowDept(Long deptId) {
        return baseMapper.myselfBelowDept(deptId);
    }

    /**
     * 计算部门所有上级路径展示
     * @param allDeptMap
     * @param deptId
     * @param selfFlag 00 不包含本级；01 包含本级
     * @return
     */
    public String compuParentsPath(Map<Long,SysDeptDto> allDeptMap,Long deptId,String selfFlag){
        String sep="/";
        String s = "";
        SysDeptDto dto = allDeptMap.get(deptId);
        if(dto!=null){
            if("01".equals(selfFlag)){
                s+=dto.getName();
            }
            while(dto.getParentId()!=null){
                if(dto!=null){
                    dto=allDeptMap.get(dto.getParentId());
                    if(s.length()==0){
                        s+=dto.getName();
                    }else{
                        s = dto.getName()+sep+s;
                    }
                }
            }
        }
        return s;
    }
}

