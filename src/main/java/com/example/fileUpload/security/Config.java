package com.example.fileUpload.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("removal")
@Configuration
@EnableMethodSecurity
public class Config
{
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/")
                .permitAll()
                .requestMatchers( "file/upload")
                .permitAll()
                .requestMatchers("file/document/*")
                .permitAll()
                .anyRequest()
                .authenticated();

        return httpSecurity.build();

    }


}

