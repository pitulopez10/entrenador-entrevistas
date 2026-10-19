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


    public void eliminarImagen(
            String publicId
    ) throws IOException {

        if (publicId == null ||
                publicId.isBlank()) {

            return;
        }

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap()
        );
    }
}