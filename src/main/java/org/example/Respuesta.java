package org.example;

public class Respuesta {

    // ATRIBUTOS
    private int id;
    private String texto;

    // RELACIÓN
    private Pregunta pregunta;

    // CONSTRUCTOR VACÍO
    public Respuesta() {

    }

    // CONSTRUCTOR COMPLETO
    public Respuesta(int id,
                     String texto,
                     Pregunta pregunta) {

        this.id = id;
        this.texto = texto;
        this.pregunta = pregunta;
    }

    // GETTERS

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    // SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}