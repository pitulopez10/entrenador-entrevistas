package org.example;

public class Area {
    //ATRIBUTOS
    private String nombre;
    private String descripcion;

    //CONSTRUCTORES
    public Area(){

    }
    public Area(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    //GETTERS
    public String getDescripcion() {
        return descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    //SETTERS
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
