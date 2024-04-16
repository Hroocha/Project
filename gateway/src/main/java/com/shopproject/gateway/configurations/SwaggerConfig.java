package com.shopproject.gateway.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("lb://GATEWAY"),
                                new Server().url("lb://PRODUCT"),
                                new Server().url("lb://USER"),
                                new Server().url("lb://PURCHASE"),
                                new Server().url("lb://GUARANTEE"),
                                new Server().url("lb://REPORT")
                        )
                )
                .info(
                        new Info().title("Shop API")
                );
    }


}