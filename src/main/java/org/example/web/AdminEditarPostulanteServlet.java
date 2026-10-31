package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DTGenero;
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/admin-editar-postulante")
public class AdminEditarPostulanteServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

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

            request.setAttribute("postulante", postulante);
            request.getRequestDispatcher("/WEB-INF/views/admin-editar-postulante.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al obtener postulante para edición", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            int ci = Integer.parseInt(request.getParameter("ci"));
            Postulante postulante = postulanteDAO.buscarPorId(ci);

            if (postulante != null) {
                postulante.setNombre(request.getParameter("nombre"));
                postulante.setMail(request.getParameter("mail"));
                postulante.setPassword(request.getParameter("password"));
                postulante.setTelefono(Integer.parseInt(request.getParameter("telefono")));
                postulante.setLocalidad(request.getParameter("localidad"));
                postulante.setDescripcion(request.getParameter("descripcion"));
                postulante.setDatosEstudio(request.getParameter("datosEstudio"));
                postulante.setDatosExperiencia(request.getParameter("datosExperiencia"));

                String fechaNacStr = request.getParameter("fechaNacimiento");
                if (fechaNacStr != null && !fechaNacStr.isEmpty()) {
                    postulante.setFechaNacimiento(LocalDate.parse(fechaNacStr));
                }

                String generoStr = request.getParameter("genero");
                if (generoStr != null && !generoStr.isEmpty()) {
                    postulante.setGenero(DTGenero.valueOf(generoStr));
                }

                // Guardar cambios en la Base de Datos
                postulanteDAO.modificar(postulante);
            }

            response.sendRedirect(request.getContextPath() + "/admin-postulantes");

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al actualizar la información del postulante", e);
        }
    }
}
