package com.app.simpleblogsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Simple Blog System",
                description = "This is a simple blog system for managing posts and users",
                version = "1.0",
                contact = @Contact(
                        name = "Jey",
                        email = "664259015@webmail.npru.ac.th",
                        url = "https://github.com/jeyZ9"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://example.com/terms"
        ),
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Simple Blog System API")
                        .version("1.0")
                        .description("Comprehensive API documentation for the Simple Blog System")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Jey")
                                .email("664259015@webmail.npru.ac.th")
                                .url("https://github.com/jeyZ9"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache License 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("GitHub Repository")
                        .url("https://github.com/JeyZ9/simple-blog-system"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Public APIs")
                .pathsToMatch("/api/v1/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("Admin APIs")
//                .pathsToMatch("/api/v1/admin/**")
//                .build();
//    }

//    @Bean
//    public GroupedOpenApi authApi() {
//        return GroupedOpenApi.builder()
//                .group("Authentication APIs")
//                .pathsToMatch("/api/v1/auth/**")
//                .build();
//    }
}
