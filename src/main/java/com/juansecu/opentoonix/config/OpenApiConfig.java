package com.juansecu.opentoonix.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Value("${spring.application.description}")
    private String description;
    @Value("${spring.application.name}")
    private String name;
    @Value("${spring.application.version}")
    private String version;

    @Bean
    protected OpenAPI openAPI() {
        final ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("README.md")
                .url("https://github.com/OpenToonix/OpenToonix-HTTP-Server#readme");
        final Info info = new Info()
            .description(this.description)
            .license(
                new License()
                    .name("MIT")
                    .url("https://github.com/OpenToonix/OpenToonix-HTTP-Server/blob/main/LICENSE")
            )
            .title(this.name)
            .version(this.version);
        final SecurityScheme jwtHeaderSecurityScheme = new SecurityScheme()
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("AuthToken")
            .scheme("bearer")
            .type(SecurityScheme.Type.HTTP);
        final Components components = new Components().addSecuritySchemes(
            "AuthToken",
            jwtHeaderSecurityScheme
        );

        return new OpenAPI()
            .components(components)
            .externalDocs(externalDocs)
            .info(info);
    }
}
