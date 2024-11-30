package org.skerdians.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/*
The annotations `@EnableFeignClients` and `@EnableJpaAuditing` in this Spring Boot application serve specific purposes:
a
1. **`@EnableFeignClients`**:
   - This annotation is used to enable Feign clients in your Spring Boot application. Feign is a declarative web service client that simplifies the process of making HTTP requests to external services. By using this annotation,
   Spring will scan for interfaces that declare they are Feign clients (using the `@FeignClient` annotation) and create implementations for them at runtime.
   - It allows you to define HTTP clients with minimal boilerplate code, making it easier to interact with other microservices or external APIs.

2. **`@EnableJpaAuditing`**:
   - This annotation is used to enable JPA Auditing in this Spring Boot application. JPA Auditing allows you to automatically populate auditing-related fields (such as created date, last modified date, created by, and last modified by) in your entities.
   - By enabling JPA Auditing, you can use annotations like `@CreatedDate`, `@LastModifiedDate`, `@CreatedBy`, and `@LastModifiedBy` in your entity classes to automatically manage these fields without having to manually set them in your code.

These annotations help in configuring and enabling specific features in your Spring Boot application, making it easier to work with external services and manage entity auditing.
 */
@EnableFeignClients
@EnableJpaAuditing
@SpringBootApplication
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
