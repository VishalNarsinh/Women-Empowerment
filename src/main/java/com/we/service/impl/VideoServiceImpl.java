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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    @Value("files.video")
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
            Path path = Paths.get(cleanFolderName + cleanFileName);
            log.info("Content Type {}",contentType);
            log.info("Path {}", path.toString());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            Video video = Video.builder()
                    .videoName(filename)
                    .videoUrl(path.toString())
                    .contentType(contentType)
                    .build();
            Video save = videoRepository.save(video);
            return videoToDto(save);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
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
        videoRepository.delete(video);
    }

    @Override
    public long processVideo(long videoId) throws IOException, InterruptedException {
        String videoId1 = String.valueOf(videoId);
        Path baseFolder = Paths.get(HLS_DIR, videoId1);
        Files.createDirectories(baseFolder);
        VideoDto dto = getVideoByVideoId(videoId);
        String videoUrl = dto.getVideoUrl();
        Path videoPath = Paths.get(videoUrl);
        log.info("videoPath : {}",videoPath);
        log.info("baseFolder : {}",baseFolder);
        // Define resolution folders
//        String[] resolutions = {"360p", "480p", "720p", "1080p"};
//        for (String res : resolutions) {
//            Files.createDirectories(Paths.get(baseFolder, res));
//        }
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
            int exit = process.waitFor();
            if (exit != 0) {
                throw new RuntimeException("Video processing failed!!");
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
            throw new RuntimeException("Error while processing video", e);
        } finally {
            if (process != null) {
                process.getInputStream().close();
                process.getErrorStream().close();
                process.getOutputStream().close();
                process.destroy();
            }
        }

        return videoId;

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
