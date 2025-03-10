package com.we.controller;

import com.we.dto.CourseDto;
import com.we.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    @PostMapping(value = "/",consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveCourse(@RequestPart("course")CourseDto courseDto,@RequestPart("file") MultipartFile file) {
//        courseService
        log.info("{}", courseDto);
        log.info("{}", file.getOriginalFilename());
        HashMap<String, Object> map = new HashMap<>();
        map.put("file",file.getOriginalFilename());
        map.put("course", courseDto);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable long courseId) {
        return ResponseEntity.ok(courseService.findCourseByCourseId(courseId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok(courseService.findAll());
    }
}
