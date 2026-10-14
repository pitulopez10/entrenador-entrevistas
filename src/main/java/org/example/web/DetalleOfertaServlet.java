package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/oferta")
public class DetalleOfertaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String idParam = request.getParameter("id");

            if (idParam == null || idParam.isBlank()) {
                response.sendRedirect(
                        request.getContextPath() + "/ofertas"
                );
                return;
            }

            int id = Integer.parseInt(idParam);

            OfertaLaboral oferta = ofertaLaboralDAO.buscarPorId(id);

            if (oferta == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "La oferta no existe"
                );
                return;
            }

            request.setAttribute("oferta", oferta);

            request.getRequestDispatcher(
                    "/WEB-INF/views/detalle-oferta.jsp"
            ).forward(request, response);

        } catch (NumberFormatException e) {

            response.sendRedirect(
                    request.getContextPath() + "/ofertas"
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar la oferta laboral",
                    e
            );
        }
    }
}