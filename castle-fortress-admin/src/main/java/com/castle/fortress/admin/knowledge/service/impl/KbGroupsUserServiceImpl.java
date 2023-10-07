package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.castle.fortress.admin.knowledge.entity.KbGroupsEntity;
import com.castle.fortress.admin.knowledge.mapper.KbGroupsMapper;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbGroupsUserDto;
import com.castle.fortress.admin.knowledge.mapper.KbGroupsUserMapper;
import com.castle.fortress.admin.knowledge.service.KbGroupsUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 知识库用户组与用户关联关系管理 服务实现类
 *
 * @author 
 * @since 2023-04-22
 */
@Service
public class KbGroupsUserServiceImpl extends ServiceImpl<KbGroupsUserMapper, KbGroupsUserEntity> implements KbGroupsUserService {
    @Autowired
    private KbGroupsUserMapper kbGroupsUserMapper;
    @Autowired
    private KbGroupsMapper kbGroupsMapper;

    /**
     * 获取查询条件
     *
     * @param kbGroupsUserDto
     * @return
     */
    public QueryWrapper<KbGroupsUserEntity> getWrapper(KbGroupsUserDto kbGroupsUserDto) {
        QueryWrapper<KbGroupsUserEntity> wrapper = new QueryWrapper();
        if (kbGroupsUserDto != null) {
            KbGroupsUserEntity kbGroupsUserEntity = ConvertUtil.transformObj(kbGroupsUserDto, KbGroupsUserEntity.class);
            wrapper.like(kbGroupsUserEntity.getId() != null, "id", kbGroupsUserEntity.getId());
            wrapper.like(kbGroupsUserEntity.getGroupId() != null, "group_id", kbGroupsUserEntity.getGroupId());
            wrapper.like(kbGroupsUserEntity.getUserId() != null, "user_id", kbGroupsUserEntity.getUserId());
        }
        return wrapper;
    }


    @Override
    public IPage<KbGroupsUserDto> pageKbGroupsUser(Page<KbGroupsUserDto> page, KbGroupsUserDto kbGroupsUserDto) {
        QueryWrapper<KbGroupsUserEntity> wrapper = getWrapper(kbGroupsUserDto);
        Page<KbGroupsUserEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbGroupsUserEntity> kbGroupsUserPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbGroupsUserDto> pageDto = new Page(kbGroupsUserPage.getCurrent(), kbGroupsUserPage.getSize(), kbGroupsUserPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbGroupsUserPage.getRecords(), KbGroupsUserDto.class));
        return pageDto;
    }


    @Override
    public List<KbGroupsUserDto> listKbGroupsUser(KbGroupsUserDto kbGroupsUserDto) {
        QueryWrapper<KbGroupsUserEntity> wrapper = getWrapper(kbGroupsUserDto);
        List<KbGroupsUserEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbGroupsUserDto.class);
    }


    @Override
    public List<KbGroupsUserEntity> findById(Long id) {
        return kbGroupsUserMapper.findById(id);
    }


    @Override
    public IPage<SysUserDto> listFindUser(Long id,String name,Page<SysUserDto> page,Integer status) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<SysUserDto>  sysUser = kbGroupsUserMapper.listFindUser(id,name,pageMap);
        if (status != null){
            if (sysUser != null) {
                for (SysUserDto x : sysUser) {
                    KbGroupsEntity kbGroupsEntity = kbGroupsMapper.selectById(x.getGroupId());
                    LambdaQueryWrapper<KbGroupsEntity> lqw = new LambdaQueryWrapper<>();
                    lqw.eq(KbGroupsEntity::getId, kbGroupsEntity.getPId());
                    KbGroupsEntity kbGroups = kbGroupsMapper.selectOne(lqw);
                    x.setGroupName(kbGroups.getName()+"/"+x.getGroupName());
                }
            }
            Integer totle = kbGroupsUserMapper.listFindUserCount(id,name);
            Page<SysUserDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), totle);
            pageDto.setRecords(sysUser);
            return pageDto;
        }else {
            Integer totle = kbGroupsUserMapper.listFindUserCount(id,name);
            Page<SysUserDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), totle);
            pageDto.setRecords(sysUser);
            return pageDto;
        }

    }

    @Override
    public boolean removeBatch(List<Long> ids, Long groupId) {
        LambdaQueryWrapper<KbGroupsUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KbGroupsUserEntity::getGroupId,groupId);
        lqw.in(KbGroupsUserEntity::getUserId,ids);
        int delete = baseMapper.delete(lqw);
        if (delete > 0) {
            return true;
        }
        return false;
    }
}

