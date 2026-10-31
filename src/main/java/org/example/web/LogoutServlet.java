package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String urlDestino = request.getContextPath() + "/ofertas";

        if (session != null) {
            // Si el usuario con sesión activa es un administrador
            if (session.getAttribute("adminLogueado") != null) {
                urlDestino = request.getContextPath() + "/login-admin";
            }

            // Invalida la sesión borrando todos los atributos
            session.invalidate();
        }

        // Redirige al destino correspondiente según el tipo de usuario
        response.sendRedirect(urlDestino);
    }
}