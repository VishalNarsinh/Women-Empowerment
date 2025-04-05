package com.we.service.impl;

import com.we.dto.LessonDto;
import com.we.exception.ResourceNotFound;
import com.we.mapper.LessonMapper;
import com.we.model.Image;
import com.we.model.Lesson;
import com.we.model.Video;
import com.we.repository.CourseRepository;
import com.we.repository.LessonRepository;
import com.we.service.ImageService;
import com.we.service.LessonService;
import com.we.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private static final Logger log = LoggerFactory.getLogger(LessonServiceImpl.class);
    private final LessonMapper lessonMapper;
    private final CourseRepository courseRepository;
    private final ImageService imageService;
    private final VideoService videoService;
    private final LessonRepository lessonRepository;

    @Override
    public LessonDto saveLesson(LessonDto lessonDto, MultipartFile imageFile, MultipartFile videoFile) throws IOException {
        Lesson entity = lessonMapper.toEntity(lessonDto);
        entity.setCourse(courseRepository.findById(lessonDto.getCourseId()).orElseThrow(()->new ResourceNotFound("Course","id",lessonDto.getCourseId())));
        Image image = imageService.uploadImage(imageFile, "lesson");
        log.info("Image Id : {}", image.getImageId());
        entity.setImage(image);
        Video video = videoService.saveVideo(videoFile);
        log.info("Video Id : {}", video.getVideoId());
        entity.setVideo(video);
        return lessonMapper.toDto(lessonRepository.save(entity));
    }

    @Override
    public LessonDto updateLesson(LessonDto lessonDto, long lessonId, MultipartFile file) {
        return null;
    }

    @Transactional
    @Override
    public void deleteLesson(long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonId));
        imageService.deleteImage(lesson.getImage().getImageId());
        videoService.deleteVideo(lesson.getVideo().getVideoId());
        lesson.setImage(null);
        lesson.setVideo(null);
//        lessonRepository.saveAndFlush(lesson);
        lessonRepository.delete(lesson);
    }

    @Override
    public LessonDto findLessonById(long lessonId) {
        return lessonRepository.findById(lessonId).map(lessonMapper::toDto).orElseThrow(() -> new ResourceNotFound("Lesson", "id", lessonId));
    }

    @Override
    public List<LessonDto> findLessonsByCourseId(long courseId) {
        return lessonRepository.findByCourseCourseId(courseId).stream().map(lessonMapper::toDto).toList();
    }
}
