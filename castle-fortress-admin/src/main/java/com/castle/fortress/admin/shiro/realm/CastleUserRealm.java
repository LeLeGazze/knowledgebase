package com.castle.fortress.admin.shiro.realm;

import com.castle.fortress.admin.shiro.diversity.CastleUserToken;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.system.service.SysMenuService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统用户域
 * @author castle
 */
public class CastleUserRealm extends AuthorizingRealm {
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CastleUserToken;
    }

    /**
     * 用户认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户的输入的账号.
        CastleUserDetail userDetail = (CastleUserDetail) token.getPrincipal();
        // principal参数使用用户Id，方便动态刷新用户权限
        return new SimpleAuthenticationInfo(
                userDetail,
                userDetail,
                getName()
        );
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        CastleUserDetail userDetail =(CastleUserDetail)principalCollection.getPrimaryPrincipal();
        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(sysMenuService.getPermissions(userDetail));
        return info;
    }

}
