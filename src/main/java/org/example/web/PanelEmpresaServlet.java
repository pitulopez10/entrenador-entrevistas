package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EstadoEntrevista;
import org.example.EstadoPostulacion;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;
import org.example.Postulacion;
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/empresa")
public class PanelEmpresaServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Solo pueden entrar empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa empresa = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        try {
            // 2. Ofertas y postulaciones de ESTA empresa
            List<OfertaLaboral> ofertas = ofertaDAO.listarPorEmpresa(empresa.getRut());
            List<Postulacion> postulaciones =
                    postulacionDAO.listarPorEmpresa(empresa.getRut());

            // Las más nuevas primero
            Collections.reverse(ofertas);

            // 3. Contar para el resumen y para cada tarjeta
            int activas = 0;
            for (OfertaLaboral oferta : ofertas) {
                if (oferta.getEstado() == EstadoEntrevista.ACTIVA) {
                    activas++;
                }
            }

            Map<Integer, Integer> postulantesPorOferta = new HashMap<>();
            int seleccionados = 0;
            for (Postulacion postulacion : postulaciones) {
                postulantesPorOferta.merge(
                        postulacion.getOfertaLaboral().getId(), 1, Integer::sum);
                if (postulacion.getEstado() == EstadoPostulacion.seleccionado) {
                    seleccionados++;
                }
            }

            request.setAttribute("ofertas", ofertas);
            request.setAttribute("totalOfertas", ofertas.size());
            request.setAttribute("ofertasActivas", activas);
            request.setAttribute("totalPostulaciones", postulaciones.size());
            request.setAttribute("seleccionados", seleccionados);
            request.setAttribute("postulantesPorOferta", postulantesPorOferta);

        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar el panel", e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/panel-empresa.jsp"
        ).forward(request, response);
    }
}