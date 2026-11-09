package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Empresa;
import org.example.EmpresaDAO;
import org.example.EstadoEntrevista;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/empresa-publica")
public class EmpresaPublicaServlet extends HttpServlet {
    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || !id.matches("[a-f0-9]{64}")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se indicó la empresa.");
            return;
        }

        try {
            Empresa empresa = empresaDAO.buscarPorIdPublico(id);
            if (empresa == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "La empresa no existe.");
                return;
            }

            List<OfertaLaboral> activas = new ArrayList<>();
            for (OfertaLaboral oferta : ofertaDAO.listarPorEmpresa(empresa.getRut())) {
                if (oferta.getEstado() == EstadoEntrevista.ACTIVA) activas.add(oferta);
            }
            int pagina = 1;
            try { pagina = Math.max(1, Integer.parseInt(request.getParameter("pagina"))); }
            catch (NumberFormatException ignored) { }
            int totalPaginas = Math.max(1, (int) Math.ceil(activas.size() / 6.0));
            pagina = Math.min(pagina, totalPaginas);
            int desde = (pagina - 1) * 6;
            int hasta = Math.min(desde + 6, activas.size());

            java.util.Map<String, Object> publico = new java.util.HashMap<>();
            publico.put("idPublico", empresa.getIdPublico());
            publico.put("nombre", empresa.getNombre());
            publico.put("logo", empresa.getLogo());
            publico.put("descripcion", empresa.getDescripcion());
            publico.put("mail", empresa.getMail());
            publico.put("telefono", empresa.getTelefono());
            publico.put("sitioWeb", empresa.getSitioWeb());
            String presentacion = "";
            if (empresa.getFechaRegistro() != null) {
                java.time.format.DateTimeFormatter formato =
                        java.time.format.DateTimeFormatter.ofPattern("MMMM 'de' uuuu",
                                java.util.Locale.forLanguageTag("es-UY"));
                presentacion = "En la plataforma desde " + empresa.getFechaRegistro().format(formato);
            } else if (empresa.getDescripcion() != null) {
                presentacion = empresa.getDescripcion().replaceAll("\\s+", " ").trim();
                if (presentacion.codePointCount(0, presentacion.length()) > 160) {
                    presentacion = presentacion.substring(0, presentacion.offsetByCodePoints(0, 160)) + "…";
                }
            }
            request.setAttribute("presentacionPublica", presentacion);
            request.setAttribute("empresa", publico);
            request.setAttribute("sitioEnlazable", EnlacesWebServlet.esHttp(empresa.getSitioWeb()));
            request.setAttribute("ofertas", activas.isEmpty()
                    ? new ArrayList<>() : activas.subList(desde, hasta));
            request.setAttribute("pagina", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.getRequestDispatcher("/WEB-INF/views/empresa-publica.jsp")
                    .forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar el perfil público de la empresa", e);
        }
    }
}
