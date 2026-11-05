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
import java.util.ArrayList;

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
            List<OfertaLaboral> todasLasOfertas =
                    ofertaLaboralDAO.listarPorFiltros(areaId, titulo);

            int pagina = 1;
            try {
                pagina = Math.max(1, Integer.parseInt(request.getParameter("pagina")));
            } catch (NumberFormatException ignored) {
                // Se utiliza la primera página cuando el parámetro no es válido.
            }

            int porPagina = 10;
            int totalPaginas = Math.max(1,
                    (int) Math.ceil(todasLasOfertas.size() / (double) porPagina));
            pagina = Math.min(pagina, totalPaginas);
            int desde = (pagina - 1) * porPagina;
            int hasta = Math.min(desde + porPagina, todasLasOfertas.size());
            List<OfertaLaboral> ofertas = todasLasOfertas.isEmpty()
                    ? new ArrayList<>()
                    : todasLasOfertas.subList(desde, hasta);

            List<Area> areas = areaDAO.listar();

            request.setAttribute("ofertas", ofertas);
            request.setAttribute("areas", areas);
            request.setAttribute("pagina", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("areaSeleccionada", areaParam);
            request.setAttribute("tituloBuscado", titulo);

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
