package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition (
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App description",
				version ="v1.0",
				contact = @Contact(
						name="Prasad Patil",
						email = "prasad.work4@gmail.com",
						url ="https://linktr.ee/prasadsLt"
				),
				license = @License(
						name="Apache 2.0",
						url="https://github.com/prasadsGh/Blog-Application-Rest-APIs"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog Application Documentation",
				url = "https://github.com/prasadsGh/Blog-Application-Rest-APIs"
		)
)
public class SpringbootBlogRestApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}

}
