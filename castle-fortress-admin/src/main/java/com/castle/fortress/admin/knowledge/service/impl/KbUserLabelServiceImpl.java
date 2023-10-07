package com.castle.fortress.admin.knowledge.service.impl;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.entity.KbModelLabelEntity;
import com.castle.fortress.admin.knowledge.mapper.KbBasicMapper;
import com.castle.fortress.admin.knowledge.mapper.KbBasicUserMapper;
import com.castle.fortress.admin.knowledge.service.KbCategoryService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbUserLabelEntity;
import com.castle.fortress.admin.knowledge.dto.KbUserLabelDto;
import com.castle.fortress.admin.knowledge.mapper.KbUserLabelMapper;
import com.castle.fortress.admin.knowledge.service.KbUserLabelService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 用户与表签关联表 服务实现类
 *
 * @author sunhr
 * @since 2023-05-17
 */
@Service
public class KbUserLabelServiceImpl extends ServiceImpl<KbUserLabelMapper, KbUserLabelEntity> implements KbUserLabelService {
    @Autowired
    private KbUserLabelMapper kbUserLabelMapper;
    @Autowired
    private KbBasicUserMapper userMapper;
    @Autowired
    private KbCategoryService kbCategoryService;

    @Autowired
    private KbBasicMapper kbBasicMapper;

    /**
     * 获取查询条件
     *
     * @param kbUserLabelDto
     * @return
     */
    public QueryWrapper<KbUserLabelEntity> getWrapper(KbUserLabelDto kbUserLabelDto) {
        QueryWrapper<KbUserLabelEntity> wrapper = new QueryWrapper<>();
        if (kbUserLabelDto != null) {
            KbUserLabelEntity kbUserLabelEntity = ConvertUtil.transformObj(kbUserLabelDto, KbUserLabelEntity.class);
            wrapper.like(kbUserLabelEntity.getId() != null, "id", kbUserLabelEntity.getId());
            wrapper.like(kbUserLabelEntity.getUserId() != null, "user_id", kbUserLabelEntity.getUserId());
            wrapper.like(kbUserLabelEntity.getLabelId() != null, "label_id", kbUserLabelEntity.getLabelId());
        }
        return wrapper;
    }


    @Override
    public IPage<KbUserLabelDto> pageKbUserLabel(Page<KbUserLabelDto> page, KbUserLabelDto kbUserLabelDto) {
        QueryWrapper<KbUserLabelEntity> wrapper = getWrapper(kbUserLabelDto);
        Page<KbUserLabelEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbUserLabelEntity> kbUserLabelPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbUserLabelDto> pageDto = new Page(kbUserLabelPage.getCurrent(), kbUserLabelPage.getSize(), kbUserLabelPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbUserLabelPage.getRecords(), KbUserLabelDto.class));
        return pageDto;
    }


    @Override
    public List<KbUserLabelDto> listKbUserLabel(KbUserLabelDto kbUserLabelDto) {
        QueryWrapper<KbUserLabelEntity> wrapper = getWrapper(kbUserLabelDto);
        List<KbUserLabelEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbUserLabelDto.class);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLabel(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getSwIds() == null || kbBaseShowDto.getSwIds().size() == 0) {
            kbBaseShowDto.setSwIds(null);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        List<Long> ids = kbCategoryService.findByUidToCategoryAuth(sysUser);
        List<KbModelTransmitDto> basic = kbUserLabelMapper.selectBasicByLabel(kbBaseShowDto, pageMap, ids);
        Integer total = kbUserLabelMapper.selectBasicByLabelCount(kbBaseShowDto, ids);
        Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(basic);
        return pageDto;
    }

    @Override
    public List<KbModelLabelEntity> findLabelByUser(Long userId) {
        return kbUserLabelMapper.findLabelByUser(userId);
    }

    @Override
    public List<Long> findIds(Long userId) {
        return kbUserLabelMapper.findIds(userId);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLabelAdmin(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<Long> categoryIds = kbBaseShowDto.getCategoryIds();
        if (categoryIds == null || categoryIds.size() == 0) {
            kbBaseShowDto.setCategoryIds(null);
        }
        if (kbBaseShowDto.getSwIds() == null || kbBaseShowDto.getSwIds().size() == 0) {
            kbBaseShowDto.setSwIds(null);
        }
        SysUser sysUser = WebUtil.currentUser();
        if (kbBaseShowDto.getFromTime() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date parse = simpleDateFormat.parse(kbBaseShowDto.getFromTime());
                String format = simpleDateFormat.format(parse);
                kbBaseShowDto.setFromTime(format);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        List<Long> ids = kbCategoryService.findByUidToCategoryAuth(sysUser);
        List<KbModelTransmitDto> basic = kbUserLabelMapper.selectBasicByLabelAdmin(kbBaseShowDto, pageMap, ids);
        Integer total = kbUserLabelMapper.selectBasicByLabelaAdminCount(kbBaseShowDto, ids);
        Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(basic);
        return pageDto;
    }

    @Override
    public Integer findLabelByUserToBasicCountAdmin(Long id) {
        return baseMapper.findLabelByUserToBasicCountAdmin(id);
    }

    @Override
    public Integer findLabelByUserToBasicCount(Long id, List<Integer> auths) {
        // 获取权限
        List<Long> authIds = kbBasicMapper.findByAuth(id, auths);
        if (authIds == null || authIds.size() < 1) {
            return 0;
        }
        return baseMapper.findLabelByUserToBasicCount(authIds, id);
    }
}

