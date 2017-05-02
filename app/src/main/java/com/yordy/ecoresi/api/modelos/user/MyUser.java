package com.yordy.ecoresi.api.modelos.user;

import android.util.Log;

import com.yordy.ecoresi.dialogs.DialogConfimAnimalito;
import com.yordy.ecoresi.remoting.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yordy on 31/01/2017.
 */
public class MyUser extends com.yordy.ecoresi.loopback.User{
    private int IdJugador;
    private String nombre;
    private String apellido;
    private String cedula;
    private String documentoid;
    private String fecha_nacimiento;
    private int current_banca;
    private Banca bancaSelected = null;
    private List<Map> enviarJugadas = new ArrayList<Map>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDocumentoid() {
        return documentoid;
    }

    public void setDocumentoid(String documentoid) {
        this.documentoid = documentoid;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getIdJugador() {
        return IdJugador;
    }

    public void setIdJugador(int idJugador) {
        IdJugador = idJugador;
    }

    public int getCurrent_banca() {
        return current_banca;
    }

    public void setCurrent_banca(int current_banca) {
        this.current_banca = current_banca;
    }

    public Banca getBancaSelected() {
        return bancaSelected;
    }

    public void setBancaSelected(Banca bancaSelected) {
        this.bancaSelected = bancaSelected;
    }

    public List<Map> getEnviarJugadas() {
        return enviarJugadas;
    }

    public int countJugadas() {
        return enviarJugadas.size();
    }

    public void setEnviarJugadas(List<Map> enviarJugadas) {
        enviarJugadas = enviarJugadas;
    }

    public void jugadasAdd(Map<String, Object> animalito){
        enviarJugadas.add(animalito);
    }
    public void jugadasRemove(int position){
        enviarJugadas.remove(position);
    }

    public boolean existJugada(Map<String, Object> animalito){
        boolean encontro = false;
        for (Map element : enviarJugadas) {
           // Log.e("animalito",element.get("animalito")+" - "+animalito.get("animalito")+" : "+animalito.get("hora")+" - "+element.get("hora"));
            if(element.get(DialogConfimAnimalito.keyAnimalito).equals(animalito.get(DialogConfimAnimalito.keyAnimalito)) && element.get(DialogConfimAnimalito.keyHora).equals(animalito.get(DialogConfimAnimalito.keyHora))){
                Log.e("elemento","ya realizo esta jugada");
                encontro = true;
            }
        }
        return encontro;
    }
}
