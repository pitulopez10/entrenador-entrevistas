package org.example;

public class Respuesta {
    private int id;
    private String texto;
    private float puntajeRespuesta;

    public Respuesta(){

    }

    public Respuesta(int id, float puntajeRespuesta, String texto) {
        this.id = id;
        this.puntajeRespuesta = puntajeRespuesta;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPuntajeRespuesta() {
        return puntajeRespuesta;
    }

    public void setPuntajeRespuesta(float puntajeRespuesta) {
        this.puntajeRespuesta = puntajeRespuesta;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
