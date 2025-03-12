package com.we.controller;


import com.we.dto.CustomMessage;
import com.we.model.Image;
import com.we.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) throws IOException {
        Image uploadedImage = imageService.uploadImage(file, folder);
        return ResponseEntity.ok(uploadedImage);
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok(CustomMessage.builder().message("Image deleted successfully").status("success").build());
    }
}
