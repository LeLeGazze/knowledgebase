package com.castle.fortress.admin.member.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.member.dto.MemberAccountSerialDto;
import com.castle.fortress.admin.member.entity.MemberAccountSerialEntity;

import java.util.List;

/**
 * 会员账户流水 服务类
 *
 * @author Mgg
 * @since 2021-12-02
 */
public interface MemberAccountSerialService extends IService<MemberAccountSerialEntity> {

    /**
     * 分页展示会员账户流水列表
     * @param page
     * @param memberAccountSerialDto
     * @return
     */
    IPage<MemberAccountSerialDto> pageMemberAccountSerial(Page<MemberAccountSerialDto> page, MemberAccountSerialDto memberAccountSerialDto);

    /**
    * 分页展示会员账户流水扩展列表
    * @param page
    * @param memberAccountSerialDto
    * @return
    */
    IPage<MemberAccountSerialDto> pageMemberAccountSerialExtends(Page<MemberAccountSerialDto> page, MemberAccountSerialDto memberAccountSerialDto);
    /**
    * 会员账户流水扩展详情
    * @param id 会员账户流水id
    * @return
    */
    MemberAccountSerialDto getByIdExtends(Long id);

    /**
     * 展示会员账户流水列表
     * @param memberAccountSerialDto
     * @return
     */
    List<MemberAccountSerialDto> listMemberAccountSerial(MemberAccountSerialDto memberAccountSerialDto);

}
