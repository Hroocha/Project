package com.shopproject.gateway.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
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
                                new Server().url("http://localhost:8765"),
                                new Server().url("http://localhost:8080"),
                                new Server().url("http://localhost:8081"),
                                new Server().url("http://localhost:8082"),
                                new Server().url("http://localhost:8083"),
                                new Server().url("http://localhost:8084")
                        )
                )
                .info(
                        new Info().title("Shop API")
                );
    }


}