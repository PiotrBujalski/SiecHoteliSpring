package com.example.SiecHoteli.Config;

import com.example.SiecHoteli.Entity.Role;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/hotels").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/reserv/getAll").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/rooms").permitAll()
                        .requestMatchers("/api/v1/reserv/add").hasRole(Role.USER.name())
                        .requestMatchers("/api/v1/hotels/add", "/api/v1/hotels/delete", "/api/v1/hotels/update").hasAuthority(Role.ADMIN.name())
                        .requestMatchers( "/api/v1/reserv/delete", "/api/v1/reserv/update","/api/v1/reserv/deleteExpired").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/rooms/add", "/api/v1/rooms/delete", "/api/v1/rooms/update").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/db").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/history").hasAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
