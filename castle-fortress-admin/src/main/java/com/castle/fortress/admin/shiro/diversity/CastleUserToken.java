package com.castle.fortress.admin.shiro.diversity;

import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;


@Setter
public class CastleUserToken implements AuthenticationToken {

    CastleUserDetail castleUserDetail;

    /**
     * 是否记住我
     */
    private boolean rememberMe;

    public CastleUserToken(CastleUserDetail castleUserDetail) {
        this.castleUserDetail = castleUserDetail;
        this.rememberMe=false;
    }

    public CastleUserToken(CastleUserDetail castleUserDetail, boolean rememberMe) {
        this.castleUserDetail = castleUserDetail;
        this.rememberMe = rememberMe;
    }

    public boolean isRememberMe() {
        return this.rememberMe;
    }



    /**
     * 主体
     * @return
     */
    @Override
    public CastleUserDetail getPrincipal() {
        return this.castleUserDetail;
    }

    @Override
    public CastleUserDetail getCredentials() {
        return this.castleUserDetail;
    }
}
