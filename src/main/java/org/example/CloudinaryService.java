package org.example;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = CloudinaryConfig.getCloudinary();
    }

    public String subirImagen(File archivo) throws IOException {

        Map<?, ?> resultado = cloudinary.uploader().upload(
                archivo,
                ObjectUtils.asMap(
                        "folder", "entrenador-entrevista"
                )
        );

        return resultado.get("secure_url").toString();
    }
}