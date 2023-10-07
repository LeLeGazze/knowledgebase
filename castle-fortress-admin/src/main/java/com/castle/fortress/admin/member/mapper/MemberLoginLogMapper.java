package com.castle.fortress.admin.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会员登录日志表Mapper 接口
 *
 * @author Mgg
 * @since 2021-11-26
 */
public interface MemberLoginLogMapper extends BaseMapper<MemberLoginLogEntity> {


    List<MemberLoginLogEntity> logLoginList(@Param("map") Map<String, Long> pageMap,@Param("logLoginEntity")  MemberLoginLogEntity logLoginEntity);

    Long logLoginCount(@Param("logLoginEntity") MemberLoginLogEntity logLoginEntity);
}

