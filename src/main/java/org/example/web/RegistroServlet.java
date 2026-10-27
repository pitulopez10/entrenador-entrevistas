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

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();


    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher(
                "/WEB-INF/views/registro.jsp"
        ).forward(
                request,
                response
        );
    }


    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding(
                "UTF-8"
        );


        // ==================================================
        // LEER FORMULARIO
        // ==================================================

        String ciTexto =
                textoLimpio(
                        request.getParameter("ci")
                );

        String nombre =
                textoLimpio(
                        request.getParameter("nombre")
                );

        String correo =
                textoLimpio(
                        request.getParameter("correo")
                );

        String generoTexto =
                textoLimpio(
                        request.getParameter("genero")
                );

        String contrasena =
                request.getParameter("contrasena") == null
                        ? ""
                        : request.getParameter("contrasena");

        String confirmar =
                request.getParameter("confirmar") == null
                        ? ""
                        : request.getParameter("confirmar");


        try {

            // ==================================================
            // VALIDAR DATOS
            // ==================================================

            String error =
                    validar(
                            ciTexto,
                            nombre,
                            correo,
                            generoTexto,
                            contrasena,
                            confirmar
                    );


            if (error != null) {

                mostrarError(
                        request,
                        response,
                        error
                );

                return;
            }


            // ==================================================
            // CONVERTIR CI
            // ==================================================

            int ci =
                    Integer.parseInt(
                            ciTexto
                    );


            // ==================================================
            // VALIDAR DUPLICADOS
            // ==================================================

            if (postulanteDAO.buscarPorId(ci) != null) {

                mostrarError(
                        request,
                        response,
                        "Ya existe un perfil con esa cédula."
                );

                return;
            }


            if (postulanteDAO.buscarPorMail(correo) != null) {

                mostrarError(
                        request,
                        response,
                        "Ya existe un perfil con ese correo."
                );

                return;
            }


            // ==================================================
            // CREAR POSTULANTE
            // ==================================================

            Postulante nuevo =
                    new Postulante();


            nuevo.setCi(
                    ci
            );

            nuevo.setNombre(
                    nombre
            );

            nuevo.setMail(
                    correo
            );

            nuevo.setPassword(
                    contrasena
            );

            nuevo.setFechaRegistro(
                    LocalDate.now()
            );

            nuevo.setGenero(
                    DTGenero.valueOf(
                            generoTexto
                    )
            );

            nuevo.setTelefono(
                    0
            );

            nuevo.setBloqueado(
                    false
            );


            postulanteDAO.agregar(
                    nuevo
            );


            // ==================================================
            // INICIAR SESIÓN AUTOMÁTICAMENTE
            // ==================================================

            HttpSession anterior =
                    request.getSession(false);


            if (anterior != null) {

                anterior.invalidate();
            }


            request.getSession(true)
                    .setAttribute(
                            "postulanteLogueado",
                            postulanteDAO.buscarPorId(ci)
                    );


            response.sendRedirect(
                    request.getContextPath()
                            + "/ofertas"
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al crear el perfil",
                    e
            );
        }
    }


    // ==================================================
    // VALIDAR FORMULARIO
    // ==================================================

    private String validar(
            String ci,
            String nombre,
            String correo,
            String genero,
            String contrasena,
            String confirmar
    ) {


        // ==================================================
        // NOMBRE
        // ==================================================

        if (nombre.isEmpty()) {

            return "El nombre es obligatorio.";
        }


        if (nombre.length() > 100) {

            return "El nombre no puede superar los 100 caracteres.";
        }


        // ==================================================
        // CÉDULA
        // ==================================================

        if (!ci.matches("\\d{7,8}")) {

            return "La cédula debe contener 7 u 8 números, sin puntos ni guiones.";
        }


        if (!cedulaUruguayaValida(ci)) {

            return "La cédula ingresada no es una cédula uruguaya válida.";
        }


        // ==================================================
        // CORREO
        // ==================================================

        if (!correo.matches(
                "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"
        )) {

            return "Ingresá un correo electrónico válido.";
        }


        if (correo.length() > 100) {

            return "El correo no puede superar los 100 caracteres.";
        }


        // ==================================================
        // GÉNERO
        // ==================================================

        try {

            DTGenero.valueOf(
                    genero
            );

        } catch (IllegalArgumentException e) {

            return "Seleccioná un género válido.";
        }


        // ==================================================
        // CONTRASEÑA
        // ==================================================

        if (contrasena.length() < 6) {

            return "La contraseña debe tener al menos 6 caracteres.";
        }


        if (!contrasena.equals(confirmar)) {

            return "Las contraseñas no coinciden.";
        }


        return null;
    }


    // ==================================================
    // VALIDAR CÉDULA URUGUAYA
    // ==================================================

    private boolean cedulaUruguayaValida(
            String ci
    ) {

        /*
         * La CI se trabaja como 8 dígitos:
         *
         * 7 dígitos principales
         * +
         * 1 dígito verificador.
         *
         * Si se recibe una CI de 7 dígitos,
         * se completa con un cero a la izquierda.
         */

        if (ci == null ||
                !ci.matches("\\d{7,8}")) {

            return false;
        }


        String cedula =
                ci;


        if (cedula.length() == 7) {

            cedula =
                    "0" + cedula;
        }


        int[] factores = {
                2, 9, 8, 7, 6, 3, 4
        };


        int suma =
                0;


        for (int i = 0;
             i < 7;
             i++) {

            int digito =
                    Character.getNumericValue(
                            cedula.charAt(i)
                    );


            suma +=
                    digito
                            * factores[i];
        }


        int digitoCalculado =
                (10 - (suma % 10)) % 10;


        int digitoIngresado =
                Character.getNumericValue(
                        cedula.charAt(7)
                );


        return digitoCalculado
                == digitoIngresado;
    }


    // ==================================================
    // MOSTRAR ERROR
    // ==================================================

    private void mostrarError(
            HttpServletRequest request,
            HttpServletResponse response,
            String mensaje
    ) throws ServletException, IOException {

        request.setAttribute(
                "error",
                mensaje
        );


        request.getRequestDispatcher(
                "/WEB-INF/views/registro.jsp"
        ).forward(
                request,
                response
        );
    }


    // ==================================================
    // LIMPIAR TEXTO
    // ==================================================

    private String textoLimpio(
            String valor
    ) {

        return valor == null
                ? ""
                : valor.trim();
    }
}