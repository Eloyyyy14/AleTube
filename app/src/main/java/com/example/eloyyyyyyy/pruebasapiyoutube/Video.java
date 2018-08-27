package com.example.eloyyyyyyy.pruebasapiyoutube;

public class Video {
    private String idVideo;
    private String titulo;
    private String nombreCanal;
    private String miniatura;
    private String diaSubida;

    public Video(){

    }

    public Video(String idVideo, String titulo, String nombreCanal, String miniatura, String diaSubida) {
        this.idVideo=idVideo;
        this.titulo = titulo;
        this.nombreCanal = nombreCanal;
        this.miniatura = miniatura;
        this.diaSubida = diaSubida;
    }

    public String getIdVideo() { return idVideo; }

    public void setIdVideo(String idVideo) { this.idVideo = idVideo; }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreCanal() {
        return nombreCanal;
    }

    public void setNombreCanal(String nombreCanal) {
        this.nombreCanal = nombreCanal;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public String getDiaSubida() {
        return diaSubida;
    }

    public void setDiaSubida(String diaSubida) {
        this.diaSubida = diaSubida;
    }
}
