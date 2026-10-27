package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Area;
import org.example.AreaDAO;
import org.example.Empresa;
import org.example.EstadoEntrevista;
import org.example.OfertaLaboral;
import org.example.OfertaLaboralDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/crear-oferta")
public class CrearOfertaServlet extends HttpServlet {

    // Si en la base descripcion/requisitos son VARCHAR(255), bajá MAX_TEXTO a 255
    private static final int MAX_TITULO = 100;
    private static final int MAX_TEXTO = 1000;

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final AreaDAO areaDAO = new AreaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        if (obtenerEmpresa(request) == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        try {
            mostrarFormulario(request, response, areaDAO.listar());
        } catch (SQLException e) {
            throw new ServletException("No se pudieron cargar las áreas", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Solo pueden publicar las empresas con sesión iniciada
        Empresa empresa = obtenerEmpresa(request);
        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        // 2. Leer lo que mandó el formulario
        String titulo = textoLimpio(request.getParameter("titulo"));
        String descripcion = textoLimpio(request.getParameter("descripcion"));
        String requisitos = textoLimpio(request.getParameter("requisitos"));
        String areaTexto = textoLimpio(request.getParameter("area"));
        String fechaCierreTexto = textoLimpio(request.getParameter("fechaCierre"));

        try {
            List<Area> areas = areaDAO.listar();
            Area area = buscarArea(areas, areaTexto);
            LocalDate fechaCierre = parsearFecha(fechaCierreTexto);

            // 3. Validar
            String error = validar(titulo, descripcion, requisitos,
                    area, fechaCierre);

            if (error != null) {
                request.setAttribute("error", error);
                request.setAttribute("areaSeleccionada",
                        area == null ? 0 : area.getId());
                mostrarFormulario(request, response, areas);
                return;
            }

            // 4. Crear la oferta: publicada hoy y activa
            OfertaLaboral oferta = new OfertaLaboral();
            oferta.setTitulo(titulo);
            oferta.setDescripcion(descripcion);
            oferta.setRequisitos(requisitos);
            oferta.setFechaPublicacion(LocalDate.now());
            oferta.setFechaCierre(fechaCierre);
            oferta.setEstado(EstadoEntrevista.ACTIVA);
            oferta.setEmpresa(empresa);
            oferta.setArea(area);

            ofertaDAO.agregar(oferta);

            response.sendRedirect(request.getContextPath() + "/empresa?creada=1");

        } catch (SQLException e) {
            throw new ServletException("Error al publicar la oferta", e);
        }
    }

    /** Devuelve el mensaje del primer error, o null si todo está bien. */
    private String validar(String titulo, String descripcion, String requisitos,
                           Area area, LocalDate fechaCierre) {

        if (titulo.isEmpty()) {
            return "El título es obligatorio.";
        }
        if (titulo.length() > MAX_TITULO) {
            return "El título no puede superar los " + MAX_TITULO + " caracteres.";
        }
        if (descripcion.isEmpty()) {
            return "La descripción es obligatoria.";
        }
        if (descripcion.length() > MAX_TEXTO) {
            return "La descripción no puede superar los " + MAX_TEXTO + " caracteres.";
        }
        if (requisitos.isEmpty()) {
            return "Los requisitos son obligatorios.";
        }
        if (requisitos.length() > MAX_TEXTO) {
            return "Los requisitos no pueden superar los " + MAX_TEXTO + " caracteres.";
        }
        if (area == null) {
            return "Seleccioná un área válida.";
        }
        if (fechaCierre == null) {
            return "Ingresá una fecha de cierre válida.";
        }
        if (fechaCierre.isBefore(LocalDate.now())) {
            return "La fecha de cierre no puede ser anterior a hoy.";
        }
        return null;
    }

    // Busca entre las áreas que existen en la base; null si no es válida
    private Area buscarArea(List<Area> areas, String texto) {
        try {
            int id = Integer.parseInt(texto);
            for (Area area : areas) {
                if (area.getId() == id) {
                    return area;
                }
            }
        } catch (NumberFormatException e) {
            // texto vacío o no numérico: queda sin área
        }
        return null;
    }

    private LocalDate parsearFecha(String texto) {
        try {
            return LocalDate.parse(texto);   // formato AAAA-MM-DD
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private void mostrarFormulario(HttpServletRequest request,
                                   HttpServletResponse response,
                                   List<Area> areas)
            throws ServletException, IOException {

        request.setAttribute("listaAreas", areas);
        request.setAttribute("hoy", LocalDate.now().toString());

        request.getRequestDispatcher(
                "/WEB-INF/views/crear-oferta.jsp"
        ).forward(request, response);
    }

    private Empresa obtenerEmpresa(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null
                ? null
                : (Empresa) session.getAttribute("empresaLogueada");
    }

    private String textoLimpio(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
