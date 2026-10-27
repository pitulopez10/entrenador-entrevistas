package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.Entrevista;
import org.example.EntrevistaDAO;
import org.example.Postulante;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/mis-entrevistas")
public class MisEntrevistasServlet extends HttpServlet {

    private final EntrevistaDAO entrevistaDAO =
            new EntrevistaDAO();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);


        // ==================================================
        // VALIDAR LOGIN
        // ==================================================

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

            // ==================================================
            // BUSCAR ENTREVISTAS DEL POSTULANTE
            // ==================================================

            List<Entrevista> entrevistas =
                    entrevistaDAO.listarPorPostulante(
                            postulante.getCi()
                    );


            request.setAttribute(
                    "entrevistas",
                    entrevistas
            );


            request.getRequestDispatcher(
                    "/WEB-INF/views/mis-entrevistas.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar las entrevistas.",
                    e
            );
        }
    }
}