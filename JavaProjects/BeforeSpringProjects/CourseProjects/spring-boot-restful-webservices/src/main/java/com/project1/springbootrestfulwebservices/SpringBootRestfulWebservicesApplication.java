package com.project1.springbootrestfulwebservices;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Rest API Documentation",
				description = "First Project",
				version = "v1.0",
				contact = @Contact(
						name = "Michal Zajac",
						email = "michal.zajac8338@gmail.com",
						url = "https://github.com/michalzajac8338/michalzajac8338.github.io/tree/main/JavaProjects"
				)
//				license = @License(
//						name = "Apache 2.0",
//						url = ""
//				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot User Management Documentation"
		)
)
public class SpringBootRestfulWebservicesApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestfulWebservicesApplication.class, args);
	}

}
