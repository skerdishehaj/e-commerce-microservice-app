package org.skerdians.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;

// The Resilience4j Aspects order is the following:
//Retry ( CircuitBreaker ( RateLimiter ( TimeLimiter ( Bulkhead ( Function ) ) ) ) )

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("customer-service", r -> r.path("/api/v1/customers/**")
                        .filters(f -> f.retry(config -> config.setRetries(3)
                                .setMethods(HttpMethod.GET)
                                .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                        )
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("customer-service-swagger", r -> r.path("/aggregates/customer-service/v3/api-docs")
                        .filters(f -> f.rewritePath("/aggregates/customer-service/v3/api-docs", "/v3/api-docs"))
                        .uri("lb://CUSTOMER-SERVICE"))
                .route("order-service", r -> r.path("/api/v1/orders/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("orderServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/contactSupport"))
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://ORDER-SERVICE"))
                .route("order-service-swagger", r -> r.path("/aggregates/order-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://ORDER-SERVICE"))
                .route("order-lines-service", r -> r.path("/api/v1/order-lines/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("orderLinesServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallback/contactSupport")))
                        .uri("lb://ORDER-SERVICE"))
                .route("product-service", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("productServiceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/contactSupport"))
                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())
                                        .setKeyResolver(userKeyResolver())))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("product-service-swagger", r -> r.path("/aggregates/product-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://PRODUCT-SERVICE"))
                .route("payment-service", r -> r.path("/api/v1/payments/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("paymentServiceCircuitBreaker")
                                .setFallbackUri("forward:/fallback/contactSupport")))
                        .uri("lb://PAYMENT-SERVICE"))
                .route("payment-service-swagger", r -> r.path("/aggregates/payment-service/v3/api-docs")
                        .filters(f -> f.setPath("/v3/api-docs"))
                        .uri("lb://PAYMENT-SERVICE"))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
                .build());
    }


    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(1, 1, 1);
    }

    @Bean
    KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user"))
                .defaultIfEmpty("anonymous");
    }
}