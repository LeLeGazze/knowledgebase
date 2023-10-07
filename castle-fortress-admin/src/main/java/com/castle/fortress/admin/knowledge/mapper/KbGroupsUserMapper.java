package com.castle.fortress.admin.knowledge.mapper;

import com.castle.fortress.admin.system.dto.SysUserDto;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.entity.KbGroupsUserEntity;
import java.util.Map;
import java.util.List;
/**
 * 知识库用户组与用户关联关系管理Mapper 接口
 *
 * @author sunhr
 * @since 2023-04-22
 */
public interface KbGroupsUserMapper extends BaseMapper<KbGroupsUserEntity> {

    List<KbGroupsUserEntity> queryList(@Param("map") Map<String, Long> pageMap, @Param("kbGroupsUserEntity") KbGroupsUserEntity kbGroupsUserEntity);


    List<KbGroupsUserEntity> findById(@Param("groupId") Long id);

    List<SysUserDto> listFindUser(@Param("id") Long id,@Param("name") String name,@Param("map") Map< String,Long> pageMap);

    Integer listFindUserCount(@Param("id") Long id,@Param("name") String name);
}
