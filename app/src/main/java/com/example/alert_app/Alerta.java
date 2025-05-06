package com.example.alert_app;

public class Alerta {
    private String fecha;
    private String ubicacion;
    private String rutaFoto;

    public Alerta(String fecha, String ubicacion, String rutaFoto) {
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.rutaFoto = rutaFoto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }
}
