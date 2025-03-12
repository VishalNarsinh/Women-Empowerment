package com.we.service.impl;

import com.we.exception.ResourceNotFound;
import com.we.model.Image;
import com.we.repository.ImageRepository;
import com.we.service.GCSService;
import com.we.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final GCSService gcsService;
    private final ImageRepository imageRepository;

    @Override
    public Image uploadImage(MultipartFile file, String folder) throws IOException {
        Image image = gcsService.uploadFile(file, folder);
        return imageRepository.save(image);
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(image -> {
            gcsService.deleteFile(image.getObjectName());
            imageRepository.delete(image);
        } , () ->{
            throw new ResourceNotFound("Image", "id", imageId);
        });
    }
}
