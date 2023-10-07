package com.castle.fortress.admin.shiro.diversity;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class SysUserMatcher extends SimpleCredentialsMatcher {


    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken)token;
        char[] pwd = usernamePasswordToken.getPassword();
        Object dbPwd = info.getCredentials();
        return true;
    }
}
