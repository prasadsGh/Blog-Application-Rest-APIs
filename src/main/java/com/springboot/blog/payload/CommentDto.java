package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    //comment should not be empty or null
    @NotEmpty(message = "Name field should not be empty in comment")
    private String name;

    // email should not bt empty
    @NotEmpty(message = "Email should not be empty or null and should be well formated")
    @Email
    private String email;

    // comment body should not be null or empty
    // comment body should be of atleast 10 characters
    @NotEmpty
    @Size(min=10, message = "minimum charcters for comment body is 10")
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
