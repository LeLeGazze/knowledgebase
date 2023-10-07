package com.castle.fortress.admin.member.event;

import com.castle.fortress.admin.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户标签增加权重事件
 */
@Getter
@Setter
public class UpdateMemberTagSortEvent extends ApplicationEvent {
    private String oldTags;
    private String newTags;

    public UpdateMemberTagSortEvent(Object source) {
        super(source);
    }

    public UpdateMemberTagSortEvent(Object source, String oldTags,String newTags) {
        super(source);
        this.oldTags = oldTags;
        this.newTags = newTags;
    }

}
