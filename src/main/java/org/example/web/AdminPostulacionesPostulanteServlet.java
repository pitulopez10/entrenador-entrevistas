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

        try {
            int ci = Integer.parseInt(ciStr);
            Postulante postulante = postulanteDAO.buscarPorId(ci);
            if (postulante == null) {
                response.sendRedirect(request.getContextPath() + "/admin-postulantes");
                return;
            }

            List<Postulacion> postulaciones = postulacionDAO.listarPorPostulante(ci);

            request.setAttribute("postulante", postulante);
            request.setAttribute("postulaciones", postulaciones);

            request.getRequestDispatcher("/WEB-INF/views/admin-postulaciones-postulante.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al consultar postulaciones del postulante", e);
        }
    }
}