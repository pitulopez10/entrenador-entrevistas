package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.example.CloudinaryService;
import org.example.Empresa;
import org.example.EmpresaDAO;

import java.io.IOException;
import java.util.Map;

@WebServlet("/editar-empresa")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class EditarEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();
    private final CloudinaryService cloudinaryService = new CloudinaryService();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        Empresa empresa = empresaEnSesion(request);
        if (empresa == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        cargarValores(request, empresa);
        request.getRequestDispatcher("/WEB-INF/views/editar-empresa.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Empresa empresaSesion = empresaEnSesion(request);
        if (empresaSesion == null) {
            response.sendRedirect(request.getContextPath() + "/login-empresa");
            return;
        }

        Empresa empresa;
        try {
            empresa = empresaDAO.buscarPorId(empresaSesion.getRut());
            if (empresa == null) {
                response.sendRedirect(request.getContextPath() + "/login-empresa");
                return;
            }

        String nombre = valor(request, "nombre");
        String correo = valor(request, "correo");
        if (nombre.isBlank() || correo.isBlank()) {
            cargarValores(request, empresa);
            request.setAttribute("error", "El nombre y el correo son obligatorios.");
            request.getRequestDispatcher("/WEB-INF/views/editar-empresa.jsp")
                    .forward(request, response);
            return;
        }

        String sitio = valor(request, "sitioWeb");
        String telefono = valor(request, "telefono");
        if ((!sitio.isBlank() && !EnlacesWebServlet.esHttp(sitio))
                || (!telefono.isBlank() && !telefono.matches("[0-9]{1,9}"))) {
            cargarValores(request, empresa);
            request.setAttribute("valorNombre", nombre);
            request.setAttribute("valorCorreo", correo);
            request.setAttribute("valorTelefono", telefono);
            request.setAttribute("valorSitio", sitio);
            request.setAttribute("valorDescripcion", valor(request, "descripcion"));
            request.setAttribute("error", "Ingresá un sitio http:// o https:// válido y un teléfono de hasta 9 dígitos.");
            request.getRequestDispatcher("/WEB-INF/views/editar-empresa.jsp").forward(request, response);
            return;
        }
        empresa.setNombre(nombre);
        empresa.setMail(correo);
        empresa.setTelefono(numero(valor(request, "telefono")));
        empresa.setSitioWeb(valor(request, "sitioWeb"));
        empresa.setDescripcion(valor(request, "descripcion"));

        Part logo = request.getPart("logo");
        if (logo != null && logo.getSize() > 0) {
            String logoAnterior = empresa.getLogoPublicId();
            Map<String, String> datos = cloudinaryService.subirImagen(
                    logo.getInputStream().readAllBytes());
            empresa.setLogo(datos.get("url"));
            empresa.setLogoPublicId(datos.get("publicId"));
            if (logoAnterior != null && !logoAnterior.isBlank()) {
                cloudinaryService.eliminarImagen(logoAnterior);
            }
        }

            empresaDAO.modificar(empresa);
            request.getSession().setAttribute("empresaLogueada", empresa);
            response.sendRedirect(request.getContextPath() + "/empresa-perfil?guardado=1");
        } catch (java.sql.SQLException e) {
            throw new ServletException("No se pudo actualizar la empresa", e);
        }
    }

    private Empresa empresaEnSesion(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;
        Empresa sesion = (Empresa) session.getAttribute("empresaLogueada");
        if (sesion == null) return null;
        try {
            return empresaDAO.buscarPorId(sesion.getRut());
        } catch (java.sql.SQLException e) {
            throw new IllegalStateException("No se pudo cargar la empresa", e);
        }
    }

    private void cargarValores(HttpServletRequest request, Empresa empresa) {
        request.setAttribute("empresa", empresa);
        request.setAttribute("valorNombre", empresa.getNombre());
        request.setAttribute("valorCorreo", empresa.getMail());
        request.setAttribute("valorTelefono", empresa.getTelefono() == 0 ? "" : empresa.getTelefono());
        request.setAttribute("valorSitio", empresa.getSitioWeb());
        request.setAttribute("valorDescripcion", empresa.getDescripcion());
    }

    private String valor(HttpServletRequest request, String nombre) {
        String valor = request.getParameter(nombre);
        return valor == null ? "" : valor.trim();
    }

    private int numero(String valor) {
        if (valor.isBlank()) return 0;
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
