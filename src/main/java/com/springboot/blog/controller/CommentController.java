package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.payload.CommentDto;

import java.util.List;


@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment
            (
                @PathVariable(value="postId") long postId,
                @RequestBody CommentDto commentDto
            ){

        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentByPostId
            (
                    @PathVariable(value="postId") long postId
            ){

        List<CommentDto> listOfComments = (commentService.getAllCommentsByPostId(postId));

        return listOfComments;
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable(value="postId") long postId,
            @PathVariable(value="commentId") long commentId
    ){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable(value="postId") long postId,
            @PathVariable(value="commentId") long commentId,
            @RequestBody CommentDto commentRequest
    ){
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentRequest),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value="postId") long postId,
            @PathVariable(value="commentId") long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }

}
