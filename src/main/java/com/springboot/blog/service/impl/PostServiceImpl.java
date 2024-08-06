package com.springboot.blog.service.impl;
import com.springboot.blog.config.ModelMapperConfig;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.CommentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;
    private ModelMapper mapper;
    private CommentRepository commentRepository;
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository= commentRepository;
        this.mapper= mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post=mapToEntity(postDto);
        Post newPost = postRepository.save(post);


        //convert entity to DTO
        PostDto postResponse= mapToDto(newPost);

        return postResponse;
    }

    // implementation of get all post interface
    @Override
    public PostResponse getAllPost(
           int pageNo,
           int pageSize,
           String sortBy
    ){
        // create a pageable instance
        Pageable pageable =  PageRequest.of(pageNo, pageSize, Sort.by(sortBy));


        Page<Post> posts = postRepository.findAll(pageable);


        // get the content for page object
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content = listOfPosts.stream().map(post->mapToDto(post)).collect(Collectors.toUnmodifiableList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        // setting all comments for the each individual post

        for(PostDto postDto : content){
            long id = postDto.getId();
            List<Comment> listOfComment = commentRepository.findByPostId(id);
            Set<CommentDto> setOfComment = new HashSet<>();

            for(Comment comment : listOfComment){
                setOfComment.add(mapToDtoComment(comment));
            }

            postDto.setComment(setOfComment);

        }


        return postResponse;

    }

    // implementation of getPostById interface
    @Override
    public PostDto getPostById(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                )
        );
        PostDto postDto = mapToDto(post);
        List<Comment> listOfComment = commentRepository.findByPostId(id);
        Set<CommentDto> setOfComment = new HashSet<>();
        for(Comment comment: listOfComment){
            setOfComment.add(mapToDtoComment(comment));
        }
        postDto.setComment(setOfComment);
        return  postDto;
    }

    // implementation of updatePost interface
    @Override
    public PostDto updatePost(PostDto postDto, long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                )
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setId(id);

        Post updatedPost =postRepository.save(post);
        return mapToDto(updatedPost);
    }

    // delete post interface implementation
    @Override
    public PostDto deletePost(long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        id
                )
        );
        postRepository.delete(post);

        return mapToDto(post);

    }


    //convert entity into DTO
    private PostDto mapToDto(Post post){
            PostDto postDto = mapper.map(post, PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }
    //convert entity into DTO for comment
    private CommentDto mapToDtoComment(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return commentDto;
    }

    // convert DTO to entity
    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto,Post.class);

//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }


}
