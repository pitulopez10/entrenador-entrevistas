package org.example;

public class RetroAlimentacion {

    // ATRIBUTO
    private String texto;

    // RELACIÓN
    private Entrevista entrevista;

    // CONSTRUCTOR VACÍO
    public RetroAlimentacion() {

    }

    // CONSTRUCTOR COMPLETO
    public RetroAlimentacion(String texto, Entrevista entrevista) {
        this.texto = texto;
        this.entrevista = entrevista;
    }

    // GETTERS

    public String getTexto() {
        return texto;
    }

    public Entrevista getEntrevista() {
        return entrevista;
    }

    // SETTERS

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setEntrevista(Entrevista entrevista) {
        this.entrevista = entrevista;
    }
}