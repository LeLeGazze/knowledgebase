package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.mapper.KbCategoryMapper;
import com.castle.fortress.admin.knowledge.mapper.KbWarehouseAuthMapper;
import com.castle.fortress.admin.knowledge.service.KbCategoryService;
import com.castle.fortress.admin.knowledge.service.KbWarehouseAuthService;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysMenuService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.UserTypeEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.dto.KbSubjectWarehouseDto;
import com.castle.fortress.admin.knowledge.mapper.KbSubjectWarehouseMapper;
import com.castle.fortress.admin.knowledge.service.KbSubjectWarehouseService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * 主题知识仓库 服务实现类
 *
 * @author lyz
 * @since 2023-04-24
 */
@Service
public class KbSubjectWarehouseServiceImpl extends ServiceImpl<KbSubjectWarehouseMapper, KbSubjectWarehouseEntity> implements KbSubjectWarehouseService {

    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    @Autowired
    private KbCategoryMapper kbCategoryMapper;

    @Autowired
    private KbWarehouseAuthMapper kbWarehouseAuthMapper;
    @Autowired
    private KbCategoryService kbCategoryService;
    /**
     * 获取查询条件
     *
     * @param kbSubjectWarehouseDto
     * @return
     */
    public QueryWrapper<KbSubjectWarehouseEntity> getWrapper(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = new QueryWrapper();
        if (kbSubjectWarehouseDto != null) {
            KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto, KbSubjectWarehouseEntity.class);
            wrapper.like(StrUtil.isNotEmpty(kbSubjectWarehouseEntity.getName()), "name", kbSubjectWarehouseEntity.getName());
            wrapper.eq(kbSubjectWarehouseEntity.getModelId() != null, "model_id", kbSubjectWarehouseEntity.getModelId());
            wrapper.eq(kbSubjectWarehouseEntity.getIsShow() != null, "is_show", kbSubjectWarehouseEntity.getIsShow());
            wrapper.eq(kbSubjectWarehouseEntity.getStatus() != null, "status", kbSubjectWarehouseEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbSubjectWarehouseDto> pageKbSubjectWarehouse(Page<KbSubjectWarehouseDto> page, KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = getWrapper(kbSubjectWarehouseDto);
        Page<KbSubjectWarehouseEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbSubjectWarehouseEntity> kbSubjectWarehousePage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbSubjectWarehouseDto> pageDto = new Page(kbSubjectWarehousePage.getCurrent(), kbSubjectWarehousePage.getSize(), kbSubjectWarehousePage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbSubjectWarehousePage.getRecords(), KbSubjectWarehouseDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbSubjectWarehouseDto> pageKbSubjectWarehouseExtends(Page<KbSubjectWarehouseDto> page, KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto, KbSubjectWarehouseEntity.class);
        List<KbSubjectWarehouseEntity> kbSubjectWarehouseList = baseMapper.extendsList(pageMap, kbSubjectWarehouseEntity);
        Long total = baseMapper.extendsCount(kbSubjectWarehouseEntity);
        Page<KbSubjectWarehouseDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbSubjectWarehouseList, KbSubjectWarehouseDto.class));
        return pageDto;
    }

    @Override
    public KbSubjectWarehouseDto getByIdExtends(Long id) {
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbSubjectWarehouseEntity, KbSubjectWarehouseDto.class);
    }

    @Override
    public List<KbSubjectWarehouseDto> listKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = getWrapper(kbSubjectWarehouseDto);
        List<KbSubjectWarehouseEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbSubjectWarehouseDto.class);
    }


    @Override
    public boolean add(KbSubjectWarehouseDto kbSubjectWarehouseDto) {

        // 校验名字是否已经存在
        List<KbSubjectWarehouseEntity> findNames = baseMapper.selectList(getWrapper(kbSubjectWarehouseDto));
        if (findNames.size() > 0) {
            throw new BizException(GlobalRespCode.NAME_ERROR);
        }
        // 添加主题知识
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto, KbSubjectWarehouseEntity.class);
        int insert = baseMapper.insert(kbSubjectWarehouseEntity);
        if (insert > 0) {
            QueryWrapper<KbSubjectWarehouseEntity> wrapper = new QueryWrapper();
            wrapper.eq("name", kbSubjectWarehouseEntity.getName());
            wrapper.eq("is_deleted", kbSubjectWarehouseEntity.getIsDeleted());
            List<KbSubjectWarehouseEntity> kbSubjectWarehouseEntities = baseMapper.selectList(wrapper);
            kbSubjectWarehouseEntity = kbSubjectWarehouseEntities.get(0);
            //添加 知识库下的权限
            KbWarehouseAuthDto kbWarehouseAuthDto = new KbWarehouseAuthDto(kbSubjectWarehouseEntity.getId(), kbSubjectWarehouseDto.getKnowledgeAuthList(), kbSubjectWarehouseDto.getClassAuthsList());
            kbWarehouseAuthService.saveAll(kbWarehouseAuthDto);
        }

        return true;
    }

    @Override
    public List<KbSubjectWarehouseDto> AuthlistKbSubjectWarehouse(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = getWrapper(kbSubjectWarehouseDto);
        QueryWrapper<KbCategoryEntity> kbCategoryEntityQueryWrapper = new QueryWrapper();
        ;
        wrapper.eq("is_deleted", "2");
        kbCategoryEntityQueryWrapper.eq("is_deleted", "2");
        List<KbCategoryEntity> kbCategoryEntities = kbCategoryMapper.selectList(kbCategoryEntityQueryWrapper);
        List<KbSubjectWarehouseEntity> list = baseMapper.selectList(wrapper);
        for (KbCategoryEntity kbCategoryEntity : kbCategoryEntities) {
            KbSubjectWarehouseEntity kbSubjectWarehouseEntity = new KbSubjectWarehouseEntity();
            kbSubjectWarehouseEntity.setId(kbCategoryEntity.getId());
            kbSubjectWarehouseEntity.setName(kbCategoryEntity.getName());
            list.add(kbSubjectWarehouseEntity);
        }
        return ConvertUtil.transformObjList(list, KbSubjectWarehouseDto.class);
    }

    @Override
    public boolean updateById(KbSubjectWarehouseDto kbSubjectWarehouseDto) {
        //修改主题知识库的权限
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = ConvertUtil.transformObj(kbSubjectWarehouseDto, KbSubjectWarehouseEntity.class);

        // 校验是否是视频
        KbSubjectWarehouseEntity selectById = baseMapper.selectById(kbSubjectWarehouseDto.getId());
        if (selectById.getStatus() == 2) {
            kbSubjectWarehouseEntity.setStatus(2);
            kbSubjectWarehouseEntity.setSort(-1);
            kbSubjectWarehouseEntity.setModelId(null);
        }
        baseMapper.updateById(kbSubjectWarehouseEntity);
        // 修改权限表
        KbWarehouseAuthDto kbWarehouseAuthDto = new KbWarehouseAuthDto(kbSubjectWarehouseDto.getId(), kbSubjectWarehouseDto.getKnowledgeAuthList(), kbSubjectWarehouseDto.getClassAuthsList());

        return kbWarehouseAuthService.updateById(kbWarehouseAuthDto);
    }

    /**
     * 根据登录用户获取自己权限下的知识目录
     *
     * @param uAuths
     * @return
     */
    @Override
    public List<KbSubjectWarehouseDto> findByWid(ArrayList<KbWarehouseAuthEntity> uAuths, int num) {
        List<Long> collect = uAuths.stream().map(x -> x.getWhId()).collect(Collectors.toList());
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = new QueryWrapper<>();
        if (collect.size() > 0 && num == 0) {
            wrapper.in("id", collect).and(item -> {
                item.eq("status", 1);
            }).or().in("is_show", 1).and(item -> {
                item.eq("status", 1);
            });

        } else if (collect.size() > 0 && num == 1) {
            wrapper.in("id", collect);
        } else if (collect.size() == 0 && num == 2) {
            wrapper.eq("status", 1).or().eq("status", 2);
        } else if (collect.size() > 0 && num == 2) {
            wrapper.in("id", collect).and(itme -> itme.eq("status", 1));
        } else {
            wrapper.eq("status", 1);
        }
        wrapper.eq("is_deleted", 2);
        wrapper.orderByDesc("sort");
        List<KbSubjectWarehouseEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbSubjectWarehouseDto.class);
    }

    /**
     * 高级管理员查看全部的功能
     *
     * @param kbWarehouseAuthEntities
     * @return
     */
    @Override
    public List<KbSubjectWarehouseDto> listKbSubjectToCategory(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities) {
        List<KbSubjectWarehouseDto> byWid = findByWid(kbWarehouseAuthEntities, 0);
        if (byWid == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        QueryWrapper<KbCategoryEntity> wrapper = new QueryWrapper<>();
        for (KbSubjectWarehouseDto kbSubjectWarehouseDto : byWid) {
            Long id = kbSubjectWarehouseDto.getId();
            wrapper.eq("sw_id", id);
            wrapper.orderByDesc("sort");
            kbSubjectWarehouseDto.setChildren(kbCategoryMapper.selectList(wrapper));
            wrapper.clear();
        }
        return byWid;
    }

    /**
     * @param kbWarehouseAuthEntities
     * @param uid
     * @param longs
     * @return
     */
    public List<KbSubjectWarehouseDto> listKbSubjectToCategory(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities, Long uid, List<Integer> longs) {
        List<KbSubjectWarehouseDto> byWid = findByWid(kbWarehouseAuthEntities, 2);
        if (byWid == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        for (KbSubjectWarehouseDto kbSubjectWarehouseDto : byWid) {
            Long swId = kbSubjectWarehouseDto.getId();
            kbSubjectWarehouseDto.setChildren(kbCategoryMapper.findByUidAndAuthKbCategory(uid, swId, longs));
        }
        byWid = byWid.stream().filter(item -> !item.getChildren().isEmpty()).collect(Collectors.toList());
        return byWid;
    }

    @Override
    public List<KbSubjectWarehouseEntity> showList() {
        QueryWrapper<KbSubjectWarehouseEntity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("is_show", 1).or().eq("status", 2);
        objectQueryWrapper.eq("is_deleted", 2);
        objectQueryWrapper.orderByDesc("sort");
        List<KbSubjectWarehouseEntity> kbSubjectWarehouseEntities = baseMapper.selectList(objectQueryWrapper);
        return kbSubjectWarehouseEntities;
    }

    @Override
    public List<KbSubjectWarehouseDto> findByListToCategoryAdd(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities, Long uid, List<Integer> asList) {
        List<KbSubjectWarehouseDto> byWid = findByWid(kbWarehouseAuthEntities, 0);
        if (byWid == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        QueryWrapper<KbCategoryEntity> wrapper = new QueryWrapper<>();

        for (KbSubjectWarehouseDto kbSubjectWarehouseDto : byWid) {

            Long swId = kbSubjectWarehouseDto.getId();

            wrapper.eq("sw_id", swId);
            kbSubjectWarehouseDto.setChildren(kbCategoryMapper.findByUidAndAuthKbCategory(uid, swId, asList));
            wrapper.clear();
        }
        byWid = byWid.stream().filter(item -> !item.getChildren().isEmpty()).collect(Collectors.toList());
        return byWid;
    }

    @Override
    public List<KbSubjectWarehouseDto> findByListToCategoryAddAdmin(ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities) {
        List<KbSubjectWarehouseDto> byWid = findByWid(kbWarehouseAuthEntities, 0);
        if (byWid == null) {
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        QueryWrapper<KbCategoryEntity> wrapper = new QueryWrapper<>();
        for (KbSubjectWarehouseDto kbSubjectWarehouseDto : byWid) {
            Long id = kbSubjectWarehouseDto.getId();
            wrapper.eq("sw_id", id);
            kbSubjectWarehouseDto.setChildren(kbCategoryMapper.selectList(wrapper));
            wrapper.clear();
        }
        byWid = byWid.stream().filter(item -> !item.getChildren().isEmpty()).collect(Collectors.toList());
        return byWid;
    }

    @Override
    public KbSubjectWarehouseDto findByListToCategoryVideoAddAdmin() {
        // 查询视频目录
        QueryWrapper<KbSubjectWarehouseEntity> kbSubjectWrapper = new QueryWrapper<>();
        kbSubjectWrapper.eq("status", 2); // 状态为2的为视频
        kbSubjectWrapper.eq("is_deleted", 2);
        KbSubjectWarehouseEntity subjectWarehouseEntity = baseMapper.selectOne(kbSubjectWrapper);
        KbSubjectWarehouseDto kbSubjectWarehouseDto = ConvertUtil.transformObj(subjectWarehouseEntity, KbSubjectWarehouseDto.class);
        // 获取目录下的分类
        QueryWrapper<KbCategoryEntity> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.eq("sw_id", kbSubjectWarehouseDto.getId());
        categoryWrapper.orderByDesc("sort");
        kbSubjectWarehouseDto.setChildren(kbCategoryMapper.selectList(categoryWrapper));
        return kbSubjectWarehouseDto;
    }

    @Override
    public KbSubjectWarehouseDto findByListToCategoryVideoAdd(ArrayList<KbWarehouseAuthEntity> uAuths, Long uid, List<Integer> asList) {
        // 查询视频目录
        List<Long> collect = uAuths.stream().map(x -> x.getWhId()).collect(Collectors.toList());
        QueryWrapper<KbSubjectWarehouseEntity> kbSubjectWrapper = new QueryWrapper<>();
        kbSubjectWrapper.eq("status", 2); // 状态为2的为视频
        kbSubjectWrapper.eq("is_deleted", 2);
        kbSubjectWrapper.in("id", collect);
        KbSubjectWarehouseEntity subjectWarehouseEntity = baseMapper.selectOne(kbSubjectWrapper);
        if (subjectWarehouseEntity == null) {
            return null;
        }
        KbSubjectWarehouseDto kbSubjectWarehouseDto = ConvertUtil.transformObj(subjectWarehouseEntity, KbSubjectWarehouseDto.class);
        Long swId = kbSubjectWarehouseDto.getId();
        List<KbCategoryEntity> kbCategoryList = kbCategoryMapper.findByUidAndAuthKbCategory(uid, swId, asList);
        if (kbCategoryList.isEmpty()) {
            return null;
        }
        kbSubjectWarehouseDto.setChildren(kbCategoryList);
        return kbSubjectWarehouseDto;
    }

    @Override
    public KbSubjectWarehouseDto findById(Long id) {
        // 构建返回对象
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = baseMapper.selectById(id);
        KbSubjectWarehouseDto kbSubjectWarehouseDto = ConvertUtil.transformObj(kbSubjectWarehouseEntity, KbSubjectWarehouseDto.class);
        if (kbSubjectWarehouseDto == null) {
            return null;
        }
        // 查询
        // 查询目录权限
        Callable<List<KbWarehouseAuthDto>> knowledgeCallable = new Callable<List<KbWarehouseAuthDto>>() {

            @Override
            public List<KbWarehouseAuthDto> call() throws Exception {
                return kbWarehouseAuthMapper.findBySwIdAuth(id, 00);
            }
        };
        // 查询目录下的分类
        Callable<List<KbWarehouseAuthDto>> classCallable = new Callable<List<KbWarehouseAuthDto>>() {
            @Override
            public List<KbWarehouseAuthDto> call() throws Exception {
                return kbWarehouseAuthMapper.findBySwIdAuth(id, 02);
            }
        };
        FutureTask<List<KbWarehouseAuthDto>> knowledgeFuture = new FutureTask<>(knowledgeCallable);
        FutureTask<List<KbWarehouseAuthDto>> classFuture = new FutureTask<>(classCallable);
        new Thread(knowledgeFuture).start();
        new Thread(classFuture).start();
        try {
            kbSubjectWarehouseDto.setKnowledgeAuthList(knowledgeFuture.get());
            kbSubjectWarehouseDto.setClassAuthsList(classFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kbSubjectWarehouseDto;
    }

    @Override
    public List<KbSubjectWarehouseEntity> findByModelId(Long id) {
        QueryWrapper<KbSubjectWarehouseEntity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("model_id", id);
        List<KbSubjectWarehouseEntity> kbSubjectWarehouseEntities = baseMapper.selectList(objectQueryWrapper);
        return kbSubjectWarehouseEntities;
    }

    @Override
    public RespBody<Object> permissionVerification(SysUser sysUser) {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 2);
        List<KbSubjectWarehouseEntity> subjectWarehouseEntities = baseMapper.selectList(wrapper);

        if (subjectWarehouseEntities == null || subjectWarehouseEntities.size() == 0) {
            return RespBody.fail("视频配置出现问题，请联系管理员");
        }
        Long swId = subjectWarehouseEntities.get(0).getId();
      return kbCategoryService.permissionVerification(sysUser,swId);
    }

    @Override
    public List<KbSubjectWarehouseDto> findByHomeShow() {
        QueryWrapper<KbSubjectWarehouseEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("is_show",1);
        wrapper.eq("is_deleted", 2);
        wrapper.orderByDesc("sort");
        List<KbSubjectWarehouseEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbSubjectWarehouseDto.class);
    }

    @Override
    public boolean removeById(Serializable id) {
        QueryWrapper<KbCategoryEntity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sw_id", id);
        KbSubjectWarehouseEntity kbSubjectWarehouseEntity = baseMapper.selectById(id);
        if (kbSubjectWarehouseEntity.getStatus() == 2) {
            throw new BizException("此知识库【" + kbSubjectWarehouseEntity.getName() + "】只允许编辑不允许删除");
        }
        // 查询目录下是否有分类，有分类不允许删除
        List<KbCategoryEntity> kbCategoryEntities = kbCategoryMapper.selectList(objectQueryWrapper);
        if (kbCategoryEntities.size() > 0) {
            throw new BizException(GlobalRespCode.DEL_HAS_CHILDREN_ERROR);
        }
        // 删除权限
        kbWarehouseAuthMapper.deleteHyWhid((Long) id);
        // 删除分类
        baseMapper.deleteById(id);
        return true;
    }

    private CastleUserDetail sysUserToCastleUser(SysUser user) {
        CastleUserDetail userDetail = null;
        if (user != null) {
            userDetail = new CastleUserDetail();
            userDetail.setId(user.getId());
            userDetail.setUsername(user.getLoginName());
            userDetail.setAvatar(user.getAvatar());
            userDetail.setUserType(UserTypeEnum.SYS_USER.getName());
            userDetail.setDeptId(user.getDeptId());
            userDetail.setDeptParents(user.getDeptParents());
            userDetail.setNickname(user.getNickname());
            userDetail.setIsSuperAdmin(user.getIsSuperAdmin());
            userDetail.setPostId(user.getPostId());
            userDetail.setRealName(user.getRealName());
            userDetail.setRoles(user.getRoles());
            userDetail.setAuthDept(user.getAuthDept());
            userDetail.setSubPost(user.getSubPost());
            userDetail.setPostDataAuth(user.getPostDataAuth());
        }
        return userDetail;
    }

}

