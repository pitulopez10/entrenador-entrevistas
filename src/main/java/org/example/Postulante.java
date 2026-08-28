package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Postulante {

    // ATRIBUTOS
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
    private boolean bloqueado;

    // RELACIONES
    private List<Area> areas;
    private List<Postulacion> postulaciones;
    private List<Entrevista> entrevistas;

    // CONSTRUCTOR VACÍO
    public Postulante() {
        this.areas = new ArrayList<>();
        this.postulaciones = new ArrayList<>();
        this.entrevistas = new ArrayList<>();
        this.bloqueado = false;
    }

    // CONSTRUCTOR COMPLETO
    public Postulante(int ci,
                      String cv,
                      String datosEstudio,
                      String datosExperiencia,
                      String descripcion,
                      LocalDate fechaNacimiento,
                      LocalDate fechaRegistro,
                      String fotoPerfil,
                      DTGenero genero,
                      String localidad,
                      String mail,
                      String nombre,
                      String password,
                      int telefono) {

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

        this.bloqueado = false;

        this.areas = new ArrayList<>();
        this.postulaciones = new ArrayList<>();
        this.entrevistas = new ArrayList<>();
    }

    // GETTERS

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

    public boolean isBloqueado() {
        return bloqueado;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<Postulacion> getPostulaciones() {
        return postulaciones;
    }

    public List<Entrevista> getEntrevistas() {
        return entrevistas;
    }

    // SETTERS

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

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public void setPostulaciones(List<Postulacion> postulaciones) {
        this.postulaciones = postulaciones;
    }

    public void setEntrevistas(List<Entrevista> entrevistas) {
        this.entrevistas = entrevistas;
    }
}