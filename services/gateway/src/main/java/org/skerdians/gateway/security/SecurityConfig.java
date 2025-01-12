package org.skerdians.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // ??? What is the difference with just EnableSecurity
public class SecurityConfig {
    private final String[] freeResourceUrls = {"/aggregates/**", "/webjars/**", "/eureka/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
            "/swagger-resources/**", "/api-docs/**", "/actuator/**"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                // In reactive web we have authorizeExchange instead of authorizeRequests
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(freeResourceUrls).permitAll()
                        .anyExchange().authenticated()//permitAll() // todo : authenticate all other requests
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return serverHttpSecurity.build();
    }
}
