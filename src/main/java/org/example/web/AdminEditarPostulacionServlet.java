package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Postulacion;
import org.example.PostulacionDAO;
import org.example.EstadoPostulacion;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-editar-postulacion")
public class AdminEditarPostulacionServlet extends HttpServlet {

    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/admin-postulantes");
            return;
        }

        // Capturar la página anterior mediante el parámetro o el header Referer
        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isBlank()) {
            returnUrl = request.getHeader("Referer");
        }
        if (returnUrl == null || returnUrl.isBlank()) {
            returnUrl = request.getContextPath() + "/admin-postulantes";
        }

        try {
            int id = Integer.parseInt(idStr);
            Postulacion postulacion = postulacionDAO.buscarPorId(id);

            if (postulacion == null) {
                response.sendRedirect(request.getContextPath() + "/admin-postulantes");
                return;
            }

            request.setAttribute("postulacion", postulacion);
            request.setAttribute("returnUrl", returnUrl);
            request.getRequestDispatcher("/WEB-INF/views/admin-editar-postulacion.jsp").forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al cargar la postulación para editar", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String returnUrl = request.getParameter("returnUrl");
        if (returnUrl == null || returnUrl.isBlank()) {
            returnUrl = request.getContextPath() + "/admin-postulantes";
        }

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String estadoStr = request.getParameter("estado");
            String mensaje = request.getParameter("mensaje");

            Postulacion postulacion = new Postulacion();
            postulacion.setId(id);
            postulacion.setEstado(EstadoPostulacion.desdeTexto(estadoStr));
            postulacion.setMensaje(mensaje);

            postulacionDAO.actualizar(postulacion);

            // Redirigir de regreso a la última página guardada
            response.sendRedirect(returnUrl);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al actualizar la postulación", e);
        }
    }
}