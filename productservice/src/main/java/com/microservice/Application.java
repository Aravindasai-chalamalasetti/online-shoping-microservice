package com.microservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(
		title = "Product microservices REST API Documentation",
		description = "Store Product details with REST API Documentation",
		version = "v1",
		contact = @Contact(
				name = "Arvind Sai",
				email = "arvindsai123@gmail.com"
		),
		license = @License(
				name = "Apache Tomcat",
				url = "https://tomcat.apache.org/"
		)
),
		externalDocs = @ExternalDocumentation(
				description = "Store Product details with REST API Documentation"
		)
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
