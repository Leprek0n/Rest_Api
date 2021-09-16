package com.example.SpringBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/users/admin").setViewName("getAllUsers");
        registry.addViewController("/users/user").setViewName("user");
        registry.addViewController("/login").setViewName("login");

    }
}