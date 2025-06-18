package com.lms.service.impl;

import com.lms.model.Image;
import com.lms.service.GCSService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("ci") // Only in CI
public class DummyGCSServiceImpl implements GCSService {

    @Override
    public Image uploadFile(MultipartFile file, String folder) {
        // No-op or return dummy object
        return Image.builder()
                .fileName("dummy.png")
                .contentType("image/png")
                .objectName("dummy-folder/dummy.png")
                .imageUrl("https://dummy-url.com/dummy.png")
                .build();
    }

    @Override
    public boolean deleteFile(String objectName) {
        return true;
    }
}
