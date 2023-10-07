package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbDownloadConfEntity;
import com.castle.fortress.admin.knowledge.dto.KbDownloadConfDto;
import com.castle.fortress.admin.knowledge.mapper.KbDownloadConfMapper;
import com.castle.fortress.admin.knowledge.service.KbDownloadConfService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;

/**
 * 文件下载配置表 服务实现类
 *
 * @author
 * @since 2023-06-25
 */
@Service
public class KbDownloadConfServiceImpl extends ServiceImpl<KbDownloadConfMapper, KbDownloadConfEntity> implements KbDownloadConfService {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 获取查询条件
     *
     * @param kbDownloadConfDto
     * @return
     */
    public QueryWrapper<KbDownloadConfEntity> getWrapper(KbDownloadConfDto kbDownloadConfDto) {
        QueryWrapper<KbDownloadConfEntity> wrapper = new QueryWrapper();
        if (kbDownloadConfDto != null) {
            KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto, KbDownloadConfEntity.class);
            wrapper.like(kbDownloadConfEntity.getId() != null, "id", kbDownloadConfEntity.getId());
            wrapper.like(StrUtil.isNotEmpty(kbDownloadConfEntity.getName()), "name", kbDownloadConfEntity.getName());
            wrapper.eq(kbDownloadConfEntity.getStatus() != null, "status", kbDownloadConfEntity.getStatus());
            wrapper.eq(kbDownloadConfEntity.getType() != null, "type", kbDownloadConfEntity.getType());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbDownloadConfDto> pageKbDownloadConf(Page<KbDownloadConfDto> page, KbDownloadConfDto kbDownloadConfDto) {
        QueryWrapper<KbDownloadConfEntity> wrapper = getWrapper(kbDownloadConfDto);
        Page<KbDownloadConfEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbDownloadConfEntity> kbDownloadConfPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbDownloadConfDto> pageDto = new Page(kbDownloadConfPage.getCurrent(), kbDownloadConfPage.getSize(), kbDownloadConfPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbDownloadConfPage.getRecords(), KbDownloadConfDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbDownloadConfDto> pageKbDownloadConfExtends(Page<KbDownloadConfDto> page, KbDownloadConfDto kbDownloadConfDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbDownloadConfEntity kbDownloadConfEntity = ConvertUtil.transformObj(kbDownloadConfDto, KbDownloadConfEntity.class);
        List<KbDownloadConfEntity> kbDownloadConfList = baseMapper.extendsList(pageMap, kbDownloadConfEntity);
        Long total = baseMapper.extendsCount(kbDownloadConfEntity);
        Page<KbDownloadConfDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbDownloadConfList, KbDownloadConfDto.class));
        return pageDto;
    }

    @Override
    public KbDownloadConfDto getByIdExtends(Long id) {
        KbDownloadConfEntity kbDownloadConfEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbDownloadConfEntity, KbDownloadConfDto.class);
    }

    @Override
    public List<KbDownloadConfDto> listKbDownloadConf(KbDownloadConfDto kbDownloadConfDto) {
        QueryWrapper<KbDownloadConfEntity> wrapper = getWrapper(kbDownloadConfDto);
        List<KbDownloadConfEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbDownloadConfDto.class);
    }

    @Override
    public boolean isWatermark() {
        // 去redis获取缓存 标记 是否开启水印
        Object isWatermark = redisUtils.get("downloadBase:isWatermark");
        if (isWatermark != null) {
            return Integer.parseInt(String.valueOf(isWatermark)) == 1;
        }
        // redis 缓存没有数据 数据库查询插入进去
        QueryWrapper<KbDownloadConfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 1);
        List<KbDownloadConfEntity> confEntityList = baseMapper.selectList(queryWrapper);
        int watermark = 2;
        if (confEntityList != null && confEntityList.size() > 0) {
            watermark = confEntityList.get(0).getStatus();
        }
        redisUtils.set("downloadBase:isWatermark", watermark);
        return watermark == 1;
    }

    @Override
    public boolean isSourceFile() {
        // 去redis获取缓存 标记是否开启水印
        Object isWatermark = redisUtils.get("downloadBase:isSourceFile");
        if (isWatermark != null) {
            return Integer.parseInt(String.valueOf(isWatermark)) == 1;
        }
        // redis 缓存没有数据 数据库查询插入进去
        QueryWrapper<KbDownloadConfEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", 2);
        List<KbDownloadConfEntity> confEntityList = baseMapper.selectList(queryWrapper);
        int watermark = 2;
        if (confEntityList != null && confEntityList.size() > 0) {
            watermark = confEntityList.get(0).getStatus();
        }
        redisUtils.set("downloadBase:isSourceFile", watermark);
        return watermark == 1;
    }
}

