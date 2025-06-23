package com.example.movieticketbookingbe.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils; // Make sure this import is present
import org.springframework.beans.factory.annotation.Value; // Import Value annotation
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(@Value("${cloudinary.cloud-name}") String cloudName,
                             @Value("${cloudinary.api-key}") String apiKey,
                             @Value("${cloudinary.api-secret}") String apiSecret) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));

        System.out.println("CloudinaryService initialized for cloud: " + cloudName);
    }

    public Map upload(Object file, Map options) throws IOException {
        return cloudinary.uploader().upload(file, options);
    }

    public Map delete(String publicId, Map options) throws IOException {
        return cloudinary.uploader().destroy(publicId, options);
    }
}