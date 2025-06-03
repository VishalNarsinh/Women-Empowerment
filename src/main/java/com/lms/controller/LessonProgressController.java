package com.lms.controller;

import com.lms.dto.LessonProgressRequest;
import com.lms.service.LessonProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lesson-progress")
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }

    @PostMapping    
    public ResponseEntity<?> updateProgress(@RequestBody LessonProgressRequest lessonProgressRequest) {
        return ResponseEntity.ok(lessonProgressService.updateLessonProgress(lessonProgressRequest));
    }
}
