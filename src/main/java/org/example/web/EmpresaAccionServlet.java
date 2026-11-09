package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EmailService;
import org.example.EstadoEntrevista;
import org.example.EstadoPostulacion;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/empresa-accion")
public class EmpresaAccionServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();
    private final EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Solo empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa empresa = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        String accion = request.getParameter("accion") == null
                ? "" : request.getParameter("accion");

        try {
            int ofertaId = Integer.parseInt(request.getParameter("ofertaId"));
            String rut = empresa.getRut();

            // 2. La oferta tiene que ser de ESTA empresa
            OfertaLaboral oferta = null;
            for (OfertaLaboral o : ofertaDAO.listarPorEmpresa(rut)) {
                if (o.getId() == ofertaId) {
                    oferta = o;
                    break;
                }
            }

            if (oferta == null) {
                response.sendError(404, "Oferta no disponible");
                return;
            }

            // 3. Ejecutar la acción pedida
            String resultado;

            if ("cerrar".equals(accion)) {
                ofertaDAO.cambiarEstadoDeOferta(ofertaId, rut, EstadoEntrevista.TERMINADA);
                resultado = "cerrada";

            } else if ("reabrir".equals(accion)) {
                ofertaDAO.cambiarEstadoDeOferta(ofertaId, rut, EstadoEntrevista.ACTIVA);
                resultado = "reabierta";

            } else if ("seleccionar".equals(accion) || "descartar".equals(accion) || "evaluar".equals(accion)) {

                // Solo se elige entre postulantes de una oferta activa
                if (oferta.getEstado() != EstadoEntrevista.ACTIVA) {
                    resultado = "ofertaInactiva";
                } else {
                    int postulacionId =
                            Integer.parseInt(request.getParameter("postulacionId"));

                    // La postulación tiene que ser de ESTA oferta (y por lo tanto de esta empresa)
                    Postulacion postulacion = null;
                    for (Postulacion p : postulacionDAO.listarPorEmpresa(rut)) {
                        if (p.getId() == postulacionId
                                && p.getOfertaLaboral().getId() == ofertaId) {
                            postulacion = p;
                            break;
                        }
                    }

                    if (postulacion == null) {
                        response.sendError(404, "Postulación no disponible");
                        return;
                    }

                    boolean seleccionar = "seleccionar".equals(accion);
                    EstadoPostulacion nuevoEstado = "evaluar".equals(accion) ? EstadoPostulacion.enRevision : seleccionar
                            ? EstadoPostulacion.seleccionado
                            : EstadoPostulacion.rechazado;

                    if (!postulacionDAO.cambiarEstadoDePostulacion(
                            postulacionId, rut, nuevoEstado)) {
                        response.sendError(404, "Postulación no disponible");
                        return;
                    }

                    if (!"evaluar".equals(accion) && postulacion.getEstado() != nuevoEstado) {
                        try (InputStream logo = getServletContext().getResourceAsStream("/img/logo.png")) {
                            if (seleccionar) {
                                emailService.enviarNotificacionEstado(
                                        postulacion.getPostulante(), oferta, logo,
                                        "¡Fuiste seleccionado! - " + oferta.getTitulo(),
                                        "¡Felicitaciones, fuiste seleccionado!",
                                        "La empresa revisó tu postulación y decidió seleccionarte para continuar con el proceso.",
                                        "¡Te deseamos muchos éxitos en esta nueva oportunidad!");
                            } else {
                                emailService.enviarNotificacionEstado(
                                        postulacion.getPostulante(), oferta, logo,
                                        "Actualización de tu postulación - " + oferta.getTitulo(),
                                        "Gracias por postularte",
                                        "En esta oportunidad la empresa decidió continuar con otro perfil.",
                                        "No te desanimes: seguí intentando, porque seguramente tendrás suerte en la próxima oportunidad.");
                            }
                        } catch (jakarta.mail.MessagingException | IllegalStateException e) {
                            getServletContext().log(
                                    "No se pudo enviar la notificación de estado al postulante", e);
                        }

                    }

                    resultado = "evaluar".equals(accion) ? "evaluado" : seleccionar ? "seleccionado" : "descartado";
                }

            } else {
                response.sendError(400, "Acción inválida");
                return;
            }

            // 4. Volver al lugar de donde vino, con un aviso
            String destino;
            if ("panel".equals(request.getParameter("volver"))) {
                destino = "/empresa?msg=" + resultado;
            } else if (("evaluacion".equals(request.getParameter("volver"))
                    || "perfil".equals(request.getParameter("volver")))
                    && ("evaluar".equals(accion) || "seleccionar".equals(accion)
                    || "descartar".equals(accion))) {
                int postulacionId = Integer.parseInt(request.getParameter("postulacionId"));
                String pantalla = "evaluacion".equals(request.getParameter("volver"))
                        ? "/empresa-evaluar" : "/empresa-postulante";
                destino = pantalla + "?oferta=" + ofertaId
                        + "&postulacion=" + postulacionId + "&msg=" + resultado;
            } else {
                destino = "/empresa-oferta?id=" + ofertaId + "&msg=" + resultado;
            }
            response.sendRedirect(request.getContextPath() + destino);

        } catch (NumberFormatException e) {
            response.sendError(400, "El dato recibido no tiene el formato esperado");
        } catch (SQLException e) {
            throw new ServletException("No se pudo completar la acción", e);
        }
    }
}
