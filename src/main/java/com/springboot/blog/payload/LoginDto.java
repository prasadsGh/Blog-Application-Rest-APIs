package com.springboot.blog.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String usernameOrLogin;
    private String password;

    public String getUsernameOrLogin() {
        return usernameOrLogin;
    }

    public void setUsernameOrLogin(String usernameOrLogin) {
        this.usernameOrLogin = usernameOrLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
