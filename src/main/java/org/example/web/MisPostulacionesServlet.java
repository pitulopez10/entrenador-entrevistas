package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Postulacion;
import org.example.PostulacionDAO;
import org.example.Postulante;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/mis-postulaciones")
public class MisPostulacionesServlet extends HttpServlet {

    private final PostulacionDAO postulacionDAO =
            new PostulacionDAO();

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

        Postulante postulante =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );

        try {

            List<Postulacion> postulaciones =
                    postulacionDAO.listarPorPostulante(
                            postulante.getCi()
                    );

            request.setAttribute(
                    "postulaciones",
                    postulaciones
            );

            request.getRequestDispatcher(
                    "/WEB-INF/views/mis-postulaciones.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar las postulaciones",
                    e
            );
        }
    }
}