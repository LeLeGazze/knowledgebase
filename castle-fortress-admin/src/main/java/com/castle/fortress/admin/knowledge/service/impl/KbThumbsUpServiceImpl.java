package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.util.StrUtil;

import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbThumbsUpEntity;
import com.castle.fortress.admin.knowledge.dto.KbThumbsUpDto;
import com.castle.fortress.admin.knowledge.mapper.KbThumbsUpMapper;
import com.castle.fortress.admin.knowledge.service.KbThumbsUpService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 知识点赞表 服务实现类
 *
 * @author 
 * @since 2023-05-11
 */
@Service
public class KbThumbsUpServiceImpl extends ServiceImpl<KbThumbsUpMapper, KbThumbsUpEntity> implements KbThumbsUpService {
    @Autowired
    private KbThumbsUpMapper kbThumbsUpMapper;
    /**
    * 获取查询条件
    * @param kbThumbsUpDto
    * @return
    */
    public QueryWrapper<KbThumbsUpEntity> getWrapper(KbThumbsUpDto kbThumbsUpDto){
        QueryWrapper<KbThumbsUpEntity> wrapper= new QueryWrapper();
        if(kbThumbsUpDto != null){
            KbThumbsUpEntity kbThumbsUpEntity = ConvertUtil.transformObj(kbThumbsUpDto,KbThumbsUpEntity.class);
            wrapper.like(kbThumbsUpEntity.getId() != null,"id",kbThumbsUpEntity.getId());
            wrapper.like(kbThumbsUpEntity.getCommentId() != null,"comment_id",kbThumbsUpEntity.getCommentId());
            wrapper.like(kbThumbsUpEntity.getStatus() != null,"status",kbThumbsUpEntity.getStatus());
            wrapper.like(kbThumbsUpEntity.getBasicId() != null,"basic_id",kbThumbsUpEntity.getBasicId());
            wrapper.like(kbThumbsUpEntity.getUserId() != null,"user_id",kbThumbsUpEntity.getUserId());
        }
        return wrapper;
    }


    @Override
    public IPage<KbThumbsUpDto> pageKbThumbsUp(Page<KbThumbsUpDto> page, KbThumbsUpDto kbThumbsUpDto) {
        QueryWrapper<KbThumbsUpEntity> wrapper = getWrapper(kbThumbsUpDto);
        Page<KbThumbsUpEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbThumbsUpEntity> kbThumbsUpPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<KbThumbsUpDto> pageDto = new Page(kbThumbsUpPage.getCurrent(), kbThumbsUpPage.getSize(),kbThumbsUpPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbThumbsUpPage.getRecords(),KbThumbsUpDto.class));
        return pageDto;
    }


    @Override
    public List<KbThumbsUpDto> listKbThumbsUp(KbThumbsUpDto kbThumbsUpDto){
        QueryWrapper<KbThumbsUpEntity> wrapper = getWrapper(kbThumbsUpDto);
        List<KbThumbsUpEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,KbThumbsUpDto.class);
    }

    @Override
    public KbThumbsUpEntity checkExites(KbThumbsUpDto kbThumbsUpDto) {
        Long basicId = kbThumbsUpDto.getBasicId();
        Long userId = kbThumbsUpDto.getUserId();
        Long commentId = kbThumbsUpDto.getCommentId();
        KbThumbsUpEntity kbThumbsUpEntity = kbThumbsUpMapper.checkExites(basicId, userId, commentId);
       return kbThumbsUpEntity;

    }

    @Override
    public boolean removeUp(KbThumbsUpDto kbThumbsUpDto) {
        Long basicId = kbThumbsUpDto.getBasicId();
        Long userId = kbThumbsUpDto.getUserId();
        Long commentId = kbThumbsUpDto.getCommentId();
        boolean remove = kbThumbsUpMapper.removeUp(basicId, userId, commentId);
        return remove;
    }
}

