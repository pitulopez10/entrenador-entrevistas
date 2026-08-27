package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Empresa {

    // ATRIBUTOS
    private String rut;
    private String nombre;
    private String mail;
    private String password;
    private LocalDate fechaRegistro;
    private int telefono;
    private String descripcion;
    private String logo;
    private String sitioWeb;

    // RELACIONES
    private List<OfertaLaboral> ofertasLaborales;

    // CONSTRUCTOR VACÍO
    public Empresa() {
        this.ofertasLaborales = new ArrayList<>();
    }

    // CONSTRUCTOR COMPLETO
    public Empresa(String descripcion, LocalDate fechaRegistro, String logo,
                   String mail, String nombre, String password,
                   String rut, String sitioWeb, int telefono) {

        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.logo = logo;
        this.mail = mail;
        this.nombre = nombre;
        this.password = password;
        this.rut = rut;
        this.sitioWeb = sitioWeb;
        this.telefono = telefono;
        this.ofertasLaborales = new ArrayList<>();
    }

    // GETTERS

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
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

    public List<OfertaLaboral> getOfertasLaborales() {
        return ofertasLaborales;
    }

    // SETTERS

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public void setOfertasLaborales(List<OfertaLaboral> ofertasLaborales) {
        this.ofertasLaborales = ofertasLaborales;
    }
}