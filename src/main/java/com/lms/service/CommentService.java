package com.lms.service;

import com.lms.dto.LessonDto;
import com.lms.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentsByLessonId(LessonDto lessonDto);

    Comment saveComment(Comment comment);

    void deleteComment(long commentId);
}
