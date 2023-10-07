package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.system.entity.FeedbackEntity;
import com.castle.fortress.admin.system.dto.FeedbackDto;
import com.castle.fortress.admin.system.mapper.FeedbackMapper;
import com.castle.fortress.admin.system.service.FeedbackService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;

/**
 * 意见反馈 服务实现类
 *
 * @author castle
 * @since 2022-11-01
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, FeedbackEntity> implements FeedbackService {
    /**
    * 获取查询条件
    * @param feedbackDto
    * @return
    */
    public QueryWrapper<FeedbackEntity> getWrapper(FeedbackDto feedbackDto){
        QueryWrapper<FeedbackEntity> wrapper= new QueryWrapper();
        if(feedbackDto != null){
            FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto,FeedbackEntity.class);
            wrapper.like(StrUtil.isNotEmpty(feedbackEntity.getContent()),"content",feedbackEntity.getContent());
            wrapper.eq(feedbackEntity.getTypeId() != null,"type_id",feedbackEntity.getTypeId());
            wrapper.eq(feedbackEntity.getMemberId() != null,"member_id",feedbackEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(feedbackEntity.getContactWay()),"contact_way",feedbackEntity.getContactWay());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<FeedbackDto> pageFeedback(Page<FeedbackDto> page, FeedbackDto feedbackDto) {
        QueryWrapper<FeedbackEntity> wrapper = getWrapper(feedbackDto);
        Page<FeedbackEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<FeedbackEntity> feedbackPage=baseMapper.selectPage(pageEntity,wrapper);
        Page<FeedbackDto> pageDto = new Page(feedbackPage.getCurrent(), feedbackPage.getSize(),feedbackPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(feedbackPage.getRecords(),FeedbackDto.class));
        return pageDto;
    }

    @Override
    public IPage<FeedbackDto> pageFeedbackExtends(Page<FeedbackDto> page, FeedbackDto feedbackDto){
        Map<String,Long> pageMap = BizCommonUtil.getPageParam(page);
        FeedbackEntity feedbackEntity = ConvertUtil.transformObj(feedbackDto,FeedbackEntity.class);
        List<FeedbackEntity> feedbackList=baseMapper.extendsList(pageMap,feedbackEntity);
        Long total = baseMapper.extendsCount(feedbackEntity);
        Page<FeedbackDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(feedbackList,FeedbackDto.class));
        return pageDto;
    }
    @Override
    public FeedbackDto getByIdExtends(Long id){
        FeedbackEntity  feedbackEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(feedbackEntity,FeedbackDto.class);
    }

    @Override
    public List<FeedbackDto> listFeedback(FeedbackDto feedbackDto){
        QueryWrapper<FeedbackEntity> wrapper = getWrapper(feedbackDto);
        List<FeedbackEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,FeedbackDto.class);
    }
}

