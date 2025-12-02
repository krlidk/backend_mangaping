package com.MangaPing.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin(
    origins = {
        "http://localhost:5173",
        "http://s3-manga-ping-reactxd-123456.s3.us-east-1.amazonaws.com"
    },
    allowCredentials = "true"
)

@Configuration
public class CorsConfig implements WebMvcConfigurer{

        @Override
        public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/api/**")
                .allowedOrigins("http://s3-manga-ping-reactxd-123456.s3.us-east-1.amazonaws.com","http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false);
        }
}
