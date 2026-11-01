package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("postulanteLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }

        Postulante postulanteSesion =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );

        try {

            Postulante postulante =
                    postulanteDAO.buscarPorId(
                            postulanteSesion.getCi()
                    );

            if (postulante == null) {

                session.invalidate();

                response.sendRedirect(
                        request.getContextPath() + "/login"
                );

                return;
            }

            /*
             * Actualizamos también el objeto de la sesión.
             * Así siempre tenemos los datos más recientes.
             */
            session.setAttribute(
                    "postulanteLogueado",
                    postulante
            );

            request.setAttribute(
                    "postulante",
                    postulante
            );

            List<String> pendientes = new ArrayList<>();
            if (vacio(postulante.getFotoPerfil())) pendientes.add("foto de perfil");
            if (postulante.getTelefono() == 0) pendientes.add("teléfono");
            if (postulante.getFechaNacimiento() == null) pendientes.add("fecha de nacimiento");
            if (postulante.getGenero() == null) pendientes.add("género");
            if (vacio(postulante.getLocalidad())) pendientes.add("localidad");
            if (vacio(postulante.getDescripcion())) pendientes.add("descripción");
            if (vacio(postulante.getDatosEstudio())) pendientes.add("formación");
            if (vacio(postulante.getDatosExperiencia())) pendientes.add("experiencia");
            if (vacio(postulante.getCv())) pendientes.add("currículum");

            request.setAttribute("porcentajeCompleto",
                    (9 - pendientes.size()) * 100 / 9);
            request.setAttribute("pendientesTexto", String.join(", ", pendientes));

            request.getRequestDispatcher(
                    "/WEB-INF/views/perfil.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar el perfil del postulante",
                    e
            );
        }
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
