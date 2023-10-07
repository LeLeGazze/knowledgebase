package com.castle.fortress.admin.utils;

import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.entity.SysRole;
import com.castle.fortress.common.enums.UserTypeEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.CommonUtil;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.regex.Pattern;

/**
 * token 工具类
 * @author castle
 */
public class TokenUtil {

    public static String createToken(CastleUserDetail userDetail, Date expiration){
        // 生成token start
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("id", userDetail.getId());
        claims.put("username", userDetail.getUsername());
        claims.put("nickname",userDetail.getNickname());
        claims.put("avatar", userDetail.getAvatar());
        claims.put("realName",userDetail.getRealName());
        claims.put("birthdate",userDetail.getBirthdate());
        claims.put("gender", userDetail.getGender());
        claims.put("phone",userDetail.getPhone());
        claims.put("email",userDetail.getEmail());
        claims.put("deptId",userDetail.getDeptId());
        claims.put("deptParents",userDetail.getDeptParents());
        claims.put("postId",userDetail.getPostId());
        claims.put("postDataAuth",userDetail.getPostDataAuth());
        claims.put("userType",userDetail.getUserType());
        claims.put("isSuperAdmin",userDetail.getIsSuperAdmin());
        claims.put("deptName",userDetail.getDeptName());
        //角色列表
        StringBuilder roles=new StringBuilder();
        if(userDetail.getRoles()!=null && userDetail.getRoles().size()>0){
            for(SysRole role:userDetail.getRoles()){
                roles.append(role.getId()+","+role.getName()+","+role.getIsAdmin()+";");
            }
        }
        claims.put("roles",roles.toString());
        if(userDetail.getAuthDept()!=null){
            claims.put("authDept",userDetail.getAuthDept());
        }
        if(userDetail.getSubPost()!=null){
            claims.put("subPost",userDetail.getSubPost());
        }
        String token = Jwts.builder()
                .setSubject(userDetail.getId()+"")
                .setClaims(claims)
                .setIssuedAt(now)//签发时间
                .setExpiration(expiration)//过期时间
                .signWith(SignatureAlgorithm.HS512, GlobalConstants.TOKEN_SIGN_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        // 生成token end
        return token;
    }



    public static String createRefreshToken(CastleUserDetail userDetail,Date expiration){
        // 生成token start
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Map<String,Object> claims = new HashMap<String,Object>();//创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        claims.put("id", userDetail.getId());
        claims.put("userType",userDetail.getUserType());
        String token = Jwts.builder()
                .setSubject(userDetail.getId()+"")
                .setClaims(claims)
                .setIssuedAt(now)//签发时间
                .setExpiration(expiration)//过期时间
                .signWith(SignatureAlgorithm.HS512, GlobalConstants.TOKEN_SIGN_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
                .compact();
        // 生成token end
        return token;
    }

    public static CastleUserDetail parseToken(String token) {
        Claims claims= null;
        try {
            claims = Jwts.parser().setSigningKey(GlobalConstants.TOKEN_SIGN_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        if(claims == null){
            return null;
        }
        CastleUserDetail userDetail=null;
        if(UserTypeEnum.SYS_USER.getName().equals(claims.get("userType")) && claims.get("id")!=null){
            userDetail=new CastleUserDetail();
            userDetail.setUserType(UserTypeEnum.SYS_USER.getName());
            userDetail.setId(Long.parseLong(claims.get("id").toString()));
            userDetail.setUsername(CommonUtil.emptyIfNull(claims.get("username")));
            userDetail.setNickname(CommonUtil.emptyIfNull(claims.get("nickname")));
            userDetail.setAvatar(CommonUtil.emptyIfNull(claims.get("avatar")));
            userDetail.setRealName(CommonUtil.emptyIfNull(claims.get("realName")));
            userDetail.setGender(CommonUtil.emptyIfNull(claims.get("gender")));
            userDetail.setPhone(CommonUtil.emptyIfNull(claims.get("phone")));
            userDetail.setEmail(CommonUtil.emptyIfNull(claims.get("email")));
            userDetail.setDeptName(CommonUtil.emptyIfNull(claims.get("deptName")));
            userDetail.setDeptId(claims.get("deptId")==null?null:Long.parseLong(claims.get("deptId").toString()));
            userDetail.setDeptParents(CommonUtil.emptyIfNull(claims.get("deptParents")));
            userDetail.setPostId(claims.get("postId")==null?null:Long.parseLong(claims.get("postId").toString()));
            userDetail.setIsSuperAdmin(claims.get("isSuperAdmin")==null?false:Boolean.parseBoolean(claims.get("isSuperAdmin").toString()));
            userDetail.setPostDataAuth(claims.get("postDataAuth")==null?null:Integer.parseInt(claims.get("postDataAuth").toString()));
            userDetail.setIsAdmin(false);
            List<SysRole> roles=new ArrayList<>();
            //构造用户角色列表
            String roleStr=CommonUtil.emptyIfNull(claims.get("roles"));
            if(Strings.isNotEmpty(roleStr)){
                String[] roleArray=roleStr.split(";");
                for(String roleTemp:roleArray){
                    String[] tempRole=roleTemp.split(",");
                    SysRole sysRole=null;
                    if(tempRole!=null && tempRole.length == 3){
                        sysRole=new SysRole();
                        sysRole.setId(Long.parseLong(tempRole[0]));
                        sysRole.setName(tempRole[1]);
                        Integer isAdmin=Integer.parseInt(tempRole[2]);
                        sysRole.setIsAdmin(isAdmin);
                        roles.add(sysRole);
                        if(YesNoEnum.YES.getCode().equals(isAdmin)){
                            userDetail.setIsAdmin(true);
                        }
                    }
                }
            }
            userDetail.setRoles(roles);
            //不是管理员也不是超管
            if(!userDetail.getIsAdmin() && !userDetail.getIsSuperAdmin()){
                userDetail.setAuthDept(claims.get("authDept")==null?null:(List<Long>)claims.get("authDept"));
                userDetail.setSubPost(claims.get("subPost")==null?null:(List<Long>)claims.get("subPost"));
            }
        }else if(UserTypeEnum.MEMBER.getName().equals(claims.get("userType")) && claims.get("id")!=null){
            userDetail=new CastleUserDetail();
            userDetail.setUserType(UserTypeEnum.MEMBER.getName());
            userDetail.setId(Long.parseLong(claims.get("id").toString()));
            userDetail.setNickname(CommonUtil.emptyIfNull(claims.get("nickname")));
            userDetail.setAvatar(CommonUtil.emptyIfNull(claims.get("avatar")));
            userDetail.setPhone(CommonUtil.emptyIfNull(claims.get("phone")));
        }
        return userDetail;
    }


    /**
     * 校验是否是token白名单
     * @return
     */
    public static boolean isWhiteList(String uri){
        String regexString="";
        for(String whiteUri:GlobalConstants.WHITE_LIST){
            regexString= whiteUri.replaceAll("\\*\\*","(.*)");
            if(Pattern.matches(regexString, uri)){
                return true;
            }
        }
        return false;
    }

}
