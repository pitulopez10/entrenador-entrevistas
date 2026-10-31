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
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            // Cargar áreas para el desplegable del buscador
            List<Area> areas = areaDAO.listar();
            request.setAttribute("areas", areas);

            // Filtrar ofertas por área si viene el parámetro 'area'
            String areaParam = request.getParameter("area");
            List<OfertaLaboral> ofertas;

            if (areaParam != null && !areaParam.trim().isEmpty()) {
                int areaId = Integer.parseInt(areaParam);
                ofertas = ofertaLaboralDAO.listarPorArea(areaId);
            } else {
                ofertas = ofertaLaboralDAO.listar();
            }

            request.setAttribute("ofertas", ofertas);

            request.getRequestDispatcher("/WEB-INF/views/admin-inicio.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al consultar las ofertas para el administrador", e);
        }
    }
}
