package com.juansecu.opentoonix.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansProvider {
    @Bean
    protected ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
