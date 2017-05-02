package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.Model;

import java.sql.Date;

/**
 * Created by Programmer on 6/3/2017.
 */
public class Jugadas extends Model {
    private int IdJugada;
    private int IdJugadorBanca;
    private int IdBanca;
    private Date fecha;

    public int getIdJugada() {
        return IdJugada;
    }

    public void setIdJugada(int idJugada) {
        IdJugada = idJugada;
    }

    public int getIdJugadorBanca() {
        return IdJugadorBanca;
    }

    public void setIdJugadorBanca(int idJugadorBanca) {
        IdJugadorBanca = idJugadorBanca;
    }

    public int getIdBanca() {
        return IdBanca;
    }

    public void setIdBanca(int idBanca) {
        IdBanca = idBanca;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
