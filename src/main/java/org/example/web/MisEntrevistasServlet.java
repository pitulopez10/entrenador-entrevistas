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
import org.example.EstadoEntrevista;
import org.example.AreaDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/mis-entrevistas")
public class MisEntrevistasServlet extends HttpServlet {

    private final EntrevistaDAO entrevistaDAO =
            new EntrevistaDAO();
    private final AreaDAO areaDAO = new AreaDAO();


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

            long finalizadas = entrevistas.stream()
                    .filter(entrevista -> entrevista.getEstado() == EstadoEntrevista.TERMINADA)
                    .count();
            request.setAttribute("totalEntrevistas", entrevistas.size());
            request.setAttribute("entrevistasEnCurso", entrevistas.size() - finalizadas);
            request.setAttribute("entrevistasFinalizadas", finalizadas);

            String nombreBuscado = valor(request.getParameter("nombre"));
            String areaBuscada = valor(request.getParameter("area"));
            List<Entrevista> entrevistasFiltradas = new ArrayList<>();
            for (Entrevista entrevista : entrevistas) {
                String titulo = entrevista.getOfertaLaboral() != null
                        && entrevista.getOfertaLaboral().getTitulo() != null
                        ? entrevista.getOfertaLaboral().getTitulo().toLowerCase() : "";
                String area = entrevista.getOfertaLaboral() != null
                        && entrevista.getOfertaLaboral().getArea() != null
                        && entrevista.getOfertaLaboral().getArea().getNombre() != null
                        ? entrevista.getOfertaLaboral().getArea().getNombre().toLowerCase() : "";
                if ((nombreBuscado.isEmpty() || titulo.contains(nombreBuscado))
                        && (areaBuscada.isEmpty() || area.contains(areaBuscada))) {
                    entrevistasFiltradas.add(entrevista);
                }
            }


            int pagina = pagina(request.getParameter("pagina"));
            int totalPaginas = Math.max(1, (int) Math.ceil(entrevistasFiltradas.size() / 6.0));
            pagina = Math.min(pagina, totalPaginas);
            int desde = (pagina - 1) * 6;
            int hasta = Math.min(desde + 6, entrevistasFiltradas.size());
            request.setAttribute("entrevistas", entrevistasFiltradas.isEmpty()
                    ? new ArrayList<>() : entrevistasFiltradas.subList(desde, hasta));
            request.setAttribute("pagina", pagina);
            request.setAttribute("totalPaginas", totalPaginas);
            request.setAttribute("nombreBuscado", nombreBuscado);
            request.setAttribute("areaBuscada", areaBuscada);
            request.setAttribute("areas", areaDAO.listar());


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

    private int pagina(String valor) {
        try { return Math.max(1, Integer.parseInt(valor)); }
        catch (Exception e) { return 1; }
    }

    private String valor(String valor) {
        return valor == null ? "" : valor.trim().toLowerCase();
    }
}
