package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Postulante;
import org.example.PostulanteDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-postulaciones-postulante")
public class AdminPostulacionesPostulanteServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String ciStr = request.getParameter("ci");
        if (ciStr == null || ciStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin-postulantes");
            return;
        }

        String estadoParam = request.getParameter("estado");

        try {
            int ci = Integer.parseInt(ciStr);
            Postulante postulante = postulanteDAO.buscarPorId(ci);
            if (postulante == null) {
                response.sendRedirect(request.getContextPath() + "/admin-postulantes");
                return;
            }

            // Obtener todas las postulaciones del postulante
            List<Postulacion> todasLasPostulaciones = postulacionDAO.listarPorPostulante(ci);

            // --- CÁLCULO DE MÉTRICAS PARA LAS TARJETAS (4 ESTADOS) ---
            int totalPostulaciones = todasLasPostulaciones.size();
            int totalSeleccionados = 0;
            int totalRechazados = 0;
            int totalRevision = 0;
            int totalEnviados = 0;

            for (Postulacion p : todasLasPostulaciones) {
                String estadoStr = p.getEstado() != null ? p.getEstado().name() : "";
                if (estadoStr.equalsIgnoreCase("SELECCIONADO")) {
                    totalSeleccionados++;
                } else if (estadoStr.equalsIgnoreCase("RECHAZADO")) {
                    totalRechazados++;
                } else if (estadoStr.equalsIgnoreCase("EN_REVISION") || estadoStr.equalsIgnoreCase("REVISION")) {
                    totalRevision++;
                } else if (estadoStr.equalsIgnoreCase("ENVIADO") || estadoStr.equalsIgnoreCase("PENDIENTE")) {
                    totalEnviados++;
                }
            }

            request.setAttribute("totalPostulaciones", totalPostulaciones);
            request.setAttribute("totalSeleccionados", totalSeleccionados);
            request.setAttribute("totalRechazados", totalRechazados);
            request.setAttribute("totalRevision", totalRevision);
            request.setAttribute("totalEnviados", totalEnviados);

            // --- FILTRAR LISTADO SI SE SELECCIONÓ UN ESTADO ---
            List<Postulacion> postulaciones;
            if (estadoParam != null && !estadoParam.isBlank()) {
                postulaciones = todasLasPostulaciones.stream()
                        .filter(p -> p.getEstado() != null && p.getEstado().name().equalsIgnoreCase(estadoParam))
                        .toList();
            } else {
                postulaciones = todasLasPostulaciones;
            }

            request.setAttribute("postulante", postulante);
            request.setAttribute("postulaciones", postulaciones);

            request.getRequestDispatcher("/WEB-INF/views/admin-postulaciones-postulante.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al consultar postulaciones del postulante", e);
        }
    }
}