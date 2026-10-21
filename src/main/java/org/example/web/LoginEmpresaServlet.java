package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EmpresaDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login-empresa")
public class LoginEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Si ya tiene la sesión iniciada, no hace falta mostrar el login
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("empresaLogueada") != null) {
            response.sendRedirect(request.getContextPath() + "/empresa");
            return;
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/login-empresa.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String correo = request.getParameter("correo") == null
                ? "" : request.getParameter("correo").trim();
        String contrasena = request.getParameter("contrasena") == null
                ? "" : request.getParameter("contrasena");

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarError(request, response,
                    "Debés ingresar correo y contraseña.");
            return;
        }

        try {
            Empresa empresa = empresaDAO.iniciarSesion(correo, contrasena);

            if (empresa == null) {
                mostrarError(request, response,
                        "Correo o contraseña incorrectos.");
                return;
            }

            if (empresa.isBloqueado()) {
                mostrarError(request, response,
                        "La cuenta de la empresa se encuentra bloqueada.");
                return;
            }

            // Sesión nueva, para no arrastrar datos de una sesión anterior
            HttpSession anterior = request.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }

            request.getSession(true).setAttribute("empresaLogueada", empresa);

            response.sendRedirect(request.getContextPath() + "/empresa");

        } catch (SQLException e) {
            throw new ServletException("Error al iniciar sesión", e);
        }
    }

    private void mostrarError(HttpServletRequest request,
                              HttpServletResponse response,
                              String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);

        request.getRequestDispatcher(
                "/WEB-INF/views/login-empresa.jsp"
        ).forward(request, response);
    }
}