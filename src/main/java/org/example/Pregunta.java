package org.example;

public class Pregunta {
    private int id;
    private String texto;
    private TipoPregunta tipo;
    private TipoDificultad dificultad;

    public Pregunta(){

    }

    public Pregunta(TipoDificultad dificultad,
                    int id, String texto, TipoPregunta tipo) {
        this.dificultad = dificultad;
        this.id = id;
        this.texto = texto;
        this.tipo = tipo;
    }

    public TipoDificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(TipoDificultad dificultad) {
        this.dificultad = dificultad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public TipoPregunta getTipo() {
        return tipo;
    }

    public void setTipo(TipoPregunta tipo) {
        this.tipo = tipo;
    }
}
