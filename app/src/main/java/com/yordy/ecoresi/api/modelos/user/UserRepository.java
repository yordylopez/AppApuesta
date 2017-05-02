package com.yordy.ecoresi.api.modelos.user;

/**
 * Created by yordy on 31/01/2017.
 */
public class UserRepository extends com.yordy.ecoresi.loopback.UserRepository<MyUser> {
    public interface LoginCallback extends com.yordy.ecoresi.loopback.UserRepository.LoginCallback<MyUser> {

    }
    public UserRepository() {
        super("aptjugador", null, MyUser.class);
    }
}
