package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EmpresaDAO;
import org.example.EstadoEntrevista;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin-ofertas-empresa")
public class AdminOfertasEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final OfertaLaboralDAO ofertaLaboralDAO = new OfertaLaboralDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            int ofertaId = Integer.parseInt(request.getParameter("ofertaId"));
            String rut = request.getParameter("rut");

            if (rut == null || rut.isBlank()
                    || !ofertaLaboralDAO.cambiarEstadoDeOferta(
                    ofertaId, rut, EstadoEntrevista.TERMINADA)) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "La oferta no está disponible");
                return;
            }

            response.sendRedirect(request.getContextPath()
                    + "/admin-ofertas-empresa?rut=" + rut + "&baja=ok");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "El ID de oferta no es válido");
        } catch (SQLException e) {
            throw new ServletException("No se pudo dar de baja la oferta", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String rut = request.getParameter("rut");
        if (rut == null || rut.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin-empresas");
            return;
        }

        String estadoParam = request.getParameter("estado");

        try {
            Empresa empresa = empresaDAO.buscarPorId(rut);
            if (empresa == null) {
                response.sendRedirect(request.getContextPath() + "/admin-empresas");
                return;
            }

            // --- CÁLCULO DE MÉTRICAS PARA LAS TARJETAS ---
            List<OfertaLaboral> todasLasOfertas = ofertaLaboralDAO.listarPorEmpresa(rut);
            int totalOfertasEmpresa = todasLasOfertas.size();
            int totalActivasEmpresa = 0;
            int totalPausadasEmpresa = 0;
            int totalTerminadasEmpresa = 0;

            for (OfertaLaboral o : todasLasOfertas) {
                if (o.getEstado() == EstadoEntrevista.ACTIVA) totalActivasEmpresa++;
                else if (o.getEstado() == EstadoEntrevista.PAUSADA) totalPausadasEmpresa++;
                else if (o.getEstado() == EstadoEntrevista.TERMINADA) totalTerminadasEmpresa++;
            }

            request.setAttribute("totalOfertasEmpresa", totalOfertasEmpresa);
            request.setAttribute("totalActivasEmpresa", totalActivasEmpresa);
            request.setAttribute("totalPausadasEmpresa", totalPausadasEmpresa);
            request.setAttribute("totalTerminadasEmpresa", totalTerminadasEmpresa);

            // Filtrar listado si se seleccionó un estado específico
            List<OfertaLaboral> ofertas;
            if (estadoParam != null && !estadoParam.isBlank()) {
                ofertas = todasLasOfertas.stream()
                        .filter(o -> o.getEstado().name().equalsIgnoreCase(estadoParam))
                        .toList();
            } else {
                ofertas = todasLasOfertas;
            }

            request.setAttribute("empresa", empresa);
            request.setAttribute("ofertas", ofertas);

            request.getRequestDispatcher("/WEB-INF/views/admin-ofertas-empresa.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar las ofertas de la empresa", e);
        }
    }
}
