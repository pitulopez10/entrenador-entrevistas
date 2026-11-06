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
import org.example.AreaDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/mis-postulaciones")
public class MisPostulacionesServlet extends HttpServlet {

    private final PostulacionDAO postulacionDAO =
            new PostulacionDAO();
    private final AreaDAO areaDAO = new AreaDAO();

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

            String nombreBuscado = valor(request.getParameter("nombre"));
            String areaBuscada = valor(request.getParameter("area"));
            List<Postulacion> postulacionesFiltradas = new ArrayList<>();
            for (Postulacion postulacion : postulaciones) {
                String titulo = postulacion.getOfertaLaboral() != null
                        && postulacion.getOfertaLaboral().getTitulo() != null
                        ? postulacion.getOfertaLaboral().getTitulo().toLowerCase() : "";
                String area = postulacion.getOfertaLaboral() != null
                        && postulacion.getOfertaLaboral().getArea() != null
                        && postulacion.getOfertaLaboral().getArea().getNombre() != null
                        ? postulacion.getOfertaLaboral().getArea().getNombre().toLowerCase() : "";
                if ((nombreBuscado.isEmpty() || titulo.contains(nombreBuscado))
                        && (areaBuscada.isEmpty() || area.contains(areaBuscada))) {
                    postulacionesFiltradas.add(postulacion);
                }
            }

            int pagina = pagina(request.getParameter("pagina"));
            int totalPaginas = Math.max(1, (int) Math.ceil(postulacionesFiltradas.size() / 6.0));
            pagina = Math.min(pagina, totalPaginas);
            int desde = (pagina - 1) * 6;
            int hasta = Math.min(desde + 6, postulacionesFiltradas.size());
            request.setAttribute("postulaciones", postulacionesFiltradas.isEmpty()
                    ? new ArrayList<>() : postulacionesFiltradas.subList(desde, hasta));
            request.setAttribute("pagina", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("nombreBuscado", nombreBuscado);
            request.setAttribute("areaBuscada", areaBuscada);
            request.setAttribute("areas", areaDAO.listar());

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

    private String valor(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }
}
