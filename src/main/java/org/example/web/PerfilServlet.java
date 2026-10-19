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
}