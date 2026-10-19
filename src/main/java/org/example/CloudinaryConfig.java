package org.example;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {

    private static final Cloudinary cloudinary =
            new Cloudinary(
                    ObjectUtils.asMap(
                            "cloud_name",
                            System.getProperty("CLOUDINARY_CLOUD_NAME"),

                            "api_key",
                            System.getProperty("CLOUDINARY_API_KEY"),

                            "api_secret",
                            System.getProperty("CLOUDINARY_API_SECRET")
                    )
            );

    public static Cloudinary getCloudinary() {
        return cloudinary;
    }
}