package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exceptions.BlogAPIException;
import com.springboot.blog.exceptions.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.config.ModelMapperConfig;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
    }

    // create comment interface rest api
    @Override
    public CommentDto createComment(long id, CommentDto commentDto){

        // converting dto to entity
        Comment comment = mapToEntity(commentDto);

        // retrieve the post by id
        Post post =postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "id",
                        "post",
                        id
                )
        );

        // set post to comment parameter
        comment.setPost(post);

        // save the entity to database

        Comment newComment = commentRepository.save(comment);

        // convert entity to dto
         commentDto = mapToDto(newComment);
        return commentDto;
    }

    // get all post by post id
    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId){

        //retrieve comments by post id
        List<Comment> listOfComments = commentRepository.findByPostId(postId);
        List<CommentDto> content = listOfComments.stream().map(comment->mapToDto(comment)).collect(Collectors.toUnmodifiableList());

        return content;

    }
    //get comment by post
    @Override
    public CommentDto getCommentById(long postId, long commentId){
        // retrive post by post id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                )
        );
        // retrive comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Comment",
                        "id",
                        commentId
                )
        );

        // if comment not found then we throw an exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST,
                    "Comment does not belong to the post"
            );
        }

        CommentDto commentDto = mapToDto(comment);

        return commentDto;

    }

    // update comment
    public CommentDto updateComment(long postId,long commentId, CommentDto commentRequest){
        // retrive post by post id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                )
        );
        // retrive comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Comment",
                        "id",
                        commentId
                )
        );

        // exception if comment does not belongs to the post then  we throw an exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST,
                    "Comment does not belonds to the post"
            );
        }
        // update comment with commentRequest
        comment.setBody(commentRequest.getBody());
        comment.setEmail(commentRequest.getEmail());
        comment.setName(commentRequest.getName());

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }
    // delete comment
    public void deleteComment(long postId, long commentId){
        // retrive post by post id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Post",
                        "id",
                        postId
                )
        );
        // retrive comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException(
                        "Comment",
                        "id",
                        commentId
                )
        );
        // exception if comment does not belongs to the post then  we throw an exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(
                    HttpStatus.BAD_REQUEST,
                    "Comment does not belonds to the post"
            );
        }
        // delete comment
        commentRepository.deleteById(commentId);
        
    }

    // map to convert comment entity to dto
    public CommentDto mapToDto(Comment comment){

        //entity to DTO
        CommentDto commentDto = mapper.map(comment,CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    //map to convert comment dto to entity

    public Comment mapToEntity(CommentDto commentDto){

        //dto to entity
        Comment comment = mapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }

}
