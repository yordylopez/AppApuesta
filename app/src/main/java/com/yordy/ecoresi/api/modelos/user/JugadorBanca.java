package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.Model;

/**
 * Created by yordy on 06/02/2017.
 */
public class JugadorBanca extends Model {
    private int IdJugadorBanca;
    private int IdBanca;
    private int IdJugador;
    private float saldoDisponible;
    private float saldoUsado;
    private float saldoPendiente;
    private String fechaActualizado;

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

    public int getIdJugador() {
        return IdJugador;
    }

    public void setIdJugador(int idJugador) {
        IdJugador = idJugador;
    }

    public float getSaldoDisponible() {
        return saldoDisponible;
    }

    public void setSaldoDisponible(float saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public float getSaldoUsado() {
        return saldoUsado;
    }

    public void setSaldoUsado(float saldoUsado) {
        this.saldoUsado = saldoUsado;
    }

    public float getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(float saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getFechaActualizado() {
        return fechaActualizado;
    }

    public void setFechaActualizado(String fechaActualizado) {
        this.fechaActualizado = fechaActualizado;
    }
}
