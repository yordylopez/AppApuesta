package com.yordy.ecoresi.loopback;

public class AccessToken extends Model {

    private Object userId;
    
    public void setUserId(Object userId) {
        this.userId = userId;
    }
    
    public Object getUserId() { return userId; }
}
