package com.lms.service;

import com.lms.dto.VideoDto;
import com.lms.model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public interface VideoService {

    Video saveVideo(MultipartFile file);

    Video getVideoByVideoId(long videoId);

    void deleteVideo(long videoId);

    void retryVideoProcessing(long videoId) throws IOException, InterruptedException;

    Video updateVideo(VideoDto videoDto, MultipartFile file, long videoId, long lessonId);

    static void deleteFolder(Path folder) throws IOException {
        Files.walkFileTree(folder, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    void processVideo(long videoId) throws IOException, InterruptedException;
}
