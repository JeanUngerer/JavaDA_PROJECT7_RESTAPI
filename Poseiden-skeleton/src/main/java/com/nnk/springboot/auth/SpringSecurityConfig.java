package com.nnk.springboot.auth;



import com.nnk.springboot.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


/**
 * SecurityConfig of application
 */
@Configuration
@EnableWebSecurity
@Getter
@Setter
public class SpringSecurityConfig {

    private final PasswordEncoder encoder;



    private final UserService myUserDetailsService;

    public SpringSecurityConfig(PasswordEncoder encoder, UserService myUserDetailsService) {
        this.encoder = encoder;
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * Bean loaded in spring context
     * SecurityFilterChain, defines the authorizations for API points
     * Adds a login and logout Url
     * @param http HttpSecurity filter chain
     * @return http.build()
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                        )
                .userDetailsService(myUserDetailsService)
                .formLogin(withDefaults())
                .logout(withDefaults())
                .build();
    }



}