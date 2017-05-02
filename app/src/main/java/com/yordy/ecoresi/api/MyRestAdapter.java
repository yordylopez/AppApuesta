package com.yordy.ecoresi.api;

import android.content.Context;

import com.yordy.ecoresi.api.modelos.user.Banca;
import com.yordy.ecoresi.api.modelos.user.JugadorBanca;
import com.yordy.ecoresi.api.modelos.user.MyUser;
import com.yordy.ecoresi.loopback.RestAdapter;
import com.yordy.ecoresi.remoting.adapters.RestContractItem;


/**
 * Created by yordy on 29/01/2017.
 */
public class MyRestAdapter extends RestAdapter {
    public static final int TIMEOUT = 90000;
    public static final String url = "http://192.168.43.215:3000/api";
    private MyUser currentUser;
    private JugadorBanca currentjugadorBanca;
    private Banca currentBanca;

    public MyRestAdapter(Context context){
        super(context,url);
        setTimeout(TIMEOUT);

    }
    static MyRestAdapter adapter;

    public static MyRestAdapter getLoopBackAdapter(Context context) {
        if (adapter == null) {
            adapter = new MyRestAdapter(context);

            adapter.getContract().addItem(
                    new RestContractItem("JugadorBancas/prueba", "POST"),
                    "JugadorBanca.prueba");
            adapter.getContract().addItem(
                    new RestContractItem("Jugadas/jugar", "POST"),
                    "Jugadas.jugar");
        }
        return adapter;
    }


    public void setTimeout(int timeoutMillis){
        getClient().setTimeout(timeoutMillis);
    }

    public MyUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(MyUser currentUser) {
        this.currentUser = currentUser;
    }

    public JugadorBanca getJugadorBanca() {
        return currentjugadorBanca;
    }

    public void setJugadorBanca(JugadorBanca jugadorBanca) {
        this.currentjugadorBanca = jugadorBanca;
    }

    public Banca getCurrentBanca() {
        return currentBanca;
    }

    public void setCurrentBanca(Banca currentBancaId) {
        this.currentBanca = currentBancaId;
    }
}
