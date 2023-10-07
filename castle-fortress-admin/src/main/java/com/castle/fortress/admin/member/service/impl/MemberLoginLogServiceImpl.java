package com.castle.fortress.admin.member.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.log.dto.LogLoginDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.member.dto.MemberLoginLogDto;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import com.castle.fortress.admin.member.mapper.MemberLoginLogMapper;
import com.castle.fortress.admin.member.service.MemberLoginLogService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员登录日志表 服务实现类
 *
 * @author Mgg
 * @since 2021-11-26
 */
@Service
public class MemberLoginLogServiceImpl extends ServiceImpl<MemberLoginLogMapper, MemberLoginLogEntity> implements MemberLoginLogService {
    /**
    * 获取查询条件
    * @param memberLoginLogDto
    * @return
    */
    public QueryWrapper<MemberLoginLogEntity> getWrapper(MemberLoginLogDto memberLoginLogDto){
        QueryWrapper<MemberLoginLogEntity> wrapper= new QueryWrapper();
        if(memberLoginLogDto != null){
            MemberLoginLogEntity memberLoginLogEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
            wrapper.like(memberLoginLogEntity.getId() != null,"id",memberLoginLogEntity.getId());
            wrapper.like(memberLoginLogEntity.getMemberId() != null,"member_id",memberLoginLogEntity.getMemberId());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getInvokeUrl()),"invoke_url",memberLoginLogEntity.getInvokeUrl());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getRemoteAddr()),"remote_addr",memberLoginLogEntity.getRemoteAddr());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getAddress()),"address",memberLoginLogEntity.getAddress());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getCusBrowser()),"cus_browser",memberLoginLogEntity.getCusBrowser());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getCusOs()),"cus_os",memberLoginLogEntity.getCusOs());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getInvokeParams()),"invoke_params",memberLoginLogEntity.getInvokeParams());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getInvokeStatus()),"invoke_status",memberLoginLogEntity.getInvokeStatus());
            wrapper.like(memberLoginLogEntity.getInvokeTime() != null,"invoke_time",memberLoginLogEntity.getInvokeTime());
            wrapper.like(StrUtil.isNotEmpty(memberLoginLogEntity.getResultData()),"result_data",memberLoginLogEntity.getResultData());
            wrapper.like(memberLoginLogEntity.getElapsedTime() != null,"elapsed_time",memberLoginLogEntity.getElapsedTime());
            wrapper.like(memberLoginLogEntity.getCreateTime() != null,"create_time",memberLoginLogEntity.getCreateTime());
        }
        return wrapper;
    }


    @Override
    public IPage<MemberLoginLogDto> pageMemberLoginLog(Page<MemberLoginLogDto> page, MemberLoginLogDto memberLoginLogDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        MemberLoginLogEntity logLoginEntity = ConvertUtil.transformObj(memberLoginLogDto,MemberLoginLogEntity.class);
        List<MemberLoginLogEntity> loginEntityList = baseMapper.logLoginList(pageMap,logLoginEntity);
        Long total = baseMapper.logLoginCount(logLoginEntity);
        Page<MemberLoginLogDto> pageDto = new Page(page.getCurrent(), page.getSize(),total);
        pageDto.setRecords(ConvertUtil.transformObjList(loginEntityList,MemberLoginLogDto.class));
        return pageDto;
    }


    @Override
    public List<MemberLoginLogDto> listMemberLoginLog(MemberLoginLogDto memberLoginLogDto){
        QueryWrapper<MemberLoginLogEntity> wrapper = getWrapper(memberLoginLogDto);
        List<MemberLoginLogEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list,MemberLoginLogDto.class);
    }

    @Async
    @Override
    public void saveLog(MemberLoginLogEntity memberLoginLogEntity) {
        baseMapper.insert(memberLoginLogEntity);
    }

}

