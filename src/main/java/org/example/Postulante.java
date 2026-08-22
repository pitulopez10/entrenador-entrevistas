package org.example;

import java.time.LocalDate;

public class Postulante {

    //ATRIBUTOS
    private int ci;
    private String nombre;
    private String mail;
    private String password;
    private LocalDate fechaRegistro;
    private int telefono;
    private String descripcion;
    private String fotoPerfil;
    private String cv;
    private LocalDate fechaNacimiento;
    private DTGenero genero;
    private String localidad;
    private String datosEstudio;
    private String datosExperiencia;

    //CONSTRUCTORES
    public Postulante(){

    }

    public Postulante(int ci, String cv, String datosEstudio, String datosExperiencia,
                      String descripcion, LocalDate fechaNacimiento, LocalDate fechaRegistro,
                      String fotoPerfil, DTGenero genero, String localidad, String mail,
                      String nombre, String password, int telefono) {
        this.ci = ci;
        this.cv = cv;
        this.datosEstudio = datosEstudio;
        this.datosExperiencia = datosExperiencia;
        this.descripcion = descripcion;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaRegistro = fechaRegistro;
        this.fotoPerfil = fotoPerfil;
        this.genero = genero;
        this.localidad = localidad;
        this.mail = mail;
        this.nombre = nombre;
        this.password = password;
        this.telefono = telefono;
    }

    //GETTERS
    public int getCi() {
        return ci;
    }

    public String getCv() {
        return cv;
    }

    public String getDatosEstudio() {
        return datosEstudio;
    }

    public String getDatosExperiencia() {
        return datosExperiencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public DTGenero getGenero() {
        return genero;
    }

    public String getLocalidad() {
        return localidad;
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

    public int getTelefono() {
        return telefono;
    }

    //SETTERS

    public void setCi(int ci) {
        this.ci = ci;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setDatosEstudio(String datosEstudio) {
        this.datosEstudio = datosEstudio;
    }

    public void setDatosExperiencia(String datosExperiencia) {
        this.datosExperiencia = datosExperiencia;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setGenero(DTGenero genero) {
        this.genero = genero;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
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

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
