package com.castle.fortress.admin.member.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.event.CreateMemberAccountEvent;
import com.castle.fortress.admin.member.event.UpdateMemberTagSortEvent;
import com.castle.fortress.admin.member.service.MemberAccountService;
import com.castle.fortress.admin.member.service.MemberPointsService;
import com.castle.fortress.admin.member.service.MemberTagService;
import com.castle.fortress.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 修改用户标签权重监听类
 */
@Component
public class UpdateMemberTagSortListener implements ApplicationListener<UpdateMemberTagSortEvent> {

    @Autowired
    private MemberTagService memberTagService;

    @Override
    public void onApplicationEvent(UpdateMemberTagSortEvent event) {
        final String oldTags = event.getOldTags();
        final String newTags = event.getNewTags();
        final List<String> oldTagList = Arrays.stream(oldTags.split(",")).collect(Collectors.toList());
        final List<String> newTagList = Arrays.stream(newTags.split(",")).collect(Collectors.toList());
        final List<String> newIds = new ArrayList<>();
        for (String newTag : newTagList) {
            if (!oldTagList.contains(newTag)) {
                newIds.add(newTag);
            }
        }
        if (newIds.size() > 0) {
            final QueryWrapper<MemberTagEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("id", newIds);
            final List<MemberTagEntity> list = memberTagService.list(queryWrapper);
            for (MemberTagEntity memberTagEntity : list) {
                final int now = memberTagEntity.getSort() != null ? memberTagEntity.getSort() : 0;
                memberTagEntity.setSort(now + 1);
                memberTagService.updateById(memberTagEntity);
            }
        }
    }

}
