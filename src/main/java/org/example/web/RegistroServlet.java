package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.DTGenero;
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO = new PostulanteDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/registro.jsp"
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. Leer lo que mandó el formulario
        String ciTexto = textoLimpio(request.getParameter("ci"));
        String nombre = textoLimpio(request.getParameter("nombre"));
        String correo = textoLimpio(request.getParameter("correo"));
        String generoTexto = textoLimpio(request.getParameter("genero"));
        String contrasena = request.getParameter("contrasena") == null
                ? "" : request.getParameter("contrasena");
        String confirmar = request.getParameter("confirmar") == null
                ? "" : request.getParameter("confirmar");

        try {
            // 2. Validar el formato de los datos
            String error = validar(ciTexto, nombre, correo,
                    generoTexto, contrasena, confirmar);

            if (error != null) {
                mostrarError(request, response, error);
                return;
            }

            // 2b. Validar que no exista ya en la base
            int ci = Integer.parseInt(ciTexto);

            if (postulanteDAO.buscarPorId(ci) != null) {
                mostrarError(request, response,
                        "Ya existe un perfil con esa cédula.");
                return;
            }

            if (postulanteDAO.buscarPorMail(correo) != null) {
                mostrarError(request, response,
                        "Ya existe un perfil con ese correo.");
                return;
            }

            // 3. Crear el postulante (mismos valores iniciales que la consola)
            Postulante nuevo = new Postulante();
            nuevo.setCi(ci);
            nuevo.setNombre(nombre);
            nuevo.setMail(correo);
            nuevo.setPassword(contrasena);
            nuevo.setFechaRegistro(LocalDate.now());
            nuevo.setGenero(DTGenero.valueOf(generoTexto));
            nuevo.setTelefono(0);
            nuevo.setBloqueado(false);

            postulanteDAO.agregar(nuevo);

            // 4. Iniciar sesión automáticamente (igual que el login)
            HttpSession anterior = request.getSession(false);
            if (anterior != null) {
                anterior.invalidate();
            }

            request.getSession(true).setAttribute(
                    "postulanteLogueado",
                    postulanteDAO.buscarPorId(ci)
            );

            response.sendRedirect(request.getContextPath() + "/ofertas");

        } catch (SQLException e) {
            throw new ServletException("Error al crear el perfil", e);
        }
    }

    /**
     * Devuelve el mensaje del primer error encontrado,
     * o null si los datos tienen un formato correcto.
     */
    private String validar(String ci, String nombre, String correo,
                           String genero, String contrasena, String confirmar) {

        if (nombre.isEmpty()) {
            return "El nombre es obligatorio.";
        }

        if (nombre.length() > 100) {
            return "El nombre no puede superar los 100 caracteres.";
        }

        if (!ci.matches("\\d{6,8}")) {
            return "La cédula debe tener entre 6 y 8 números, sin puntos ni guiones.";
        }

        if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            return "Ingresá un correo electrónico válido.";
        }

        if (correo.length() > 100) {
            return "El correo no puede superar los 100 caracteres.";
        }

        try {
            DTGenero.valueOf(genero);
        } catch (IllegalArgumentException e) {
            return "Seleccioná un género válido.";
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
                "/WEB-INF/views/registro.jsp"
        ).forward(request, response);
    }

    private String textoLimpio(String valor) {
        return valor == null ? "" : valor.trim();
    }
}