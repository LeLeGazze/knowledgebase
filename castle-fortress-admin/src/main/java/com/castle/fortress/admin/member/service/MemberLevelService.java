package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.entity.MemberLevelEntity;
import com.castle.fortress.admin.member.dto.MemberLevelDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 会员等级表 服务类
 *
 * @author whc
 * @since 2022-12-29
 */
public interface MemberLevelService extends IService<MemberLevelEntity> {

    /**
     * 分页展示会员等级表列表
     * @param page
     * @param memberLevelDto
     * @return
     */
    IPage<MemberLevelDto> pageMemberLevel(Page<MemberLevelDto> page, MemberLevelDto memberLevelDto);

    /**
    * 分页展示会员等级表扩展列表
    * @param page
    * @param memberLevelDto
    * @return
    */
    IPage<MemberLevelDto> pageMemberLevelExtends(Page<MemberLevelDto> page, MemberLevelDto memberLevelDto);
    /**
    * 会员等级表扩展详情
    * @param id 会员等级表id
    * @return
    */
    MemberLevelDto getByIdExtends(Long id);

    /**
     * 展示会员等级表列表
     * @param memberLevelDto
     * @return
     */
    List<MemberLevelDto> listMemberLevel(MemberLevelDto memberLevelDto);

}
