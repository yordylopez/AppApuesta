package com.yordy.ecoresi.api.modelos.user;

import com.yordy.ecoresi.loopback.Model;

/**
 * Created by yordy on 05/02/2017.
 */
public class Banca extends Model {
    private int IdBanca;
    private String nombre;
    private String direccion;
    private String telefono;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdBanca() {
        return IdBanca;
    }

    public void setIdBanca(int idBanca) {
        IdBanca = idBanca;
    }
}
