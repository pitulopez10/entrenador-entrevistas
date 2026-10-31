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
import java.util.List;

@WebServlet("/admin-empresas")
public class AdminEmpresasServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            List<Empresa> empresas = empresaDAO.listar();
            request.setAttribute("empresas", empresas);
            request.getRequestDispatcher("/WEB-INF/views/admin-empresas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar las empresas", e);
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

        String rut = request.getParameter("rut");
        String accion = request.getParameter("accion");

        if (rut != null && !rut.isEmpty() && accion != null) {
            try {
                if ("bloquear".equals(accion)) {
                    empresaDAO.bloquear(rut);
                } else if ("desbloquear".equals(accion)) {
                    empresaDAO.desbloquear(rut);
                }
            } catch (SQLException e) {
                throw new ServletException("Error al cambiar estado de bloqueo de la empresa", e);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin-empresas");
    }
}