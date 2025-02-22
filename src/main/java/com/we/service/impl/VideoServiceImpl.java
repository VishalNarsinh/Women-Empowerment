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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service

@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    @Value("${files.video}")
    private String DIR;

    @PostConstruct
    public void init(){
        File dir = new File(DIR);
        if(!dir.exists()){
            dir.mkdir();
            log.info("Directory Created");
        }
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
    public VideoDto updateVideo(VideoDto videoDto, MultipartFile file, long videoId, long lessonId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new ResourceNotFound("Video", "id", videoId));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonId));
        video.setVideoUrl(videoDto.getVideoUrl());
        video.setVideoName(videoDto.getVideoName());
        video.setContentType(videoDto.getContentType());
        return videoToDto(videoRepository.save(video));
    }
}
