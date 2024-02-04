package com.dailycodework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Configuration;


@SpringBootApplication
//ho configurato nel pom xml la dipendenza per swagger
@EnableSwagger2
@Configuration
public class BibliotecaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaApplication.class, args
		);
	}

	@Bean
	public Docket apis() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.bcsoft"))
				.build()
				.tags(new Tag("Controller Libro", "Api sul controllo dei Libri"))
				.tags(new Tag("Controller Utente", "Api sul controllo degli utenti"));
	}
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Swagger REST API for Customers")
				.description("This API is about customers. Which is internal project in BCSoft. The project stores information about team members")
				.version("1.0.0").build();
	}

	//http://localhost:8080/swagger-ui.html#/


}