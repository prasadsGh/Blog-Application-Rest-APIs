package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long id, CommentDto commentDto);
    List<CommentDto> getAllCommentsByPostId(long id);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId,long commentId, CommentDto commentRequest);
     void deleteComment(long postId, long commentId);

}
