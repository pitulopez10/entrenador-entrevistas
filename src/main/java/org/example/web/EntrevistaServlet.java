package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.Entrevista;
import org.example.EntrevistaDAO;
import org.example.EstadoEntrevista;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulante;
import org.example.Pregunta;
import org.example.PreguntaDAO;
import org.example.Respuesta;
import org.example.RespuestaDAO;
import org.example.RetroAlimentacion;
import org.example.RetroAlimentacionDAO;

import org.example.ia.GeminiIaService;
import org.example.ia.IaService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@WebServlet("/entrevista")
public class EntrevistaServlet extends HttpServlet {


    // ==================================================
    // DAOS
    // ==================================================

    private final EntrevistaDAO entrevistaDAO =
            new EntrevistaDAO();

    private final OfertaLaboralDAO ofertaLaboralDAO =
            new OfertaLaboralDAO();

    private final PreguntaDAO preguntaDAO =
            new PreguntaDAO();

    private final RespuestaDAO respuestaDAO =
            new RespuestaDAO();

    private final RetroAlimentacionDAO retroAlimentacionDAO =
            new RetroAlimentacionDAO();


    // ==================================================
    // SERVICIO IA
    // ==================================================

    /*
     * Por ahora usamos el modo DEMO.
     *
     * Más adelante cambiaremos esta implementación
     * por GeminiIaService.
     */
    private final IaService iaService =
            new GeminiIaService();


    // ==================================================
    // GET
    // ==================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);


        // ==================================================
        // VALIDAR LOGIN
        // ==================================================

        if (session == null ||
                session.getAttribute("postulanteLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }


        String entrevistaIdTexto =
                request.getParameter("entrevistaId");


        /*
         * Si viene entrevistaId significa que la entrevista
         * ya fue creada.
         */
        if (entrevistaIdTexto != null &&
                !entrevistaIdTexto.isBlank()) {

            mostrarEntrevista(
                    request,
                    response,
                    entrevistaIdTexto
            );

            return;
        }


        /*
         * Si todavía no existe entrevista,
         * esperamos recibir una oferta.
         */
        String ofertaIdTexto =
                request.getParameter("ofertaId");


        if (ofertaIdTexto == null ||
                ofertaIdTexto.isBlank()) {

            response.sendRedirect(
                    request.getContextPath() + "/ofertas"
            );

            return;
        }


        try {

            int ofertaId =
                    Integer.parseInt(
                            ofertaIdTexto
                    );


            OfertaLaboral oferta =
                    ofertaLaboralDAO.buscarPorId(
                            ofertaId
                    );


            if (oferta == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La oferta laboral no existe."
                );

                return;
            }


            request.setAttribute(
                    "oferta",
                    oferta
            );


            request.getRequestDispatcher(
                    "/WEB-INF/views/entrevista-inicio.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la oferta no es válido."
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar la oferta laboral.",
                    e
            );
        }
    }


    // ==================================================
    // POST
    // ==================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8"
        );


        HttpSession session =
                request.getSession(false);


        // ==================================================
        // VALIDAR LOGIN
        // ==================================================

        if (session == null ||
                session.getAttribute("postulanteLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }


        Postulante postulante =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );


        String accion =
                request.getParameter("accion");


        if (accion == null ||
                accion.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "No se indicó una acción."
            );

            return;
        }


        // ==================================================
        // ACCIONES
        // ==================================================

        switch (accion) {

            case "generar":

                generarEntrevista(
                        request,
                        response,
                        postulante
                );

                break;


            case "responder":

                responderPregunta(
                        request,
                        response,
                        postulante
                );

                break;


            default:

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "La acción indicada no es válida."
                );

                break;
        }
    }


    // ==================================================
    // GENERAR ENTREVISTA
    // ==================================================

    private void generarEntrevista(
            HttpServletRequest request,
            HttpServletResponse response,
            Postulante postulante
    ) throws ServletException, IOException {

        String ofertaIdTexto =
                request.getParameter("ofertaId");


        if (ofertaIdTexto == null ||
                ofertaIdTexto.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "No se indicó la oferta laboral."
            );

            return;
        }


        try {

            int ofertaId =
                    Integer.parseInt(
                            ofertaIdTexto
                    );


            // ==================================================
            // BUSCAR OFERTA
            // ==================================================

            OfertaLaboral oferta =
                    ofertaLaboralDAO.buscarPorId(
                            ofertaId
                    );


            if (oferta == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La oferta laboral no existe."
                );

                return;
            }


            // ==================================================
            // CREAR ENTREVISTA
            // ==================================================

            Entrevista entrevista =
                    new Entrevista();


            entrevista.setFecha(
                    LocalDate.now()
            );


            /*
             * Duración estimada de una práctica
             * de 15 preguntas.
             */
            entrevista.setDuracionMin(
                    30
            );


            entrevista.setEstado(
                    EstadoEntrevista.ACTIVA
            );


            entrevista.setPostulante(
                    postulante
            );


            entrevista.setOfertaLaboral(
                    oferta
            );


            // ==================================================
            // GUARDAR ENTREVISTA
            // ==================================================

            int entrevistaId =
                    entrevistaDAO.agregar(
                            entrevista
                    );


            entrevista.setId(
                    entrevistaId
            );


            // ==================================================
            // GENERAR LAS 15 PREGUNTAS
            // ==================================================

            List<Pregunta> preguntas =
                    iaService.generarPreguntas(
                            oferta
                    );


            // ==================================================
            // VALIDAR QUE SEAN 15
            // ==================================================

            if (preguntas == null ||
                    preguntas.size() != 15) {

                entrevistaDAO.terminar(
                        entrevistaId
                );

                throw new ServletException(
                        "El servicio debe generar exactamente 15 preguntas."
                );
            }


            // ==================================================
            // ASOCIAR PREGUNTAS CON ENTREVISTA
            // ==================================================

            for (Pregunta pregunta : preguntas) {

                pregunta.setEntrevista(
                        entrevista
                );
            }


            // ==================================================
            // GUARDAR LAS 15 PREGUNTAS
            // ==================================================

            preguntaDAO.agregarTodas(
                    preguntas,
                    entrevistaId
            );


            entrevista.setPreguntas(
                    preguntas
            );


            // ==================================================
            // POST - REDIRECT - GET
            // ==================================================

            response.sendRedirect(
                    request.getContextPath()
                            + "/entrevista?entrevistaId="
                            + entrevistaId
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la oferta no es válido."
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al crear la entrevista.",
                    e
            );


        } catch (Exception e) {

            throw new ServletException(
                    "Error al generar las preguntas de la entrevista.",
                    e
            );
        }
    }


    // ==================================================
    // RESPONDER PREGUNTA
    // ==================================================

    private void responderPregunta(
            HttpServletRequest request,
            HttpServletResponse response,
            Postulante postulante
    ) throws ServletException, IOException {

        String entrevistaIdTexto =
                request.getParameter("entrevistaId");

        String preguntaIdTexto =
                request.getParameter("preguntaId");

        String textoRespuesta =
                request.getParameter("respuesta");


        // ==================================================
        // VALIDACIONES
        // ==================================================

        if (entrevistaIdTexto == null ||
                entrevistaIdTexto.isBlank() ||
                preguntaIdTexto == null ||
                preguntaIdTexto.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Faltan datos de la entrevista."
            );

            return;
        }


        if (textoRespuesta == null ||
                textoRespuesta.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La respuesta no puede estar vacía."
            );

            return;
        }


        textoRespuesta =
                textoRespuesta.trim();


        if (textoRespuesta.length() > 4000) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "La respuesta supera el máximo permitido."
            );

            return;
        }


        try {

            int entrevistaId =
                    Integer.parseInt(
                            entrevistaIdTexto
                    );


            int preguntaId =
                    Integer.parseInt(
                            preguntaIdTexto
                    );


            // ==================================================
            // BUSCAR ENTREVISTA
            // ==================================================

            Entrevista entrevista =
                    entrevistaDAO.buscarPorId(
                            entrevistaId
                    );


            if (entrevista == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La entrevista no existe."
                );

                return;
            }


            // ==================================================
            // VALIDAR PROPIETARIO
            // ==================================================

            if (entrevista.getPostulante() == null ||
                    entrevista.getPostulante().getCi()
                            != postulante.getCi()) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No tenés permiso para modificar esta entrevista."
                );

                return;
            }


            // ==================================================
            // VALIDAR QUE SIGA ACTIVA
            // ==================================================

            if (entrevista.getEstado()
                    != EstadoEntrevista.ACTIVA) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Esta entrevista ya fue finalizada."
                );

                return;
            }


            // ==================================================
            // CARGAR PREGUNTAS DE ESTA ENTREVISTA
            // ==================================================

            List<Pregunta> preguntas =
                    preguntaDAO.listarPorEntrevista(
                            entrevistaId
                    );


            // ==================================================
            // VALIDAR QUE LA PREGUNTA PERTENEZCA
            // A ESTA ENTREVISTA
            // ==================================================

            boolean preguntaValida =
                    false;


            for (Pregunta pregunta : preguntas) {

                if (pregunta.getId() == preguntaId) {

                    preguntaValida =
                            true;

                    break;
                }
            }


            if (!preguntaValida) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "La pregunta no pertenece a esta entrevista."
                );

                return;
            }


            // ==================================================
            // GUARDAR RESPUESTA
            // ==================================================

            respuestaDAO.guardarOActualizar(
                    textoRespuesta,
                    preguntaId
            );


            // ==================================================
            // CONTAR CUÁNTAS RESPONDIÓ
            // ==================================================

            int cantidadRespondidas =
                    respuestaDAO.contarPorEntrevista(
                            entrevistaId
                    );


            // ==================================================
            // ENTREVISTA COMPLETA
            // ==================================================

            if (cantidadRespondidas >= preguntas.size()) {

                List<Respuesta> respuestas =
                        respuestaDAO.listarPorEntrevista(
                                entrevistaId
                        );


                // ==================================================
                // GENERAR RETROALIMENTACIÓN
                // ==================================================

                String devolucion =
                        iaService.generarRetroalimentacion(
                                entrevista.getOfertaLaboral(),
                                preguntas,
                                respuestas
                        );


                RetroAlimentacion retro =
                        new RetroAlimentacion();


                retro.setTexto(
                        devolucion
                );


                retro.setEntrevista(
                        entrevista
                );


                // ==================================================
                // GUARDAR RETROALIMENTACIÓN
                // ==================================================

                retroAlimentacionDAO.guardarOActualizar(
                        retro
                );


                // ==================================================
                // FINALIZAR ENTREVISTA
                // ==================================================

                entrevistaDAO.terminar(
                        entrevistaId
                );


                // ==================================================
                // REDIRECT AL RESULTADO
                // ==================================================

                response.sendRedirect(
                        request.getContextPath()
                                + "/entrevista?entrevistaId="
                                + entrevistaId
                );


                return;
            }


            // ==================================================
            // TODAVÍA QUEDAN PREGUNTAS
            // ==================================================

            response.sendRedirect(
                    request.getContextPath()
                            + "/entrevista?entrevistaId="
                            + entrevistaId
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Los identificadores recibidos no son válidos."
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al guardar la respuesta.",
                    e
            );


        } catch (Exception e) {

            throw new ServletException(
                    "Error al procesar la entrevista.",
                    e
            );
        }
    }


    // ==================================================
    // MOSTRAR ENTREVISTA
    // ==================================================

    private void mostrarEntrevista(
            HttpServletRequest request,
            HttpServletResponse response,
            String entrevistaIdTexto
    ) throws ServletException, IOException {

        try {

            int entrevistaId =
                    Integer.parseInt(
                            entrevistaIdTexto
                    );


            // ==================================================
            // BUSCAR ENTREVISTA
            // ==================================================

            Entrevista entrevista =
                    entrevistaDAO.buscarPorId(
                            entrevistaId
                    );


            if (entrevista == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La entrevista no existe."
                );

                return;
            }


            // ==================================================
            // VALIDAR PROPIETARIO
            // ==================================================

            HttpSession session =
                    request.getSession(false);


            Postulante postulante =
                    (Postulante) session.getAttribute(
                            "postulanteLogueado"
                    );


            if (entrevista.getPostulante() == null ||
                    entrevista.getPostulante().getCi()
                            != postulante.getCi()) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "No tenés permiso para acceder a esta entrevista."
                );

                return;
            }


            // ==================================================
            // CARGAR PREGUNTAS
            // ==================================================

            List<Pregunta> preguntas =
                    preguntaDAO.listarPorEntrevista(
                            entrevistaId
                    );


            entrevista.setPreguntas(
                    preguntas
            );


            request.setAttribute(
                    "entrevista",
                    entrevista
            );


            request.setAttribute(
                    "preguntas",
                    preguntas
            );


            // ==================================================
            // CARGAR RESPUESTAS YA REALIZADAS
            // ==================================================

            List<Respuesta> respuestas =
                    respuestaDAO.listarPorEntrevista(
                            entrevistaId
                    );


            Set<Integer> preguntasRespondidas =
                    new HashSet<>();


            for (Respuesta respuesta : respuestas) {

                if (respuesta.getPregunta() != null) {

                    preguntasRespondidas.add(
                            respuesta.getPregunta().getId()
                    );
                }
            }


            // ==================================================
            // BUSCAR PRIMERA PREGUNTA SIN RESPONDER
            // ==================================================

            Pregunta preguntaActual =
                    null;


            int numeroPregunta =
                    0;


            for (int i = 0;
                 i < preguntas.size();
                 i++) {

                Pregunta pregunta =
                        preguntas.get(i);


                if (!preguntasRespondidas.contains(
                        pregunta.getId()
                )) {

                    preguntaActual =
                            pregunta;


                    numeroPregunta =
                            i + 1;


                    break;
                }
            }


            // ==================================================
            // YA RESPONDIÓ TODAS
            // ==================================================

            if (preguntaActual == null &&
                    !preguntas.isEmpty()) {

                RetroAlimentacion retro =
                        retroAlimentacionDAO.buscarPorEntrevista(
                                entrevistaId
                        );


                request.setAttribute(
                        "retroalimentacion",
                        retro
                );


                request.setAttribute(
                        "respuestas",
                        respuestas
                );


                request.setAttribute(
                        "totalPreguntas",
                        preguntas.size()
                );


                request.getRequestDispatcher(
                        "/WEB-INF/views/entrevista-resultado.jsp"
                ).forward(
                        request,
                        response
                );


                return;
            }


            // ==================================================
            // NO HAY PREGUNTAS
            // ==================================================

            if (preguntas.isEmpty()) {

                response.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "La entrevista no contiene preguntas."
                );

                return;
            }


            // ==================================================
            // MOSTRAR PREGUNTA ACTUAL
            // ==================================================

            request.setAttribute(
                    "preguntaActual",
                    preguntaActual
            );


            request.setAttribute(
                    "numeroPregunta",
                    numeroPregunta
            );


            request.setAttribute(
                    "totalPreguntas",
                    preguntas.size()
            );


            request.getRequestDispatcher(
                    "/WEB-INF/views/entrevista-preguntas.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (NumberFormatException e) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador de la entrevista no es válido."
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar la entrevista.",
                    e
            );
        }
    }
}