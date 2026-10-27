package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/empresa-oferta")
public class EmpresaOfertaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Solo empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa empresa = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        // 2. Leer el id de la oferta
        int ofertaId;
        try {
            ofertaId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(400, "Oferta inválida");
            return;
        }

        try {
            // 3. Buscar la oferta SOLO entre las de esta empresa
            OfertaLaboral oferta = null;
            for (OfertaLaboral o : ofertaDAO.listarPorEmpresa(empresa.getRut())) {
                if (o.getId() == ofertaId) {
                    oferta = o;
                    break;
                }
            }

            if (oferta == null) {
                response.sendError(404, "Oferta no disponible");
                return;
            }

            // 4. Quedarse con las postulaciones de esa oferta
            List<Postulacion> postulaciones = new ArrayList<>();
            for (Postulacion p : postulacionDAO.listarPorEmpresa(empresa.getRut())) {
                if (p.getOfertaLaboral().getId() == ofertaId) {
                    postulaciones.add(p);
                }
            }

            request.setAttribute("oferta", oferta);
            request.setAttribute("postulaciones", postulaciones);

        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar la oferta", e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/empresa-oferta.jsp"
        ).forward(request, response);
    }
}
