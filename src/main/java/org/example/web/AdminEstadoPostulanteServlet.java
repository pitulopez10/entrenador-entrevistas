package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-estado-postulante")
public class AdminEstadoPostulanteServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String ciStr = request.getParameter("ci");
        String accion = request.getParameter("accion");

        if (ciStr != null && !ciStr.isEmpty() && accion != null) {
            try {
                int ci = Integer.parseInt(ciStr);
                if ("bloquear".equals(accion)) {
                    postulanteDAO.bloquear(ci);
                } else if ("desbloquear".equals(accion)) {
                    postulanteDAO.desbloquear(ci);
                }
            } catch (SQLException | NumberFormatException e) {
                throw new ServletException("Error al cambiar estado del postulante", e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin-postulantes");
    }
}