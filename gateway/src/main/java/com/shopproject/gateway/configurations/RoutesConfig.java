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
                        .uri("lb://PRODUCT/products"))
                .route(r -> r.path("/products/{id}")
                        .uri("lb://PRODUCT/products/"))

                .route(r -> r.path("/me")
                        .uri("lb://USER/me"))
                .route(r -> r.path("/registration")
                        .uri("lb://USER/registration"))
                .route(r -> r.path("/auth")
                        .uri("lb://USER/auth"))

                .route(r -> r.path("/purchase/{id}")
                        .uri("lb://PURCHASE/purchase/"))
                .route(r -> r.path("/refund/{id}")
                        .uri("lb://PURCHASE/refund/"))

                .route(r -> r.path("/orders")
                        .uri("lb://PURCHASE/orders"))

                .route(r -> r.path("/statistics/sales")
                        .uri("lb://REPORT/statistics/sales"))
                .route(r -> r.path("/statistics/sales/{product_id}")
                        .uri("lb://REPORT/statistics/sales/"))
                .route(r -> r.path("/statistics/average_bill")
                        .uri("lb://REPORT/statistics/average_bill"))
                .route(r -> r.path("/statistics/average_bill/{user_id}")
                        .uri("lb://REPORT/statistics/average_bill/"))

                .route(r -> r.path("/product/api-docs")
                        .uri("lb://PRODUCT/product/api-docs"))
                .route(r -> r.path("/user/api-docs")
                        .uri("lb://USER/user/api-docs"))
                .route("purchase", r -> r.path( "/purchase/api-docs")
                        .uri("lb://PURCHASE/purchase/api-docs"))
                .route(r -> r.path("/guarantee/api-docs")
                        .uri("lb://GUARANTEE/guarantee/api-docs"))
                .route(r -> r.path("/report/api-docs")
                        .uri("lb://REPORT/report/api-docs"))

                .build();

    }
}