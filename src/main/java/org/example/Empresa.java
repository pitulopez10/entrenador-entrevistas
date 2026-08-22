package org.example;

import java.time.LocalDate;
import java.util.SplittableRandom;

public class Empresa {
    //ATRIBUTOS
    private String rut;
    private String nombre;
    private String mail;
    private String password;
    private LocalDate fechRegistro;
    private int telefono;
    private String descripcion;
    private String logo;
    private String sitioWeb;

    //CONSTRUCTORES
    public Empresa(){

    }

    public Empresa(String descripcion, LocalDate fechRegistro, String logo,
                   String mail, String nombre, String password,
                   String rut, String sitioWeb, int telefono) {
        this.descripcion = descripcion;
        this.fechRegistro = fechRegistro;
        this.logo = logo;
        this.mail = mail;
        this.nombre = nombre;
        this.password = password;
        this.rut = rut;
        this.sitioWeb = sitioWeb;
        this.telefono = telefono;
    }

    //GETTERS

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechRegistro() {
        return fechRegistro;
    }

    public String getLogo() {
        return logo;
    }

    public String getMail() {
        return mail;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public String getRut() {
        return rut;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public int getTelefono() {
        return telefono;
    }

    //SETTERS

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechRegistro(LocalDate fechRegistro) {
        this.fechRegistro = fechRegistro;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
