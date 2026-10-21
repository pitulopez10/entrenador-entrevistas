package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/empresa")
public class PanelEmpresaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Solo pueden entrar empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa empresa = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        // 2. Cargar las ofertas de esta empresa
        try {
            request.setAttribute("ofertas",
                    ofertaDAO.listarPorEmpresa(empresa.getRut()));
        } catch (SQLException e) {
            throw new ServletException("No se pudieron cargar las ofertas", e);
        }

        // 3. Mostrar el panel
        request.getRequestDispatcher(
                "/WEB-INF/views/panel-empresa.jsp"
        ).forward(request, response);
    }
}
