package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.admin.knowledge.dto.KbBaseShowDto;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.knowledge.mapper.KbBasicUserMapper;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbCollectEntity;
import com.castle.fortress.admin.knowledge.dto.KbCollectDto;
import com.castle.fortress.admin.knowledge.mapper.KbCollectMapper;
import com.castle.fortress.admin.knowledge.service.KbCollectService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 知识收藏表 服务实现类
 *
 * @author 
 * @since 2023-05-12
 */
@Service
public class KbCollectServiceImpl extends ServiceImpl<KbCollectMapper, KbCollectEntity> implements KbCollectService {

    @Autowired
    private KbCollectMapper kbCollectMapper;
    @Autowired
    private KbBasicUserMapper userMapper;
    /**
    * 获取查询条件
    * @param kbCollectDto
    * @return
    */
    public QueryWrapper<KbCollectEntity> getWrapper(KbCollectDto kbCollectDto){
        QueryWrapper<KbCollectEntity> wrapper= new QueryWrapper();
        if(kbCollectDto != null){
            KbCollectEntity kbCollectEntity = ConvertUtil.transformObj(kbCollectDto,KbCollectEntity.class);
            wrapper.like(kbCollectEntity.getId() != null,"id",kbCollectEntity.getId());
            wrapper.like(kbCollectEntity.getBasicId() != null,"basic_id",kbCollectEntity.getBasicId());
            wrapper.like(kbCollectEntity.getCollectStatus() != null,"status",kbCollectEntity.getCollectStatus());
            wrapper.like(kbCollectEntity.getUserId() != null,"user_id",kbCollectEntity.getUserId());
            wrapper.like(kbCollectEntity.getType() != null,"type",kbCollectEntity.getType());
            wrapper.like(kbCollectEntity.getCreateTime() != null,"create_time",kbCollectEntity.getCreateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<KbCollectDto> pageKbCollect(Page<KbCollectDto> page, KbCollectDto kbCollectDto) {
        QueryWrapper<KbCollectEntity> wrapper = getWrapper(kbCollectDto);
        Page<KbCollectEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbCollectEntity> kbCollectPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbCollectDto> pageDto = new Page(kbCollectPage.getCurrent(), kbCollectPage.getSize(),kbCollectPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbCollectPage.getRecords(),KbCollectDto.class));
        return pageDto;
    }


    @Override
    public List<KbCollectDto> listKbCollect(KbCollectDto kbCollectDto){
        QueryWrapper<KbCollectEntity> wrapper = getWrapper(kbCollectDto);
        List<KbCollectEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbCollectDto.class);
    }

    @Override
    public KbCollectEntity checkExites(KbCollectDto kbCollectDto) {
        Long basicId = kbCollectDto.getBasicId();
        Long userId = kbCollectDto.getUserId();
        return kbCollectMapper.checkExites(basicId, userId);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLike(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        SysUser sysUser = WebUtil.currentUser();
        Long userid = sysUser.getId();
        kbBaseShowDto.setCreateUser(userid);
        if (kbBaseShowDto.getCategoryIds() == null || kbBaseShowDto.getCategoryIds().size()==0){
            kbBaseShowDto.setCategoryIds(null);
        }
        if(kbBaseShowDto.getSwIds() == null|| kbBaseShowDto.getSwIds().size() == 0){
            kbBaseShowDto.setSwIds(null);
        }
        if (kbBaseShowDto.getType()==1){
            List<KbModelTransmitDto> basic = kbCollectMapper.findBasicByLike(kbBaseShowDto,pageMap);
            Integer total = kbCollectMapper.findBasicByLikeCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(),page.getSize(),total);
            pageDto.setRecords(basic);
            return pageDto;
        }else {
            List<KbModelTransmitDto> basic = kbCollectMapper.findBasicByVideo(kbBaseShowDto,pageMap);
            Integer total = kbCollectMapper.findBasicByVideoCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(),page.getSize(),total);
            pageDto.setRecords(basic);
            return pageDto;
        }

    }

    @Override
    public KbCollectEntity findByid(Long userId, Long basicId) {
        return kbCollectMapper.findByid(userId,basicId);
    }

    @Override
    public boolean removeCollect(Long userId, Long basicId) {
        return kbCollectMapper.removeCollect(userId,basicId);
    }

    @Override
    public int deleteByBid(Long id) {
        return baseMapper.deleteByBid(id);
    }

    @Override
    public IPage<KbModelTransmitDto> findBasicByLikeAdmin(KbBaseShowDto kbBaseShowDto, Page<KbModelTransmitDto> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        SysUser sysUser = WebUtil.currentUser();
        Long userid = sysUser.getId();
        kbBaseShowDto.setCreateUser(userid);
        if (kbBaseShowDto.getCategoryIds() == null || kbBaseShowDto.getCategoryIds().size()==0){
            kbBaseShowDto.setCategoryIds(null);
        }
        if(kbBaseShowDto.getSwIds() == null|| kbBaseShowDto.getSwIds().size() == 0){
            kbBaseShowDto.setSwIds(null);
        }
        if (kbBaseShowDto.getType()==1){
            List<KbModelTransmitDto> basic = kbCollectMapper.findBasicByLikeAdmin(kbBaseShowDto,pageMap);
            Integer total = kbCollectMapper.findBasicByLikeAdminCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(),page.getSize(),total);
            pageDto.setRecords(basic);
            return pageDto;
        }else {
            List<KbModelTransmitDto> basic = kbCollectMapper.findBasicByVideoAdmin(kbBaseShowDto,pageMap);
            Integer total = kbCollectMapper.findBasicByVideoAdminCount(kbBaseShowDto);
            Page<KbModelTransmitDto> pageDto = new Page<>(page.getCurrent(),page.getSize(),total);
            pageDto.setRecords(basic);
            return pageDto;
        }
    }
}

