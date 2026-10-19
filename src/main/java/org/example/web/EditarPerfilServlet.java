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
        maxRequestSize = 7 * 1024 * 1024
)
public class EditarPerfilServlet extends HttpServlet {

    private final PostulanteDAO postulanteDAO =
            new PostulanteDAO();

    private final CloudinaryService cloudinaryService =
            new CloudinaryService();


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


            // =========================
            // DATOS DEL FORMULARIO
            // =========================

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


            // =========================
            // VALIDACIONES BÁSICAS
            // =========================

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


            // =========================
            // FOTO DE PERFIL
            // =========================

            Part foto =
                    request.getPart(
                            "fotoPerfil"
                    );

            if (foto != null &&
                    foto.getSize() > 0) {

                String contentType =
                        foto.getContentType();

                if (contentType == null ||
                        !contentType.startsWith(
                                "image/"
                        )) {

                    volverConError(
                            request,
                            response,
                            postulante,
                            "El archivo seleccionado debe ser una imagen."
                    );

                    return;
                }

                byte[] contenido =
                        foto.getInputStream()
                                .readAllBytes();

                Map<String, String> resultado =
                        cloudinaryService.subirImagen(
                                contenido
                        );

                String nuevaUrl =
                        resultado.get("url");

                String nuevoPublicId =
                        resultado.get("publicId");


                String publicIdAnterior =
                        postulante.getFotoPerfilPublicId();


                postulante.setFotoPerfil(
                        nuevaUrl
                );

                postulante.setFotoPerfilPublicId(
                        nuevoPublicId
                );


                if (publicIdAnterior != null &&
                        !publicIdAnterior.isBlank()) {

                    try {

                        cloudinaryService.eliminarImagen(
                                publicIdAnterior
                        );

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            }


            // =========================
            // ACTUALIZAR OBJETO
            // =========================

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


            // =========================
            // GUARDAR EN BD
            // =========================

            postulanteDAO.modificar(
                    postulante
            );


            // Actualizamos la sesión
            session.setAttribute(
                    "postulanteLogueado",
                    postulante
            );


            // PRG
            response.sendRedirect(
                    request.getContextPath()
                            + "/perfil?actualizado=ok"
            );

        } catch (SQLException e) {

            throw new ServletException(
                    "Error al actualizar el perfil.",
                    e
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Error al subir la imagen.",
                    e
            );
        }
    }


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
}