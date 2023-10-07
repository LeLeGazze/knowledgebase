package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.system.entity.FeedbackEntity;
import com.castle.fortress.admin.system.dto.FeedbackDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 意见反馈 服务类
 *
 * @author castle
 * @since 2022-11-01
 */
public interface FeedbackService extends IService<FeedbackEntity> {

    /**
     * 分页展示意见反馈列表
     * @param page
     * @param feedbackDto
     * @return
     */
    IPage<FeedbackDto> pageFeedback(Page<FeedbackDto> page, FeedbackDto feedbackDto);

    /**
    * 分页展示意见反馈扩展列表
    * @param page
    * @param feedbackDto
    * @return
    */
    IPage<FeedbackDto> pageFeedbackExtends(Page<FeedbackDto> page, FeedbackDto feedbackDto);
    /**
    * 意见反馈扩展详情
    * @param id 意见反馈id
    * @return
    */
    FeedbackDto getByIdExtends(Long id);

    /**
     * 展示意见反馈列表
     * @param feedbackDto
     * @return
     */
    List<FeedbackDto> listFeedback(FeedbackDto feedbackDto);

}
