package com.castle.fortress.admin.shiro.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import com.castle.fortress.admin.log.enums.LogSmsStatusEnum;
import com.castle.fortress.admin.log.enums.LogSmsTypeEnum;
import com.castle.fortress.admin.log.service.LogLoginService;
import com.castle.fortress.admin.log.service.LogSmsService;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.shiro.entity.LoginUser;
import com.castle.fortress.admin.shiro.service.DataAuthService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.dto.SysUserDto;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysRoleService;
import com.castle.fortress.admin.system.service.SysUserService;
import com.castle.fortress.admin.utils.*;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.DataPermissionPostEnum;
import com.castle.fortress.common.enums.LoginMethodEnum;
import com.castle.fortress.common.enums.UserTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthDingTalkRequest;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.request.AuthWeChatOpenRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


/**
 * 第三方登录/绑定 user端
 *
 * @author castle
 */
@Api(tags = "第三方登录/绑定登录控制器")
@Controller
public class ThirdLoginController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private DataAuthService dataAuthService;
    @Autowired
    private LogSmsService logSmsService;
    @Autowired
    private BindApiUtils bindApiUtils;
    @Autowired
    private LogLoginService logLoginService;


    @Value("${castle.logs.login}")
    private boolean loginFlag;

    /**
     * 系统用户转化为CastleUserDetail类型
     *
     * @param user
     * @return
     */
    private CastleUserDetail sysUserToCastleUser(SysUser user) {
        CastleUserDetail userDetail = null;
        if (user != null) {
            userDetail = new CastleUserDetail();
            userDetail.setId(user.getId());
            userDetail.setUsername(user.getLoginName());
            userDetail.setAvatar(user.getAvatar());
            userDetail.setUserType(UserTypeEnum.SYS_USER.getName());
            userDetail.setDeptId(user.getDeptId());
            userDetail.setDeptParents(user.getDeptParents());
            userDetail.setNickname(user.getNickname());
            userDetail.setIsSuperAdmin(user.getIsSuperAdmin());
            userDetail.setPostId(user.getPostId());
            userDetail.setRealName(user.getRealName());
            userDetail.setRoles(user.getRoles());
            userDetail.setAuthDept(user.getAuthDept());
            userDetail.setSubPost(user.getSubPost());
            userDetail.setPostDataAuth(user.getPostDataAuth());
        }
        return userDetail;
    }

    /**
     * 第三方 登录
     *
     * @param userName    用户名
     * @param redirectUrl URL
     * @return
     */
    private ModelAndView thirdLogin(String userName, String redirectUrl) {
        //用户信息
        boolean isAdmin = false;
        List<SysUser> users = sysUserService.queryByLoginName(userName);
        //微信未绑定
        if (users == null || users.size() != 1) {
            throw new BizException(BizErrorCode.WX_USER_ERROR);
        }
        SysUser user = users.get(0);

        // 赋予角色
        List<SysRole> roleList = sysRoleService.queryListByUser(user.getId());
        user.setRoles(roleList);
        // 赋予角色
        List<Long> roleIds = new ArrayList<>();
        for (SysRole role : roleList) {
            roleIds.add(role.getId());
            if (role.getIsAdmin().equals(YesNoEnum.YES.getCode())) {
                isAdmin = true;
            }
        }

        if (!isAdmin) {
            //数据权限部门
            List<Long> authDept = dataAuthService.getAuthDeptsList(user.getId(), null);
            if (user.getPostId() != null) {
                //下级岗位
                List<Long> subPost = dataAuthService.getSubPostList(user.getPostId());
                user.setSubPost(subPost);
            }
            //无职位限制 则部门内不限
            if (user.getPostId() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(user.getPostDataAuth())) {
                authDept = dataAuthService.getAuthDeptsList(user.getId(), user.getDeptId());
            }
            user.setAuthDept(authDept);
        }

        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(sysUserToCastleUser(user), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(sysUserToCastleUser(user), refreshTokenExpiration);
        ModelAndView mv = new ModelAndView("redirect:" + redirectUrl + "/isLogin");
        mv.addObject("token", authToken);
        mv.addObject("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        mv.addObject("refreshToken", refreshToken);
        return mv;
    }


    @GetMapping("wxLoginCode")
    @ApiOperation(value = "生成微信登录二维码网页")
    public ModelAndView wxLoginCode() {
        ConfigApiDto dto = bindApiUtils.getData("WX_LOGIN");
        if (dto == null) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String, Object> map = dto.getParamMap();
        if (map == null || map.isEmpty()) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = map.get("url") + "/wxQrCodeLogin";
        AuthRequest authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return new ModelAndView("redirect:" + authorizeUrl);
    }

    @GetMapping("wxQrCodeLogin")
    @ApiOperation(value = "微信扫码登录")
    public ModelAndView wxQrCodeLogin(AuthCallback callback, HttpServletRequest request) {
        ConfigApiDto dto = bindApiUtils.getData("WX_LOGIN");
        if (dto == null) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String, Object> map = dto.getParamMap();
        if (map == null || map.isEmpty()) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = map.get("url") + "/wxQrCodeLogin";
        AuthRequest authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        AuthResponse authResponse = authRequest.login(callback);
        JSONObject data = JSONUtil.parseObj(JSON.toJSONString(authResponse.getData()));
        String unionId = data.getJSONObject("token").getStr("unionId");
        String openid = data.getJSONObject("token").getStr("openId");
        String headImgUrl = data.getStr("avatar");
        String nickname = data.getStr("nickname");
        // 用unionid 获取系统user 账号信息  若为空则跳转绑定页面
        SysUserDto user = sysUserService.getByUnionId(unionId);
        if (user == null) {
            String redirectUrl = (String) map.get("frontUrl");
            try {
                return new ModelAndView("redirect:" + redirectUrl + "/binding-wechat?type=wx&unionid=" + unionId
                        + "&openid=" + openid + "&headimgurl=" + headImgUrl + "&nickname=" + URLEncoder.encode(nickname, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //保存登录日志
        saveLoginLog(data.toString(),"00","登录成功",user.getId(), LoginMethodEnum.WX.getCode());
        // 已绑定 直接登录
        return thirdLogin(user.getLoginName(), (String) map.get("frontUrl"));
    }

    @PostMapping("bindingWeChatLogin")
    @ApiOperation(value = "微信绑定并登录")
    @ResponseBody
    public RespBody<Map> bindingWeChatLogin(HttpServletRequest request, @RequestBody LoginUser login) {

        // 验证验证码
        String smsCode = login.getSmscode();
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", login.getPhone());
        params.put("status", LogSmsStatusEnum.VALID.getCode());
        params.put("type", LogSmsTypeEnum.LOGIN.getCode());
        List<LogSmsDto> list = logSmsService.getDataList(params);
        if (list.size() > 0) {
            LogSmsDto logSmsDto = list.get(0);
            if (!smsCode.equals(logSmsDto.getParams1())) {
                return RespBody.fail("验证码错误");
            } else {
                // 验证成功 将验证码改为失效
                logSmsDto.setStatus(LogSmsStatusEnum.INVALID.getCode());
                logSmsService.updateById(ConvertUtil.transformObj(logSmsDto, LogSmsEntity.class));
            }
        } else {
            return RespBody.fail("验证码错误");
        }

        SysUser user = null;
        boolean isAdmin = false;
        List<SysUser> users = sysUserService.queryByLoginName(login.getPhone());
        if (users == null || users.size() != 1) {
            throw new BizException(BizErrorCode.PHONE_NO_EXIST_ERROR);
        }
        user = users.get(0);

        // 判断是否传输 微信unionId
        if (StrUtil.isEmpty(login.getUnionId())) {
            throw new BizException(BizErrorCode.WX_NO_UNIONID_ERROR);
        }
        // 判断账号是否已绑定过微信
        if (StrUtil.isNotEmpty(user.getUnionId())) {
            throw new BizException(BizErrorCode.WX_BIND_ERROR);
        }
        // 赋予角色
        List<SysRole> roleList = sysRoleService.queryListByUser(user.getId());
        user.setRoles(roleList);
        // 赋予角色
        List<Long> roleIds = new ArrayList<>();
        for (SysRole role : roleList) {
            roleIds.add(role.getId());
            if (role.getIsAdmin().equals(YesNoEnum.YES.getCode())) {
                isAdmin = true;
            }
        }
        if (!isAdmin) {
            //数据权限部门
            List<Long> authDept = dataAuthService.getAuthDeptsList(user.getId(), null);
            if (user.getPostId() != null) {
                //下级岗位
                List<Long> subPost = dataAuthService.getSubPostList(user.getPostId());
                user.setSubPost(subPost);
            }
            //无职位限制 则部门内不限
            if (user.getPostId() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(user.getPostDataAuth())) {
                authDept = dataAuthService.getAuthDeptsList(user.getId(), user.getDeptId());
            }
            user.setAuthDept(authDept);
        }


        // 账号绑定unionId openid
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setUnionId(login.getUnionId());
        updateUser.setOpenid(login.getOpenid());
        updateUser.setWxName(login.getNickname());
        updateUser.setWxAvatar(login.getAvatar());
        updateUser.setWxBindTime(new Date());
        sysUserService.updateById(updateUser);

        //保存登录日志
        saveLoginLog(JSONUtil.toJsonStr(login),"00","登录成功",user.getId(), LoginMethodEnum.WX.getCode());

        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(sysUserToCastleUser(user), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(sysUserToCastleUser(user), refreshTokenExpiration);
        Map<String, Object> map = new HashMap<>();
        map.put("token", authToken);
        map.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        map.put("refreshToken", refreshToken);
        RespBody<Map> respBody = RespBody.data(map);
        return respBody;

    }

    @GetMapping("wxBindCode")
    @ApiOperation(value = "生成微信绑定二维码网页")
    public ModelAndView wxBindCode() {
        ConfigApiDto dto = bindApiUtils.getData("WX_LOGIN");
        if (dto == null) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String, Object> map = dto.getParamMap();
        if (map == null || map.isEmpty()) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = map.get("url") + "/wxQrCodeBind";
        AuthRequest authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
        return new ModelAndView("redirect:" + authorizeUrl);

    }

    @GetMapping("wxQrCodeBind")
    @ApiOperation(value = "微信扫码绑定")
    public ModelAndView wxQrCodeBind(AuthCallback callback, HttpServletRequest request) {
        ConfigApiDto dto = bindApiUtils.getData("WX_LOGIN");
        if (dto == null) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String, Object> map = dto.getParamMap();
        if (map == null || map.isEmpty()) {
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = map.get("url") + "/wxQrCodeLogin";
        AuthRequest authRequest = new AuthWeChatOpenRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        AuthResponse authResponse = authRequest.login(callback);
        JSONObject data = JSONUtil.parseObj(JSON.toJSONString(authResponse.getData()));
        String unionId = data.getJSONObject("token").getStr("unionId");
        String openid = data.getJSONObject("token").getStr("openId");
        String headImgUrl = data.getStr("avatar");
        String nickname = data.getStr("nickname");
        // 用unionid 获取系统user 账号信息  如果被绑定过  提示已被绑定
        SysUserDto user = sysUserService.getByUnionId(unionId);
        String redirectUrl = (String) map.get("frontUrl");
        if (user == null) {
            try {

                return new ModelAndView("redirect:" + redirectUrl + "/home/person?current=2&type=wx&unionid=" + unionId
                        + "&openid=" + openid + "&headimgurl=" + headImgUrl + "&nickname=" + URLEncoder.encode(nickname, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("redirect:" + redirectUrl + "/home/person?current=2&type=wx&msg=error");
    }


    @GetMapping("qqLoginCode")
    @ApiOperation(value = "生成QQ登录授权页面")
    public ModelAndView qqLoginCode() {
        ConfigApiDto dto = bindApiUtils.getData("QQ_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = (String) map.get("url");

        AuthRequest authRequest = new AuthQqRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        String authorizeUrl = authRequest.authorize("QQ@castle");
        return new ModelAndView("redirect:" + authorizeUrl);
    }

    @GetMapping("qqCallBack")
    @ApiOperation(value = "qq登录回调")
    public ModelAndView qqCallBack(AuthCallback callback, HttpServletRequest request) {
        ConfigApiDto dto = bindApiUtils.getData("QQ_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String url = (String) map.get("url");
        String redirectUrl = (String) map.get("frontUrl");


        AuthRequest authRequest = new AuthQqRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(url)
                .build());

        AuthResponse authResponse = authRequest.login(callback);
        String data = JSON.toJSONString(authResponse.getData());
        String openId = JSONUtil.parseObj(data).getJSONObject("token").getStr("openId");
        String avatar = JSONUtil.parseObj(data).getStr("avatar");
        String nickname = JSONUtil.parseObj(data).getStr("nickname");


        // qq openId 获取对应user账号信息  若为空则跳转绑定页面
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("qq_openid",openId);
        SysUser user = sysUserService.getOne(wrapper);
        if (user == null) {
            try{

                return new ModelAndView("redirect:"+redirectUrl + "/binding-wechat?type=qq&unionid=" + openId
                        + "&openid=" + openId + "&headimgurl=" + URLEncoder.encode(avatar, "UTF-8")
                        + "&nickname=" + URLEncoder.encode(nickname, "UTF-8"));
            }catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        //保存登录日志
        saveLoginLog(data,"00","登录成功",user.getId(), LoginMethodEnum.QQ.getCode());
        // 已绑定 直接登录
        return thirdLogin(user.getLoginName(), redirectUrl);
    }


    @PostMapping("bindingQqLogin")
    @ApiOperation(value = "QQ绑定并登录")
    @ResponseBody
    public RespBody<Map>  bindingQqLogin(HttpServletRequest request, @RequestBody LoginUser login) {

        // 验证验证码
        String smsCode = login.getSmscode();
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", login.getPhone());
        params.put("status", LogSmsStatusEnum.VALID.getCode());
        params.put("type", LogSmsTypeEnum.LOGIN.getCode());
        List<LogSmsDto> list = logSmsService.getDataList(params);
        if (list.size() > 0) {
            LogSmsDto logSmsDto = list.get(0);
            if (!smsCode.equals(logSmsDto.getParams1())) {
                return RespBody.fail("验证码错误");
            } else {
                // 验证成功 将验证码改为失效
                logSmsDto.setStatus(LogSmsStatusEnum.INVALID.getCode());
                logSmsService.updateById(ConvertUtil.transformObj(logSmsDto, LogSmsEntity.class));
            }
        } else {
            return RespBody.fail("验证码错误");
        }

        SysUser user = null;
        boolean isAdmin = false;
        List<SysUser> users = sysUserService.queryByLoginName(login.getPhone());
        if (users == null || users.size() != 1) {
            throw new BizException(BizErrorCode.PHONE_NO_EXIST_ERROR);
        }
        user = users.get(0);

        // 判断是否传输 QQ的openid
        if (StrUtil.isEmpty(login.getOpenid())) {
            throw new BizException(BizErrorCode.QQ_NO_OPENID_ERROR);
        }
        // 判断账号是否已绑定过QQ
        if (StrUtil.isNotEmpty(user.getQqOpenid())) {
            throw new BizException(BizErrorCode.WX_BIND_ERROR);
        }
        // 赋予角色
        List<SysRole> roleList = sysRoleService.queryListByUser(user.getId());
        user.setRoles(roleList);
        // 赋予角色
        List<Long> roleIds = new ArrayList<>();
        for (SysRole role : roleList) {
            roleIds.add(role.getId());
            if (role.getIsAdmin().equals(YesNoEnum.YES.getCode())) {
                isAdmin = true;
            }
        }
        if (!isAdmin) {
            //数据权限部门
            List<Long> authDept = dataAuthService.getAuthDeptsList(user.getId(), null);
            if (user.getPostId() != null) {
                //下级岗位
                List<Long> subPost = dataAuthService.getSubPostList(user.getPostId());
                user.setSubPost(subPost);
            }
            //无职位限制 则部门内不限
            if (user.getPostId() == null || DataPermissionPostEnum.NO_LIMIT.getCode().equals(user.getPostDataAuth())) {
                authDept = dataAuthService.getAuthDeptsList(user.getId(), user.getDeptId());
            }
            user.setAuthDept(authDept);
        }



        // 账号绑定unionId openid
        SysUser updateUser = new SysUser();
        updateUser.setId(user.getId());
        updateUser.setQqOpenid(login.getOpenid());
        updateUser.setQqName(login.getNickname());
        updateUser.setQqAvatar(login.getAvatar());
        updateUser.setQqBindTime(new Date());
        sysUserService.updateById(updateUser);

        //保存登录日志
        saveLoginLog(JSONUtil.toJsonStr(login),"00","登录成功",user.getId(), LoginMethodEnum.QQ.getCode());

        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(sysUserToCastleUser(user), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(sysUserToCastleUser(user), refreshTokenExpiration);
        Map<String, Object> map = new HashMap<>();
        map.put("token", authToken);
        map.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        map.put("refreshToken", refreshToken);
        RespBody<Map> respBody = RespBody.data(map);
        return respBody;

    }

    @GetMapping("qqBindCode")
    @ApiOperation(value = "生成QQ绑定授权页面")
    public ModelAndView qqBindCode() {
        ConfigApiDto dto = bindApiUtils.getData("QQ_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String redirectUri = (String) map.get("bindUrl");//此处是已登录 绑定回调 bindUrl

        AuthRequest authRequest = new AuthQqRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        String authorizeUrl = authRequest.authorize("QQ@castle");
        return new ModelAndView("redirect:" + authorizeUrl);
    }

    @GetMapping("qqBindCallBack")
    @ApiOperation(value = "qq绑定回调")
    public ModelAndView qqBindCallBack(AuthCallback callback, HttpServletRequest request) {
        ConfigApiDto dto = bindApiUtils.getData("QQ_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("appid");
        String AppSecret = (String) map.get("secret");
        String url = (String) map.get("bindUrl");
        String redirectUrl = (String) map.get("frontUrl");


        AuthRequest authRequest = new AuthQqRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(url)
                .build());

        AuthResponse authResponse = authRequest.login(callback);
        String data = JSON.toJSONString(authResponse.getData());
        String openId = JSONUtil.parseObj(data).getJSONObject("token").getStr("openId");
        String avatar = JSONUtil.parseObj(data).getStr("avatar");
        String nickname = JSONUtil.parseObj(data).getStr("nickname");


        // qq openId 获取对应user账号信息  如果被绑定过  提示已被绑定
        QueryWrapper<SysUser> wrapper = new QueryWrapper();
        wrapper.eq("qq_openid",openId);
        SysUser user = sysUserService.getOne(wrapper);
        if (user == null) {
            try{

                return new ModelAndView("redirect:" + redirectUrl + "/home/person?current=2&type=qq&unionid=" + openId
                        + "&openid=" + openId + "&headimgurl=" + URLEncoder.encode(avatar, "UTF-8")  + "&nickname=" + URLEncoder.encode(nickname, "UTF-8"));
            }catch(UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        // 已绑定 直接登录
        return new ModelAndView("redirect:" + redirectUrl + "/home/person?current=2&type=qq&msg=error");
    }

    @GetMapping("entWetchatLogin")
    @ApiOperation(value = "企业微信扫码登录")
    public ModelAndView entWetchatLogin(HttpServletRequest request, RedirectAttributes attr, @RequestParam Map<String, Object> params) {

        //获取配置的企微信息
        ConfigApiDto dto = bindApiUtils.getData("SELF_WECOM");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String corpId = (String) map.get("clientId");
        String corpSecret = (String) map.get("agentSecret");
        String redirectUrl = (String) map.get("frontUrl");

        //state 固定为entWechat@castle 不一致不允许登录
        String state = params.get("state")==null?null:params.get("state").toString();
        if (!"entWechat@castle".equals(state)) {
            return new ModelAndView("redirect:" + redirectUrl);
        }
        //获取url内返回的code 没有code不允许登录
        String code = params.get("code")==null?null:params.get("code").toString();
        if (StrUtil.isEmpty(code)) {
            return new ModelAndView("redirect:" + redirectUrl);
        }
        //获取 access_token
        String resultToken = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId +
                "&corpsecret=" + corpSecret).execute().body();
        JSONObject json = JSONUtil.parseObj(resultToken);
        String accessToken = json.getStr("access_token");
        //如果token不存在
        if (StrUtil.isEmpty(accessToken)) {
            return new ModelAndView("redirect:" + redirectUrl);
        }

        //获取user_info
        String userInfoResult = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="
                        + accessToken + "&code=" + code)
                .execute().body();
        JSONObject userInfoResultJson = JSONUtil.parseObj(userInfoResult);
        if (userInfoResultJson == null || userInfoResultJson.get("UserId") == null) {
            return new ModelAndView("redirect:" + redirectUrl );
        }
        String userId = userInfoResultJson.get("UserId").toString();

        // 根据企微userId  获取系统对应的user信息
        SysUser sysUser = sysUserService.getByWeComUserId(userId);
        if (sysUser==null) {
            return new ModelAndView("redirect:" + redirectUrl );
        }
        //保存登录日志
        saveLoginLog(params.toString(),"00","登录成功",sysUser.getId(), LoginMethodEnum.WX_WORK.getCode());
        return thirdLogin(sysUser.getLoginName(), redirectUrl);

    }


    @GetMapping("dingLoginCode")
    @ApiOperation(value = "生成钉钉登录二维码网页")
    public ModelAndView dingDingCode() {

        ConfigApiDto dto = bindApiUtils.getData("DING_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String AppID = (String) map.get("loginAppId");
        String AppSecret = (String) map.get("loginAppSecret");
        String redirectUri = (String) map.get("backUrl");

        AuthRequest authRequest = new AuthDingTalkRequest(AuthConfig.builder()
                .clientId(AppID)
                .clientSecret(AppSecret)
                .redirectUri(redirectUri)
                .build());
        String authorizeUrl = authRequest.authorize("dingDing@castle");

        return new ModelAndView("redirect:" + authorizeUrl);

    }

    @GetMapping("dingQrCodeLogin")
    @ApiOperation(value = "钉钉扫码登录")
    public ModelAndView dingDingLogin(HttpServletRequest request, RedirectAttributes attr, @RequestParam Map<String, Object> params) {

        // 获取配置的参数
        ConfigApiDto dto = bindApiUtils.getData("DING_LOGIN");
        if(dto == null){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        Map<String,Object> map = dto.getParamMap();
        if(map == null || map.isEmpty()){
            throw new BizException(BizErrorCode.CONFIG_ERROR);
        }
        String appKey = (String) map.get("appKey");
        String appSecret = (String) map.get("appSecret");
        String appIdLogin = (String) map.get("loginAppId");
        String appSecretLogin = (String) map.get("loginAppSecret");
        String url = (String) map.get("url");
        String frontUrl = (String) map.get("frontUrl");

        if (!"dingDing@castle".equals(params.get("state"))) {
            return new ModelAndView("redirect:"+frontUrl);
        }
        String code = (String) params.get("code");
        // 根据timestamp, appSecret计算签名值
        String timestamp = System.currentTimeMillis() + "";
        String urlEncodeSignature = "";
        String signature = ""; // 通过appSecret计算出来的签名值
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appSecretLogin.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(timestamp.getBytes("UTF-8"));
            signature = new String(Base64.encodeBase64(signatureBytes));
            urlEncodeSignature = URLEncoder.encode(signature,"UTF-8");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 获取access_token
        String accessToken = HttpRequest.get("https://oapi.dingtalk.com/gettoken?appkey=" + appKey + "&appsecret=" + appSecret)
                .execute().body();
        JSONObject json = JSONUtil.parseObj(accessToken);
        accessToken = json.getStr("access_token");
        JSONObject jsonObject = JSONUtil.createObj().put("tmp_auth_code", code);

        // 获取用户信息
        String result2 = HttpRequest.post("https://oapi.dingtalk.com/sns/getuserinfo_bycode?access_token=" + accessToken
                        + "&accessKey=" + appIdLogin + "&timestamp=" + timestamp + "&signature=" + urlEncodeSignature)
                .body(jsonObject.toString())
                .timeout(20000)
                .execute().body();
        JSONObject userInfo = JSONUtil.parseObj(result2).getJSONObject("user_info");
        String unionId = userInfo.getStr("unionid");
        // 根据unionid获取对应的用户
        // 根据企微userId  获取系统对应的user信息
        SysUser sysUser = sysUserService.getByDingUnionid(unionId);
        if (sysUser==null) {
            return new ModelAndView("redirect:" + frontUrl );
        }
        //保存登录日志
        saveLoginLog(params.toString(),"00","登录成功",sysUser.getId(), LoginMethodEnum.DING.getCode());
        return thirdLogin(sysUser.getLoginName(),frontUrl);
    }

    /**
     * 通用 保存登录日志.
     * @param params 参数
     * @param invokeStatus 状态
     * @param resultData 说明
     * @return
     */
    private LogLoginEntity saveLoginLog(String params, String invokeStatus, String resultData, Long userId, Integer loginMethod){
        //如果不需要保存日志
        if (!loginFlag){
            return  null;
        }
        HttpServletRequest request = WebUtil.request();
        // 获取IP
        String ipAddress = WebUtil.getIpAddress(request);
        // 请求路径
        String uri = request.getServletPath();
        //IP信息
        String ipInfo="";
        //将ip转换为地址
        if(WebUtil.internalIp(ipAddress)){
            ipInfo="内网地址";
        }else{
            Map<String,String> query= new HashMap<>();
            query.put("ip",ipAddress);
            query.put("json","true");
            String rspStr = HttpUtil.doRequest("get","http://whois.pconline.com.cn/ipJson.jsp",new HashMap<>(), query,null);
            if (StrUtil.isNotEmpty(rspStr))
            {
                com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(rspStr);
                //String pro = obj.getString("pro");
                //String city = obj.getString("city");
                //ipInfo= pro+"/"+city;
                String addr = obj.getString("addr");
                ipInfo= addr;

            }
        }
        LogLoginEntity loginLogEntity = new LogLoginEntity(uri,ipAddress,params,invokeStatus,new Date(),resultData,userId,loginMethod);
        // 地址信息
        loginLogEntity.setAddress(ipInfo);
        //获取浏览器信息
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version =browser.getVersion(request.getHeader("User-Agent"));
        loginLogEntity.setCusBrowser(browser.getName()+"/"+version.getVersion());
        //操作系统
        OperatingSystem os = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
        loginLogEntity.setCusOs(os==null?"未知系统":os.getName());

        System.err.println("loginLogEntity:"+loginLogEntity);
        logLoginService.saveLog(loginLogEntity);
        return  loginLogEntity;
    }

}
