package com.securebank.securebank.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI secureBankOpenAPI() {

        return new OpenAPI()

                .info(new Info()
                        .title("Secure Banking System API")
                        .description("REST APIs for Secure Banking System")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sindhu Rani")
                                .email("sindhu@gmail.com"))
                        .license(new License()
                                .name("MIT License")))

                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/K-SindhuRani/Secure-Banking-System"));
    }
}