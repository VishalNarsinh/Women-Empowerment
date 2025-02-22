package com.we.service;

import com.we.dto.VideoDto;
import com.we.model.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {

    VideoDto videoToDto(Video video);

    Video dtoToVideo(VideoDto videoDto);

    VideoDto saveVideo(MultipartFile file, long lessonId);

    VideoDto getVideoByVideoId(long videoId);

    void deleteVideo(long videoId);

    VideoDto updateVideo(VideoDto videoDto, MultipartFile file, long videoId, long lessonId);

}
