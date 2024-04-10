package com.shopproject.guarantee;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GuaranteeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuaranteeApplication.class, args);
	}

}
