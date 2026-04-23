package com.upeu.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI authOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("auth API")
                        .description("API REST del microservicio de gestión de auth. Versión actual: v1")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo auth")
                                .email("auth@upeu.edu.pe"))
                        .license(new License()
                                .name("Internal Use Only")
                                .url("https://upeu.edu.pe")));
    }
}
