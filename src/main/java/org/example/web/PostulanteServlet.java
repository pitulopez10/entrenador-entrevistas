package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/postulantes")
public class PostulanteServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {

            List<Postulante> postulantes = postulanteDAO.listar();

            request.setAttribute("postulantes", postulantes);

            request.getRequestDispatcher(
                    "/WEB-INF/views/postulantes.jsp"
            ).forward(request, response);

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar los postulantes",
                    e
            );
        }
    }
}
