package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-postulantes-oferta")
public class AdminPostulantesOfertaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin-inicio");
            return;
        }

        try {
            int ofertaId = Integer.parseInt(idStr);
            OfertaLaboral oferta = ofertaLaboralDAO.buscarPorId(ofertaId);

            if (oferta == null) {
                response.sendRedirect(request.getContextPath() + "/admin-inicio");
                return;
            }

            List<Postulacion> postulaciones = postulacionDAO.listarPorOferta(ofertaId);

            request.setAttribute("oferta", oferta);
            request.setAttribute("postulaciones", postulaciones);

            request.getRequestDispatcher("/WEB-INF/views/admin-postulantes-oferta.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al obtener candidatos de la oferta", e);
        }
    }
}