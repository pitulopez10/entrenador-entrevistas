package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-consultar-oferta")
public class AdminConsultarOfertaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();

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
            int id = Integer.parseInt(idStr);
            OfertaLaboral oferta = ofertaLaboralDAO.buscarPorId(id);

            if (oferta == null) {
                response.sendRedirect(request.getContextPath() + "/admin-inicio");
                return;
            }

            request.setAttribute("oferta", oferta);
            request.getRequestDispatcher("/WEB-INF/views/admin-consultar-oferta.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al consultar la oferta", e);
        }
    }
}