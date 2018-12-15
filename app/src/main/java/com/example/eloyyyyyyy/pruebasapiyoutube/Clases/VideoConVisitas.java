package com.example.eloyyyyyyy.pruebasapiyoutube.Clases;

public class VideoConVisitas {
    private String idVideo;
    private String titulo;
    private String nombreCanal;
    private int visitas;
    private String diaSubida;

    public VideoConVisitas(){

    }

    public VideoConVisitas(String idVideo, String titulo, String nombreCanal, int visitas, String diaSubida) {
        this.idVideo=idVideo;
        this.titulo = titulo;
        this.nombreCanal = nombreCanal;
        this.visitas = visitas;
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

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public String getDiaSubida() {
        return diaSubida;
    }

    public void setDiaSubida(String diaSubida) {
        this.diaSubida = diaSubida;
    }
}
