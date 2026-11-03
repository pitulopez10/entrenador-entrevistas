package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.Area;
import org.example.AreaDAO;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-inicio")
public class AdminInicioServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();
    private final AreaDAO areaDAO = new AreaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Verificar que el administrador haya iniciado sesión
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            // Obtener las áreas para el filtro
            List<Area> areas = areaDAO.listar();
            request.setAttribute("areas", areas);

            // Obtener los parámetros enviados desde el formulario
            String areaParam = request.getParameter("area");
            String nombreParam = request.getParameter("nombre");

            Integer areaId = null;

            // Convertir el área a número si se seleccionó alguna
            if (areaParam != null && !areaParam.trim().isEmpty()) {
                areaId = Integer.parseInt(areaParam);
            }

            // Aplicar los filtros por área y título
            List<OfertaLaboral> ofertas =
                    ofertaLaboralDAO.listarPorFiltros(areaId, nombreParam);

            request.setAttribute("ofertas", ofertas);

            // Mostrar el JSP
            request.getRequestDispatcher("/WEB-INF/views/admin-inicio.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException(
                    "Error al consultar las ofertas para el administrador", e
            );
        }
    }
}
