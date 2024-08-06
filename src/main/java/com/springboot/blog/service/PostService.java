package com.springboot.blog.service;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;


import java.util.List;


public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto,long id);
    PostDto deletePost(long id);


}
