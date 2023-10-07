package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.shiro.service.DataAuthService;
import com.castle.fortress.admin.system.dto.SysPostDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysDeptEntity;
import com.castle.fortress.admin.system.entity.SysPostEntity;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.mapper.SysDeptMapper;
import com.castle.fortress.admin.system.mapper.SysRoleMapper;
import com.castle.fortress.admin.system.mapper.SysUserMapper;
import com.castle.fortress.admin.system.service.SysPostService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.DataPermissionPostEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现类
 *
 * @author castle
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private DataAuthService dataAuthService;
    @Autowired
    private SysPostService sysPostService;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    private static final String REGEX = "^(?!.*[\\u4e00-\\u9fa5\\pP])(?!\\d+$)[a-zA-Z0-9]{4,}$";

    @Override
    public List<SysUser> queryByLoginName(String loginName) {
        if (CommonUtil.verifyParamNull(loginName)) {
            return null;
        }
        return baseMapper.queryByLoginName(loginName);
    }

    @Override
    public IPage<SysUserDto> pageExtendsSysUser(Page<SysUserDto> page, SysUserDto sysUserDto, SysUser sysUser) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        //部门权限
        List<Long> deptIdList = null;
        //下级职位
        List<Long> subPostList = null;
        //当前用户
        Long createUserLimit = null;
        if (!sysUser.getIsAdmin()) {
            //数据权限部门
            deptIdList = sysUser.getAuthDept();
            //职位权限
            if (sysUser.getPostId() != null) {
                //不限 数据权限部门及当前用户所在部门及下属部门
                if (sysUser.getPostDataAuth() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(sysUser.getPostDataAuth())) {
                    deptIdList = dataAuthService.getAuthDeptsList(sysUser.getId(), sysUser.getDeptId());
                    //本人及下级岗位
                } else if (DataPermissionPostEnum.SELF_SUB.getCode().equals(sysUser.getPostDataAuth())) {
                    deptIdList.remove(sysUser.getDeptId());
                    createUserLimit = sysUser.getId();
                    subPostList = sysUser.getSubPost();
                    //本人
                } else if (DataPermissionPostEnum.SELF.getCode().equals(sysUser.getPostDataAuth())) {
                    deptIdList.remove(sysUser.getDeptId());
                    createUserLimit = sysUser.getId();
                }
            }
        }
        List<SysUser> sysUserList = baseMapper.extendsList(pageMap, sysUserDto, deptIdList, subPostList, createUserLimit);
        Long total = baseMapper.extendsCount(sysUserDto, deptIdList, subPostList, createUserLimit);
        Page<SysUserDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(sysUserList, SysUserDto.class));
        return pageDto;
    }

    @Override
    public SysUser getByIdExtends(Long id) {
        SysUser sysUser = baseMapper.getByIdExtends(id);
        if (sysUser == null) {
            return sysUser;
        }
        List<SysRole> sysRoles = sysRoleMapper.queryListByUser(id);
        sysUser.setRoles(sysRoles);
        return sysUser;
    }

    @Override
    public List<SysUserDto> listByDeptId(Long deptId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id", deptId);
        List<SysUser> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, SysUserDto.class);
    }

    @Override
    public List<SysUserDto> listByPostId(Long postId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id", postId);
        List<SysUser> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, SysUserDto.class);
    }

    @Override
    public RespBody checkColumnRepeat(SysUser sysUser) {
        if (sysUser.getId() == null) {
            // 校验用户名是否特殊字符
            if (!sysUser.getLoginName().matches(REGEX)) {
                return RespBody.fail("登录名不和规（不允许出现特殊字符或中文或纯数字）");
            }
        }
        //校验用户名
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_name", sysUser.getLoginName());
        List<SysUser> list = baseMapper.selectList(queryWrapper);
        //校验是否占用超管名称
        if (sysUser.getId() == null) {
            if ("admin".equals(sysUser.getLoginName()) || GlobalConstants.SUPER_ADMIN_NAME.equals(sysUser.getLoginName())) {
                throw new BizException(BizErrorCode.USER_NAME_lIMITED_ERROR);
            }
        }
        if (list != null && !list.isEmpty()) {
            //新增
            if (sysUser.getId() == null) {
                return RespBody.fail(BizErrorCode.USER_NAME_EXIST_ERROR);
                //修改
            } else {
                for (SysUser entity : list) {
                    if (!entity.getId().equals(sysUser.getId())) {
                        return RespBody.fail(BizErrorCode.USER_NAME_EXIST_ERROR);
                    }
                }
            }
        }else {
            //如果修改了用户名 则库里没有该用户名
            if (sysUser.getId() != null && !sysUser.getLoginName().matches(REGEX)) {
                return RespBody.fail("登录名不和规（不允许出现特殊字符或中文或纯数字）");
            }
        }
        //校验手机号
        if (StrUtil.isNotEmpty(sysUser.getPhone())) {
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("phone", sysUser.getPhone());
            list = baseMapper.selectList(queryWrapper);
            if (list != null && !list.isEmpty()) {
                //新增
                if (sysUser.getId() == null) {
                    return RespBody.fail(BizErrorCode.USER_PHONE_EXIST_ERROR);
                    //修改
                } else {
                    for (SysUser entity : list) {
                        if (!entity.getId().equals(sysUser.getId())) {
                            return RespBody.fail(BizErrorCode.USER_PHONE_EXIST_ERROR);
                        }
                    }
                }
            }
        }
        if (StrUtil.isNotEmpty(sysUser.getEmail())) {
            //校验邮箱
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("email", sysUser.getEmail());
            list = baseMapper.selectList(queryWrapper);
            if (list == null || list.isEmpty()) {
                return RespBody.data("校验通过");
            }
            //新增
            if (sysUser.getId() == null) {
                return RespBody.fail(BizErrorCode.USER_EMAIL_EXIST_ERROR);
                //修改
            } else {
                for (SysUser entity : list) {
                    if (!entity.getId().equals(sysUser.getId())) {
                        return RespBody.fail(BizErrorCode.USER_EMAIL_EXIST_ERROR);
                    }
                }
            }
        }
        return RespBody.data("校验通过");
    }

    @Override
    public List<SysUserDto> listExtendsSysUser(SysUserDto sysUserDto, SysUser sysUser) {
        //部门权限
        List<Long> deptIdList = null;
        //下级职位
        List<Long> subPostList = null;
        //当前用户
        Long createUserLimit = null;
        if (!sysUser.getIsAdmin()) {
            //数据权限部门
            deptIdList = sysUser.getAuthDept();
            //职位权限
            if (sysUser.getPostId() != null) {
                //不限 数据权限部门及当前用户所在部门及下属部门
                if (sysUser.getPostDataAuth() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(sysUser.getPostDataAuth())) {
                    deptIdList = dataAuthService.getAuthDeptsList(sysUser.getId(), sysUser.getDeptId());
                    //本人及下级岗位
                } else if (DataPermissionPostEnum.SELF_SUB.getCode().equals(sysUser.getPostDataAuth())) {
                    createUserLimit = sysUser.getId();
                    subPostList = sysUser.getSubPost();
                    //本人
                } else if (DataPermissionPostEnum.SELF.getCode().equals(sysUser.getPostDataAuth())) {
                    createUserLimit = sysUser.getId();
                }
            }
        }
        List<SysUser> sysUserList = baseMapper.extendsList(null, sysUserDto, deptIdList, subPostList, createUserLimit);
        return ConvertUtil.transformObjList(sysUserList, SysUserDto.class);
    }

    @Override
    public List<SysUserDto> listSysUser(List<Long> userIds, SysUser sysUser) {
        //部门权限
        List<Long> deptIdList = null;
        //下级职位
        List<Long> subPostList = null;
        //当前用户
        Long createUserLimit = null;
        if (!sysUser.getIsAdmin()) {
            //数据权限部门
            deptIdList = sysUser.getAuthDept();
            //职位权限
            if (sysUser.getPostId() != null) {
                //不限 数据权限部门及当前用户所在部门及下属部门
                if (sysUser.getPostDataAuth() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(sysUser.getPostDataAuth())) {
                    deptIdList = dataAuthService.getAuthDeptsList(sysUser.getId(), sysUser.getDeptId());
                    //本人及下级岗位
                } else if (DataPermissionPostEnum.SELF_SUB.getCode().equals(sysUser.getPostDataAuth())) {
                    createUserLimit = sysUser.getId();
                    subPostList = sysUser.getSubPost();
                    //本人
                } else if (DataPermissionPostEnum.SELF.getCode().equals(sysUser.getPostDataAuth())) {
                    createUserLimit = sysUser.getId();
                }
            }
        }
        List<SysUser> sysUserList = baseMapper.listSysUser(userIds, deptIdList, subPostList, createUserLimit);
        return ConvertUtil.transformObjList(sysUserList, SysUserDto.class);
    }

    @Override
    public List<SysUser> noPunched(Integer type, Date begin, Date end) {
        return baseMapper.noPunched(type, begin, end);
    }

    @Override
    public Map<Integer, List<SysUserDto>> leaders(SysUser sysUser) {
        Map<Integer, List<SysUserDto>> result = new HashMap<>();
        Long postId = sysUser.getPostId();
        SysPostEntity postEntity = sysPostService.getById(postId);
        SysDeptEntity deptEntity = sysDeptMapper.selectById(sysUser.getDeptId());
        //查询本部门及所有上级部门的主管职位
        List<SysPostDto> posts = sysPostService.leadersPost(deptEntity);
        List<Long> postIds = new ArrayList<>();
        List<SysUserDto> allLeader = new ArrayList<>();
        for (SysPostDto sysPostDto : posts) {
            //本职位是超管，则本部门的超管不再查询
            if (DataPermissionPostEnum.NO_LIMIT.getCode().equals(postEntity.getDataAuthType()) && postEntity.getId().equals(sysPostDto.getId())) {
                continue;
            }
            postIds.add(sysPostDto.getId());
        }
        //查询主管职位的所有用户
        if (!postIds.isEmpty()) {
            QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
            wrapper.eq("is_deleted", YesNoEnum.NO.getCode());
            wrapper.in("post_id", postIds);
            allLeader = ConvertUtil.transformObjList(baseMapper.selectList(wrapper), SysUserDto.class);
        }
        if (!allLeader.isEmpty()) {
            //组织返回数据
            Integer index = 1;
            List<SysUserDto> leader = new ArrayList<>();
            if (!DataPermissionPostEnum.NO_LIMIT.getCode().equals(postEntity.getDataAuthType())) {
                for (SysUserDto u : allLeader) {
                    if (u.getDeptId().equals(sysUser.getDeptId())) {
                        leader.add(u);
                    }
                }
                if (!leader.isEmpty()) {
                    result.put(index, leader);
                    ++index;
                }
            }
            if (StrUtil.isNotEmpty(deptEntity.getParents())) {
                String[] deptParents = deptEntity.getParents().split(",");
                for (int i = deptParents.length - 1; i >= 0 && StrUtil.isNotEmpty(deptParents[i]); i--) {
                    leader = new ArrayList<>();
                    for (SysUserDto u : allLeader) {
                        if (u.getDeptId().toString().equals(deptParents[i])) {
                            leader.add(u);
                        }
                    }
                    if (!leader.isEmpty()) {
                        result.put(index, leader);
                        ++index;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, List<String>> leadersId(SysUser sysUser) {
        Map<String, List<String>> result = new HashMap<>();
        Map<Integer, List<SysUserDto>> map = leaders(sysUser);
        for (Integer key : map.keySet()) {
            List<SysUserDto> list = map.get(key);
            List<String> ids = new ArrayList<>();
            for (SysUserDto u : list) {
                ids.add(u.getId() + "");
            }
            result.put(key + "", ids);
        }
        return result;
    }

    @Override
    public SysUserDto getByUnionId(String unionId) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("union_id", unionId);
        wrapper.eq("is_deleted", YesNoEnum.NO.getCode());
        wrapper.last("limit 1");
        SysUser entity = getOne(wrapper);
        return ConvertUtil.transformObj(entity, SysUserDto.class);
    }

    @Override
    public SysUser getByWeComUserId(String userId) {
        return baseMapper.getByWeComUserId(userId);
    }

    @Override
    public SysUser getByDingUnionid(String unionId) {
        return baseMapper.getByDingUnionid(unionId);
    }

    @Override
    public IPage<SysUserDto> findByIds(List<Long> ids, Page<SysUser> page) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<SysUserDto> sysUser = sysUserMapper.findUserByIds(ids, pageMap);
        Integer totle = sysUserMapper.findUserByidsCount(ids);
        Page<SysUserDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), totle);
        pageDto.setRecords(sysUser);
        return pageDto;
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserMapper.findAll(null);
    }

    @Override
    public boolean updateByIds(List<Long> ids) {
        return sysUserMapper.updateBatch(ids);
    }

    @Override
    public SysUserDto selectById(Long id) {
        return sysUserMapper.selectUserInfo(id);
    }

    @Override
    public List<SysUser> list(String name) {
        return sysUserMapper.findAll(name);
    }

    @Override
    public SysUser getByLongName(String loginName) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.eq("login_name", loginName);
        return baseMapper.selectOne(sysUserQueryWrapper);
    }

    @Override
    public IPage<SysUserDto> authorityAllUserName(Page<SysUserDto> page, List<Long> roleIds, String realName) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        List<SysUserDto> sysUserDtos = baseMapper.authorityAllUserName(pageMap, roleIds, realName);
        Integer totle = baseMapper.authorityAllUserNameCount(roleIds, realName);
        Page<SysUserDto> pageDto = new Page<>(page.getCurrent(), page.getSize(), totle);
        pageDto.setRecords(sysUserDtos);
        return pageDto;
    }

    @Override
    public List<String> findByUidList(List<Long> userByIdList) {
        QueryWrapper<SysUser> sysUserQueryWrapper = new QueryWrapper<>();
        sysUserQueryWrapper.in("id", userByIdList);
        return baseMapper.selectList(sysUserQueryWrapper).stream().map(SysUser::getRealName).collect(Collectors.toList());
    }

    @Override
    public boolean updateByIdsTrue(List<Long> ids) {
        return sysUserMapper.updateBatchTrue(ids);
    }

}
