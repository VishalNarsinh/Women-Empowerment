package com.we.service;

import com.we.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    Image uploadImage(MultipartFile file, String folder) throws IOException
            ;
    void deleteImage(Long imageId);
}
