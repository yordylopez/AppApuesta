package com.yordy.ecoresi.classes;

public class Post {
    String titulo;
    String descripcion;
    String num_coments;
    int tipo;
    String url_foto;
    String url_image;
    String user;

    public Post(int tipo, String titulo, String descripcion, String user, String num_coments, String url_image, String url_foto) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.user = user;
        this.num_coments = num_coments;
        this.url_image = url_image;
        this.url_foto = url_foto;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumComents() {
        return this.num_coments;
    }

    public void setNumComents(String likes) {
        this.num_coments = likes;
    }

    public String getUrl_image() {
        return this.url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getUrl_foto() {
        return this.url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
