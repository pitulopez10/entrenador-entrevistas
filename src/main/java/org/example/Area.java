package org.example;

import java.util.ArrayList;
import java.util.List;

public class Area {

    // ATRIBUTOS
    private int id;
    private String nombre;
    private String descripcion;

    // RELACIONES
    private List<Postulante> postulantes;
    private List<OfertaLaboral> ofertasLaborales;

    // CONSTRUCTOR VACÍO
    public Area() {
        this.postulantes = new ArrayList<>();
        this.ofertasLaborales = new ArrayList<>();
    }

    // CONSTRUCTOR COMPLETO
    public Area(int id, String descripcion, String nombre) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.postulantes = new ArrayList<>();
        this.ofertasLaborales = new ArrayList<>();
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Postulante> getPostulantes() {
        return postulantes;
    }

    public List<OfertaLaboral> getOfertasLaborales() {
        return ofertasLaborales;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPostulantes(List<Postulante> postulantes) {
        this.postulantes = postulantes;
    }

    public void setOfertasLaborales(List<OfertaLaboral> ofertasLaborales) {
        this.ofertasLaborales = ofertasLaborales;
    }

}