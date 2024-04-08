package com.shopproject.gateway.configurations;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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

                .route(r -> r.path("/statistics/sales")
                        .uri("http://localhost:8084/statistics/sales"))
                .route(r -> r.path("/statistics/sales/{product_id}")
                        .uri("http://localhost:8084/statistics/sales/"))
                .route(r -> r.path("/statistics/average_bill")
                        .uri("http://localhost:8084/statistics/average_bill"))
                .route(r -> r.path("/statistics/average_bill/{user_id}")
                        .uri("http://localhost:8084/statistics/average_bill/"))

                .route(r -> r.path("/product/api-docs")
                        .uri("http://localhost:8080/product/api-docs"))
                .route(r -> r.path("/user/api-docs")
                        .uri("http://localhost:8081/user/api-docs"))
                .route(r -> r.path("/purchase/api-docs")
                        .uri("http://localhost:8082/purchase/api-docs"))
                .route(r -> r.path("/guarantee/api-docs")
                        .uri("http://localhost:8083/guarantee/api-docs"))
                .route(r -> r.path("/report/api-docs")
                        .uri("http://localhost:8084/report/api-docs"))

                .build();

    }
}