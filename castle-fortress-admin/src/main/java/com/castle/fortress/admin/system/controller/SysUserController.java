package com.castle.fortress.admin.system.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.knowledge.dto.KbGroupsDto;
import com.castle.fortress.admin.knowledge.service.KbGroupsService;
import com.castle.fortress.admin.knowledge.service.KbGroupsUserService;
import com.castle.fortress.admin.shiro.entity.LoginUser;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.entity.SysUserRole;
import com.castle.fortress.admin.system.service.*;
import com.castle.fortress.admin.utils.PwdUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统用户管理
 *
 * @author castle
 */
@Api(tags = "系统用户管理控制器")
@Controller
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysCaptchaService sysCaptchaService;

    @Autowired
    private CastleSysUserWeComService castleSysUserWeComService;
    @Autowired
    private CastleSysUserDingService castleSysUserDingService;
    @Autowired
    private ConfigApiService configApiService;

    @Autowired

    private SysDeptService deptService;
    @Autowired
    private KbGroupsService kbGroupsService;
    @Autowired
    private KbGroupsUserService kbGroupsUserService;

    /**
     * 系统用户表的分页展示 单独处理数据权限
     *
     * @param sysUserDto 系统用户表实体类
     * @param current    当前页
     * @param size       每页记录数
     * @return
     */
    @ApiOperation("系统用户表分页展示")
    @GetMapping("/system/sysUser/page")
    @ResponseBody
//    @RequiresPermissions("system:sysUser:pageList")
    public RespBody<IPage<SysUserDto>> pageSysUserDto(SysUserDto sysUserDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        //不是超管 只能查看普通用户，不可查看超管用户
        if (!sysUser.getIsAdmin()) {
            sysUserDto.setIsAdmin(false);
        }
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<SysUserDto> page = new Page(pageIndex, pageSize);
        IPage<SysUserDto> pages = sysUserService.pageExtendsSysUser(page, sysUserDto, sysUser);
        return RespBody.data(pages);
    }

    @ApiOperation("系统用户表分页展示")
    @GetMapping("/system/sysUser/list")
    @ResponseBody
    public RespBody<IPage<SysUserDto>> listSysUserDto(SysUserDto sysUserDto, @RequestParam(required = false) Integer current, @RequestParam(required = false) Integer size) {
        Integer pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        Integer pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<SysUserDto> page = new Page(pageIndex, pageSize);
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        List<Long> roleIds = null;
        //非管理员
        if (!sysUser.getIsAdmin()) {
            List<SysRole> roleList = sysUser.getRoles();
            if (roleList == null || roleList.isEmpty()) {
                return RespBody.data(null);
            }
            if (roleList != null && roleList.size() > 0) {
                roleIds = new ArrayList<>();
                for (SysRole role : roleList) {
                    roleIds.add(role.getId());
                }
            }
        }
        IPage<SysUserDto> sysDeptDtos = sysUserService.authorityAllUserName(page,roleIds, sysUserDto.getRealName());
        return RespBody.data(sysDeptDtos);
    }

    /**
     * 系统用户表保存
     *
     * @param sysUserDto 系统用户表实体类
     * @return
     */
    @ApiOperation("系统用户表保存")
    @PostMapping("/system/sysUser/save")
    @ResponseBody
    @RequiresPermissions("system:sysUser:save")
    public RespBody<String> saveSysUserDto(@RequestBody SysUserDto sysUserDto) {
        if (sysUserDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = ConvertUtil.transformObj(sysUserDto, SysUser.class);
        sysUser.setPassword(PwdUtil.encode(sysUser.getPassword()));
        RespBody checkResult = sysUserService.checkColumnRepeat(sysUser);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        //todo 校验手机号、证件号
        if (sysUserService.save(sysUser)) {
            //插入用户角色信息
            if (sysUser.getRoles() != null && sysUser.getRoles().size() > 0) {
                List<SysUserRole> userRoleList = new ArrayList<>();
                for (SysRole role : sysUser.getRoles()) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(role.getId());
                    userRole.setUserId(sysUser.getId());
                    userRoleList.add(userRole);
                }
                sysUserRoleService.saveBatch(userRoleList);
            }
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * @return
     */
    @ApiOperation("搜索用户或者部门")
    @GetMapping("/system/sysUser/findToDept")
    @ResponseBody
//    @RequiresPermissions("system:sysUser:findToDept")
    public RespBody<HashMap<String, Object>> findToDeptSysUser(@RequestParam(required = false) String name,
                                                               @RequestParam Long type) {
        if (type == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 查询部门所有
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        HashMap<String, Object> resMap = new HashMap<>();

        if (type == 1 || type == 3) {
            resMap.put("user", sysUserService.list(name));
        }
        if (type == 2 || type == 3) {
            List<Long> roleIds = null;
            //非管理员
            if (!sysUser.getIsAdmin()) {
                List<SysRole> roleList = sysUser.getRoles();
                if (roleList == null || roleList.isEmpty()) {
                    return RespBody.data(null);
                }
                if (roleList != null && roleList.size() > 0) {
                    roleIds = new ArrayList<>();
                    for (SysRole role : roleList) {
                        roleIds.add(role.getId());
                    }
                }
            }
            resMap.put("dept", deptService.listToName(roleIds, name));
        }
        return RespBody.data(resMap);
    }

    @ApiOperation("搜索用户或者群组")
    @GetMapping("/system/sysUser/findToGroup")
    @ResponseBody
    //@RequiresPermissions("system:sysUser:findToDept")
    public RespBody<HashMap<String, Object>> findToGroupSysUser(@RequestParam(required = false) Long id,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) Integer status,
                                                                @RequestParam Long type,
                                                                @RequestParam(required = false) Integer current,
                                                                @RequestParam(required = false) Integer size) {
        if (type == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        int pageIndex = current == null ? GlobalConstants.DEFAULT_PAGE_INDEX : current;
        int pageSize = size == null ? GlobalConstants.DEFAULT_PAGE_SIZE : size;
        Page<SysUserDto> page = new Page<>(pageIndex, pageSize);
        // 查询部门所有
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        HashMap<String, Object> resMap = new HashMap<>();

        if (type == 1 || type == 3) {
            resMap.put("user", kbGroupsUserService.listFindUser(id, name, page, status));
        }
        if (type == 2 || type == 3) {
            Page<KbGroupsDto> page1 = new Page<>(pageIndex, pageSize);
            KbGroupsDto kbGroupsDto = new KbGroupsDto();
            kbGroupsDto.setName(name);
            resMap.put("group", kbGroupsService.pageKbGroups(page1, kbGroupsDto));
        }
        return RespBody.data(resMap);
    }

    /**
     * 置为无效
     *
     * @param ids 系统用户表实体类
     * @return
     */
    @ApiOperation("系统用户表置为无效")
    @PostMapping("/system/sysUser/setStatus")
    @ResponseBody
    @RequiresPermissions("system:sysUser:setStatus")
    public RespBody<String> setStatusSysUser(Integer num,@RequestBody List<Long> ids) {
        // todo： 待开发 系统用户表置为无效
        if (ids.size() == 0) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (num == null){
        boolean update = sysUserService.updateByIds(ids);
        if (update) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.data("操作失败");
        }
        }else {
            boolean update = sysUserService.updateByIdsTrue(ids);
            if (update) {
                return RespBody.data("保存成功");
            } else {
                return RespBody.data("操作失败");
            }
        }

    }

    /**
     * 系统用户表编辑
     *
     * @param sysUserDto 系统用户表实体类
     * @return
     */
    @ApiOperation("系统用户表编辑")
    @PostMapping("/system/sysUser/edit")
    @ResponseBody
    @RequiresPermissions("system:sysUser:edit")
    public RespBody<String> updateSysUser(@RequestBody SysUserDto sysUserDto) {
        if (sysUserDto == null || sysUserDto.getId() == null || sysUserDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = ConvertUtil.transformObj(sysUserDto, SysUser.class);
        if (StrUtil.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(PwdUtil.encode(sysUser.getPassword()));
        } else {
            sysUser.setPassword(null);
        }
        RespBody checkResult = sysUserService.checkColumnRepeat(sysUser);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        if (sysUserService.updateById(sysUser)) {
            //保存新的角色关系
            if (!(sysUser.getRoles() == null)) {
                //删除原有的角色管理
                sysUserRoleService.delByUserId(sysUser.getId());

                List<SysUserRole> userRoleList = new ArrayList<>();
                for (SysRole role : sysUser.getRoles()) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setRoleId(role.getId());
                    userRole.setUserId(sysUser.getId());
                    userRoleList.add(userRole);
                }
                sysUserRoleService.saveBatch(userRoleList);
                return RespBody.data("保存成功");
            }
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
        return RespBody.data("保存成功");
    }


    /**
     * 系统用户表删除
     *
     * @param id
     * @return
     */
    @ApiOperation("系统用户表删除")
    @PostMapping("/system/sysUser/delete")
    @ResponseBody
    @RequiresPermissions("system:sysUser:delete")
    public RespBody<String> deleteSysUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser user = WebUtil.currentUser();
        if (user.getId().equals(id)) {
            return RespBody.fail(BizErrorCode.USER_DEL_SELF_ERROR);
        }
        if (sysUserService.removeById(id)) {
            //删除原有的角色管理
            sysUserRoleService.delByUserId(id);
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 系统用户表详情
     *
     * @param id 系统用户表id
     * @return
     */
    @ApiOperation("系统用户表详情")
    @GetMapping("/system/sysUser/info")
    @ResponseBody
    @RequiresPermissions("system:sysUser:info")
    public RespBody<SysUserDto> infoSysUser(@RequestParam Long id) {
        if (id == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser sysUser = sysUserService.getByIdExtends(id);
        SysUserDto sysUserDto = ConvertUtil.transformObj(sysUser, SysUserDto.class);
        return RespBody.data(sysUserDto);
    }

    /**
     * 个人设置-信息
     *
     * @return
     */
    @ApiOperation("个人设置-信息")
    @GetMapping("/system/sysUser/personInfo")
    @ResponseBody
    public RespBody<SysUserDto> personInfo() {
        SysUser u = WebUtil.currentUser();
        SysUser sysUser = sysUserService.getByIdExtends(u.getId());
        SysUserDto sysUserDto = ConvertUtil.transformObj(sysUser, SysUserDto.class);
        return RespBody.data(sysUserDto);
    }

    /**
     * 个人设置-更新
     *
     * @param sysUserDto 系统用户表实体类
     * @return
     */
    @ApiOperation("系统用户表编辑")
    @PostMapping("/system/sysUser/personEdit")
    @ResponseBody
    public RespBody<String> personEdit(@RequestBody SysUserDto sysUserDto) {
        if (sysUserDto == null || sysUserDto.getId() == null || sysUserDto.getId().equals(0L)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser u = WebUtil.currentUser();
        if (!u.getId().equals(sysUserDto.getId())) {
            throw new BizException(GlobalRespCode.UNAUTHORIZED);
        }
        SysUser sysUser = ConvertUtil.transformObj(sysUserDto, SysUser.class);
        //不修改密码
        sysUser.setPassword(null);
        RespBody checkResult = sysUserService.checkColumnRepeat(sysUser);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        if (sysUserService.updateById(sysUser)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 个人设置-修改密码
     *
     * @param sysUserDto 系统用户表实体类
     * @return
     */
    @ApiOperation("个人设置-修改密码")
    @PostMapping("/system/sysUser/personUptPwd")
    @ResponseBody
    public RespBody<String> personUptPwd(@RequestBody SysUserDto sysUserDto) {
        if (CommonUtil.verifyParamNull(sysUserDto, sysUserDto.getPassword(), sysUserDto.getOldPassword())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser u = WebUtil.currentUser();
        if (u.getId() == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if (!u.getId().equals(sysUserDto.getId())) {
            throw new BizException(GlobalRespCode.UNAUTHORIZED);
        }
        SysUser su = sysUserService.getById(sysUserDto.getId());
        //校验原密码
        if (!PwdUtil.matches(sysUserDto.getOldPassword(), su.getPassword())) {
            throw new BizException(BizErrorCode.OLD_PWD_ERROR);
        }
        SysUser uptUser = new SysUser();
        uptUser.setId(sysUserDto.getId());
        uptUser.setPassword(PwdUtil.encode(sysUserDto.getPassword()));
        if (sysUserService.updateById(uptUser)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    @ApiOperation("修改密码和登录名")
    @PostMapping("/system/sysUser/longinNamePwd")
    @ResponseBody
    public RespBody<String> longinNamePwd(@RequestBody SysUserDto sysUserDto) {
        // 获取当前登录用户
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.TOKEN_EXPIRED_ERROR);
        }
        SysUser uptUser = new SysUser();
        if (sysUser.getLoginName().equals("admin") && sysUserDto.getLoginName() != null) {
            String loginName = sysUserDto.getLoginName();
            SysUser getByLongUser = sysUserService.getByLongName(loginName);
            if (getByLongUser != null && !sysUserDto.getId().equals(getByLongUser.getId())) {
                return RespBody.fail("登录名已存在 不允许重复");
            }
            uptUser.setLoginName(sysUserDto.getLoginName());
        }
        uptUser.setId(sysUserDto.getId());
        uptUser.setPassword(PwdUtil.encode(sysUserDto.getPassword()));
        if (sysUserService.updateById(uptUser)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 个人设置-修改手机号
     *
     * @param sysUserDto 系统用户表实体类
     * @return
     */
    @ApiOperation("个人设置-修改手机号")
    @PostMapping("/system/sysUser/personUptPhone")
    @ResponseBody
    public RespBody<String> personUptPhone(@RequestBody SysUserDto sysUserDto) {
        if (CommonUtil.verifyParamNull(sysUserDto, sysUserDto.getId(), sysUserDto.getPhone(), sysUserDto.getOldPhone(), sysUserDto.getCaptcha(), sysUserDto.getOldCaptcha())) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser u = WebUtil.currentUser();
        if (!u.getId().equals(sysUserDto.getId())) {
            throw new BizException(GlobalRespCode.UNAUTHORIZED);
        }
        SysUser su = sysUserService.getById(u.getId());
        //校验原手机号
        if (!su.getPhone().equals(sysUserDto.getOldPhone())) {
            throw new BizException(BizErrorCode.OLD_PHONE_ERROR);
        }
        //校验验证码
        sysCaptchaService.verifySysCaptcha(sysUserDto.getPhone(), sysUserDto.getCaptcha());
        //校验验证码
        sysCaptchaService.verifySysCaptcha(sysUserDto.getOldPhone(), sysUserDto.getOldCaptcha());
        //校验新手机号是否占用
        su.setPhone(sysUserDto.getPhone());
        RespBody repeatRb = sysUserService.checkColumnRepeat(su);
        if (repeatRb.isSuccess()) {
            SysUser uptUser = new SysUser();
            uptUser.setId(u.getId());
            uptUser.setPhone(sysUserDto.getPhone());
            if (sysUserService.updateById(uptUser)) {
                return RespBody.data("保存成功");
            } else {
                return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
            }
        } else {
            return repeatRb;
        }
    }


    /**
     * 同步用户企微信息
     *
     * @return
     */
    @ApiOperation("同步用户企微信息")
    @GetMapping("/system/sysUser/syncWeCom")
    @ResponseBody
    public RespBody<String> syncWeCom() {

        ConfigApiDto configApiDto = configApiService.getByBindCode("SELF_WECOM");
        if (configApiDto == null || StrUtil.isEmpty(configApiDto.getBindDetail())) {
            return RespBody.fail("请先配置企微相关参数");
        }
        JSONObject jsonObject = JSONUtil.parseObj(configApiDto.getBindDetail());
        Integer status = jsonObject.getInt("syncStatus");
        if (status == null || status.equals(YesNoEnum.NO.getCode())) {
            return RespBody.fail("请先开启企微同步开关");
        }
        if (castleSysUserWeComService.syncWeCom()) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.data("操作失败");
        }
    }


    /**
     * 同步用户企微信息
     *
     * @return
     */
    @ApiOperation("同步用户钉钉信息")
    @GetMapping("/system/sysUser/syncDing")
    @ResponseBody
    public RespBody<String> syncDing() {

        ConfigApiDto configApiDto = configApiService.getByBindCode("DING_LOGIN");
        if (configApiDto == null || StrUtil.isEmpty(configApiDto.getBindDetail())) {
            return RespBody.fail("请先配置钉钉相关参数");
        }
        JSONObject jsonObject = JSONUtil.parseObj(configApiDto.getBindDetail());
        Integer status = jsonObject.getInt("syncStatus");
        if (status == null || YesNoEnum.NO.getCode().equals(status)) {
            return RespBody.fail("请先开启钉钉同步开关");
        }

        if (castleSysUserDingService.syncDing()) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.data("操作失败");
        }
    }


    /**
     * 个人设置-微信解绑
     *
     * @return
     */
    @ApiOperation("个人设置-微信解绑")
    @PostMapping("/system/sysUser/unbindWechat")
    @ResponseBody
    public RespBody<String> unbindWechat() {
        SysUser u = WebUtil.currentUser();
        boolean status = sysUserService.lambdaUpdate()
                .eq(SysUser::getId, u.getId())
                .set(SysUser::getWxAvatar, null)
                .set(SysUser::getWxBindTime, null)
                .set(SysUser::getWxName, null)
                .set(SysUser::getUnionId, null)
                .set(SysUser::getOpenid, null)
                .update();
        if (status) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    /**
     * 个人设置-微信QQ
     *
     * @return
     */
    @ApiOperation("个人设置-微信解绑")
    @PostMapping("/system/sysUser/unbindQQ")
    @ResponseBody
    public RespBody<String> unbindQQ() {
        SysUser u = WebUtil.currentUser();
        boolean status = sysUserService.lambdaUpdate()
                .eq(SysUser::getId, u.getId())
                .set(SysUser::getQqOpenid, null)
                .set(SysUser::getQqAvatar, null)
                .set(SysUser::getQqBindTime, null)
                .set(SysUser::getQqName, null)
                .update();
        if (status) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 个人设置-微信绑定
     *
     * @return
     */
    @ApiOperation("个人设置-微信绑定")
    @PostMapping("/system/sysUser/bindWechat")
    @ResponseBody
    public RespBody<String> bindWechat(@RequestBody LoginUser login) {
        SysUser u = WebUtil.currentUser();
        SysUser user = sysUserService.getById(u.getId());
        // 判断是否传输 微信unionId
        if (StrUtil.isEmpty(login.getUnionId())) {
            throw new BizException(BizErrorCode.WX_NO_UNIONID_ERROR);
        }
        // 判断账号是否已绑定过微信
        if (StrUtil.isNotEmpty(user.getUnionId())) {
            throw new BizException(BizErrorCode.WX_BIND_ERROR);
        }
        // 账号绑定unionId openid
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setUnionId(login.getUnionId());
        updateUser.setOpenid(login.getOpenid());
        updateUser.setWxName(login.getNickname());
        updateUser.setWxAvatar(login.getAvatar());
        updateUser.setWxBindTime(new Date());

        if (sysUserService.updateById(updateUser)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }


    /**
     * 个人设置-QQ绑定
     *
     * @return
     */
    @ApiOperation("个人设置-QQ绑定")
    @PostMapping("/system/sysUser/bindQQ")
    @ResponseBody
    public RespBody<String> bindQQ(@RequestBody LoginUser login) {
        SysUser u = WebUtil.currentUser();
        SysUser user = sysUserService.getById(u.getId());
        // 判断是否传输 QQ openID
        if (StrUtil.isEmpty(login.getOpenid())) {
            throw new BizException(BizErrorCode.QQ_NO_OPENID_ERROR);
        }
        // 判断账号是否已绑定过微信
        if (StrUtil.isNotEmpty(user.getQqOpenid())) {
            throw new BizException(BizErrorCode.WX_BIND_ERROR);
        }
        // 账号绑定unionId openid
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setId(user.getId());
        updateUser.setQqOpenid(login.getOpenid());
        updateUser.setQqName(login.getNickname());
        updateUser.setQqAvatar(login.getAvatar());
        updateUser.setQqBindTime(new Date());

        if (sysUserService.updateById(updateUser)) {
            return RespBody.data("保存成功");
        } else {
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

    @ApiOperation("系统用户表置为无效")
    @GetMapping("/system/sysUser/selectById")
    @ResponseBody
//    @RequiresPermissions("system:sysUser:setStatus")
    public RespBody<SysUserDto> selectById() {
        // todo： 待开发 系统用户表置为无效
        SysUser sysUser = WebUtil.currentUser();
        Long id = sysUser.getId();
        SysUserDto user = sysUserService.selectById(id);
        return RespBody.data(user);
    }

}
