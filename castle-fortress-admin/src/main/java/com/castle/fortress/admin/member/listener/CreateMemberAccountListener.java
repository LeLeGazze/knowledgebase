package com.castle.fortress.admin.member.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.member.entity.MemberAccountEntity;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.entity.MemberPointsEntity;
import com.castle.fortress.admin.member.event.CreateMemberAccountEvent;
import com.castle.fortress.admin.member.service.MemberAccountService;
import com.castle.fortress.admin.member.service.MemberPointsService;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 创建用户账户事件监听类
 */
@Component
public class CreateMemberAccountListener implements ApplicationListener<CreateMemberAccountEvent> {
    @Autowired
    private MemberAccountService memberAccountService;
    @Autowired
    private MemberPointsService memberPointsService;

    @Override
    public void onApplicationEvent(CreateMemberAccountEvent event) {
        System.out.println("接收到创建用户账户事件");
        if (event.getMemberEntity() == null || event.getMemberEntity().getId() == null) {
            throw new BizException("创建账户异常");
        }
        this.generateAccount(event.getMemberEntity());
        this.generatePoint(event.getMemberEntity());
    }

    /**
     * 生成积分账户
     * @param memberEntity
     */
    private void generatePoint(MemberEntity memberEntity) {
        final QueryWrapper<MemberPointsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberEntity.getId());
        queryWrapper.last("limit 1");
        MemberPointsEntity memberPoint = memberPointsService.getOne(queryWrapper);
        if (memberPoint == null) {
            memberPoint = new MemberPointsEntity();
            memberPoint.setMemberId(memberEntity.getId());
            memberPoint.setBalance("0");
            memberPoint.setFrozen("0");
            memberPointsService.save(memberPoint);
        }

    }

    /**
     * 生成账户
     * @param memberEntity
     */
    private void generateAccount(MemberEntity memberEntity) {
        final QueryWrapper<MemberAccountEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberEntity.getId());
        queryWrapper.last("limit 1");
        MemberAccountEntity memberAccount = memberAccountService.getOne(queryWrapper);
        if (memberAccount == null) {
            memberAccount = new MemberAccountEntity();
            memberAccount.setMemberId(memberEntity.getId());
            memberAccount.setBalance("0");
            memberAccount.setFrozen("0");
            memberAccountService.save(memberAccount);
        }
    }
}
