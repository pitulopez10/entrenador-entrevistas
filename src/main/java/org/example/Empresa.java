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
    private boolean bloqueado;

    // RELACIONES
    private List<OfertaLaboral> ofertasLaborales;

    // CONSTRUCTOR VACÍO
    public Empresa() {
        this.ofertasLaborales = new ArrayList<>();
        this.bloqueado = false;
    }

    // CONSTRUCTOR COMPLETO
    public Empresa(String descripcion,
                   LocalDate fechaRegistro,
                   String logo,
                   String mail,
                   String nombre,
                   String password,
                   String rut,
                   String sitioWeb,
                   int telefono) {

        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
        this.logo = logo;
        this.mail = mail;
        this.nombre = nombre;
        this.password = password;
        this.rut = rut;
        this.sitioWeb = sitioWeb;
        this.telefono = telefono;

        this.bloqueado = false;

        this.ofertasLaborales = new ArrayList<>();
    }

    // GETTERS

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLogo() {
        return logo;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public List<OfertaLaboral> getOfertasLaborales() {
        return ofertasLaborales;
    }

    // SETTERS

    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setOfertasLaborales(List<OfertaLaboral> ofertasLaborales) {
        this.ofertasLaborales = ofertasLaborales;
    }
}