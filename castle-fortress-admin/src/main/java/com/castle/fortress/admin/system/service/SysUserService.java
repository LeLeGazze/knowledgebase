package com.castle.fortress.admin.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.common.entity.RespBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务类
 * @author castle
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 通过登录名 查询系统用户
     * @param loginName
     * @return
     */
    List<SysUser> queryByLoginName(String loginName);

    IPage<SysUserDto> pageExtendsSysUser(Page<SysUserDto> page, SysUserDto sysUserDto,SysUser sysUser);

    SysUser getByIdExtends(Long id);

    /**
     * 查询指定部门下的用户
     * @param deptId
     * @return
     */
    List<SysUserDto> listByDeptId(Long deptId);

    /**
     * 查询指定岗位下的用户
     * @param postId
     * @return
     */
    List<SysUserDto> listByPostId(Long postId);

    /**
     * 检查字段是否重复
     * @param sysUser
     * @return
     */
    RespBody checkColumnRepeat(SysUser sysUser);

    List<SysUserDto> listExtendsSysUser(SysUserDto sysUserDto,SysUser sysUser);

    /**
     * 用于组件 获取指定id的用户
     * @param userIds 用户id列表
     * @param sysUser 当前登录用户
     * @return
     */
    List<SysUserDto> listSysUser(List<Long> userIds, SysUser sysUser);

    /**
     * 根据日期查询未打卡用户
     * @param type
     * @param begin
     * @param end
     * @return
     */
    List<SysUser> noPunched(Integer type, Date begin, Date end);

    /**
     * *上级概念：该职位的上级且数据权限为不限 以及 部门的父级部门只查找数据权限不限的岗位
     * 获取指定用户的上级列表(职位中无上级职位且权限是不限)
     * @param sysUser 指定用户
     * @return 每个级别的领导人员，key是级别，从1开始
     */
    Map<Integer,List<SysUserDto>> leaders(SysUser sysUser);

    Map<String,List<String>> leadersId(SysUser sysUser);

    /**
     * 根据微信unionId获取用户信息
     * @param unionId
     * @return
     */
    SysUserDto getByUnionId(String unionId);

    /**
     * 根据企微ID 获取用户信息
     * @param userId
     * @return
     */
    SysUser getByWeComUserId(String userId);

    /**
     * 根据钉钉ID 获取用户信息
     * @param unionId
     * @return
     */
    SysUser getByDingUnionid(String unionId);

    IPage<SysUserDto> findByIds(List<Long> ids,Page<SysUser> page);

    List<SysUser> findAll();

    boolean updateByIds(List<Long> sysUserDtos);

    SysUserDto selectById(Long id);

     List<SysUser> list(String name);

    SysUser getByLongName(String loginName);

    IPage<SysUserDto> authorityAllUserName( Page<SysUserDto> page,List<Long> roleIds, String realName);

    List<String> findByUidList(List<Long> userByIdList);

    boolean updateByIdsTrue(List<Long> ids);
}
