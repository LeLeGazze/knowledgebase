package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbBasicEntity;
import com.castle.fortress.admin.knowledge.enums.FileTypeEnum;
import com.castle.fortress.admin.knowledge.mapper.KbBasicMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicService;
import com.castle.fortress.admin.knowledge.service.KbCommentService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbBasicUserEntity;
import com.castle.fortress.admin.knowledge.dto.KbBasicUserDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicUserMapper;
import com.castle.fortress.admin.knowledge.service.KbBasicUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 知识浏览收藏评论 服务实现类
 *
 * @author
 * @since 2023-05-05
 */
@Service
public class KbBasicUserServiceImpl extends ServiceImpl<KbBasicUserMapper, KbBasicUserEntity> implements KbBasicUserService {
    @Autowired
    private KbBasicUserMapper kbBasicUserMapper;
    @Autowired
    private KbBasicMapper kbBasicmapper;
    @Autowired
    private KbCommentService kbCommentService;

    /**
     * 获取查询条件
     *
     * @param kbBasicUserDto
     * @return
     */
    public QueryWrapper<KbBasicUserEntity> getWrapper(KbBasicUserDto kbBasicUserDto) {
        QueryWrapper<KbBasicUserEntity> wrapper = new QueryWrapper();
        if (kbBasicUserDto != null) {
            KbBasicUserEntity kbBasicUserEntity = ConvertUtil.transformObj(kbBasicUserDto, KbBasicUserEntity.class);
            wrapper.like(kbBasicUserEntity.getId() != null, "id", kbBasicUserEntity.getId());
            wrapper.like(kbBasicUserEntity.getUserId() != null, "user_id", kbBasicUserEntity.getUserId());
            wrapper.like(kbBasicUserEntity.getBId() != null, "b_id", kbBasicUserEntity.getBId());
            wrapper.like(kbBasicUserEntity.getType() != null, "type", kbBasicUserEntity.getType());
            wrapper.like(kbBasicUserEntity.getCreateTime() != null, "create_time", kbBasicUserEntity.getCreateTime());
            wrapper.like(StrUtil.isNotEmpty(kbBasicUserEntity.getAttachment()), "attachment", kbBasicUserEntity.getAttachment());
        }
        return wrapper;
    }


    @Override
    public IPage<KbBasicUserDto> pageKbBasicUser(Page<KbBasicUserDto> page, KbBasicUserDto kbBasicUserDto) {
        QueryWrapper<KbBasicUserEntity> wrapper = getWrapper(kbBasicUserDto);
        Page<KbBasicUserEntity> pageEntity = new Page<>(page.getCurrent(), page.getSize());
        Page<KbBasicUserEntity> kbBasicUserPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbBasicUserDto> pageDto = new Page<>(kbBasicUserPage.getCurrent(), kbBasicUserPage.getSize(), kbBasicUserPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbBasicUserPage.getRecords(), KbBasicUserDto.class));
        return pageDto;
    }


    @Override
    public List<KbBasicUserDto> listKbBasicUser(KbBasicUserDto kbBasicUserDto) {
        QueryWrapper<KbBasicUserEntity> wrapper = getWrapper(kbBasicUserDto);
        List<KbBasicUserEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbBasicUserDto.class);
    }


    @Override
    public void saveKnowUser(KbModelAcceptanceDto formDataDto) {
//        SysUser sysUser = WebUtil.currentUser();
//        Long id = sysUser.getId();
        //删除原有中间表关系
        LambdaQueryWrapper<KbBasicUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KbBasicUserEntity::getUserId, formDataDto.getAuth());
        wrapper.eq(KbBasicUserEntity::getBId, formDataDto.getId());
        wrapper.eq(KbBasicUserEntity::getType, 2);
        wrapper.eq(KbBasicUserEntity::getStatus, 1);
        remove(wrapper);
        //保存新的中间表关系
        KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
        kbBasicUserEntity.setBId(formDataDto.getId());
        kbBasicUserEntity.setUserId(formDataDto.getAuth());
        kbBasicUserEntity.setType(2);
        kbBasicUserEntity.setStatus(1);
        baseMapper.insert(kbBasicUserEntity);
    }



    @Override
    public IPage<KbModelTransmitDto> recentViews(Page<KbModelTransmitDto> page, Long userId) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbModelTransmitDto> kbModelTransmitDtos = kbBasicUserMapper.recentViews(pageMap, userId);
        Integer total = kbBasicUserMapper.recentViewsCount(userId);
        Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(kbModelTransmitDtos);
        return pageDto;
    }

    @Override
    public IPage<KbModelTransmitDto> recentlyUploaded(Page<KbModelTransmitDto> page, Long userId) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbModelTransmitDto> kbModelTransmitDtos = kbBasicUserMapper.recentlyUploaded(pageMap, userId);
        Integer total = kbBasicUserMapper.recentlyUploadedCount(userId);
        Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(kbModelTransmitDtos);
        return pageDto;
    }

    @Override
    public IPage<KbModelTransmitDto> recentlyDownloaded(Page<KbModelTransmitDto> page, Long userId) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<KbModelTransmitDto> kbModelTransmitDtos = kbBasicUserMapper.recentlyDownloaded(pageMap, userId);
        Integer total = kbBasicUserMapper.recentlyDownloadedCount(userId);
        Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(kbModelTransmitDtos);
        return pageDto;
    }

    @Override
    public void insertDownLoad(Long basicId) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        Long uid = sysUser.getId();
        KbBasicEntity kbBasic = kbBasicmapper.selectById(basicId);
        if(kbBasic == null){
            throw new BizException(GlobalRespCode.OPERATE_ERROR);
        }
        KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
        kbBasicUserEntity.setBId(kbBasic.getId());
        kbBasicUserEntity.setUserId(uid);
        kbBasicUserEntity.setType(3);
        kbBasicUserEntity.setStatus(1);
        baseMapper.insert(kbBasicUserEntity);
    }

    @Override
    public List<KbModelTransmitDto> randomId() {
        List<Long> longs = kbBasicUserMapper.randomId();
        return kbBasicmapper.intelligentRecommendation(longs);
    }

    @Override
    public KbModelTransmitDto selectDownCount(Long userId) {
        return kbBasicUserMapper.selectDownNum(userId);
    }

    @Override
    public KbModelTransmitDto selectUpCount() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        Long userId = sysUser.getId();
        return kbBasicUserMapper.selectUpNum(userId);
    }

    /**
     * 查询上传的知识
     * @param auth
     * @param id
     */
    @Override
    public void removeBasicUser(Long auth, Long id) {
        kbBasicUserMapper.removeBasicUser(auth,id);
    }

    @Override
    public void addVideoUser(Long auth, Long videoId   ) {
        //删除原有中间表关系
        LambdaQueryWrapper<KbBasicUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(KbBasicUserEntity::getUserId, videoId);
        wrapper.eq(KbBasicUserEntity::getBId, auth);
        wrapper.eq(KbBasicUserEntity::getType, 2);
        wrapper.eq(KbBasicUserEntity::getStatus, 2);
        remove(wrapper);
        //保存新的中间表关系
        KbBasicUserEntity kbBasicUserEntity = new KbBasicUserEntity();
        kbBasicUserEntity.setBId(auth);
        kbBasicUserEntity.setUserId(videoId);
        kbBasicUserEntity.setType(2);
        kbBasicUserEntity.setStatus(2);
        baseMapper.insert(kbBasicUserEntity);
    }
}

