package com.castle.fortress.admin.member.event;

import com.castle.fortress.admin.member.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * 创建用户账户事件类
 */
@Getter
@Setter
public class CreateMemberAccountEvent extends ApplicationEvent {
    private MemberEntity memberEntity;

    public CreateMemberAccountEvent(Object source) {
        super(source);
    }

    public CreateMemberAccountEvent(Object source, MemberEntity memberEntity) {
        super(source);
        this.memberEntity = memberEntity;
    }

}
