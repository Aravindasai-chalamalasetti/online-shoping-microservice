package com.inventoryservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.inventoryservice")
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info(
		title = "Inventory microservices REST API Documentation",
		description = "Store Stock Inventory details with REST API Documentation",
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
				description = "Store Stock Inventory details with REST API Documentation"
		)
)
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(InventoryServiceApplication.class);
	}
	
//	@Bean
//	public CommandLineRunner loadData(InventoryDatabase inventoryDatabase) {
//		return args -> {
//			Inventory inv = new Inventory();
//			inv.setInventoryCode("Iphone 15 Pro");
//			inv.setInventoryQuantity(1583);
//			
//			Inventory inv1 = new Inventory();
//			inv1.setInventoryCode("Iphone 11 Pro");
//			inv1.setInventoryQuantity(0);
//			
//			inventoryDatabase.save(inv);
//			inventoryDatabase.save(inv1);
//		};
//	}
}
