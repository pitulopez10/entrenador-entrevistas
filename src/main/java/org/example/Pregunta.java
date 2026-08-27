package org.example;

public class Pregunta {

    // ATRIBUTOS
    private int id;
    private String texto;
    private TipoPregunta tipo;
    private TipoDificultad dificultad;

    // RELACIONES
    private Entrevista entrevista;
    private Respuesta respuesta;

    // CONSTRUCTOR VACÍO
    public Pregunta() {

    }

    // CONSTRUCTOR COMPLETO
    public Pregunta(TipoDificultad dificultad,
                    int id,
                    String texto,
                    TipoPregunta tipo,
                    Entrevista entrevista,
                    Respuesta respuesta) {

        this.dificultad = dificultad;
        this.id = id;
        this.texto = texto;
        this.tipo = tipo;
        this.entrevista = entrevista;
        this.respuesta = respuesta;
    }

    // GETTERS

    public TipoDificultad getDificultad() {
        return dificultad;
    }

    public int getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public TipoPregunta getTipo() {
        return tipo;
    }

    public Entrevista getEntrevista() {
        return entrevista;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    // SETTERS

    public void setDificultad(TipoDificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setTipo(TipoPregunta tipo) {
        this.tipo = tipo;
    }

    public void setEntrevista(Entrevista entrevista) {
        this.entrevista = entrevista;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }
}