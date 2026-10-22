package org.example.ia;

import org.example.OfertaLaboral;
import org.example.Pregunta;
import org.example.Respuesta;
import org.example.TipoDificultad;
import org.example.TipoPregunta;

import java.util.ArrayList;
import java.util.List;

public class DemoIaService implements IaService {

    @Override
    public List<Pregunta> generarPreguntas(
            OfertaLaboral oferta
    ) {

        List<Pregunta> preguntas =
                new ArrayList<>();


        // ==================================================
        // 5 PREGUNTAS TÉCNICAS
        // ==================================================

        preguntas.add(
                crearPregunta(
                        "¿Qué conocimientos técnicos considerás más importantes para desempeñarte en el puesto de "
                                + oferta.getTitulo() + "?",
                        TipoPregunta.TECNICA,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "Según los requisitos de esta oferta, ¿qué herramientas o tecnologías dominás y cómo las utilizarías en el puesto?",
                        TipoPregunta.TECNICA,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Cómo resolverías un problema técnico relacionado con las tareas principales de este puesto?",
                        TipoPregunta.TECNICA,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Cómo comprobarías que una solución técnica implementada funciona correctamente?",
                        TipoPregunta.TECNICA,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Qué harías si durante una tarea encontrás un problema técnico que no sabés resolver?",
                        TipoPregunta.TECNICA,
                        TipoDificultad.MEDIA
                )
        );


        // ==================================================
        // 5 PREGUNTAS SITUACIONALES
        // ==================================================

        preguntas.add(
                crearPregunta(
                        "Imaginá que recibís una tarea urgente y tenés poco tiempo para completarla. ¿Cómo te organizarías?",
                        TipoPregunta.SITUACIONAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Qué harías si un compañero propone una solución diferente a la tuya para resolver un problema?",
                        TipoPregunta.SITUACIONAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "Si cometieras un error que afecta una tarea importante, ¿cómo actuarías?",
                        TipoPregunta.SITUACIONAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Cómo actuarías si recibís instrucciones poco claras sobre una tarea?",
                        TipoPregunta.SITUACIONAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "Si tuvieras varias tareas importantes al mismo tiempo, ¿cómo decidirías cuál realizar primero?",
                        TipoPregunta.SITUACIONAL,
                        TipoDificultad.MEDIA
                )
        );


        // ==================================================
        // 5 PREGUNTAS CONDUCTUALES
        // ==================================================

        preguntas.add(
                crearPregunta(
                        "Contá una situación en la que hayas tenido que trabajar en equipo para alcanzar un objetivo.",
                        TipoPregunta.CONDUCTUAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "Contá una situación en la que hayas tenido que aprender algo nuevo rápidamente.",
                        TipoPregunta.CONDUCTUAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Podés contar una experiencia en la que hayas tenido que resolver un problema difícil?",
                        TipoPregunta.CONDUCTUAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "Contá una situación en la que hayas recibido una crítica o sugerencia. ¿Cómo reaccionaste?",
                        TipoPregunta.CONDUCTUAL,
                        TipoDificultad.MEDIA
                )
        );


        preguntas.add(
                crearPregunta(
                        "¿Podés contar una situación en la que hayas tenido que organizarte para cumplir un objetivo o una fecha límite?",
                        TipoPregunta.CONDUCTUAL,
                        TipoDificultad.MEDIA
                )
        );


        return preguntas;
    }


    @Override
    public String generarRetroalimentacion(
            OfertaLaboral oferta,
            List<Pregunta> preguntas,
            List<Respuesta> respuestas
    ) {

        return """
                FORTALEZAS GENERALES

                Completaste las 15 preguntas de la entrevista práctica.
                Tus respuestas permiten evaluar cómo comunicás tus ideas,
                cómo enfrentás situaciones laborales y cómo explicás tus
                conocimientos relacionados con el puesto.


                ASPECTOS A MEJORAR

                Intentá responder de forma concreta y evitar respuestas
                demasiado generales.

                En las preguntas técnicas, explicá no solo qué harías,
                sino también por qué elegirías esa solución.

                En las preguntas situacionales y conductuales,
                incluí ejemplos reales siempre que sea posible.


                RECOMENDACIONES

                Podés estructurar tus respuestas indicando:

                1. Situación o contexto.
                2. Qué acción realizaste o realizarías.
                3. Por qué tomaste esa decisión.
                4. Qué resultado esperás obtener.


                DEVOLUCIÓN GENERAL

                Esta devolución corresponde al modo demostración.
                Cuando se conecte Gemini, las observaciones serán
                generadas específicamente a partir de las 15 respuestas
                proporcionadas y de la oferta laboral seleccionada.
                """;
    }


    // ==================================================
    // MÉTODO AUXILIAR
    // ==================================================

    private Pregunta crearPregunta(
            String texto,
            TipoPregunta tipo,
            TipoDificultad dificultad
    ) {

        Pregunta pregunta =
                new Pregunta();

        pregunta.setTexto(
                texto
        );

        pregunta.setTipo(
                tipo
        );

        pregunta.setDificultad(
                dificultad
        );

        return pregunta;
    }
}