package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.entity.MemberExtendFieldConfigEntity;
import com.castle.fortress.admin.member.dto.MemberExtendFieldConfigDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * 用户扩展字段配置表 服务类
 *
 * @author whc
 * @since 2022-11-23
 */
public interface MemberExtendFieldConfigService extends IService<MemberExtendFieldConfigEntity> {

    /**
     * 分页展示用户扩展字段配置表列表
     *
     * @param page
     * @param memberExtendFieldConfigDto
     * @return
     */
    IPage<MemberExtendFieldConfigDto> pageMemberExtendFieldConfig(Page<MemberExtendFieldConfigDto> page, MemberExtendFieldConfigDto memberExtendFieldConfigDto);


    /**
     * 展示用户扩展字段配置表列表
     *
     * @param memberExtendFieldConfigDto
     * @return
     */
    List<MemberExtendFieldConfigDto> listMemberExtendFieldConfig(MemberExtendFieldConfigDto memberExtendFieldConfigDto);


    /**
     * 执行扩展字段生成
     */
    void generate();

    /**
     * 根据member_id 查询扩展字段
     *
     * @param id
     * @return
     */
    List<HashMap<String, String>> extendInfoMember(Long id);

    /**
     * 根据memberId来创建/修改 扩展表字段
     *
     * @param memberId
     * @param params
     */
    void updateOrCreateByMemberId(Long memberId, HashMap<String, String> params);

}
