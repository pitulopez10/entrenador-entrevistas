package org.example.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.CloudinaryConfig;

import java.io.IOException;
import java.util.Map;

@WebServlet("/prueba-cloudinary")
@MultipartConfig
public class PruebaCloudinaryServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        Part archivo = request.getPart("archivo");

        if (archivo == null || archivo.getSize() == 0) {
            response.getWriter().println("No se seleccionó ningún archivo.");
            return;
        }

        try {

            byte[] contenido = archivo.getInputStream().readAllBytes();

            Map<?, ?> resultado =
                    CloudinaryConfig
                            .getCloudinary()
                            .uploader()
                            .upload(
                                    contenido,
                                    Map.of(
                                            "folder",
                                            "entrenador-entrevista"
                                    )
                            );

            String url =
                    resultado.get("secure_url").toString();

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2>Archivo subido correctamente</h2>"
            );

            response.getWriter().println(
                    "<p>URL:</p>"
            );

            response.getWriter().println(
                    "<a href=\"" + url + "\" target=\"_blank\">"
                            + url
                            + "</a>"
            );

        } catch (Exception e) {

            throw new ServletException(
                    "Error al subir el archivo a Cloudinary",
                    e
            );
        }
    }
}