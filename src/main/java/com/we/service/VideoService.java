package com.we.service;

import com.we.dto.VideoDto;
import com.we.model.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public interface VideoService {

    VideoDto videoToDto(Video video);

    Video dtoToVideo(VideoDto videoDto);

    VideoDto saveVideo(MultipartFile file, long lessonId);

    VideoDto getVideoByVideoId(long videoId);

    void deleteVideo(long videoId);

    void retryVideoProcessing(long videoId) throws IOException, InterruptedException;

    VideoDto updateVideo(VideoDto videoDto, MultipartFile file, long videoId, long lessonId);

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
