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
            String estadoParam = request.getParameter("estado");

            Integer areaId = null;

            // Convertir el área a número si se seleccionó alguna
            if (areaParam != null && !areaParam.trim().isEmpty()) {
                areaId = Integer.parseInt(areaParam);
            }

            // --- CÁLCULO DE MÉTRICAS PARA LAS TARJETAS ---
            int totalOfertas = ofertaLaboralDAO.contarPorFiltros(null, null, null);
            int totalActivas = ofertaLaboralDAO.contarPorEstado("ACTIVA");
            int totalPausadas = ofertaLaboralDAO.contarPorEstado("PAUSADA");
            int totalTerminadas = ofertaLaboralDAO.contarPorEstado("TERMINADA");

            request.setAttribute("totalOfertas", totalOfertas);
            request.setAttribute("totalActivas", totalActivas);
            request.setAttribute("totalPausadas", totalPausadas);
            request.setAttribute("totalTerminadas", totalTerminadas);

            // --- LÓGICA DE PAGINACIÓN Y TOTALES ---
            int pagina = 1;
            int registrosPorPagina = 15;
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

            // Obtener las ofertas aplicando filtros (incluyendo estado) y paginación
            List<OfertaLaboral> ofertas =
                    ofertaLaboralDAO.listarPorFiltrosPaginados(areaId, nombreParam, estadoParam, registrosPorPagina, offset);

            // Obtener la cantidad total de resultados para la paginación según los filtros actuales
            int totalResultados = ofertaLaboralDAO.contarPorFiltros(areaId, nombreParam, estadoParam);
            int totalPaginas = (int) Math.ceil((double) totalResultados / registrosPorPagina);

            // Pasar atributos a la vista
            request.setAttribute("ofertas", ofertas);
            request.setAttribute("totalResultados", totalResultados);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

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