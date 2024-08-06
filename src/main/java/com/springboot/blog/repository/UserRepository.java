package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // retrive an user by email
    Optional<User> findByEmail(String email);

    // retrive an user by useraname of email
    Optional<User> findByUsernameOrEmail(String username,String email);

    // retrive an user by username
    Optional<User> findByUsername(String username);

    //existance of user by username
    Boolean existsByUsername(String username);

    // existance of user by email
    Boolean existsByEmail(String email);
}
