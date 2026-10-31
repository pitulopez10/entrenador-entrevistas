package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EmpresaDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-editar-empresa")
public class AdminEditarEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        String rut = request.getParameter("rut");
        if (rut == null || rut.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin-empresas");
            return;
        }

        try {
            Empresa empresa = empresaDAO.buscarPorId(rut);
            if (empresa == null) {
                response.sendRedirect(request.getContextPath() + "/admin-empresas");
                return;
            }

            request.setAttribute("empresa", empresa);
            request.getRequestDispatcher("/WEB-INF/views/admin-editar-empresa.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al obtener la empresa para edición", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login-admin");
            return;
        }

        try {
            String rut = request.getParameter("rut");
            Empresa empresa = empresaDAO.buscarPorId(rut);

            if (empresa != null) {
                empresa.setNombre(request.getParameter("nombre"));
                empresa.setMail(request.getParameter("mail"));
                empresa.setPassword(request.getParameter("password"));
                empresa.setTelefono(Integer.parseInt(request.getParameter("telefono")));
                empresa.setSitioWeb(request.getParameter("sitioWeb"));
                empresa.setDescripcion(request.getParameter("descripcion"));

                empresaDAO.modificar(empresa);
            }

            response.sendRedirect(request.getContextPath() + "/admin-empresas");

        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error al modificar la empresa", e);
        }
    }
}
