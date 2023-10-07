package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.entity.MemberTagEntity;
import com.castle.fortress.admin.member.dto.MemberTagDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 会员标签表 服务类
 *
 * @author whc
 * @since 2022-12-08
 */
public interface MemberTagService extends IService<MemberTagEntity> {

    /**
     * 分页展示会员标签表列表
     * @param page
     * @param memberTagDto
     * @return
     */
    IPage<MemberTagDto> pageMemberTag(Page<MemberTagDto> page, MemberTagDto memberTagDto);

    /**
    * 分页展示会员标签表扩展列表
    * @param page
    * @param memberTagDto
    * @return
    */
    IPage<MemberTagDto> pageMemberTagExtends(Page<MemberTagDto> page, MemberTagDto memberTagDto);
    /**
    * 会员标签表扩展详情
    * @param id 会员标签表id
    * @return
    */
    MemberTagDto getByIdExtends(Long id);

    /**
     * 展示会员标签表列表
     * @param memberTagDto
     * @return
     */
    List<MemberTagDto> listMemberTag(MemberTagDto memberTagDto);

}
