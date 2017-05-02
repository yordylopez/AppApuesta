package com.yordy.ecoresi.loopback;


public class AccessTokenRepository extends ModelRepository<AccessToken> {
    public AccessTokenRepository() {
        super("accessToken", AccessToken.class);
    }
}

