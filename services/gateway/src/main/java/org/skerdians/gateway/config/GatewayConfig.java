package org.skerdians.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customer-service", r -> r.path("/api/v1/customers/**")
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("customer-service-swagger", r -> r.path("/aggregates/customer-service/v3/api-docs")
                    .filters(f -> f.rewritePath("/aggregates/customer-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("order-service-swagger", r -> r.path("/aggregates/order-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://ORDER-SERVICE"))
                .route("order-lines-service", r -> r.path("/api/v1/order-lines/**")
                        .uri("lb://ORDER-SERVICE"))
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .uri("lb://PRODUCT-SERVICE"))
                .route("product-service-swagger", r -> r.path("/aggregates/product-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("payment-service", r -> r.path("/api/v1/payments/**")
                        .uri("lb://PAYMENT-SERVICE"))
                .route("payment-service-swagger", r -> r.path("/aggregates/payment-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://PAYMENT-SERVICE"))
                .build();
    }
}