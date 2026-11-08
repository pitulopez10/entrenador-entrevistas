package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.Area;
import org.example.AreaDAO;
import org.example.EstadoEntrevista;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/admin-editar-oferta")
public class AdminEditarOfertaServlet extends HttpServlet {

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

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
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

            List<Area> areas = areaDAO.listar();

            request.setAttribute("oferta", oferta);
            request.setAttribute("areas", areas);

            request.getRequestDispatcher("/WEB-INF/views/admin-editar-oferta.jsp")
                    .forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al cargar oferta para edición", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        request.setCharacterEncoding("UTF-8");

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String requisitos = request.getParameter("requisitos");
            int areaId = Integer.parseInt(request.getParameter("areaId"));
            String estadoStr = request.getParameter("estado");
            String fechaPubStr = request.getParameter("fechaPublicacion");
            String fechaCierreStr = request.getParameter("fechaCierre");

            OfertaLaboral oferta = ofertaLaboralDAO.buscarPorId(id);

            if (oferta != null) {
                oferta.setTitulo(titulo);
                oferta.setDescripcion(descripcion);
                oferta.setRequisitos(requisitos);

                Area area = new Area();
                area.setId(areaId);
                oferta.setArea(area);

                oferta.setEstado(EstadoEntrevista.valueOf(estadoStr.toUpperCase()));

                if (fechaPubStr != null && !fechaPubStr.isBlank()) {
                    oferta.setFechaPublicacion(LocalDate.parse(fechaPubStr));
                }

                if (fechaCierreStr != null && !fechaCierreStr.isBlank()) {
                    oferta.setFechaCierre(LocalDate.parse(fechaCierreStr));
                } else {
                    oferta.setFechaCierre(null);
                }

                ofertaLaboralDAO.modificar(oferta);
            }

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin-inicio");
    }
}