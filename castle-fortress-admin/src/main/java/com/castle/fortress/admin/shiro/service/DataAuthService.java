package com.castle.fortress.admin.shiro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.system.dto.SysDeptDto;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import com.castle.fortress.admin.system.mapper.SysDeptMapper;
import com.castle.fortress.admin.system.mapper.SysPostMapper;
import com.castle.fortress.admin.system.mapper.SysRoleDataAuthMapper;
import com.castle.fortress.common.utils.ConvertUtil;
import com.castle.fortress.common.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 部门权限服务类
 * @author castle
 */
@Service
public class DataAuthService {
    @Autowired
    private SysRoleDataAuthMapper sysRoleDataAuthMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysPostMapper sysPostMapper;

    /**
     * 获取指定用户所拥有的数据权限部门及所在的部门及所在部门的子部门
     * @param userId 指定用户的id 不可为空
     * @param selfDeptId 用户所属的部门id,如果为空只查询用户的数据权限部门
     * @return 部门id集合，包含子级部门
     */
    public Set<Long> getAuthDeptsSet(Long userId,Long selfDeptId){
        if(userId == null){
            return null;
        }
        //获取用户角色对应的部门列表
        List<Long> authDepts= sysRoleDataAuthMapper.getAuthDeptByUserId(userId);
        if(selfDeptId!=null&&selfDeptId!=-1){
            QueryWrapper<SysDeptEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id",selfDeptId);
            wrapper.or().like("parents",selfDeptId);
            List<SysDeptEntity> selfDepts = sysDeptMapper.selectList(wrapper);
            if(selfDepts!=null){
                selfDepts.forEach(item->{
                    if(!authDepts.stream().filter(r->r.equals(item.getId())).findFirst().isPresent()){
                        authDepts.add(item.getId());
                    }
                });
            }
        }
        //获取所有部门信息 部门树
        List<SysDeptEntity> allDepts = sysDeptMapper.selectList(null);
        List<SysDeptDto> deptDtos = ConvertUtil.transformObjList(allDepts,SysDeptDto.class);
        List<SysDeptDto> deptDtoTree = ConvertUtil.listToTree(deptDtos);

        //获取部门列表及子部门集合
        Set<SysDeptDto> dtoSet =new HashSet<>();
        Set<SysDeptDto> dtoSetTemp =null;
        for(Long deptId: authDepts){
            dtoSetTemp = TreeUtil.findSelfAndChildren(deptDtoTree,deptId);
            dtoSet.addAll(dtoSetTemp);
        }
        Set<Long> deptIdSet =new HashSet<>();
        //去除重复元素
        for(SysDeptDto deptDto:dtoSet){
            deptIdSet.add(deptDto.getId());
        }
        return deptIdSet;
    }

    /**
     * 获取指定用户所拥有的部门数据权限
     * @param userId
     * @param selfDeptId 用户所属的部门id
     * @return 部门id集合，包含子级部门
     */
    public List<Long> getAuthDeptsList(Long userId,Long selfDeptId){
        Set<Long> deptSet = this.getAuthDeptsSet(userId,selfDeptId);
        if(deptSet == null){
            return null;
        }
        return new ArrayList<Long>(deptSet);
    }

    /**
     * 获取权限职位信息
     * @param postId
     * @return
     */
    public SysPostEntity getAuthPost(Long postId){
        return  sysPostMapper.selectById(postId);
    }

    /**
     * 获取当前职位的下属职位集合，不包含当前职位
     * @param postId
     * @return set
     */
    public Set<Long> getSubPostSet(Long postId){
        //获取所有职位
        List<SysPostEntity> allPostEntity = sysPostMapper.selectList(null);
        List<SysPostDto> allPostDtos = ConvertUtil.transformObjList(allPostEntity,SysPostDto.class);
        //获取职位树
        List<SysPostDto> postTree = ConvertUtil.listToTree(allPostDtos);
        //获取当前职位的下属职位集合
        Set<SysPostDto> postSet = TreeUtil.findSelfAndChildren(postTree,postId);
        //去除当前职位
        Set<Long> postIdSet = null;
        if(postSet != null && !postSet.isEmpty()){
            postIdSet = new HashSet<>();
            for(SysPostDto postDto:postSet){
                if(!postDto.getId().equals(postId)){
                    postIdSet.add(postDto.getId());
                }
            }
        }
        return postIdSet;
    }
    /**
     * 获取当前职位的下属职位集合，不包含当前职位
     * @param postId
     * @return 列表
     */
    public List<Long> getSubPostList(Long postId) {
        Set<Long> postSet = this.getSubPostSet(postId);
        if(postSet == null){
            return null;
        }
        return new ArrayList<Long>(postSet);
    }
}
