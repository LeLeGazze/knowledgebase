package com.castle.fortress.admin.shiro.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.service.LogLoginService;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.shiro.service.DataAuthService;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.system.service.SysMenuService;
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
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 登录
 *
 * @author castle
 */
@Api(tags = "登录控制器")
@Controller
public class LoginController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysRoleService sysRoleService;
    @Autowired
    private DataAuthService dataAuthService;

    @Autowired
    private LogLoginService logLoginService;

    @Value("${castle.logs.login}")
    private boolean loginFlag;
    /**
     * 系统用户登录界面
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping({"/login"})
    public String toLoginPage() {
        return "admin/login/pages-login.html";
    }

    @ApiOperation("系统用户登录接口")
    @PostMapping("/login")
    @ResponseBody
    public RespBody<Map> doLogin(@RequestParam String username, @RequestParam String password) {
        if (CommonUtil.verifyParamNull(username, password)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        SysUser user = null;
        boolean isAdmin = false;
        if (GlobalConstants.SUPER_ADMIN_NAME.equals(username) && GlobalConstants.ROOT_FLAG) {
            user = new SysUser();
            user.setId(-1L);
            user.setLoginName(username);
            user.setPassword(PwdUtil.encode(SecureUtil.md5(GlobalConstants.ROOT_PWD)));
            //校验密码
            if (!PwdUtil.matches(password, user.getPassword())) {
                throw new BizException(BizErrorCode.PWD_ERROR);
            }
            // 赋予角色
            List<SysRole> roleList = new ArrayList<>();
            SysRole sr = new SysRole();
            sr.setName("超级管理员");
            sr.setId(-1L);
            sr.setIsAdmin(YesNoEnum.YES.getCode());
            roleList.add(sr);
            user.setRoles(roleList);
            user.setIsSuperAdmin(true);
            isAdmin = true;
        } else {
            List<SysUser> users = sysUserService.queryByLoginName(username);
            if (users == null || users.size() != 1) {
                throw new BizException(BizErrorCode.PWD_ERROR);
            }
            user = users.get(0);
            if (user.getStatus()==2){
                throw new BizException(BizErrorCode.USER_STATUS_ERROR);
            }
            //校验密码
            if (!PwdUtil.matches(password, user.getPassword())) {
                //保存登录日志
                saveLoginLog(username,"01",BizErrorCode.PWD_ERROR.getMsg(),user.getId(), LoginMethodEnum.PWD.getCode());
                throw new BizException(BizErrorCode.PWD_ERROR);
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
        //保存登录日志
        saveLoginLog(username,"00","登录成功",user.getId(), LoginMethodEnum.PWD.getCode());
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
            userDetail.setDeptName(user.getDeptName());
        }
        return userDetail;
    }

    @ApiOperation("获取当前登录的系统用户信息")
    @GetMapping("/userInfo")
    @ResponseBody
    public RespBody<SysUser> userInfo() {
        SysUser sysUser = WebUtil.currentUser();
        if (sysUser == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        CastleUserDetail userDetail = sysUserToCastleUser(sysUser);
        Set<String> permissions = sysMenuService.getPermissions(userDetail);
        sysUser.setPermission(permissions);
        return RespBody.data(sysUser);
    }

    @ApiOperation("通过refreshToken刷新token")
    @PostMapping("/refreshToken")
    @ResponseBody
    public RespBody<Map> refreshToken(HttpServletRequest request) {
        String refreshToken = WebUtil.getRefreshToken(request);
        if (StrUtil.isEmpty(refreshToken)) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        CastleUserDetail userDetail = TokenUtil.parseToken(refreshToken);
        if (userDetail != null) {
            SysUser user = null;
            boolean isAdmin = false;
            if (userDetail.getId().equals(-1L) && GlobalConstants.ROOT_FLAG) {
                user = new SysUser();
                user.setId(-1L);
                user.setLoginName(GlobalConstants.SUPER_ADMIN_NAME);
                user.setPassword(PwdUtil.encode(SecureUtil.md5(GlobalConstants.ROOT_PWD)));
                // 赋予角色
                List<SysRole> roleList = new ArrayList<>();
                SysRole sr = new SysRole();
                sr.setName("超级管理员");
                sr.setId(-1L);
                sr.setIsAdmin(YesNoEnum.YES.getCode());
                roleList.add(sr);
                user.setRoles(roleList);
                user.setIsSuperAdmin(true);
                isAdmin = true;
            } else {
                user = sysUserService.getByIdExtends(userDetail.getId());
                if (user == null) {
                    throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
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
            String refreshTokenNew = TokenUtil.createRefreshToken(sysUserToCastleUser(user), refreshTokenExpiration);
            Map<String, Object> map = new HashMap<>();
            map.put("token", authToken);
            map.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
            map.put("refreshToken", refreshTokenNew);
            RespBody<Map> respBody = RespBody.data(map);
            return respBody;
        } else {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
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
