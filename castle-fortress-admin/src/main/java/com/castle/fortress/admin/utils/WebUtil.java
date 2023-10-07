package com.castle.fortress.admin.utils;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.member.dto.MemberDto;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.common.enums.UserTypeEnum;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * web工具类
 * @author castle
 */
public class WebUtil extends WebUtils {
    /**
     * 获取请求
     * @return
     */
    public static HttpServletRequest request() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null){
            return null;
        }
        return ((ServletRequestAttributes)requestAttributes).getRequest();
    }

    /**
     * 获取请求头指定header的值
     * @param name
     * @return
     */
    public static String header(String name){
        HttpServletRequest request=request();
        if(request == null){
            return null;
        }
        return request.getHeader(name);
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static SysUser currentUser(){
        String token=header(GlobalConstants.TOKEN_HEADER_KEY);
        if(Strings.isEmpty(token)){
            return null;
        }
        CastleUserDetail userDetail= null;
        try {
            userDetail = TokenUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if(userDetail==null){
            return null;
        }
        if(UserTypeEnum.SYS_USER.getName().equals(userDetail.getUserType()) && userDetail.getId()!=null){
            SysUser sysUser=new SysUser();
            sysUser.setId(userDetail.getId());
            sysUser.setLoginName(userDetail.getUsername());
            sysUser.setNickname(userDetail.getNickname());
            sysUser.setAvatar(userDetail.getAvatar());
            sysUser.setRealName(userDetail.getRealName());
            sysUser.setBirthday(userDetail.getBirthdate());
            sysUser.setGender(userDetail.getGender());
            sysUser.setPhone(userDetail.getPhone());
            sysUser.setEmail(userDetail.getEmail());
            sysUser.setDeptId(userDetail.getDeptId());
            sysUser.setDeptParents(userDetail.getDeptParents());
            sysUser.setPostId(userDetail.getPostId());
            sysUser.setRoles(userDetail.getRoles());
            sysUser.setIsAdmin(userDetail.getIsAdmin());
            sysUser.setIsSuperAdmin(userDetail.getIsSuperAdmin());
            sysUser.setAuthDept(userDetail.getAuthDept());
            sysUser.setSubPost(userDetail.getSubPost());
            sysUser.setPostDataAuth(userDetail.getPostDataAuth());
            sysUser.setDeptName(userDetail.getDeptName());
            return sysUser;
        }
        return null;
    }

    /**
     * 获取当前登录会员
     * @return
     */
    public static MemberDto currentMember(){
        String token=header(GlobalConstants.TOKEN_HEADER_KEY);
        if(Strings.isEmpty(token)){
            return null;
        }
        CastleUserDetail userDetail= null;
        try {
            userDetail = TokenUtil.parseToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if(userDetail==null){
            return null;
        }
        if(UserTypeEnum.MEMBER.getName().equals(userDetail.getUserType()) && userDetail.getId()!=null){
            MemberDto member=new MemberDto();
            member.setId(userDetail.getId());
            member.setNickName(userDetail.getNickname());
            member.setAvatar(userDetail.getAvatar());
            member.setPhone(userDetail.getPhone());
            return member;
        }
        return null;
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    public final static String getIpAddress(HttpServletRequest request)  {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 判断ip是否内网
     * @param ip
     * @return
     */
    public static boolean internalIp(String ip)
    {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr)
    {
        if (addr==null || addr.length < 2)
        {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0)
        {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4)
                {
                    return true;
                }
            case SECTION_5:
                switch (b1)
                {
                    case SECTION_6:
                        return true;
                }
            default:
                return false;
        }
    }

    /**
     * 将IPv4地址转换成字节
     *
     * @param text IPv4地址
     * @return byte 字节
     */
    public static byte[] textToNumericFormatV4(String text)
    {
        if (text.length() == 0)
        {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try
        {
            long l;
            int i;
            switch (elements.length)
            {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L))
                        return null;
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L))
                        return null;
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L))
                        return null;
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i)
                    {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L))
                        return null;
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i)
                    {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L))
                            return null;
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        }
        catch (NumberFormatException e)
        {
            return null;
        }
        return bytes;
    }

    /**
     * 获取请求的刷新token
     */
    public static String getRefreshToken(ServletRequest request){
        HttpServletRequest httpRequest=(HttpServletRequest)request;
        //从header中获取token
        String token = httpRequest.getHeader(GlobalConstants.REFRESH_TOKEN_HEADER_KEY);
        return token;
    }
}
