package com.we.service;

import com.we.dto.LessonDto;
import com.we.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findCommentsByLessonId(LessonDto lessonDto);

    Comment saveComment(Comment comment);

    void deleteComment(long commentId);
}
