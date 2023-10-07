package com.castle.fortress.admin.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.castle.fortress.admin.knowledge.dto.KbModelTransmitDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Mapper
/**
 * 系统用户
 * @author castle
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> queryByLoginName(@Param("loginName") String loginName);

    /**
     * 扩展信息列表
     * @param pageMap
     * @param sysUserDto
     * @return
     */
    List<SysUser> extendsList(@Param("map")Map<String, Long> pageMap, @Param("sysUserDto") SysUserDto sysUserDto, @Param("deptIdList") List<Long> deptIdList, @Param("subPostList") List<Long> subPostList, @Param("createUserLimit") Long createUserLimit);

    /**
     * 扩展信息记录总数
     * @param sysUserDto
     * @return
     */
    Long extendsCount(@Param("sysUserDto") SysUserDto sysUserDto, @Param("deptIdList") List<Long> deptIdList, @Param("subPostList") List<Long> subPostList, @Param("createUserLimit") Long createUserLimit);

    /**
     * 扩展信息详情
     * @param id
     * @return
     */
    SysUser getByIdExtends(@Param("id")Long id);

    List<SysUser> listSysUser(@Param("userIds")List<Long> userIds, @Param("deptIdList") List<Long> deptIdList, @Param("subPostList") List<Long> subPostList, @Param("createUserLimit") Long createUserLimit);

    List<SysUser> noPunched(Integer type, Date begin, Date end);

    SysUser getByWeComUserId(@Param("userId") String userId);

    SysUser getByDingUnionid(@Param("unionId") String unionId);

    List<SysUser> findByIds(@Param("ids") List<Long> ids);

    List<SysUser> findAll(@Param("name") String name);

    boolean updateBatch(@Param("ids") List<Long> ids);

    SysUserDto selectUserInfo(@Param("id") Long id);

    List<SysUserDto> findUserByIds(@Param("ids") List<Long> ids,@Param("map") Map<String, Long> pageMap);

    Integer findUserByidsCount(@Param("ids") List<Long> ids);

    List<SysUserDto> authorityAllUserName(@Param("map")  Map<String, Long>pageMap ,@Param("roleIds") List<Long> roleIds, @Param("realName") String realName);

    Integer authorityAllUserNameCount(@Param("roleIds") List<Long> roleIds, @Param("realName") String realName);

    boolean updateBatchTrue(@Param("ids") List<Long> ids);

}
