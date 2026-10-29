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
import org.example.PostulacionDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/empresa-perfil")
public class EmpresaPerfilServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Solo empresas con la sesión iniciada
        HttpSession session = request.getSession(false);
        Empresa enSesion = session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");

        if (enSesion == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        try {
            // 2. Datos actuales, leídos de la base
            Empresa empresa = empresaDAO.buscarPorId(enSesion.getRut());

            if (empresa == null) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/login-empresa");
                return;
            }

            String rut = empresa.getRut();

            // 3. Números para el resumen
            List<OfertaLaboral> ofertas = ofertaDAO.listarPorEmpresa(rut);
            int activas = 0;
            for (OfertaLaboral oferta : ofertas) {
                if (oferta.getEstado() == EstadoEntrevista.ACTIVA) {
                    activas++;
                }
            }
            int totalPostulaciones = postulacionDAO.listarPorEmpresa(rut).size();

            // 4. Qué tan completo está el perfil (solo datos que se pueden editar)
            List<String> pendientes = new ArrayList<>();
            if (vacio(empresa.getDescripcion())) {
                pendientes.add("descripción");
            }
            if (empresa.getTelefono() == 0) {
                pendientes.add("teléfono");
            }
            if (vacio(empresa.getSitioWeb())) {
                pendientes.add("sitio web");
            }
            int porcentaje = (3 - pendientes.size()) * 100 / 3;

            // 5. El sitio solo se muestra como link si es http o https
            String sitio = empresa.getSitioWeb() == null
                    ? "" : empresa.getSitioWeb().trim().toLowerCase();
            boolean sitioEnlazable = sitio.startsWith("http://")
                    || sitio.startsWith("https://");

            request.setAttribute("empresa", empresa);
            request.setAttribute("inicial", inicialDe(empresa.getNombre()));
            request.setAttribute("totalOfertas", ofertas.size());
            request.setAttribute("ofertasActivas", activas);
            request.setAttribute("totalPostulaciones", totalPostulaciones);
            request.setAttribute("porcentajeCompleto", porcentaje);
            request.setAttribute("pendientesTexto", String.join(", ", pendientes));
            request.setAttribute("sitioEnlazable", sitioEnlazable);

        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar el perfil", e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/empresa-perfil.jsp"
        ).forward(request, response);
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }

    // Primera letra del nombre, en mayúscula, para el cuadrado del logo
    private String inicialDe(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "?";
        }
        String limpio = nombre.trim();
        return new String(Character.toChars(limpio.codePointAt(0))).toUpperCase();
    }
}