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
import org.example.EstadoPostulacion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

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

            int seleccionadas = 0;
            int rechazadas = 0;
            for (Postulacion postulacion : postulaciones) {
                if (postulacion.getEstado() == EstadoPostulacion.seleccionado) seleccionadas++;
                if (postulacion.getEstado() == EstadoPostulacion.rechazado) rechazadas++;
            }
            request.setAttribute("totalPostulaciones", postulaciones.size());
            request.setAttribute("postulacionesSeleccionadas", seleccionadas);
            request.setAttribute("postulacionesRechazadas", rechazadas);

            int pagina = pagina(request.getParameter("pagina"));
            int totalPaginas = Math.max(1, (int) Math.ceil(postulaciones.size() / 6.0));
            pagina = Math.min(pagina, totalPaginas);
            int desde = (pagina - 1) * 6;
            int hasta = Math.min(desde + 6, postulaciones.size());
            request.setAttribute("postulaciones", postulaciones.isEmpty()
                    ? new ArrayList<>() : postulaciones.subList(desde, hasta));
            request.setAttribute("pagina", pagina);
            request.setAttribute("totalPaginas", totalPaginas);

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

    private int pagina(String valor) {
        try { return Math.max(1, Integer.parseInt(valor)); }
        catch (Exception e) { return 1; }
    }
}
