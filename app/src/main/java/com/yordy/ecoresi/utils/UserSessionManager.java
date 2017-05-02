package com.yordy.ecoresi.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.yordy.ecoresi.BuildConfig;
import com.yordy.ecoresi.api.modelos.user.MyUser;

public class UserSessionManager {
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ESTADO = "ESTADO";
    private static final String KEY_FOTO = "FOTO";
    private static final String KEY_ID = "id_user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_SEGUIDORES = "SEGUIDORES";
    private static final String KEY_SIGUIENDO = "siguiendo";
    private static final String KEY_USERNAME = "userNAME";
    private static final String KEY_CURRENT_BANCA_ID = "currentBancaId";
    private static final String PREFER_NAME = "SesionPref";
    private MyUser currentUser;
    int PRIVATE_MODE;
    Context _context;
    Editor editor;
    SharedPreferences pref;

    public UserSessionManager(Context context) {
        this.PRIVATE_MODE = 0;
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREFER_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public void logoutUser() {
        this.editor.clear();
        this.editor.apply();
        this.editor.commit();
    }

    public boolean isUserLoggedIn() {
        return this.pref.getBoolean(IS_USER_LOGIN, false);
    }

    public void setUserLoggedIn(boolean remember) {
        this.editor.putBoolean(IS_USER_LOGIN, remember);
        this.editor.apply();
        this.editor.commit();
    }

    public String getIdUser() {
        return this.pref.getString(KEY_ID, BuildConfig.FLAVOR);
    }

    public void setIdUser(String id) {
        this.editor.putString(KEY_ID, id);
        this.editor.apply();
        this.editor.commit();
    }

    public String getUsername() {
        return this.pref.getString(KEY_USERNAME, BuildConfig.FLAVOR);
    }

    public void setUsername(String username) {
        this.editor.putString(KEY_USERNAME, username);
        this.editor.apply();
        this.editor.commit();
    }
    public void setEmail(String email) {
        this.editor.putString(KEY_EMAIL, email);
        this.editor.apply();
        this.editor.commit();
    }

    public String getEmail() {
        return this.pref.getString(KEY_EMAIL, BuildConfig.FLAVOR);
    }

    public String getPassword() {
        return this.pref.getString(KEY_PASS, BuildConfig.FLAVOR);
    }

    public void setPassword(String password) {
        this.editor.putString(KEY_PASS, password);
        this.editor.apply();
        this.editor.commit();
    }

    public String getSeguidores() {
        return this.pref.getString(KEY_SEGUIDORES, "0");
    }

    public void setSeguidores(String seg) {
        this.editor.putString(KEY_SEGUIDORES, seg);
        this.editor.apply();
        this.editor.commit();
    }

    public String getSiguiendo() {
        return this.pref.getString(KEY_SIGUIENDO, "0");
    }

    public void setSiguiendo(String sig) {
        this.editor.putString(KEY_SIGUIENDO, sig);
        this.editor.apply();
        this.editor.commit();
    }

    public String getEstado() {
        return this.pref.getString(KEY_ESTADO, "Sin estado");
    }

    public void setEstado(String estado) {
        this.editor.putString(KEY_ESTADO, estado);
        this.editor.apply();
        this.editor.commit();
    }

    public String getUrlFoto() {
        return this.pref.getString(KEY_FOTO, "htpp://www.google.co.ve/");
    }

    public void setUrlFoto(String urlFoto) {
        this.editor.putString(KEY_FOTO, urlFoto);
        this.editor.apply();
        this.editor.commit();
    }

    public MyUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(MyUser currentUser) {
        this.currentUser = currentUser;
    }
}
