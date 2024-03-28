package com.shopproject.gateway.configurations;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Tag(name = "main_methods")
public class RoutesConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/products")
                        .uri("http://localhost:8080/products"))
                .route(r -> r.path("/products/{id}")
                        .uri("http://localhost:8080/products/"))

                .route(r -> r.path("/me")
                        .uri("http://localhost:8081/me"))
                .route(r -> r.path("/registration")
                        .uri("http://localhost:8081/registration"))
                .route(r -> r.path("/auth")
                        .uri("http://localhost:8081/auth"))

                .route(r -> r.path("/purchase/{id}")
                        .uri("http://localhost:8082/purchase/"))
                .route(r -> r.path("/refund/{id}")
                        .uri("http://localhost:8082/refund/"))
                .route(r -> r.path("/orders")
                        .uri("http://localhost:8082/orders"))
                .build();
    }
}