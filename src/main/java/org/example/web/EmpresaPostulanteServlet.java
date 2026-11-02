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
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet("/empresa-postulante")
public class EmpresaPostulanteServlet extends HttpServlet {

    private final OfertaLaboralDAO ofertaDAO = new OfertaLaboralDAO();
    private final PostulacionDAO postulacionDAO = new PostulacionDAO();
    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

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

        // 2. Leer los dos números de la URL
        int ofertaId;
        int postulacionId;
        try {
            ofertaId = Integer.parseInt(request.getParameter("oferta"));
            postulacionId = Integer.parseInt(request.getParameter("postulacion"));
        } catch (NumberFormatException e) {
            response.sendError(400, "Datos inválidos");
            return;
        }

        try {
            String rut = empresa.getRut();

            // 3. La oferta tiene que ser de ESTA empresa
            OfertaLaboral oferta = null;
            for (OfertaLaboral o : ofertaDAO.listarPorEmpresa(rut)) {
                if (o.getId() == ofertaId) {
                    oferta = o;
                    break;
                }
            }
            if (oferta == null) {
                response.sendError(404, "Oferta no disponible");
                return;
            }

            // 4. La postulación tiene que ser de ESA oferta
            Postulacion postulacion = null;
            for (Postulacion p : postulacionDAO.listarPorEmpresa(rut)) {
                if (p.getId() == postulacionId
                        && p.getOfertaLaboral().getId() == ofertaId) {
                    postulacion = p;
                    break;
                }
            }
            if (postulacion == null) {
                response.sendError(404, "Postulación no disponible");
                return;
            }

            // 5. Recién ahora se carga el perfil completo del postulante.
            //    El número sale de la postulación, NO de la URL.
            Postulante postulante =
                    postulanteDAO.buscarPorId(postulacion.getPostulante().getCi());
            if (postulante == null) {
                response.sendError(404, "Postulante no disponible");
                return;
            }

            // 6. Datos calculados para la vista
            Integer edad = null;
            if (postulante.getFechaNacimiento() != null) {
                edad = Period.between(postulante.getFechaNacimiento(),
                        LocalDate.now()).getYears();
            }

            String cv = postulante.getCv() == null
                    ? "" : postulante.getCv().trim().toLowerCase();
            boolean cvEnlazable = cv.startsWith("http://") || cv.startsWith("https://");

            request.setAttribute("oferta", oferta);
            request.setAttribute("postulacion", postulacion);
            request.setAttribute("postulante", postulante);
            request.setAttribute("edad", edad);
            request.setAttribute("cvEnlazable", cvEnlazable);
            request.setAttribute("inicial", inicialDe(postulante.getNombre()));

        } catch (SQLException e) {
            throw new ServletException("No se pudo cargar el perfil del postulante", e);
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/empresa-postulante.jsp"
        ).forward(request, response);
    }

    private String inicialDe(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return "?";
        }
        String limpio = nombre.trim();
        return new String(Character.toChars(limpio.codePointAt(0))).toUpperCase();
    }
}