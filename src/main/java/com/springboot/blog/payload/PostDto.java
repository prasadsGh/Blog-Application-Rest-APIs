package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;


    // title should not be null or empty
    // title should contain atleast 2 characters
    @NotEmpty
    @Size(min=2,message = "title should contain at least 2 characters")
    private String title;

    // post description should not be empty
    // post description should be atleast of 10 characters\
    @NotEmpty
    @Size(min=10,message = "Post description should contain atleast 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comment;
    private long categoryId;

    public long getCategoryId() {
        return categoryId;
    }

    public void setComment(Set<CommentDto> comment) {
        this.comment = comment;
    }

    public Set<CommentDto> getComment() {
        return comment;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
