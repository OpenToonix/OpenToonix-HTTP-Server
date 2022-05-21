package com.juansecu.opentoonix.config;

/* --- Third-party modules --- */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/* --- Application modules --- */
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;

/**
 * Provides different beans to be used across the application.
 */
@Configuration
public class BeansProvider {
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAdapter getJwtAdapter() {
        return new JwtAdapter();
    }
}
