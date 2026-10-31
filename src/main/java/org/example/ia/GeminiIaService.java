package org.example.ia;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.example.OfertaLaboral;
import org.example.Pregunta;
import org.example.Respuesta;
import org.example.TipoDificultad;
import org.example.TipoPregunta;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class GeminiIaService implements IaService {


    // ==================================================
    // CONFIGURACIÓN
    // ==================================================

    private static final String MODELO_POR_DEFECTO =
            "gemini-3.5-flash-lite";

    private static final int MAX_INTENTOS =
            3;


    private final String apiKey;
    private final String modelo;
    private final HttpClient httpClient;


    // ==================================================
    // CONSTRUCTOR
    // ==================================================

    public GeminiIaService() {

        /*
         * Primero buscamos la variable de entorno.
         *
         * Si en algún momento querés configurarla con
         * -DGEMINI_API_KEY en Tomcat también funciona.
         */
        String key =
                System.getenv(
                        "GEMINI_API_KEY"
                );


        if (key == null || key.isBlank()) {

            key =
                    System.getProperty(
                            "GEMINI_API_KEY"
                    );
        }


        if (key == null || key.isBlank()) {

            throw new IllegalStateException(
                    "No se encontró GEMINI_API_KEY."
            );
        }


        this.apiKey =
                key;


        String modeloConfigurado =
                System.getenv(
                        "GEMINI_MODEL"
                );


        if (modeloConfigurado == null ||
                modeloConfigurado.isBlank()) {

            modeloConfigurado =
                    System.getProperty(
                            "GEMINI_MODEL"
                    );
        }


        if (modeloConfigurado == null ||
                modeloConfigurado.isBlank()) {

            modeloConfigurado =
                    MODELO_POR_DEFECTO;
        }


        this.modelo =
                modeloConfigurado;


        this.httpClient =
                HttpClient.newBuilder()
                        .connectTimeout(
                                Duration.ofSeconds(20)
                        )
                        .build();
    }


    // ==================================================
    // GENERAR 15 PREGUNTAS
    // ==================================================

    @Override
    public List<Pregunta> generarPreguntas(
            OfertaLaboral oferta
    ) throws Exception {


        String prompt =
                construirPromptPreguntas(
                        oferta
                );


        String respuestaGemini =
                generarContenido(
                        prompt,
                        true
                );


        List<Pregunta> preguntas =
                convertirJsonAPreguntas(
                        respuestaGemini
                );


        if (preguntas.size() != 15) {

            throw new IOException(
                    "Gemini debía generar 15 preguntas, pero generó "
                            + preguntas.size()
                            + "."
            );
        }


        return preguntas;
    }


    // ==================================================
    // GENERAR RETROALIMENTACIÓN
    // ==================================================

    @Override
    public String generarRetroalimentacion(
            OfertaLaboral oferta,
            List<Pregunta> preguntas,
            List<Respuesta> respuestas
    ) throws Exception {


        String prompt =
                construirPromptRetroalimentacion(
                        oferta,
                        preguntas,
                        respuestas
                );


        return generarContenido(
                prompt,
                false
        );
    }


    // ==================================================
    // PROMPT PARA GENERAR PREGUNTAS
    // ==================================================

    private String construirPromptPreguntas(
            OfertaLaboral oferta
    ) {


        String titulo =
                textoSeguro(
                        oferta.getTitulo()
                );


        String descripcion =
                textoSeguro(
                        oferta.getDescripcion()
                );


        String requisitos =
                textoSeguro(
                        oferta.getRequisitos()
                );


        String empresa =
                "No especificada";


        if (oferta.getEmpresa() != null &&
                oferta.getEmpresa().getNombre() != null) {

            empresa =
                    oferta.getEmpresa().getNombre();
        }


        String area =
                "No especificada";


        if (oferta.getArea() != null &&
                oferta.getArea().getNombre() != null) {

            area =
                    oferta.getArea().getNombre();
        }


        return """
                Actuá como un entrevistador laboral profesional.

                Tenés que preparar una entrevista práctica para una persona
                que se postula a la siguiente oferta laboral.

                INFORMACIÓN DE LA OFERTA

                Puesto:
                %s

                Empresa:
                %s

                Área:
                %s

                Descripción:
                %s

                Requisitos:
                %s


                OBJETIVO

                Generá EXACTAMENTE 15 preguntas de entrevista.

                Deben distribuirse de esta manera:

                - 5 preguntas TECNICAS
                - 5 preguntas SITUACIONALES
                - 5 preguntas CONDUCTUALES


                REGLAS

                1. Las preguntas deben estar relacionadas con la oferta laboral.

                2. Las preguntas técnicas deben evaluar conocimientos,
                   herramientas, tecnologías o habilidades relevantes para
                   el puesto.

                3. Las preguntas situacionales deben presentar escenarios
                   hipotéticos relacionados con el trabajo.

                4. Las preguntas conductuales deben pedir experiencias o
                   comportamientos anteriores del candidato.

                5. No inventes experiencia laboral del candidato.

                6. No asumas información que no aparece en la oferta.

                7. Las preguntas deben ser claras y diferentes entre sí.

                8. Utilizá español.

                9. No incluyas explicaciones antes ni después del JSON.

                10. Cada pregunta debe tener una dificultad:
                    BAJA, MEDIA o ALTA.

                11. Los valores permitidos para "tipo" son únicamente:

                    TECNICA
                    SITUACIONAL
                    CONDUCTUAL


                RESPONDÉ ÚNICAMENTE CON ESTE FORMATO JSON:

                {
                  "preguntas": [
                    {
                      "texto": "Pregunta...",
                      "tipo": "TECNICA",
                      "dificultad": "MEDIA"
                    }
                  ]
                }

                Recordá:
                el array "preguntas" debe contener EXACTAMENTE 15 elementos.
                """
                .formatted(
                        titulo,
                        empresa,
                        area,
                        descripcion,
                        requisitos
                );
    }


    // ==================================================
    // PROMPT PARA RETROALIMENTACIÓN
    // ==================================================

    private String construirPromptRetroalimentacion(
            OfertaLaboral oferta,
            List<Pregunta> preguntas,
            List<Respuesta> respuestas
    ) {


        StringBuilder prompt =
                new StringBuilder();


        prompt.append("""
                Actuá como un entrevistador laboral profesional.

                Una persona acaba de completar una entrevista práctica
                de 15 preguntas.

                Tu tarea es analizar SUS RESPUESTAS y generar una devolución
                útil para ayudarla a mejorar en futuras entrevistas.

                INFORMACIÓN DE LA OFERTA

                """);


        prompt.append("Puesto: ")
                .append(
                        textoSeguro(
                                oferta.getTitulo()
                        )
                )
                .append("\n");


        if (oferta.getEmpresa() != null) {

            prompt.append("Empresa: ")
                    .append(
                            textoSeguro(
                                    oferta.getEmpresa().getNombre()
                            )
                    )
                    .append("\n");
        }


        if (oferta.getArea() != null) {

            prompt.append("Área: ")
                    .append(
                            textoSeguro(
                                    oferta.getArea().getNombre()
                            )
                    )
                    .append("\n");
        }


        prompt.append("Descripción: ")
                .append(
                        textoSeguro(
                                oferta.getDescripcion()
                        )
                )
                .append("\n");


        prompt.append("Requisitos: ")
                .append(
                        textoSeguro(
                                oferta.getRequisitos()
                        )
                )
                .append("\n\n");


        prompt.append("""
                RESPUESTAS DEL CANDIDATO

                """);


        for (int i = 0;
             i < preguntas.size();
             i++) {


            Pregunta pregunta =
                    preguntas.get(i);


            Respuesta respuesta =
                    buscarRespuesta(
                            pregunta.getId(),
                            respuestas
                    );


            prompt.append("Pregunta ")
                    .append(i + 1)
                    .append(" [")
                    .append(
                            pregunta.getTipo()
                    )
                    .append("]:\n");


            prompt.append(
                    pregunta.getTexto()
            );


            prompt.append("\nRespuesta:\n");


            if (respuesta != null) {

                prompt.append(
                        textoSeguro(
                                respuesta.getTexto()
                        )
                );

            } else {

                prompt.append(
                        "Sin respuesta."
                );
            }


            prompt.append("\n\n");
        }


        prompt.append("""
                CRITERIOS DE EVALUACIÓN

                Analizá especialmente:

                - Pertinencia de la respuesta respecto a la pregunta.
                - Claridad al comunicar ideas.
                - Precisión y nivel de detalle.
                - Uso de ejemplos o evidencias.
                - Capacidad para justificar decisiones.
                - Relación de las respuestas con el puesto.
                - Calidad de las respuestas técnicas.
                - Manejo de situaciones laborales.
                - Comunicación de experiencias personales.


                IMPORTANTE

                - No inventes conocimientos ni experiencias que el candidato
                  no haya mencionado.

                - No determines si la persona debería ser contratada o no.

                - No hagas predicciones de contratación.

                - La finalidad es únicamente educativa y de práctica.

                - Si una respuesta es muy corta, vaga o incorrecta,
                  indicá concretamente cómo podría mejorarla.

                - Utilizá un tono profesional, claro y constructivo.

                - Respondé en español.


                GENERÁ LA DEVOLUCIÓN CON ESTA ESTRUCTURA:


                FORTALEZAS GENERALES

                Explicá los principales puntos fuertes observados.


                ASPECTOS A MEJORAR

                Indicá los principales problemas o debilidades de las
                respuestas y cómo podrían mejorarse.


                ANÁLISIS DE RESPUESTAS DESTACADAS

                Comentá algunas respuestas importantes, tanto buenas
                como mejorables.

                Cuando sea útil, mencioná el número de pregunta.


                RECOMENDACIONES

                Brindá recomendaciones concretas para próximas entrevistas.


                DEVOLUCIÓN GENERAL

                Cerrá con una evaluación general del desempeño durante
                la práctica.

                No uses JSON para esta respuesta.
                Devolvé directamente el texto de la evaluación.
                """);


        return prompt.toString();
    }


    // ==================================================
    // BUSCAR RESPUESTA DE UNA PREGUNTA
    // ==================================================

    private Respuesta buscarRespuesta(
            int preguntaId,
            List<Respuesta> respuestas
    ) {


        for (Respuesta respuesta : respuestas) {

            if (respuesta.getPregunta() != null &&
                    respuesta.getPregunta().getId()
                            == preguntaId) {

                return respuesta;
            }
        }


        return null;
    }


    // ==================================================
    // LLAMAR A GEMINI
    // ==================================================

    private String generarContenido(
            String prompt,
            boolean respuestaJson
    ) throws Exception {


        String endpoint =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + modelo
                        + ":generateContent?key="
                        + apiKey;


        // ==================================================
        // CONTENT
        // ==================================================

        JsonObject parte =
                new JsonObject();

        parte.addProperty(
                "text",
                prompt
        );


        JsonArray parts =
                new JsonArray();

        parts.add(
                parte
        );


        JsonObject content =
                new JsonObject();

        content.add(
                "parts",
                parts
        );


        JsonArray contents =
                new JsonArray();

        contents.add(
                content
        );


        // ==================================================
        // BODY
        // ==================================================

        JsonObject body =
                new JsonObject();

        body.add(
                "contents",
                contents
        );


        // ==================================================
        // CONFIGURACIÓN DE GENERACIÓN
        // ==================================================

        JsonObject generationConfig =
                new JsonObject();


        generationConfig.addProperty(
                "temperature",
                respuestaJson
                        ? 0.4
                        : 0.6
        );


        if (respuestaJson) {

            generationConfig.addProperty(
                    "responseMimeType",
                    "application/json"
            );
        }


        body.add(
                "generationConfig",
                generationConfig
        );


        // ==================================================
        // REQUEST
        // ==================================================

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(
                                URI.create(
                                        endpoint
                                )
                        )
                        .timeout(
                                Duration.ofSeconds(90)
                        )
                        .header(
                                "Content-Type",
                                "application/json"
                        )
                        .POST(
                                HttpRequest.BodyPublishers.ofString(
                                        body.toString()
                                )
                        )
                        .build();


        // ==================================================
        // REINTENTOS
        // ==================================================

        for (int intento = 1;
             intento <= MAX_INTENTOS;
             intento++) {


            HttpResponse<String> response =
                    httpClient.send(
                            request,
                            HttpResponse.BodyHandlers.ofString()
                    );


            int codigo =
                    response.statusCode();


            // ==================================================
            // RESPUESTA CORRECTA
            // ==================================================

            if (codigo >= 200 &&
                    codigo < 300) {

                return extraerTexto(
                        response.body()
                );
            }


            // ==================================================
            // ERROR TEMPORAL
            // ==================================================
            //
            // 429 = demasiadas solicitudes
            // 503 = servicio temporalmente saturado
            // ==================================================

            if ((codigo == 429 ||
                    codigo == 503) &&
                    intento < MAX_INTENTOS) {


                long espera =
                        intento * 1500L;


                Thread.sleep(
                        espera
                );


                continue;
            }


            // ==================================================
            // ERROR DEFINITIVO
            // ==================================================

            throw new IOException(
                    "Error al llamar a Gemini. Código: "
                            + codigo
                            + " - Respuesta: "
                            + response.body()
            );
        }


        throw new IOException(
                "No fue posible obtener una respuesta de Gemini."
        );
    }


    // ==================================================
    // EXTRAER TEXTO DE GEMINI
    // ==================================================

    private String extraerTexto(
            String cuerpoRespuesta
    ) throws IOException {


        JsonObject json =
                JsonParser
                        .parseString(
                                cuerpoRespuesta
                        )
                        .getAsJsonObject();


        JsonArray candidates =
                json.getAsJsonArray(
                        "candidates"
                );


        if (candidates == null ||
                candidates.isEmpty()) {

            throw new IOException(
                    "Gemini no devolvió candidatos."
            );
        }


        JsonObject candidate =
                candidates
                        .get(0)
                        .getAsJsonObject();


        JsonObject content =
                candidate.getAsJsonObject(
                        "content"
                );


        if (content == null) {

            throw new IOException(
                    "Gemini no devolvió contenido."
            );
        }


        JsonArray parts =
                content.getAsJsonArray(
                        "parts"
                );


        if (parts == null ||
                parts.isEmpty()) {

            throw new IOException(
                    "Gemini no devolvió texto."
            );
        }


        StringBuilder texto =
                new StringBuilder();


        for (JsonElement elemento : parts) {

            JsonObject part =
                    elemento.getAsJsonObject();


            if (part.has("text")) {

                texto.append(
                        part.get("text")
                                .getAsString()
                );
            }
        }


        if (texto.isEmpty()) {

            throw new IOException(
                    "Gemini devolvió una respuesta vacía."
            );
        }


        return texto.toString().trim();
    }


    // ==================================================
    // CONVERTIR JSON EN PREGUNTAS
    // ==================================================

    private List<Pregunta> convertirJsonAPreguntas(
            String jsonTexto
    ) throws IOException {


        List<Pregunta> preguntas =
                new ArrayList<>();


        try {

            JsonObject raiz =
                    JsonParser
                            .parseString(
                                    jsonTexto
                            )
                            .getAsJsonObject();


            JsonArray preguntasJson =
                    raiz.getAsJsonArray(
                            "preguntas"
                    );


            if (preguntasJson == null) {

                throw new IOException(
                        "La respuesta de Gemini no contiene 'preguntas'."
                );
            }


            for (JsonElement elemento :
                    preguntasJson) {


                JsonObject preguntaJson =
                        elemento.getAsJsonObject();


                String texto =
                        preguntaJson
                                .get("texto")
                                .getAsString();


                String tipoTexto =
                        preguntaJson
                                .get("tipo")
                                .getAsString()
                                .trim()
                                .toUpperCase();


                String dificultadTexto =
                        preguntaJson
                                .get("dificultad")
                                .getAsString()
                                .trim()
                                .toUpperCase();


                TipoPregunta tipo =
                        TipoPregunta.valueOf(
                                tipoTexto
                        );


                TipoDificultad dificultad =
                        TipoDificultad.valueOf(
                                dificultadTexto
                        );


                Pregunta pregunta =
                        new Pregunta();


                pregunta.setTexto(
                        texto.trim()
                );


                pregunta.setTipo(
                        tipo
                );


                pregunta.setDificultad(
                        dificultad
                );


                preguntas.add(
                        pregunta
                );
            }


        } catch (IllegalArgumentException e) {

            throw new IOException(
                    "Gemini devolvió un tipo o dificultad inválidos. Respuesta: "
                            + jsonTexto,
                    e
            );


        } catch (Exception e) {

            throw new IOException(
                    "No se pudo interpretar la respuesta JSON de Gemini. Respuesta: "
                            + jsonTexto,
                    e
            );
        }


        return preguntas;
    }


    // ==================================================
    // TEXTO SEGURO
    // ==================================================

    private String textoSeguro(
            String texto
    ) {

        if (texto == null ||
                texto.isBlank()) {

            return "No especificado";
        }


        return texto.trim();
    }
}