package com.StayEase.Stay_Ease_Project.Config;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.StayEase.Stay_Ease_Project.Service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class SecurityConfig {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(csrf -> csrf.disable());
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.authorizeHttpRequests(configurer -> configurer
        .requestMatchers("/login", "/register")
        .permitAll()
        .requestMatchers("/user/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.POST, "/hotel/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/hotel/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.PUT, "/hotel/**").hasRole("HOTEL_MANAGER")
        .requestMatchers(HttpMethod.GET, "/hotel/**").permitAll()
        .requestMatchers(HttpMethod.POST, "/booking/checkout").hasRole("CUSTOMER")
        .requestMatchers(HttpMethod.POST, "/booking/**").hasRole("CUSTOMER")
        .requestMatchers(HttpMethod.GET, "/booking/**").hasRole("ADMIN")
        .requestMatchers(HttpMethod.DELETE, "/booking/**").hasRole("HOTEL_MANAGER")
        .anyRequest()
        .authenticated());
        httpSecurity.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
