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
            String nombreParam = request.getParameter("nombre");

            // LOGICA TI PAGINACIÓN (Saggaysan a 10)
            int pagina = 1;
            int registrosPorPagina = 10;
            String pageParam = request.getParameter("page");

            if (pageParam != null && !pageParam.trim().isEmpty()) {
                try {
                    pagina = Integer.parseInt(pageParam);
                    if (pagina < 1) pagina = 1;
                } catch (NumberFormatException e) {
                    pagina = 1;
                }
            }

            int offset = (pagina - 1) * registrosPorPagina;

            // Panangalap kadagiti empresas a addaan limit a 10 ken ti kabuklan a bilang
            List<Empresa> empresas =
                    empresaDAO.listarPorNombrePaginadas(nombreParam, registrosPorPagina, offset);

            int totalResultados = empresaDAO.contarPorNombre(nombreParam);
            int totalPaginas = (int) Math.ceil((double) totalResultados / registrosPorPagina);

            // Panangipasa kadagiti atributos iti JSP
            request.setAttribute("empresas", empresas);
            request.setAttribute("totalResultados", totalResultados);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/WEB-INF/views/admin-empresas.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar la lista de empresas", e);
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

        try {
            if (rut != null && !rut.isBlank()) {
                if ("bloquear".equals(accion)) {
                    empresaDAO.bloquear(rut);
                } else if ("desbloquear".equals(accion)) {
                    empresaDAO.desbloquear(rut);
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Error al actualizar el estado de la empresa", e);
        }

        response.sendRedirect(request.getContextPath() + "/admin-empresas");
    }
}