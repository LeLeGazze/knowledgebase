package com.castle.fortress.admin.shiro.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.dto.LogSmsDto;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.entity.LogSmsEntity;
import com.castle.fortress.admin.log.enums.LogSmsStatusEnum;
import com.castle.fortress.admin.log.enums.LogSmsTypeEnum;
import com.castle.fortress.admin.log.service.LogSmsService;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.member.entity.MemberEntity;
import com.castle.fortress.admin.member.entity.MemberLoginLogEntity;
import com.castle.fortress.admin.member.entity.MemberWxEntity;
import com.castle.fortress.admin.member.event.CreateMemberAccountEvent;
import com.castle.fortress.admin.member.service.MemberLoginLogService;
import com.castle.fortress.admin.member.service.MemberService;
import com.castle.fortress.admin.member.service.MemberWxService;
import com.castle.fortress.admin.message.sms.service.SmsService;
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
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import com.castle.fortress.common.utils.ConvertUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 会员登录日志
 *
 * @author castle
 */
@Api(tags = "登录控制器")
@Controller
public class MemberLoginController {
    @Autowired
    private LogSmsService logSmsService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private MemberService membersService;
    @Autowired
    private MemberWxService memberWxService;
    @Autowired
    private BindApiUtils bindApiUtils;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private MemberLoginLogService memberLoginLogService;

    @Value("${castle.logs.memberLogin}")
    private boolean memberLogFlag;

    @ApiOperation("Member通过refreshToken刷新token")
    @PostMapping("/memberRefreshToken")
    @ResponseBody
    public RespBody<Map> memberRefreshToken(HttpServletRequest request) {
        String refreshToken = WebUtil.getRefreshToken(request);
        if (StrUtil.isEmpty(refreshToken)) {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
        CastleUserDetail userDetail = TokenUtil.parseToken(refreshToken);
        if (userDetail != null) {
            MemberEntity memberEntity = membersService.getById(userDetail.getId());

            Date now = new Date();
            Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
            String authToken = TokenUtil.createToken(memberToCastleUser(memberEntity), tokenExpiration);
            Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
            String newRefreshToken = TokenUtil.createRefreshToken(memberToCastleUser(memberEntity), refreshTokenExpiration);
            Long serialNumToken = IdWorker.getId();
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", authToken);
            tokenMap.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
            tokenMap.put("refreshToken", newRefreshToken);
            tokenMap.put("id", memberEntity.getId() + "");
            tokenMap.put("status", "login");
            Map<String, Object> map = new HashMap<>();
            map.put("token", authToken);
            map.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
            map.put("refreshToken", newRefreshToken);
            RespBody<Map> respBody = RespBody.data(map);
            return respBody;
        } else {
            throw new BizException(GlobalRespCode.TOKEN_INVALID_ERROR);
        }
    }

    /**
     * 微信浏览器登录
     *
     * @param code 微信sdk返回code
     * @return
     */
    @PostMapping("/memberLogin/wxLogin")
    @ApiOperation(value = "微信浏览器登录")
    @ResponseBody
    public RespBody<HashMap<String, Object>> wxLogin(@RequestParam String code) {
        final JSONObject config = new JSONObject(bindApiUtils.getData("WX_APP_LOGIN").getBindDetail());
        String appId = config.getStr("appid");
        String appSecret = config.getStr("secret");
        //通过code获取access_code
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("appid", appId);
        queryParams.put("secret", appSecret);
        queryParams.put("code", code);
        queryParams.put("grant_type", "authorization_code");
        String accessCodeData = HttpUtil.doRequest("get", "https://api.weixin.qq.com/sns/oauth2/access_token", new HashMap<>(), queryParams, null);
        JSONObject accessCodeDataJson = new JSONObject(accessCodeData);
        //获取到的token  和 openId
        String accessCode = accessCodeDataJson.getStr("access_token");
        String openId = accessCodeDataJson.getStr("openid");
        //用token 和 openId 获取微信用户信息
        Map<String, String> userInfoQueryParams = new HashMap<>();
        userInfoQueryParams.put("access_token", accessCode);
        userInfoQueryParams.put("openid", openId);
        String userInfoData = HttpUtil.doRequest("get", "https://api.weixin.qq.com/sns/userinfo", new HashMap<>(), userInfoQueryParams, null);
        JSONObject userInfoDataJson = new JSONObject(userInfoData);
        System.err.println("userInfoDataJson:" + userInfoDataJson);
        String unionId = userInfoDataJson.getStr("unionid");
        QueryWrapper<MemberWxEntity> existsWrapper = new QueryWrapper<>();
        existsWrapper.eq("union_id", unionId);
        existsWrapper.eq("open_id", openId);
        existsWrapper.last("limit 1");
        MemberWxEntity memberWx = memberWxService.getOne(existsWrapper);
        //如果存在 微信用户表 查询是否存在关联主表记录
        if (memberWx == null) {
            //生成一条memberWx记录 并返回id给前端 让其跳转到绑定手机号页面
            memberWx = memberWxService.create(userInfoDataJson);
        }
        final HashMap<String, Object> result = new HashMap<>();
        if (memberWx.getMemberId() != null && memberWx.getMemberId() > 0) {
            //存在的话则直接返回token
            MemberEntity member = membersService.getById(memberWx.getMemberId());
            Date now = new Date();
            Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
            String authToken = TokenUtil.createToken(memberToCastleUser(member), tokenExpiration);
            Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
            String refreshToken = TokenUtil.createRefreshToken(memberToCastleUser(member), refreshTokenExpiration);
            result.put("token", authToken);
            result.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
            result.put("refreshToken", refreshToken);
            result.put("id", member.getId());
            result.put("msg", "正常获取token");
            return RespBody.data(result);
        }
        //不存在的话 跳转去绑定手机号页面
        result.put("code", 3000);
        result.put("msg", "需要绑定手机号");
        result.put("unionid", memberWx.getUnionId());
        return RespBody.data(result);
    }


    /**
     * 微信小程序登录
     *
     * @param params
     * @return
     */
    @PostMapping("/memberLogin/wxAppLogin")
    @ApiOperation(value = "微信小程序登录")
    @ResponseBody
    public RespBody<HashMap<String, Object>> wxAppLogin(@RequestBody HashMap<String, String> params) {
        String code = params.get("code");
        final JSONObject config = new JSONObject(bindApiUtils.getData("WX_APP_LOGIN").getBindDetail());
        String appId = config.getStr("appid");
        String appSecret = config.getStr("secret");


        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("appid", appId);
        queryParams.put("secret", appSecret);
        queryParams.put("js_code", code);
        queryParams.put("grant_type", "authorization_code");
        //获取用户凭证
        String proveResponse = HttpUtil.doRequest("get", "https://api.weixin.qq.com/sns/jscode2session", new HashMap<>(), queryParams, null);

        final JSONObject proveJson = new JSONObject(proveResponse);
        final String openId = proveJson.getStr("openid");
        final String sessionKey = proveJson.getStr("session_key");
        final String unionId = proveJson.getStr("unionid");

        if (openId == null || "".equals(openId)) {
            throw new ErrorException(GlobalRespCode.PARAM_MISSED);
        }
        if (unionId == null || "".equals(unionId)) {
            throw new ErrorException(GlobalRespCode.PARAM_MISSED);
        }
        QueryWrapper<MemberWxEntity> existsWrapper = new QueryWrapper<>();
        existsWrapper.eq("union_id", unionId);
        existsWrapper.eq("open_id", openId);
        existsWrapper.last("limit 1");
        MemberWxEntity memberWx = memberWxService.getOne(existsWrapper);
        final JSONObject userInfoDataJson = new JSONObject();
        userInfoDataJson.set("openid", openId);
        userInfoDataJson.set("unionid", unionId);
        userInfoDataJson.set("nickname", params.get("username"));
        userInfoDataJson.set("sex", params.get("sex"));
        userInfoDataJson.set("city", params.get("city"));
        userInfoDataJson.set("province", params.get("province"));
        userInfoDataJson.set("country", params.get("country"));
        userInfoDataJson.set("headimgurl", params.get("avatar"));
        if (memberWx == null) {
            memberWx = memberWxService.create(userInfoDataJson);
        }
        MemberEntity member = null;
        final HashMap<String, Object> result = new HashMap<>();
        if (memberWx.getMemberId() == null || memberWx.getMemberId() <= 0) {
            result.put("code", 3000);
            result.put("msg", "需要绑定手机号");
            result.put("unionid", memberWx.getUnionId());
            return RespBody.data(result);
        }
        member = membersService.getById(memberWx.getMemberId());
        // 保存会员登录日志
        saveMemberLog(JSONUtil.toJsonStr(params),"00","登录成功",member.getId(), LoginMethodEnum.WX.getCode());
        //存在的话则直接返回token
        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(memberToCastleUser(member), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(memberToCastleUser(member), refreshTokenExpiration);
        result.put("token", authToken);
        result.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        result.put("refreshToken", refreshToken);
        result.put("id", member.getId());
        result.put("msg", "正常获取token");
        return RespBody.data(result);
    }

    private String getAccessToken(String appId, String appSecret) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("grant_type", "client_credential");
        queryParams.put("appid", appId);
        queryParams.put("secret", appSecret);
        String accessTokenResponse = HttpUtil.doRequest("get", "https://api.weixin.qq.com/cgi-bin/token", new HashMap<>(), queryParams, null);
        System.err.println("accessTokenResponse:" + accessTokenResponse);
        JSONObject accessTokenJson = new JSONObject(accessTokenResponse);
        return accessTokenJson.getStr("access_token");
    }

    private String getPhoneNumber(String code, String token) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("access_token", token);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("code", code);
        String phoneResponse = HttpUtil.doRequest("post", "https://api.weixin.qq.com/wxa/business/getuserphonenumber", new HashMap<>(), queryParams, bodyMap);
        JSONObject response = new JSONObject(phoneResponse);
        final JSONObject phoneInfo = new JSONObject(response.getStr("phone_info"));
        return phoneInfo.getStr("purePhoneNumber");
    }


    /**
     * 绑定手机号
     *
     * @return
     */
    @PostMapping("/bindPhoneNumber")
    @ApiOperation(value = "微信用户绑定手机号")
    @ResponseBody
    public RespBody<HashMap<String, Object>> bindPhoneNumber(@RequestBody HashMap<String, Object> params) {

        final JSONObject config = new JSONObject(bindApiUtils.getData("WX_APP_LOGIN").getBindDetail());
        String appId = config.getStr("appid");
        String appSecret = config.getStr("secret");

        String unionId = params.get("unionid").toString();
        String code = params.get("code").toString();

        if (unionId == null || "".equals(unionId)) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }

        //获取accessToken
        String accessToken = getAccessToken(appId, appSecret);
        //获取手机号
        String phoneNumber = getPhoneNumber(code, accessToken);

        if (phoneNumber == null || "".equals(phoneNumber)) {
            throw new BizException("授权异常!");
        }
        //首先根据手机号查询 是否存在
        final QueryWrapper<MemberEntity> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("phone", phoneNumber);
        memberQueryWrapper.last("limit 1");
        MemberEntity member = membersService.getOne(memberQueryWrapper);

        final QueryWrapper<MemberWxEntity> memberWxQueryWrapper = new QueryWrapper<>();
        memberWxQueryWrapper.eq("union_id", unionId);
        memberWxQueryWrapper.last("limit 1");
        final MemberWxEntity memberWx = memberWxService.getOne(memberWxQueryWrapper);
        if (memberWx == null) {
            throw new BizException(GlobalRespCode.NO_LOGIN_ERROR);
        }
        if (member != null) {
            //存在的话 查询是否被其他微信用户绑定 没有绑定则 正常绑定 返回token
            final QueryWrapper<MemberWxEntity> bindExistsQueryWrapper = new QueryWrapper<>();
            bindExistsQueryWrapper.eq("member_id", member.getId());
            bindExistsQueryWrapper.last("limit 1");
            MemberWxEntity bindExists = memberWxService.getOne(bindExistsQueryWrapper);
            if (bindExists != null) {
                //存在 且已经被绑定  那就报错
                throw new BizException("该手机号已被绑定!请更换后登录");
            }
        }
        if (member == null) {
            //不存在则新增记录 然后绑定 返回token
            member = new MemberEntity();
            member.setPhone(phoneNumber);
            member.setUserName(phoneNumber);
            member.setNickName(memberWx.getNickName());
            member.setAvatar(memberWx.getHeadImgUrl());
            member.setUnionid(memberWx.getUnionId());
            member.setOpenid(memberWx.getOpenId());
            membersService.save(member);
            memberWx.setMemberId(member.getId());
            memberWxService.updateById(memberWx);

            publisher.publishEvent(new CreateMemberAccountEvent(this,member));

        }

        // 保存会员登录日志
        saveMemberLog(JSONUtil.toJsonStr(params),"00","登录成功",member.getId(), LoginMethodEnum.WX.getCode());
        //走到这里代表可以正常绑定
        final HashMap<String, Object> result = new HashMap<>();
        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(memberToCastleUser(member), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(memberToCastleUser(member), refreshTokenExpiration);
        result.put("token", authToken);
        result.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        result.put("refreshToken", refreshToken);
        result.put("id", member.getId());
        result.put("msg", "正常获取token");
        return RespBody.data(result);
    }


    /**
     * 会员验证码注册或登录
     *
     * @param membersDto 会员信息
     * @return
     */
    @ApiOperation("会员验证码注册或登录")
    @PostMapping("/memberLogin/codeLogin")
    @ResponseBody
    public RespBody<Map> register(@RequestBody MemberDto membersDto) {
        if (membersDto == null) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        // 验证验证码
        String smsCode = membersDto.getSmsCode();
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", membersDto.getPhone());
        params.put("status", LogSmsStatusEnum.VALID.getCode());
        params.put("type", LogSmsTypeEnum.LOGIN.getCode());
        System.err.println(params);
        List<LogSmsDto> list = logSmsService.getDataList(params);
        if (list.size() > 0) {
            LogSmsDto logSmsDto = list.get(0);
            if (!smsCode.equals(logSmsDto.getParams1())) {
                // 保存会员登录日志
                saveMemberLog(JSONUtil.toJsonStr(membersDto),"01","验证码错误",null,LoginMethodEnum.CODE.getCode());
                return RespBody.fail("验证码错误");
            } else {
                // 验证成功 将验证码改为失效
                logSmsDto.setStatus(LogSmsStatusEnum.INVALID.getCode());
                logSmsService.updateById(ConvertUtil.transformObj(logSmsDto, LogSmsEntity.class));
            }
        } else {
            // 保存会员登录日志
            saveMemberLog(JSONUtil.toJsonStr(membersDto),"01","验证码错误",null,LoginMethodEnum.CODE.getCode());
            return RespBody.fail("验证码错误");
        }
        MemberEntity membersEntity = ConvertUtil.transformObj(membersDto, MemberEntity.class);
        MemberDto membersDto1 = membersService.getByPhone(membersDto.getPhone());
        if (null != membersDto1) {
            membersEntity = ConvertUtil.transformObj(membersDto1, MemberEntity.class);
        } else {
            // 用户首次登录
            membersEntity.setNickName(getNickName(8));
            //将手机号作为用户名
            membersEntity.setUserName(membersEntity.getPhone());
            membersEntity.setAvatar("http://app.qinglh.com/image/upload/header.png");
            membersService.save(membersEntity);
            publisher.publishEvent(new CreateMemberAccountEvent(this,membersEntity));
        }
        // 保存会员登录日志
        saveMemberLog(JSONUtil.toJsonStr(membersDto),"00","登录成功",membersEntity.getId(),LoginMethodEnum.CODE.getCode());
        Date now = new Date();
        Date tokenExpiration = DateUtil.offsetMinute(now, GlobalConstants.TOKEN_EXPIRATION_MINUTE);
        String authToken = TokenUtil.createToken(memberToCastleUser(membersEntity), tokenExpiration);
        Date refreshTokenExpiration = DateUtil.offsetHour(now, GlobalConstants.REFRESH_TOKEN_EXPIRATION_HOUR);
        String refreshToken = TokenUtil.createRefreshToken(memberToCastleUser(membersEntity), refreshTokenExpiration);

        Map<String, Object> map = new HashMap<>();
        map.put("token", authToken);
        map.put("expiredTime", DateUtil.format(tokenExpiration, "yyyy-MM-dd HH:mm:ss"));
        map.put("refreshToken", refreshToken);
        map.put("id", membersEntity.getId());
        RespBody<Map> respBody = RespBody.data(map);
        return respBody;

    }

    /**
     * 通用 会员登录日志.
     * @param params 参数
     * @param invokeStatus 状态
     * @param resultData 说明
     * @param memberId 会员ID
     * @param loginMethod 登录方式
     * @return
     */
    private MemberLoginLogEntity saveMemberLog(String params,String invokeStatus,String resultData,Long memberId,Integer loginMethod){
        if (!memberLogFlag){
            return null;
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
        MemberLoginLogEntity memberLoginLogEntity = new MemberLoginLogEntity(uri,ipAddress,params,invokeStatus,new Date(),resultData,memberId,loginMethod);
        // 地址信息
        memberLoginLogEntity.setAddress(ipInfo);
        //获取浏览器信息
        Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
        Version version =browser.getVersion(request.getHeader("User-Agent"));
        memberLoginLogEntity.setCusBrowser(browser.getName()+"/"+version.getVersion());
        //操作系统
        OperatingSystem os = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
        memberLoginLogEntity.setCusOs(os==null?"未知系统":os.getName());

        System.err.println("memberLoginLogEntity:"+memberLoginLogEntity);
        memberLoginLogService.saveLog(memberLoginLogEntity);
        return  memberLoginLogEntity;
    }

    @PostMapping("/api/sendCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, paramType = "body", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "短信类型 0注册验证码 1登录验证码", required = true, paramType = "body", dataType = "String")
    })
    @ApiOperation("发送短信验证码")
    @ResponseBody
    public RespBody send(@ApiIgnore @RequestBody Map<String, String> map) {
        String mobile = map.get("mobile");
        if (map == null || StrUtil.isEmpty(mobile) || StrUtil.isEmpty(map.get("type"))) {
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Integer type = Integer.valueOf(map.get("type"));
        // 6位随机验证码
        String code = String.valueOf((int) (Math.random() * 1000000));

        // 阿里云短信参数为json格式  腾讯云为逗号隔开字符串
//        String params = "{ 1 : \""+code+"\" , 2: \"5\" }";
        // 短信编码 表中配置
        String smsCode = null;
        RespBody result = null;
        if (type == LogSmsTypeEnum.LOGIN.getCode()) {
            // 登录验证码  短信编码  暂用  1001
            smsCode = "1001";
//            String params = code+",5";
            String params = "{\"code\":" + code + "}";
            result = smsService.send("1001", mobile, params);
        } else if (type == LogSmsTypeEnum.RES.getCode()) {
            // 注册验证码   暂时不使用
//            smsCode = "1001";
//            result = smsService.send("1001", mobile , params);
        } else {
            return RespBody.fail("短信类型错误");
        }

        if (result.getCode() == GlobalRespCode.SUCCESS.getCode()) {
            // 若当前已有生效短信 改为不生效
            Map<String, Object> smsParams = new HashMap<>();
            smsParams.put("mobile", mobile);
            smsParams.put("status", LogSmsStatusEnum.VALID.getCode());
            smsParams.put("type", type);
            List<LogSmsDto> list = logSmsService.getDataList(smsParams);
            if (list.size() > 0) {
                list.forEach(e -> e.setStatus(LogSmsStatusEnum.INVALID.getCode()));
                logSmsService.updateBatchById(ConvertUtil.transformObjList(list, LogSmsEntity.class));
            }
            // 短信发送成功 保存记录
            LogSmsEntity logSmsEntity = new LogSmsEntity();
            logSmsEntity.setSmsCode(smsCode);
            logSmsEntity.setMobile(mobile);
            logSmsEntity.setType(type);
            logSmsEntity.setCreateDate(new Date());
            logSmsEntity.setParams1(code);
            logSmsEntity.setStatus(LogSmsStatusEnum.VALID.getCode());
            logSmsService.save(logSmsEntity);
        }

        return result;
    }



    //生成随机数字和字母昵称
    private String getNickName(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(String.valueOf(random.nextInt(10)));
            }
        }
        return "用户" + val.toString();
    }


    /**
     * 会员转化为CastleUserDetail类型
     *
     * @param membersEntity
     * @return
     */
    private CastleUserDetail memberToCastleUser(MemberEntity membersEntity) {
        CastleUserDetail userDetail = null;
        if (membersEntity != null) {
            userDetail = new CastleUserDetail();
            userDetail.setId(membersEntity.getId());
            userDetail.setUsername(membersEntity.getPhone());
            userDetail.setPhone(membersEntity.getPhone());
            userDetail.setNickname(membersEntity.getNickName());
            userDetail.setAvatar(membersEntity.getAvatar());
            userDetail.setUserType(UserTypeEnum.MEMBER.getName());
        }
        return userDetail;
    }



}
