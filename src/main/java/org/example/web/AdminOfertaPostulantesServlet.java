package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulante;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-oferta-postulantes")
public class AdminOfertaPostulantesServlet extends HttpServlet {

    private final PostulacionDAO postulacionDAO = new PostulacionDAO();
    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                response.sendRedirect(request.getContextPath() + "/admin-inicio");
                return;
            }

            int ofertaId = Integer.parseInt(idParam);
            OfertaLaboral oferta = ofertaLaboralDAO.buscarPorId(ofertaId);
            if (oferta == null) {
                response.sendRedirect(request.getContextPath() + "/admin-inicio");
                return;
            }

            String nombreFiltro = request.getParameter("nombre");

            // Paginación (Máximo 10 resultados por página)
            int pagina = 1;
            int registrosPorPagina = 10;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isBlank()) {
                try {
                    pagina = Integer.parseInt(pageParam);
                    if (pagina < 1) pagina = 1;
                } catch (NumberFormatException e) {
                    pagina = 1;
                }
            }
            int offset = (pagina - 1) * registrosPorPagina;

            List<Postulante> postulantes = postulacionDAO.listarPostulantesPorOfertaPaginados(ofertaId, nombreFiltro, registrosPorPagina, offset);
            int totalResultados = postulacionDAO.contarPostulantesPorOferta(ofertaId, nombreFiltro);
            int totalPaginas = (int) Math.ceil((double) totalResultados / registrosPorPagina);

            request.setAttribute("oferta", oferta);
            request.setAttribute("postulantes", postulantes);
            request.setAttribute("totalResultados", totalResultados);
            request.setAttribute("paginaActual", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

            request.getRequestDispatcher("/WEB-INF/views/admin-oferta-postulantes.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al consultar los postulantes de la oferta", e);
        }
    }
}