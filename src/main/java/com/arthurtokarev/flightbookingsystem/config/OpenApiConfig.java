package com.arthurtokarev.flightbookingsystem.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI flightBookingOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Flight Booking System API")
                        .description("Spring Boot Flight Booking System")
                        .version("1.0")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}