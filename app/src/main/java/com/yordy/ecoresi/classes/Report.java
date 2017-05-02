package com.yordy.ecoresi.classes;

public class Report {
    String descripcion;
    String image;

    public Report(String image, String descripcion) {
        this.image = image;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
