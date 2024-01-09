package com.example.gateway;

import com.example.gateway.common.FilterError;
import com.example.gateway.common.FilterPost;
import com.example.gateway.common.FilterPre;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@CrossOrigin(origins = "http://127.0.0.1:8000")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    @CrossOrigin
    public FilterError filterError() {
        return new FilterError();
    }

    @Bean
    @CrossOrigin
    public FilterPre filterPre() {
        return new FilterPre();
    }

    @Bean
    @CrossOrigin
    public FilterPost filterPost() {
        return new FilterPost();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(5120000000L);
        factory.setMaxRequestSize(5120000000L);
        return factory.createMultipartConfig();
    }
}
