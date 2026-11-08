package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-postulantes")
public class AdminPostulantesServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

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
            String estadoParam = request.getParameter("estado"); // Capturar filtro de estado

            // --- CÁLCULO DE MÉTRICAS PARA LAS TARJETAS ---
            int totalPostulantes = postulanteDAO.contarPorFiltros(null, null);
            int totalActivos = postulanteDAO.contarPorFiltros(null, "activo");
            int totalBloqueados = postulanteDAO.contarPorFiltros(null, "bloqueado");

            request.setAttribute("totalPostulantes", totalPostulantes);
            request.setAttribute("totalActivos", totalActivos);
            request.setAttribute("totalBloqueados", totalBloqueados);

            // --- LOGICA DE PAGINACIÓN ---
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

            // Listar postulantes filtrando por nombre, estado y aplicando paginación
            List<Postulante> postulantes =
                    postulanteDAO.listarPorFiltrosPaginados(nombreParam, estadoParam, registrosPorPagina, offset);

            int totalResultados = postulanteDAO.contarPorFiltros(nombreParam, estadoParam);
            int totalPaginas = (int) Math.ceil((double) totalResultados / registrosPorPagina);

            // Pasar atributos al JSP
            request.setAttribute("postulantes", postulantes);
            request.setAttribute("totalResultados", totalResultados);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/WEB-INF/views/admin-postulantes.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar la lista de postulantes", e);
        }
    }
}