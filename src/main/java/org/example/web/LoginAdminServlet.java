package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Administrador;
import org.example.AdministradorDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login-admin")
public class LoginAdminServlet extends HttpServlet {

    private final AdministradorDAO administradorDAO = new AdministradorDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Si ya tiene sesión iniciada como administrador, lo mandamos al dashboard
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("adminLogueado") != null) {
            response.sendRedirect(request.getContextPath() + "/admin-inicio");
            return;
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/login-admin.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario") == null
                ? "" : request.getParameter("usuario").trim();
        String password = request.getParameter("password") == null
                ? "" : request.getParameter("password");

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError(request, response,
                    "Debés ingresar usuario y contraseña.");
            return;
        }

        try {
            // Buscamos al administrador por su identificador/usuario
            Administrador admin = administradorDAO.buscarPorId(usuario);

            // Validamos que exista y que la contraseña coincida
            if (admin == null || !admin.getPassword().equals(password)) {
                mostrarError(request, response,
                        "Usuario o contraseña incorrectos.");
                return;
            }

            // Invalida la sesión anterior por seguridad para evitar fijación de sesión
            HttpSession anterior = request.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }

            // Guardamos el objeto Administrador en la nueva sesión
            request.getSession(true).setAttribute("adminLogueado", admin);

            response.sendRedirect(request.getContextPath() + "/admin-inicio");

        } catch (SQLException e) {
            throw new ServletException("Error al iniciar sesión como administrador", e);
        }
    }

    private void mostrarError(HttpServletRequest request,
                              HttpServletResponse response,
                              String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);

        request.getRequestDispatcher(
                "/WEB-INF/views/login-admin.jsp"
        ).forward(request, response);
    }
}