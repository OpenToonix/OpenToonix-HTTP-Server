package com.juansecu.opentoonix.config;

/* --- Java modules --- */
import java.util.ArrayList;
import java.util.List;

/* --- Third-party modules --- */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* --- Application modules --- */
import com.juansecu.opentoonix.core.filters.JwtAuthenticationFilter;
import com.juansecu.opentoonix.shared.adapters.JwtAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAdapter jwtAdapter;

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
        final FilterRegistrationBean<JwtAuthenticationFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        final List<AntPathRequestMatcher> excludedMatchers = new ArrayList<>();

        excludedMatchers.add(new AntPathRequestMatcher("/favicon.ico"));
        excludedMatchers.add(new AntPathRequestMatcher("/user/is-valid-username"));
        excludedMatchers.add(new AntPathRequestMatcher("/user/login"));
        excludedMatchers.add(new AntPathRequestMatcher("/user/save-user"));

        filterFilterRegistrationBean.addUrlPatterns("*");
        filterFilterRegistrationBean.setFilter(
            new JwtAuthenticationFilter(excludedMatchers, this.jwtAdapter)
        );
        filterFilterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return filterFilterRegistrationBean;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
