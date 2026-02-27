package com.diplomado.diplomado.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // Orígenes permitidos
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200"));
        // Métodos permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Cabeceras permitidas
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Permitir que se envíen cookies (autenticación, etc.)
        config.setAllowCredentials(true);
        // Asegurarse de permitir la cabecera 'Access-Control-Allow-Origin'
        config.addExposedHeader("Access-Control-Allow-Origin");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
