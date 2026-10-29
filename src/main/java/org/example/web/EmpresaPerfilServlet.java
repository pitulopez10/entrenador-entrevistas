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

@WebServlet("/empresa-perfil")
public class EmpresaPerfilServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Solo empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa enSesion = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (enSesion == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        try {
            // 2. Datos actuales, leídos de la base
            Empresa empresa = empresaDAO.buscarPorId(enSesion.getRut());

            if (empresa == null) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login-empresa");
                return;
            }

            request.setAttribute("empresa", empresa);

        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar el perfil", e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/empresa-perfil.jsp"
        ).forward(request, response);
    }
}
