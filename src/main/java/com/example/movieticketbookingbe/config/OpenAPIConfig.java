package com.example.movieticketbookingbe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

        @Bean
        public OpenAPI myOpenAPI() {
                return new OpenAPI()
                                .info(new Info()
                                                .title("Movie Ticket Booking API")
                                                .version("1.0")
                                                .description("API documentation for Movie Ticket Booking System")
                                                .contact(new Contact()
                                                                .name("Movie Ticket Booking Team")
                                                                .email("support@movieticket.com"))
                                                .license(new License()
                                                                .name("Apache 2.0")
                                                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                                .components(new Components()
                                                .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .in(SecurityScheme.In.HEADER)
                                                                .name("Authorization")));
        }
}