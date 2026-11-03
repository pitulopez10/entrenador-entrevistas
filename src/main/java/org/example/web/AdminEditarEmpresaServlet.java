package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.Empresa;
import org.example.EmpresaDAO;
import org.example.CloudinaryService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin-editar-empresa")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class AdminEditarEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final CloudinaryService cloudinaryService = new CloudinaryService();

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

                Part logo = request.getPart("logo");
                if (logo != null && logo.getSize() > 0) {
                    String logoAnterior = empresa.getLogoPublicId();
                    java.util.Map<String, String> datos = cloudinaryService.subirImagen(
                            logo.getInputStream().readAllBytes());
                    empresa.setLogo(datos.get("url"));
                    empresa.setLogoPublicId(datos.get("publicId"));
                    if (logoAnterior != null && !logoAnterior.isBlank()) {
                        cloudinaryService.eliminarImagen(logoAnterior);
                    }
                }

                empresaDAO.modificar(empresa);
            }

            response.sendRedirect(request.getContextPath() + "/admin-empresas");

        } catch (SQLException | NumberFormatException | java.io.IOException e) {
            throw new ServletException("Error al modificar la empresa", e);
        }
    }
}
