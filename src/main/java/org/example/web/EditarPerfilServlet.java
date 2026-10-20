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
import org.example.DTGenero;
import org.example.Postulante;
import org.example.PostulanteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@WebServlet("/editar-perfil")
@MultipartConfig(
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 12 * 1024 * 1024
)
public class EditarPerfilServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();

    private final CloudinaryService cloudinaryService =
            new CloudinaryService();


    // ==================================================
    // GET
    // ==================================================

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


        Postulante postulanteSesion =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );

        try {

            Postulante postulante =
                    postulanteDAO.buscarPorId(
                            postulanteSesion.getCi()
                    );

            if (postulante == null) {

                session.invalidate();

                response.sendRedirect(
                        request.getContextPath() + "/login"
                );

                return;
            }


            request.setAttribute(
                    "postulante",
                    postulante
            );


            request.getRequestDispatcher(
                    "/WEB-INF/views/editar-perfil.jsp"
            ).forward(
                    request,
                    response
            );


        } catch (SQLException e) {

            throw new ServletException(
                    "Error al cargar el perfil.",
                    e
            );
        }
    }


    // ==================================================
    // POST
    // ==================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        HttpSession session =
                request.getSession(false);

        if (session == null ||
                session.getAttribute("postulanteLogueado") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );

            return;
        }


        Postulante postulanteSesion =
                (Postulante) session.getAttribute(
                        "postulanteLogueado"
                );


        /*
         * Guardamos los IDs de los nuevos archivos
         * por si ocurre un error después de subirlos.
         */
        String nuevaFotoPublicId = null;
        String nuevoCvPublicId = null;


        try {

            Postulante postulante =
                    postulanteDAO.buscarPorId(
                            postulanteSesion.getCi()
                    );


            if (postulante == null) {

                session.invalidate();

                response.sendRedirect(
                        request.getContextPath() + "/login"
                );

                return;
            }


            // ==================================================
            // DATOS DEL FORMULARIO
            // ==================================================

            String nombre =
                    request.getParameter("nombre");

            String mail =
                    request.getParameter("mail");

            String telefonoTexto =
                    request.getParameter("telefono");

            String descripcion =
                    request.getParameter("descripcion");

            String fechaNacimientoTexto =
                    request.getParameter("fechaNacimiento");

            String generoTexto =
                    request.getParameter("genero");

            String localidad =
                    request.getParameter("localidad");

            String datosEstudio =
                    request.getParameter("datosEstudio");

            String datosExperiencia =
                    request.getParameter("datosExperiencia");


            // ==================================================
            // VALIDACIÓN NOMBRE
            // ==================================================

            if (nombre == null ||
                    nombre.trim().isEmpty()) {

                volverConError(
                        request,
                        response,
                        postulante,
                        "El nombre es obligatorio."
                );

                return;
            }


            // ==================================================
            // VALIDACIÓN CORREO
            // ==================================================

            if (mail == null ||
                    mail.trim().isEmpty()) {

                volverConError(
                        request,
                        response,
                        postulante,
                        "El correo es obligatorio."
                );

                return;
            }


            if (!mail.contains("@")) {

                volverConError(
                        request,
                        response,
                        postulante,
                        "El correo ingresado no es válido."
                );

                return;
            }


            // ==================================================
            // TELÉFONO
            // ==================================================

            int telefono = 0;

            if (telefonoTexto != null &&
                    !telefonoTexto.trim().isEmpty()) {

                try {

                    telefono =
                            Integer.parseInt(
                                    telefonoTexto.trim()
                            );

                } catch (NumberFormatException e) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El teléfono debe contener solamente números."
                    );

                    return;
                }
            }


            // ==================================================
            // FECHA DE NACIMIENTO
            // ==================================================

            LocalDate fechaNacimiento = null;

            if (fechaNacimientoTexto != null &&
                    !fechaNacimientoTexto.isBlank()) {

                try {

                    fechaNacimiento =
                            LocalDate.parse(
                                    fechaNacimientoTexto
                            );

                } catch (Exception e) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "La fecha de nacimiento no es válida."
                    );

                    return;
                }


                if (fechaNacimiento.isAfter(
                        LocalDate.now()
                )) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "La fecha de nacimiento no puede ser futura."
                    );

                    return;
                }
            }


            // ==================================================
            // GÉNERO
            // ==================================================

            DTGenero genero = null;

            if (generoTexto != null &&
                    !generoTexto.isBlank()) {

                try {

                    genero =
                            DTGenero.valueOf(
                                    generoTexto
                            );

                } catch (IllegalArgumentException e) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El género seleccionado no es válido."
                    );

                    return;
                }
            }


            // ==================================================
            // GUARDAMOS LOS ARCHIVOS ANTERIORES
            // ==================================================

            String fotoPublicIdAnterior =
                    postulante.getFotoPerfilPublicId();

            String cvPublicIdAnterior =
                    postulante.getCvPublicId();


            // ==================================================
            // FOTO DE PERFIL
            // ==================================================

            Part foto =
                    request.getPart(
                            "fotoPerfil"
                    );


            if (foto != null &&
                    foto.getSize() > 0) {

                String contentType =
                        foto.getContentType();


                if (contentType == null ||
                        !contentType.startsWith("image/")) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El archivo seleccionado como foto debe ser una imagen."
                    );

                    return;
                }


                byte[] contenidoFoto =
                        foto.getInputStream()
                                .readAllBytes();


                Map<String, String> resultadoFoto =
                        cloudinaryService.subirImagen(
                                contenidoFoto
                        );


                String nuevaFotoUrl =
                        resultadoFoto.get("url");

                nuevaFotoPublicId =
                        resultadoFoto.get("publicId");


                postulante.setFotoPerfil(
                        nuevaFotoUrl
                );

                postulante.setFotoPerfilPublicId(
                        nuevaFotoPublicId
                );
            }


            // ==================================================
            // CURRÍCULUM PDF
            // ==================================================

            Part cv =
                    request.getPart(
                            "cv"
                    );


            if (cv != null &&
                    cv.getSize() > 0) {

                String contentTypeCv =
                        cv.getContentType();

                String nombreArchivo =
                        cv.getSubmittedFileName();


                if (nombreArchivo == null ||
                        !nombreArchivo
                                .toLowerCase()
                                .endsWith(".pdf")) {

                    limpiarNuevaFoto(
                            nuevaFotoPublicId
                    );

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El currículum debe ser un archivo PDF."
                    );

                    return;
                }


                if (contentTypeCv == null ||
                        !contentTypeCv.equalsIgnoreCase(
                                "application/pdf"
                        )) {

                    limpiarNuevaFoto(
                            nuevaFotoPublicId
                    );

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El archivo seleccionado como currículum no es un PDF válido."
                    );

                    return;
                }


                byte[] contenidoCv =
                        cv.getInputStream()
                                .readAllBytes();


                Map<String, String> resultadoCv =
                        cloudinaryService.subirCv(
                                contenidoCv,
                                nombreArchivo
                        );


                String nuevaCvUrl =
                        resultadoCv.get("url");

                nuevoCvPublicId =
                        resultadoCv.get("publicId");


                postulante.setCv(
                        nuevaCvUrl
                );

                postulante.setCvPublicId(
                        nuevoCvPublicId
                );
            }


            // ==================================================
            // ACTUALIZAR DATOS DEL OBJETO
            // ==================================================

            postulante.setNombre(
                    nombre.trim()
            );

            postulante.setMail(
                    mail.trim()
            );

            postulante.setTelefono(
                    telefono
            );

            postulante.setDescripcion(
                    descripcion
            );

            postulante.setFechaNacimiento(
                    fechaNacimiento
            );

            postulante.setGenero(
                    genero
            );

            postulante.setLocalidad(
                    localidad
            );

            postulante.setDatosEstudio(
                    datosEstudio
            );

            postulante.setDatosExperiencia(
                    datosExperiencia
            );


            // ==================================================
            // GUARDAR EN BASE DE DATOS
            // ==================================================

            postulanteDAO.modificar(
                    postulante
            );


            // ==================================================
            // ELIMINAR ARCHIVOS ANTERIORES
            // SOLO DESPUÉS DE ACTUALIZAR LA BD
            // ==================================================

            if (nuevaFotoPublicId != null &&
                    fotoPublicIdAnterior != null &&
                    !fotoPublicIdAnterior.isBlank()) {

                try {

                    cloudinaryService.eliminarImagen(
                            fotoPublicIdAnterior
                    );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }


            if (nuevoCvPublicId != null &&
                    cvPublicIdAnterior != null &&
                    !cvPublicIdAnterior.isBlank()) {

                try {

                    cloudinaryService.eliminarCv(
                            cvPublicIdAnterior
                    );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }


            // ==================================================
            // ACTUALIZAR SESIÓN
            // ==================================================

            session.setAttribute(
                    "postulanteLogueado",
                    postulante
            );


            // ==================================================
            // PRG
            // ==================================================

            response.sendRedirect(
                    request.getContextPath()
                            + "/perfil?actualizado=ok"
            );


        } catch (SQLException e) {

            /*
             * Si se subieron archivos nuevos pero falló
             * la actualización de la base de datos,
             * eliminamos esos archivos nuevos.
             */

            limpiarNuevaFoto(
                    nuevaFotoPublicId
            );

            limpiarNuevoCv(
                    nuevoCvPublicId
            );


            throw new ServletException(
                    "Error al actualizar el perfil.",
                    e
            );


        } catch (Exception e) {

            limpiarNuevaFoto(
                    nuevaFotoPublicId
            );

            limpiarNuevoCv(
                    nuevoCvPublicId
            );


            throw new ServletException(
                    "Error al actualizar los archivos del perfil.",
                    e
            );
        }
    }


    // ==================================================
    // VOLVER AL FORMULARIO CON ERROR
    // ==================================================

    private void volverConError(
            HttpServletRequest request,
            HttpServletResponse response,
            Postulante postulante,
            String mensaje
    ) throws ServletException, IOException {

        request.setAttribute(
                "error",
                mensaje
        );

        request.setAttribute(
                "postulante",
                postulante
        );


        request.getRequestDispatcher(
                "/WEB-INF/views/editar-perfil.jsp"
        ).forward(
                request,
                response
        );
    }


    // ==================================================
    // LIMPIAR FOTO NUEVA SI HUBO ERROR
    // ==================================================

    private void limpiarNuevaFoto(
            String publicId
    ) {

        if (publicId == null ||
                publicId.isBlank()) {

            return;
        }


        try {

            cloudinaryService.eliminarImagen(
                    publicId
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==================================================
    // LIMPIAR CV NUEVO SI HUBO ERROR
    // ==================================================

    private void limpiarNuevoCv(
            String publicId
    ) {

        if (publicId == null ||
                publicId.isBlank()) {

            return;
        }


        try {

            cloudinaryService.eliminarCv(
                    publicId
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}