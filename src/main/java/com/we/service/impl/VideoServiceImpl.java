package com.we.service.impl;

import com.we.dto.VideoDto;
import com.we.exception.ResourceNotFound;
import com.we.model.Lesson;
import com.we.model.Video;
import com.we.repository.LessonRepository;
import com.we.repository.VideoRepository;
import com.we.service.VideoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    private String DIR;

    @Value("${files.video.hls}")
    private String HLS_DIR;

    @PostConstruct
    public void init() throws IOException {
        File dir = new File(DIR);
        if(!dir.exists()){
            dir.mkdir();
            log.info("Video Directory Created");
        }

       Files.createDirectories(Paths.get(HLS_DIR));
    }

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;
    private final VideoRepository videoRepository;


    @Override
    public VideoDto videoToDto(Video video) {
        return modelMapper.map(video, VideoDto.class);
    }

    @Override
    public Video dtoToVideo(VideoDto videoDto) {
        return modelMapper.map(videoDto, Video.class);
    }

    @Override
    public VideoDto saveVideo(MultipartFile file, long lessonId) {
        try {

//            Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonId));
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            InputStream inputStream = file.getInputStream();
            String cleanFileName = StringUtils.cleanPath(filename);
            String cleanFolderName = StringUtils.cleanPath(DIR);
            Path path = Paths.get(cleanFolderName,cleanFileName);
            log.info("Content Type {}",contentType);
            log.info("Path {}", path.toString());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            Video video = Video.builder()
                    .videoName(filename)
                    .videoUrl(path.toString())
                    .contentType(contentType)
                    .processingStatus("PENDING")
                    .build();
            Video save = videoRepository.save(video);
//            processVideo(save.getVideoId());
            return videoToDto(save);
        } catch (Exception e) {
            log.error("{}", e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public VideoDto getVideoByVideoId(long videoId) {
        return videoRepository.findById(videoId).map(this::videoToDto).orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));
    }

    @Override
    public void deleteVideo(long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));
        try {
            Files.deleteIfExists(Paths.get(video.getVideoUrl()));
            VideoService.deleteFolder(Paths.get(HLS_DIR,String.valueOf(videoId)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        videoRepository.delete(video);
    }




    @Override
    public void processVideo(long videoId) throws IOException, InterruptedException {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));
        String videoId1 = String.valueOf(videoId);
        Path baseFolder = Paths.get(HLS_DIR, videoId1);
        Files.createDirectories(baseFolder);
        String videoUrl = video.getVideoUrl();
        Path videoPath = Paths.get(videoUrl);
        log.info("videoPath : {}",videoPath);
        log.info("baseFolder : {}",baseFolder);
        String ffmpegCmd = String.format(
                "ffmpeg -i \"%s\" -c:v libx264 -c:a aac -strict -2 -f hls -hls_time 10 -hls_list_size 0 " +
                        "-hls_segment_filename \"%s/segment_%%03d.ts\" \"%s/master.m3u8\"",
                videoPath,baseFolder,baseFolder
        );
        ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", ffmpegCmd);
        processBuilder.inheritIO();

        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                video.setProcessingStatus("COMPLETED");
            } else {
                video.setProcessingStatus("FAILED");
            }
            videoRepository.save(video);
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
            if (e.getMessage().contains("Invalid data found") || e.getMessage().contains("unsupported codec")) {
                // If the error is due to an invalid or corrupt file, delete it.
                log.error("Invalid/corrupt video detected. Deleting video.");
                deleteVideo(videoId);
            } else {
                // Mark as failed in the database, so you can retry later.
                video.setProcessingStatus("FAILED"); // Ensure this column exists
                videoRepository.save(video);
            }
            throw new RuntimeException("Error while processing video", e);
        } finally {
            if (process != null) {
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
                process.destroy();
            }
        }
    }

    @Override
    public void retryVideoProcessing(long videoId) throws IOException, InterruptedException {
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));

        if (!"FAILED".equals(video.getProcessingStatus())) {
            throw new IllegalStateException("Only failed videos can be retried.");
        }

        log.info("Retrying video processing for video ID: {}", videoId);

        // Delete previous failed HLS segments (cleanup before retry)
        Path hlsFolder = Paths.get(HLS_DIR, String.valueOf(videoId));
        if (Files.exists(hlsFolder)) {
            VideoService.deleteFolder(hlsFolder);
        }

        video.setProcessingStatus("PENDING");
        videoRepository.save(video);

        processVideo(videoId);
    }


    @Override
    public VideoDto updateVideo(VideoDto videoDto, MultipartFile file, long videoId, long lessonId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonId));
        video.setVideoUrl(videoDto.getVideoUrl());
        video.setVideoName(videoDto.getVideoName());
        video.setContentType(videoDto.getContentType());
        return videoToDto(videoRepository.save(video));
    }
}
