package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.EstadoEntrevista;
import org.example.EstadoPostulacion;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;
import org.example.Postulante;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/postularse")
public class PostularseServlet extends HttpServlet {

    private final PostulacionDAO postulacionDAO =
            new PostulacionDAO();

    private final OfertaLaboralDAO ofertaLaboralDAO =
            new OfertaLaboralDAO();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String ofertaIdParam =
                request.getParameter("ofertaId");

        if (ofertaIdParam == null ||
                ofertaIdParam.isBlank()) {

            response.sendRedirect(
                    request.getContextPath() + "/ofertas"
            );

            return;
        }

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("postulanteLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login?ofertaId="
                            + ofertaIdParam
            );

            return;
        }

        Postulante postulante =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );

        try {

            int ofertaId =
                    Integer.parseInt(ofertaIdParam);

            OfertaLaboral oferta =
                    ofertaLaboralDAO.buscarPorId(
                            ofertaId
                    );

            if (oferta == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La oferta no existe."
                );

                return;
            }

            if (oferta.getEstado() !=
                    EstadoEntrevista.ACTIVA) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/oferta?id="
                                + ofertaId
                                + "&error=noActiva"
                );

                return;
            }

            boolean yaPostulado =
                    postulacionDAO.existePostulacion(
                            postulante.getCi(),
                            ofertaId
                    );

            if (yaPostulado) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/oferta?id="
                                + ofertaId
                                + "&error=duplicada"
                );

                return;
            }

            Postulacion postulacion =
                    new Postulacion();

            postulacion.setFechaPostulacion(
                    LocalDate.now()
            );

            postulacion.setEstado(
                    EstadoPostulacion.enviado
            );

            postulacion.setMensaje(
                    "Postulación realizada desde el portal web."
            );

            postulacion.setPostulante(
                    postulante
            );

            postulacion.setOfertaLaboral(
                    oferta
            );

            postulacionDAO.agregar(
                    postulacion
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/oferta?id="
                            + ofertaId
                            + "&postulacion=ok"
            );

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/ofertas"
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al registrar la postulación",
                    e
            );
        }
    }
}