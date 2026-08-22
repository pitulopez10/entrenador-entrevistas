package org.example;

import java.time.LocalDate;

public class Entrevista {
    //ATRIBUTOS
    private int id;
    private LocalDate fecha;
    private int duracionMin;
    private float puntajeTotal;
    private EstadoEntrevista estado;

    public Entrevista(){

    }

    public Entrevista(int duracionMin, EstadoEntrevista estado, LocalDate fecha,
                      int id, float puntajeTotal) {
        this.duracionMin = duracionMin;
        this.estado = estado;
        this.fecha = fecha;
        this.id = id;
        this.puntajeTotal = puntajeTotal;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public EstadoEntrevista getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntrevista estado) {
        this.estado = estado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(float puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }
}
