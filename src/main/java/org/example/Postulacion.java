package org.example;

import java.time.LocalDate;

public class Postulacion {

    // ATRIBUTOS
    private int id;
    private LocalDate fechaPostulacion;
    private EstadoPostulacion estado;
    private String mensaje;

    // RELACIONES
    private Postulante postulante;
    private OfertaLaboral ofertaLaboral;

    // CONSTRUCTOR VACÍO
    public Postulacion() {

    }

    // CONSTRUCTOR COMPLETO
    public Postulacion(EstadoPostulacion estado,
                       LocalDate fechaPostulacion,
                       int id,
                       String mensaje,
                       Postulante postulante,
                       OfertaLaboral ofertaLaboral) {

        this.estado = estado;
        this.fechaPostulacion = fechaPostulacion;
        this.id = id;
        this.mensaje = mensaje;
        this.postulante = postulante;
        this.ofertaLaboral = ofertaLaboral;
    }

    // GETTERS

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public int getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Postulante getPostulante() {
        return postulante;
    }

    public OfertaLaboral getOfertaLaboral() {
        return ofertaLaboral;
    }

    // SETTERS

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setPostulante(Postulante postulante) {
        this.postulante = postulante;
    }

    public void setOfertaLaboral(OfertaLaboral ofertaLaboral) {
        this.ofertaLaboral = ofertaLaboral;
    }
}