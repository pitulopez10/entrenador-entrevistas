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

@WebServlet("/login")
public class LoginPostulanteServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String ofertaId =
                request.getParameter("ofertaId");

        if (ofertaId != null &&
                !ofertaId.isBlank()) {

            request.setAttribute(
                    "ofertaId",
                    ofertaId
            );
        }

        request.getRequestDispatcher(
                "/WEB-INF/views/login-postulante.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String correo =
                request.getParameter("correo");

        String contrasena =
                request.getParameter("contrasena");

        String ofertaId =
                request.getParameter("ofertaId");

        if (correo == null ||
                correo.isBlank() ||
                contrasena == null ||
                contrasena.isBlank()) {

            request.setAttribute(
                    "error",
                    "Debés ingresar correo y contraseña."
            );

            request.setAttribute(
                    "ofertaId",
                    ofertaId
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/login-postulante.jsp"
            ).forward(request, response);

            return;
        }

        try {

            Postulante postulante =
                    postulanteDAO.iniciarSesion(
                            correo,
                            contrasena
                    );

            if (postulante == null) {

                request.setAttribute(
                        "error",
                        "Correo o contraseña incorrectos."
                );

                request.setAttribute(
                        "ofertaId",
                        ofertaId
                );

                request.getRequestDispatcher(
                        "/WEB-INF/views/login-postulante.jsp"
                ).forward(request, response);

                return;
            }

            if (postulante.isBloqueado()) {

                request.setAttribute(
                        "error",
                        "Tu cuenta se encuentra bloqueada."
                );

                request.setAttribute(
                        "ofertaId",
                        ofertaId
                );

                request.getRequestDispatcher(
                        "/WEB-INF/views/login-postulante.jsp"
                ).forward(request, response);

                return;
            }

            HttpSession session =
                    request.getSession();

            session.setAttribute(
                    "postulanteLogueado",
                    postulante
            );

            /*
             * Si venía desde una oferta,
             * volvemos a esa oferta.
             */
            if (ofertaId != null &&
                    !ofertaId.isBlank()) {

                response.sendRedirect(
                        request.getContextPath()
                                + "/oferta?id="
                                + ofertaId
                );

                return;
            }

            /*
             * Login normal.
             */
            response.sendRedirect(
                    request.getContextPath()
                            + "/ofertas"
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al iniciar sesión",
                    e
            );
        }
    }
}