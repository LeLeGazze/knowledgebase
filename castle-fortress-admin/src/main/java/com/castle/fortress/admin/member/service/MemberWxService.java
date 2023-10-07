package com.castle.fortress.admin.member.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import com.castle.fortress.admin.member.dto.MemberWxDto;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;
import java.util.List;

/**
 * 微信会员表 服务类
 *
 * @author whc
 * @since 2022-11-28
 */
public interface MemberWxService extends IService<MemberWxEntity> {

    /**
     * 分页展示微信会员表列表
     * @param page
     * @param memberWxDto
     * @return
     */
    IPage<MemberWxDto> pageMemberWx(Page<MemberWxDto> page, MemberWxDto memberWxDto);

    /**
    * 分页展示微信会员表扩展列表
    * @param page
    * @param memberWxDto
    * @return
    */
    IPage<MemberWxDto> pageMemberWxExtends(Page<MemberWxDto> page, MemberWxDto memberWxDto);
    /**
    * 微信会员表扩展详情
    * @param id 微信会员表id
    * @return
    */
    MemberWxDto getByIdExtends(Long id);

    /**
     * 展示微信会员表列表
     * @param memberWxDto
     * @return
     */
    List<MemberWxDto> listMemberWx(MemberWxDto memberWxDto);

    /**
     * 生成新的微信用户记录
     * @param userInfoDataJson
     * @return
     */
    MemberWxEntity create(JSONObject userInfoDataJson);

}
