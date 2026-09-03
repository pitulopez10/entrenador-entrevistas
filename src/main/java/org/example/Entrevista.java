package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Entrevista {

    // ATRIBUTOS
    private int id;
    private LocalDate fecha;
    private int duracionMin;
    private EstadoEntrevista estado;

    // RELACIONES
    private Postulante postulante;
    private OfertaLaboral ofertaLaboral;
    private List<Pregunta> preguntas;
    private RetroAlimentacion retroAlimentacion;

    // CONSTRUCTOR VACÍO
    public Entrevista() {
        this.preguntas = new ArrayList<>();
    }

    // CONSTRUCTOR COMPLETO
    public Entrevista(int duracionMin,
                      EstadoEntrevista estado,
                      LocalDate fecha,
                      int id,
                      Postulante postulante,
                      OfertaLaboral ofertaLaboral,
                      RetroAlimentacion retroAlimentacion) {

        this.duracionMin = duracionMin;
        this.estado = estado;
        this.fecha = fecha;
        this.id = id;

        this.postulante = postulante;
        this.ofertaLaboral = ofertaLaboral;
        this.retroAlimentacion = retroAlimentacion;

        this.preguntas = new ArrayList<>();
    }

    // GETTERS

    public int getDuracionMin() {
        return duracionMin;
    }

    public EstadoEntrevista getEstado() {
        return estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }

    public Postulante getPostulante() {
        return postulante;
    }

    public OfertaLaboral getOfertaLaboral() {
        return ofertaLaboral;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public RetroAlimentacion getRetroAlimentacion() {
        return retroAlimentacion;
    }

    // SETTERS

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public void setEstado(EstadoEntrevista estado) {
        this.estado = estado;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPostulante(Postulante postulante) {
        this.postulante = postulante;
    }

    public void setOfertaLaboral(OfertaLaboral ofertaLaboral) {
        this.ofertaLaboral = ofertaLaboral;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public void setRetroAlimentacion(RetroAlimentacion retroAlimentacion) {
        this.retroAlimentacion = retroAlimentacion;
    }
}