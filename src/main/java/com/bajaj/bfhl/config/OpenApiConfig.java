package com.bajaj.bfhl.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bfhlOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BFHL REST API")
                        .description("Bajaj Finserv Health Hiring Challenge")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ankit Bansal")
                                .email("bansalankit1575@gmail.com")));
    }
}
