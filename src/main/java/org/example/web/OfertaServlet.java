package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.Area;
import org.example.AreaDAO;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ofertas")
public class OfertaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();
    private final AreaDAO areaDAO = new AreaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String areaParam = request.getParameter("area");
            String titulo = request.getParameter("titulo");
            Integer areaId = areaParam != null && !areaParam.isBlank()
                    ? Integer.parseInt(areaParam)
                    : null;
            List<OfertaLaboral> ofertas =
                    ofertaLaboralDAO.listarPorFiltros(areaId, titulo);

            List<Area> areas = areaDAO.listar();

            request.setAttribute("ofertas", ofertas);
            request.setAttribute("areas", areas);

            request.getRequestDispatcher(
                    "/WEB-INF/views/ofertas.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar las ofertas laborales",
                    e
            );
        }
    }
}
