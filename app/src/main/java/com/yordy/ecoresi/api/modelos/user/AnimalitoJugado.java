package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.Model;

/**
 * Created by yordy on 05/02/2017.
 */
public class AnimalitoJugado extends Model {
    private int IdJugadaAnimalito;
    private int IdJugadorBanca;
    private int IdJugada;
    private String animalito;
    private String hora;
    private float monto;
    private String status;
    private String fecha;

    public int getIdJugadaAnimalito() {
        return IdJugadaAnimalito;
    }

    public void setIdJugadaAnimalito(int idJugadaAnimalito) {
        IdJugadaAnimalito = idJugadaAnimalito;
    }

    public int getIdJugadorBanca() {
        return IdJugadorBanca;
    }

    public void setIdJugadorBanca(int idJugadorBanca) {
        IdJugadorBanca = idJugadorBanca;
    }

    public int getIdJugada() {
        return IdJugada;
    }

    public void setIdJugada(int idJugada) {
        IdJugada = idJugada;
    }

    public String getAnimalito() {
        return animalito;
    }

    public void setAnimalito(String animalito) {
        this.animalito = animalito;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
