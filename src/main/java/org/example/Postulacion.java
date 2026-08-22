package org.example;

import java.time.LocalDate;

public class Postulacion {
    private int id;
    private LocalDate fechaPostulacion;
    private EstadoPostulacion estado;
    private String mensaje;

    public Postulacion(){

    }

    public Postulacion(EstadoPostulacion estado,
                       LocalDate fechaPostulacion, int id, String mensaje) {
        this.estado = estado;
        this.fechaPostulacion = fechaPostulacion;
        this.id = id;
        this.mensaje = mensaje;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
