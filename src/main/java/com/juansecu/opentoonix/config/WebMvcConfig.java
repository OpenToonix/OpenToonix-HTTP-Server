package com.juansecu.opentoonix.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${server.cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedMethods(
                "DELETE",
                "GET",
                "OPTIONS",
                "POST",
                "PUT"
            )
            .allowedOrigins(this.getAllowedOrigins());
    }

    private String[] getAllowedOrigins() {
        return allowedOrigins.split(",");
    }
}
