package com.lms.service;

import com.lms.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GCSService {

    Image uploadFile(MultipartFile file, String folder) throws IOException;

    boolean deleteFile(String objectName);
}
