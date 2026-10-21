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
import java.time.LocalDate;

@WebServlet("/registro-empresa")
public class RegistroEmpresaServlet extends HttpServlet {

    private final EmpresaDAO empresaDAO = new EmpresaDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/registro-empresa.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Leer lo que mandó el formulario
        String rut = textoLimpio(request.getParameter("rut"));
        String nombre = textoLimpio(request.getParameter("nombre"));
        String correo = textoLimpio(request.getParameter("correo"));
        String contrasena = request.getParameter("contrasena") == null
                ? "" : request.getParameter("contrasena");
        String confirmar = request.getParameter("confirmar") == null
                ? "" : request.getParameter("confirmar");

        try {
            // 2. Validar el formato de los datos
            String error = validar(rut, nombre, correo, contrasena, confirmar);

            if (error != null) {
                mostrarError(request, response, error);
                return;
            }

            // 2b. Validar que no exista ya en la base
            if (empresaDAO.buscarPorId(rut) != null) {
                mostrarError(request, response,
                        "Ya existe una empresa con ese RUT.");
                return;
            }

            if (empresaDAO.buscarPorMail(correo) != null) {
                mostrarError(request, response,
                        "Ya existe una empresa con ese correo.");
                return;
            }

            // 3. Crear la empresa (los datos opcionales quedan vacíos)
            Empresa nueva = new Empresa();
            nueva.setRut(rut);
            nueva.setNombre(nombre);
            nueva.setMail(correo);
            nueva.setPassword(contrasena);
            nueva.setFechaRegistro(LocalDate.now());
            nueva.setTelefono(0);
            nueva.setBloqueado(false);

            empresaDAO.agregar(nueva);

            // 4. Iniciar sesión automáticamente como empresa
            HttpSession anterior = request.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }

            request.getSession(true).setAttribute(
                    "empresaLogueada",
                    empresaDAO.buscarPorId(rut)
            );


            response.sendRedirect(request.getContextPath() + "/empresa");

        } catch (SQLException e) {
            throw new ServletException("Error al crear la empresa", e);
        }
    }

    /**
     * Devuelve el mensaje del primer error encontrado,
     * o null si los datos tienen un formato correcto.
     */
    private String validar(String rut, String nombre, String correo,
                           String contrasena, String confirmar) {

        if (nombre.isEmpty()) {
            return "El nombre de la empresa es obligatorio.";
        }

        if (nombre.length() > 100) {
            return "El nombre no puede superar los 100 caracteres.";
        }

        if (!rut.matches("\\d{12}")) {
            return "El RUT debe tener 12 números, sin puntos ni guiones.";
        }

        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "Ingresá un correo electrónico válido.";
        }

        if (correo.length() > 100) {
            return "El correo no puede superar los 100 caracteres.";
        }

        if (contrasena.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }

        if (!contrasena.equals(confirmar)) {
            return "Las contraseñas no coinciden.";
        }

        return null;
    }

    // Vuelve al formulario con el mensaje; los datos escritos se conservan solos
    private void mostrarError(HttpServletRequest request,
                              HttpServletResponse response,
                              String mensaje)
            throws ServletException, IOException {

        request.setAttribute("error", mensaje);

        request.getRequestDispatcher(
                "/WEB-INF/views/registro-empresa.jsp"
        ).forward(request, response);
    }

    private String textoLimpio(String valor) {
        return valor == null ? "" : valor.trim();
    }
}
