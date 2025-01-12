package org.skerdians.ecommerce.customer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Customer Service API")
                        .description("Customer Service API for E-Commerce Application")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0")).license(new License().name("Apache 2.0")));
    }
}