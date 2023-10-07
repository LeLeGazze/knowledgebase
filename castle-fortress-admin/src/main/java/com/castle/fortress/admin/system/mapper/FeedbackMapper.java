package com.castle.fortress.admin.system.mapper;

import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.system.entity.FeedbackEntity;
import java.util.Map;
import java.util.List;
/**
 * 意见反馈Mapper 接口
 *
 * @author castle
 * @since 2022-11-01
 */
public interface FeedbackMapper extends BaseMapper<FeedbackEntity> {

    /**
    * 扩展信息列表
    * @param pageMap
    * @param feedbackEntity
    * @return
    */
    List<FeedbackEntity> extendsList(@Param("map")Map<String, Long> pageMap, @Param("feedbackEntity") FeedbackEntity feedbackEntity);

    /**
    * 扩展信息记录总数
    * @param feedbackEntity
    * @return
    */
    Long extendsCount(@Param("feedbackEntity") FeedbackEntity feedbackEntity);

    /**
    * 意见反馈扩展详情
    * @param id 意见反馈id
    * @return
    */
    FeedbackEntity getByIdExtends(@Param("id")Long id);



}
