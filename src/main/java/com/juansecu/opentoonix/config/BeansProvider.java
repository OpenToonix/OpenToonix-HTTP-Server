package com.juansecu.opentoonix.config;

/* --- Third-party modules --- */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;

/**
 * Provides different beans to be used across the application.
 */
@Configuration
public class BeansProvider {
    @Bean
    public JwtAdapter jwtAdapter() {
        return new JwtAdapter();
    }
}
