package com.lms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.dto.CourseDto;
import com.lms.dto.CustomMessage;
import com.lms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;
    private final ObjectMapper objectMapper;

//    @PostMapping(value = "/",consumes = {"multipart/form-data"})
//    public ResponseEntity<?> saveCourse(@RequestPart("course")CourseDto courseDto,@RequestPart("file") MultipartFile file) throws  IOException {
//        return ResponseEntity.ok(courseService.saveCourse(courseDto,file));
//    }


    @PostMapping(value = "/")
    public ResponseEntity<?> saveCourse(@RequestParam("course")String courseDtoData,@RequestParam("file") MultipartFile file) throws IOException {
        CourseDto courseDto = objectMapper.readValue(courseDtoData, CourseDto.class);
        return ResponseEntity.ok(courseService.saveCourse(courseDto,file));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable long courseId) {
        return ResponseEntity.ok(courseService.findCourseByCourseId(courseId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @PutMapping(value = "/{courseId}",consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateCourse(@PathVariable long courseId, @RequestPart("course")CourseDto courseDto,@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(courseService.updateCourse(courseDto, courseId, file));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.ok(CustomMessage.builder().message("Course deleted successfully").status("success").build());
    }
}
