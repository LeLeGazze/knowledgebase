package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbCategoryShowDto;
import com.castle.fortress.admin.knowledge.dto.KbWarehouseAuthDto;
import com.castle.fortress.admin.knowledge.entity.KbSubjectWarehouseEntity;
import com.castle.fortress.admin.knowledge.entity.KbVideoEntity;
import com.castle.fortress.admin.knowledge.entity.KbWarehouseAuthEntity;
import com.castle.fortress.admin.knowledge.enums.KbAuthEnum;
import com.castle.fortress.admin.knowledge.mapper.KbWarehouseAuthMapper;
import com.castle.fortress.admin.knowledge.service.KbSubjectWarehouseService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbCategoryEntity;
import com.castle.fortress.admin.knowledge.dto.KbCategoryDto;
import com.castle.fortress.admin.knowledge.mapper.KbCategoryMapper;
import com.castle.fortress.admin.knowledge.service.KbCategoryService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;

/**
 * 知识分类表 服务实现类
 *
 * @author
 * @since 2023-04-24
 */
@Service
public class KbCategoryServiceImpl extends ServiceImpl<KbCategoryMapper, KbCategoryEntity> implements KbCategoryService {

    @Autowired
    private KbWarehouseAuthMapper kbWarehouseAuthMapper;
    @Autowired
    private KbWarehouseAuthService kbWarehouseAuthService;

    @Autowired
    private KbSubjectWarehouseService kbSubjectWarehouseService;
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 获取查询条件
     *
     * @param kbCategoryDto
     * @return
     */
    public QueryWrapper<KbCategoryEntity> getWrapper(KbCategoryDto kbCategoryDto) {
        QueryWrapper<KbCategoryEntity> wrapper = new QueryWrapper();
        if (kbCategoryDto != null) {
            KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto, KbCategoryEntity.class);
            wrapper.like(StrUtil.isNotEmpty(kbCategoryEntity.getName()), "name", kbCategoryEntity.getName());
            wrapper.eq(kbCategoryEntity.getSwId() != null, "sw_id", kbCategoryEntity.getSwId());
            wrapper.eq(kbCategoryEntity.getSort() != null, "sort", kbCategoryEntity.getSort());
            wrapper.eq(kbCategoryEntity.getStatus() != null, "status", kbCategoryEntity.getStatus());
//            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbCategoryDto> pageKbCategory(Page<KbCategoryDto> page, KbCategoryDto kbCategoryDto) {
        QueryWrapper<KbCategoryEntity> wrapper = getWrapper(kbCategoryDto);
        Page<KbCategoryEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbCategoryEntity> kbCategoryPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbCategoryDto> pageDto = new Page(kbCategoryPage.getCurrent(), kbCategoryPage.getSize(), kbCategoryPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbCategoryPage.getRecords(), KbCategoryDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbCategoryDto> pageKbCategoryExtends(Page<KbCategoryDto> page, KbCategoryDto kbCategoryDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto, KbCategoryEntity.class);
        List<KbCategoryEntity> kbCategoryList = baseMapper.extendsList(pageMap, kbCategoryEntity);
        Long total = baseMapper.extendsCount(kbCategoryEntity);
        Page<KbCategoryDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbCategoryList, KbCategoryDto.class));
        return pageDto;
    }

    @Override
    public KbCategoryDto getByIdExtends(Long id) {
        KbCategoryEntity kbCategoryEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbCategoryEntity, KbCategoryDto.class);
    }

    @Override
    public List<KbCategoryDto> listKbCategory(KbCategoryDto kbCategoryDto) {
        QueryWrapper<KbCategoryEntity> wrapper = getWrapper(kbCategoryDto);
        wrapper.orderByDesc("sort");
        wrapper.orderByDesc("create_time");
        List<KbCategoryEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbCategoryDto.class);
    }


    @Override
    public boolean add(KbCategoryDto kbCategoryDto) {
        // 校验该知识库下的分类是否已经存在
        QueryWrapper<KbCategoryEntity> wrapper2 = new QueryWrapper();
        wrapper2.eq(StrUtil.isNotEmpty(kbCategoryDto.getName()), "name", kbCategoryDto.getName());
        wrapper2.eq("sw_id", kbCategoryDto.getSwId());
        List<KbCategoryEntity> findNames = baseMapper.selectList(wrapper2);
        if (findNames.size() > 0) {
            throw new BizException(GlobalRespCode.NAME_ERROR);
        }
        // 添加分类
        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto, KbCategoryEntity.class);
        int insert = baseMapper.insert(kbCategoryEntity);
        if (insert > 0) {
            QueryWrapper<KbCategoryEntity> wrapper = new QueryWrapper();
            wrapper.eq("sw_id", kbCategoryEntity.getSwId());
            wrapper.eq("name", kbCategoryEntity.getName());
            wrapper.eq("is_deleted", kbCategoryEntity.getIsDeleted());
            List<KbCategoryEntity> kbSubjectWarehouseEntities = baseMapper.selectList(wrapper);
            kbCategoryEntity = kbSubjectWarehouseEntities.get(0);
            //添加 知识库下的权限
            KbWarehouseAuthDto kbWarehouseAuthDto = new KbWarehouseAuthDto(kbCategoryEntity.getId(), kbCategoryDto.getAuthsList());
            kbWarehouseAuthService.saveAll(kbWarehouseAuthDto);
        }

        return true;
    }

    @Override
    public boolean removeById(Serializable id) {
        kbWarehouseAuthMapper.updateByWhId((Long) id);
        return super.removeById(id);
    }

    @Override
    public boolean updateById(KbCategoryDto kbCategoryDto) {
        //修改分类表信息
        KbCategoryEntity kbCategoryEntity = ConvertUtil.transformObj(kbCategoryDto, KbCategoryEntity.class);
        baseMapper.updateById(kbCategoryEntity);
        // 修改权限表
        KbWarehouseAuthDto kbWarehouseAuthDto = new KbWarehouseAuthDto(kbCategoryDto.getId(), kbCategoryDto.getAuthsList());
        return kbWarehouseAuthService.updateById(kbWarehouseAuthDto);
    }

    @Override
    public List<KbCategoryDto> findByUidAndAuthKbCategory(Long uid, Long wh_id, Integer[] kb_auths) {
        List<Integer> integers = Arrays.asList(kb_auths);

        List<KbCategoryEntity> entities = baseMapper.findByUidAndAuthKbCategory(uid, wh_id, integers);
        return ConvertUtil.transformObjList(entities, KbCategoryDto.class);
    }

    /**
     * 管理员查询全部的知识分类取top10
     *
     * @param o
     * @return
     */
    @Override
    public List<KbCategoryShowDto> findByUidAuthHotKbCategory(List<Integer> asList, Long uid, int topCount) {
        // 超级管理员
        if (asList == null) {
            return baseMapper.findByUidAuthHotKbCategoryAdmin(topCount);
        }
        return baseMapper.findByUidAuthHotKbCategory(asList, uid, topCount);
    }

    @Override
    public boolean deleteById(Long id) {
        kbWarehouseAuthService.deleteByWid(id); // 删除权限表
        int deleteById = baseMapper.deleteById(id);

        return true;

    }

    @Override
    public KbCategoryDto selectById(Long id) {
        KbCategoryEntity kbCategoryEntity = baseMapper.selectById(id);
        KbCategoryDto kbCategoryDto = ConvertUtil.transformObj(kbCategoryEntity, KbCategoryDto.class);
        // 查询知识
        List<KbWarehouseAuthDto> warehouseAuthDtoList = kbWarehouseAuthService.findBySwId(id);
        if (warehouseAuthDtoList != null && warehouseAuthDtoList.size() > 0) {
            kbCategoryDto.setAuthsList(warehouseAuthDtoList);
        }
        return kbCategoryDto;
    }

    @Override
    public List<Long> findByUidToCategoryAuth(SysUser sysUser) {
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        if ((sysUser.getIsAdmin() && GlobalConstants.ROOT_FLAG && sysUser.getLoginName().equals(GlobalConstants.SUPER_ADMIN_NAME)) || sysUser.getLoginName().equals("admin")) {
            // 管理员不校验权限
            return null;
        } else {
            List<Integer> integers = Arrays.asList(
                    KbAuthEnum.UPDATE.getCode(),
                    KbAuthEnum.DOWNLOAD.getCode(),
                    KbAuthEnum.SHOW.getCode(),
                    KbAuthEnum.MANAGE.getCode()
            );
            return baseMapper.findByUidAndAuthKbCategory(sysUser.getId(), null, integers).stream().map(item -> item.getId()).collect(Collectors.toList());
        }

    }

    @Override
    public Map<String, Object> findVideoCategoryAdmin() {
        HashMap<String, Object> hashMap = new HashMap<>();
        //查询目录 id
        QueryWrapper<KbSubjectWarehouseEntity> kbSubjectWarehouseEntityWrapper = new QueryWrapper<>();
        kbSubjectWarehouseEntityWrapper.eq("status", 2);
        KbSubjectWarehouseEntity warehouseEntity = kbSubjectWarehouseService.getOne(kbSubjectWarehouseEntityWrapper);
        List<KbCategoryEntity> entities = baseMapper.findVideoCategoryAdmin(warehouseEntity.getId());
        hashMap.put("kbSubjectWarehouse", warehouseEntity);
        hashMap.put("KbCategoryList", entities);
        return hashMap;
    }

    @Override
    public Map<String, Object> findVideoCategory(List<Integer> asList, Long uid) {
        HashMap<String, Object> hashMap = new HashMap<>();
        //查询目录 id
        QueryWrapper<KbSubjectWarehouseEntity> kbSubjectWarehouseEntityWrapper = new QueryWrapper<>();
        kbSubjectWarehouseEntityWrapper.eq("status", 2);
        KbSubjectWarehouseEntity warehouseEntity = kbSubjectWarehouseService.getOne(kbSubjectWarehouseEntityWrapper);
        ArrayList<KbWarehouseAuthEntity> byUidVideo = kbWarehouseAuthService.findByUidVideo(uid, "00");
        List<KbCategoryEntity> entities = null;
        if (byUidVideo != null && byUidVideo.size() > 0) {
            entities = baseMapper.findVideoCategory(warehouseEntity.getId(), asList, uid);
        }
        hashMap.put("kbSubjectWarehouse", warehouseEntity);
        hashMap.put("KbCategoryList", entities);
        return hashMap;
    }

    @Override
    public RespBody<Object> permissionVerification(SysUser sysUser, Long swId) {
        // 校验有没有知识库权限
        HashMap<String, String> res = new HashMap<>();
        ArrayList<KbWarehouseAuthEntity> kbWarehouseAuthEntities = kbWarehouseAuthService.findByUid(sysUser.getId(), "00", swId);
        if (kbWarehouseAuthEntities == null || kbWarehouseAuthEntities.size() == 0) {
            res.put("code", "2022");
            res.put("data", "无知识库权限，请联系管理员");
            return RespBody.data( res);
        }
        // 校验没有添加菜单权限
        CastleUserDetail userDetail = sysUserToCastleUser(sysUser);
        Set<String> permissions = sysMenuService.getPermissions(userDetail);
        if (!permissions.contains("knowledge:kbCategory:add")) {
            res.put("code", "2022");
            res.put("data", "无分类菜单权限，请联系管理员");
            return RespBody.data(res);
        }
        res.put("data", "暂无数据，请前往后台管理，添加分类");
        res.put("code", "2023");
        return RespBody.data( res);
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

