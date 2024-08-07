package com.springboot.blog.controller;


import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.payload.PostDto;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    //@PreAuthorize("hasRole('ADMIN','USER')")
    @GetMapping
    public PostResponse getAllPost(
             @RequestParam(
                     value="pageNo",
                     defaultValue = "0",
                     required = false
             )int pageNo,
             @RequestParam(
                     value="pageSize",
                     defaultValue = "5",
                     required = false
             ) int pageSize,
             @RequestParam(
                     value="sortBy",
                     defaultValue = "id",
                     required = false
             ) String sortBy
     ){
        return postService.getAllPost(pageNo, pageSize, sortBy);
     }
   // get post by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    // update post rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name ="id") long id){
        PostDto postResponse = postService.updatePost(postDto,id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //delete post rest api

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable(name="id") long id){

        return new ResponseEntity<>(postService.deletePost(id), HttpStatus.OK);
    }

}
