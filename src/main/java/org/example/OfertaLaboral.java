package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OfertaLaboral {

    // ATRIBUTOS
    private int id;
    private String titulo;
    private String descripcion;
    private String requisitos;
    private LocalDate fechaPublicacion;
    private LocalDate fechaCierre;
    private EstadoEntrevista estado;

    // RELACIONES
    private Empresa empresa;
    private List<Postulacion> postulaciones;
    private Entrevista entrevista;

    // CONSTRUCTOR VACÍO
    public OfertaLaboral() {
        this.postulaciones = new ArrayList<>();
    }

    // CONSTRUCTOR COMPLETO
    public OfertaLaboral(String descripcion,
                         EstadoEntrevista estado,
                         LocalDate fechaCierre,
                         LocalDate fechaPublicacion,
                         int id,
                         String requisitos,
                         String titulo,
                         Empresa empresa,
                         Entrevista entrevista) {

        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCierre = fechaCierre;
        this.fechaPublicacion = fechaPublicacion;
        this.id = id;
        this.requisitos = requisitos;
        this.titulo = titulo;

        this.empresa = empresa;
        this.entrevista = entrevista;
        this.postulaciones = new ArrayList<>();
    }

    // GETTERS

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoEntrevista getEstado() {
        return estado;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public int getId() {
        return id;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public String getTitulo() {
        return titulo;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public Entrevista getEntrevista() {
        return entrevista;
    }

    // SETTERS

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(EstadoEntrevista estado) {
        this.estado = estado;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public void setEntrevista(Entrevista entrevista) {
        this.entrevista = entrevista;
    }
}