package org.example;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = CloudinaryConfig.getCloudinary();
    }


    // ==================================================
    // SUBIR IMAGEN DE PERFIL
    // ==================================================

    public Map<String, String> subirImagen(byte[] contenido)
            throws IOException {

        Map<?, ?> resultado =
                cloudinary.uploader().upload(
                        contenido,
                        ObjectUtils.asMap(
                                "folder",
                                "entrenador-entrevista/perfiles",

                                "resource_type",
                                "image",

                                "unique_filename",
                                true
                        )
                );

        return obtenerDatos(resultado);
    }


    // ==================================================
    // SUBIR CV PDF
    // ==================================================

    public Map<String, String> subirCv(
            byte[] contenido,
            String nombreArchivo
    ) throws IOException {

        Map<?, ?> resultado =
                cloudinary.uploader().upload(
                        contenido,
                        ObjectUtils.asMap(
                                "folder",
                                "entrenador-entrevista/cv",

                                "resource_type",
                                "image",

                                "use_filename",
                                true,

                                "unique_filename",
                                true,

                                "filename_override",
                                nombreArchivo
                        )
                );

        return obtenerDatos(resultado);
    }


    // ==================================================
    // ELIMINAR IMAGEN
    // ==================================================

    public void eliminarImagen(
            String publicId
    ) throws IOException {

        if (publicId == null ||
                publicId.isBlank()) {

            return;
        }

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type",
                        "image"
                )
        );
    }


    // ==================================================
    // ELIMINAR CV
    // ==================================================

    public void eliminarCv(
            String publicId
    ) throws IOException {

        if (publicId == null ||
                publicId.isBlank()) {

            return;
        }

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type",
                        "image"
                )
        );
    }


    // ==================================================
    // MÉTODO AUXILIAR
    // ==================================================

    private Map<String, String> obtenerDatos(
            Map<?, ?> resultado
    ) {

        Map<String, String> datos =
                new HashMap<>();

        datos.put(
                "url",
                resultado.get("secure_url").toString()
        );

        datos.put(
                "publicId",
                resultado.get("public_id").toString()
        );

        return datos;
    }
}