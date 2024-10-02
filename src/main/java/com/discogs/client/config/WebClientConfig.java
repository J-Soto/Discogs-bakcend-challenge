package com.discogs.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration for WebClient.
 */
@Configuration
public class WebClientConfig {

    /**
     * Provides a WebClient.Builder bean.
     *
     * @return the WebClient.Builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}