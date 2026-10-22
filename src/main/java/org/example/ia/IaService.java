package org.example.ia;

import org.example.OfertaLaboral;
import org.example.Pregunta;
import org.example.Respuesta;

import java.util.List;

public interface IaService {

    List<Pregunta> generarPreguntas(
            OfertaLaboral oferta
    ) throws Exception;

    String generarRetroalimentacion(
            OfertaLaboral oferta,
            List<Pregunta> preguntas,
            List<Respuesta> respuestas
    ) throws Exception;
}