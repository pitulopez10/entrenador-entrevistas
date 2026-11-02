package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-postulantes")
public class AdminPostulantesServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Validar que exista una sesión activa de administrador
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String nombre = request.getParameter("nombre");

        try {
            List<Postulante> postulantes;
            if (nombre != null && !nombre.trim().isEmpty()) {
                postulantes = postulanteDAO.buscarPorNombre(nombre);
            } else {
                postulantes = postulanteDAO.listar();
            }

            request.setAttribute("postulantes", postulantes);

            // Redirigir a la vista JSP de postulantes
            request.getRequestDispatcher("/WEB-INF/views/admin-postulantes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar postulantes desde la base de datos", e);
        }
    }
}